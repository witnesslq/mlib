package mysh.crawler2;

import mysh.net.httpclient.HttpClientAssist;
import mysh.net.httpclient.HttpClientConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mysh
 * @since 2014/9/24 12:50
 */
public class Crawler {
	private final Logger log;

	private final HttpClientAssist hca;
	private final CrawlerSeed seed;
	private final CrawlerRepo repo;
	private final String name;

	private ThreadPoolExecutor e;
	private final BlockingQueue<Runnable> wq = new LinkedBlockingQueue<>();
	private final Queue<String> unhandledUrls = new ConcurrentLinkedQueue<>();

	public Crawler(CrawlerSeed seed, HttpClientConfig hcc) {
		Objects.requireNonNull(seed, "need seed");
		Objects.requireNonNull(seed.getRepo(), "need crawler repository");
		Objects.requireNonNull(hcc, "need http client config");

		this.seed = seed;
		this.repo = seed.getRepo();

		String seedName = seed.getClass().getName();
		int dotIdx = seedName.lastIndexOf('.');
		this.name = dotIdx > -1 ? seedName.substring(dotIdx + 1) : seedName;

		this.log = LoggerFactory.getLogger("Crawler[" + name + "]");
		this.hca = new HttpClientAssist(hcc);
	}

	private void startAutoStopChk() {
		new Thread(name + "-autoStopChk") {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(5000);
						log.debug("wq.size=" + wq.size() + ", e.actCount=" + e.getActiveCount());
						if (wq.size() == 0 && e.getActiveCount() == 0) {
							Crawler.this.stop();
							return;
						}
					} catch (Exception ex) {
						log.error("error on stop check", ex);
					}
				}
			}
		}.start();
	}

	public void start() {
		int threadSize = seed.requestThreadSize();
		int maxThreadSize = Runtime.getRuntime().availableProcessors() * 50;
		threadSize = Math.min(Math.max(1, threadSize), maxThreadSize);
		log.info("crawler[" + name + "] max thread size:" + threadSize);

		AtomicInteger threadCount = new AtomicInteger(0);
		e = new ThreadPoolExecutor(threadSize, threadSize, 2, TimeUnit.SECONDS, wq,
						r -> {
							Thread t = new Thread(r, name + "-" + threadCount.incrementAndGet());
							t.setDaemon(true);
							return t;
						},
						(r, executor) -> unhandledUrls.add(((Worker) r).url)
		);
		e.allowCoreThreadTimeOut(true);

		log.info("crawler [" + this.name + "] started.");

		seed.getSeeds().stream()
						.forEach(url -> e.execute(new Worker(url)));

		if (seed.autoStop())
			startAutoStopChk();
	}

	public void stop() throws InterruptedException, IOException {
		try {
			e.shutdown();
			e.awaitTermination(1, TimeUnit.MINUTES);
		} finally {
			repo.unhandledSeeds(unhandledUrls);
			hca.close();
		}
	}

	private static final Pattern httpExp =
					Pattern.compile("[Hh][Tt][Tt][Pp][Ss]?://[^\"'<>\\s#]+");
	private static final Pattern hrefValueExp =
					Pattern.compile("[Hh][Rr][Ee][Ff][\\s]*=[\\s]*[\"']([^\"'#]*)");

	private class Worker implements Runnable {
		private String url;

		public Worker(String url) {
			this.url = url;
		}

		@Override
		public void run() {
			try (HttpClientAssist.UrlEntity ue = hca.access(url)) {
				log.debug("on accessing page: " + ue.getCurrentURL());
				repo.put(url);
				repo.put(ue.getCurrentURL());
				seed.onGet(ue);

				if (ue.isHtml()) {
					distillUrl(ue.getCurrentURL(), ue.getEntityStr())
									.stream()
									.filter(url -> !repo.contains(url) && seed.isAcceptable(url))
									.forEach(url -> e.execute(new Worker(url)));
				}
			} catch (SocketTimeoutException | ConnectTimeoutException ex) {
				e.execute(this);
				log.debug(ex.toString());
			} catch (Exception ex) {
				log.error("on error handling url: " + this.url, ex);
			}
		}

		private Set<String> distillUrl(String pageUrl, String pageContent) {

			Set<String> urls = new HashSet<String>();

			try {
				// 查找 http 开头的数据
				Matcher httpExpMatcher = httpExp.matcher(pageContent);
				while (httpExpMatcher.find()) {
					urls.add(HttpClientAssist.getShortURL(httpExpMatcher.group()));
				}

				// 取得当前根目录
				String currentRoot = pageUrl;
				int sepIndex = -1;
				// 取 ? 前面的串
				if ((sepIndex = currentRoot.lastIndexOf('?')) != -1) {
					currentRoot = currentRoot.substring(0, sepIndex);
				}
				// 取 # 前面的串
				if ((sepIndex = currentRoot.lastIndexOf('#')) != -1) {
					currentRoot = currentRoot.substring(0, sepIndex);
				}
				// 取根目录
				currentRoot = currentRoot.substring(0, currentRoot.lastIndexOf('/') + 1);

				// 查找 href 指向的地址
				Matcher hrefValueMatcher = hrefValueExp.matcher(pageContent);
				String tValue = "";
				while (hrefValueMatcher.find()) {
					tValue = hrefValueMatcher.group(1);
					if (tValue == null || tValue.length() == 0 || tValue.startsWith("#")) {
						continue;
					} else if (tValue.startsWith("http://")) {
						urls.add(tValue);
					} else if (tValue.startsWith("/")) {
						urls.add(currentRoot.substring(0, currentRoot.indexOf("/", 8)) + tValue);
					} else {
						urls.add(HttpClientAssist.getShortURL(currentRoot + tValue));
					}
				}
			} catch (Exception e) {
				log.error("分析页面链接时异常: " + pageUrl + "  " + e);
			}

			return urls;
		}
	}
}
