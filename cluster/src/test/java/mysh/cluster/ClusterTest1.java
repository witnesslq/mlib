package mysh.cluster;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Mysh
 * @since 14-2-25 下午11:26
 */
@Ignore
public class ClusterTest1 {

	private static final int cmdPort = 8030;

	@Test
	public void t1() throws Throwable {
		ClusterClient c = new ClusterClient(cmdPort);
		final String r = c.runTask(null, new Cu2(), "m y s h", 100000, 0);
		System.out.println(r);
	}

	@Test
	public void t2() throws Throwable {
		ClusterClient c = new ClusterClient(cmdPort);
		final String r = c.runTask(null, new Cu3Perm(), null, 10000, 0);
		System.out.println(r);
	}

	@Test
	public void t3() throws Throwable {
		ClusterClient c = new ClusterClient(cmdPort);
		final String r = c.runTask("cu4", new Cu4Perm(), null, 10000, 0);
		System.out.println(r);
	}

}
