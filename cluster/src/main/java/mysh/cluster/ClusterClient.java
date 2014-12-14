package mysh.cluster;

import mysh.cluster.rpc.IFaceHolder;
import mysh.cluster.rpc.thrift.RpcUtil;
import mysh.cluster.update.FilesMgr.FileType;
import mysh.cluster.update.FilesMgr.UpdateType;
import mysh.util.ExpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.DatagramSocket;
import java.net.InterfaceAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Mysh
 * @since 14-2-23 下午2:21
 */
public final class ClusterClient implements Closeable {
	private static final Logger log = LoggerFactory.getLogger(ClusterClient.class);
	private static final int CMD_SOCK_BUF = 1024 * 1024;

	private volatile boolean running = true;
	private final int cmdPort;
	private final DatagramSocket cmdSock;
	/**
	 * network interface addresses with broadcast address.
	 */
	private final Set<InterfaceAddress> bcAdds = new HashSet<>();
	private volatile IFaceHolder<IClusterService> service;


	/**
	 * it takes expensive cost to create such a client, so reuse it.
	 *
	 * @param pCmdPort cluster master node cmd port.
	 * @throws java.net.SocketException
	 */
	public ClusterClient(int pCmdPort) throws SocketException {

		this.cmdPort = pCmdPort;
		this.cmdSock = new DatagramSocket();
		this.cmdSock.setSoTimeout(ClusterNode.NETWORK_TIMEOUT);
		this.cmdSock.setReceiveBufferSize(CMD_SOCK_BUF);
		this.cmdSock.setSendBufferSize(CMD_SOCK_BUF);

		SockUtil.iterateNetworkIF(nif -> {
			for (InterfaceAddress addr : nif.getInterfaceAddresses()) {
				if (addr.getBroadcast() != null && bcAdds.add(addr))
					log.info("add addr with broadcast: " + addr);
			}
		});

		this.prepareClusterService();
	}

	/**
	 * run task in cluster.
	 *
	 * @param cUser          task define.
	 * @param task           task data.
	 * @param timeout        task execution timeout(milli-second).
	 *                       <code>0</code> represent for never timeout, even waiting for cluster getting ready.
	 *                       <code>negative</code> represent for never timeout for task execution,
	 *                       but throwing {@link ClusterExp.Unready} immediately if cluster is not ready.
	 * @param subTaskTimeout suggested subTask execution timeout,
	 *                       obeying it or not depends on the implementation of cUser.
	 * @throws mysh.cluster.ClusterExp        exceptions from cluster, generally about cluster
	 *                                        status and task status.
	 * @throws java.lang.InterruptedException current thread interrupted.
	 * @throws java.lang.Exception            other exceptions.
	 */
	public <T, ST, SR, R> R runTask(final IClusterUser<T, ST, SR, R> cUser, final T task,
	                                final int timeout, final int subTaskTimeout) throws Exception {

		if (this.service == null && timeout < 0) {
			this.prepareClusterService();
			throw new ClusterExp.Unready();
		}

		long startTime = System.currentTimeMillis();
		int leftTime;

		IFaceHolder<IClusterService> cs = this.service;
		while (this.running) {
			if (timeout == 0) leftTime = 0;
			else {
				leftTime = timeout - (int) (System.currentTimeMillis() - startTime);
				if (leftTime <= 0) throw new ClusterExp.Unready();
			}

			if (cs == null)
				cs = this.waitForClusterPreparing(startTime, leftTime);
			if (!this.running)
				break;

			try {
				return cs.getClient().runTask(cUser, task, leftTime, subTaskTimeout);
			} catch (Exception e) {
				log.debug("client run cluster task error.", e);
				if (isClusterUnready(e)) {
					if (this.service != null)
						try {
							this.service.close();
						} catch (Exception ex) {
						}
					cs = this.service = null;
					this.prepareClusterService();
					if (timeout < 0) throw new ClusterExp.Unready(e);
				} else {
					Exception et;
					if (e instanceof UndeclaredThrowableException && (et = (Exception) e.getCause()) != null)
						e = et;
					if (e instanceof InvocationTargetException && (et = (Exception) e.getCause()) != null)
						e = et;
					throw e;
				}
			}
		}
		throw new ClusterExp.ClientClosed();
	}

	/**
	 * close the client.
	 */
	public void close() {
		this.running = false;
		this.cmdSock.close();

		if (this.tPrepareClusterService != null) {
			this.tPrepareClusterService.interrupt();
			try {
				this.tPrepareClusterService.join();
			} catch (Exception e) {
			}
		}

		if (this.service != null)
			try {
				this.service.close();
			} catch (Exception e) {
			}
	}

	/**
	 * get all workers' current states.
	 *
	 * @param wsClass worker state class. if <code>null</code>, use default class.
	 */
	public <WS extends WorkerState> Map<String, WS> mgrGetWorkerStates(Class<WS> wsClass) throws Exception {
		return runTask(new MgrGetWorkerStates<>(wsClass), null, ClusterNode.NETWORK_TIMEOUT, 0);
	}

	private final MgrCancelTask cancelTaskUser = new MgrCancelTask();

	/**
	 * request cancel task by taskId.
	 */
	public void mgrCancelTask(int taskId) throws Exception {
		runTask(cancelTaskUser, taskId, ClusterNode.NETWORK_TIMEOUT, 0);
	}

	/**
	 * update cluster files.
	 *
	 * @param ctx file content.
	 */
	public void mgrUpdateFile(UpdateType updateType, FileType fileType, String fileName, byte[] ctx) throws Exception {
		runTask(new MgrFileUpdate(updateType, fileType, fileName, ctx), null, 0, 0);
	}

	private final MgrRestartCluster restartUser = new MgrRestartCluster();

	/**
	 * restart the cluster.
	 */
	public void mgrRestartCluster() throws Exception {
		runTask(restartUser, null, 20_000, 0);
	}

	/**
	 * preparing cluster service flag.
	 * use <b>AtomicBoolean</b> but not <b>volatile boolean</b> here to make sure only one thread started.
	 */
	private final AtomicBoolean isPreparingClusterService = new AtomicBoolean(false);

	private void prepareClusterService() {
		if (isPreparingClusterService.compareAndSet(false, true)) {
			tPrepareClusterService = new Thread(this.rPrepareClusterService,
							"clusterClient:prepare-cluster-service-" + System.currentTimeMillis());
			tPrepareClusterService.setDaemon(true);
			tPrepareClusterService.start();
		}
	}

	private volatile Thread tPrepareClusterService;
	private final Runnable rPrepareClusterService = new Runnable() {
		@Override
		public void run() {
			try {
				if (service != null) return;

				bcForMaster();

				// in case of multi-responses, so end the loop until sock.receive timeout exception throw.
				while (ClusterClient.this.running) {
					Cmd cmd = SockUtil.receiveCmd(cmdSock, CMD_SOCK_BUF);
					log.debug("rec cmd <<< " + cmd);
					if (service == null && cmd.action == Cmd.Action.I_AM_THE_MASTER) {
						try {
							service = RpcUtil.getClient(IClusterService.class, cmd.ipAddr, cmd.masterPort, 0, null);
						} catch (Exception e) {
							log.error("connect to master service error.", e);
						}
					}
				}
			} catch (Exception e) {
				if (!(e instanceof SocketTimeoutException))
					log.error("receive master response error.", e);
			} finally {
				isPreparingClusterService.set(false);
			}
		}

		/**
		 * broadcast {@link Cmd.Action#WHO_IS_THE_MASTER_BY_CLIENT} for look up master.
		 */
		private void bcForMaster() {
			for (InterfaceAddress addr : bcAdds) {
				try {
					Cmd cmd = new Cmd(Cmd.Action.WHO_IS_THE_MASTER_BY_CLIENT, null, 0,
									addr.getAddress().getHostAddress(), addr.getNetworkPrefixLength(),
									cmdSock.getLocalPort(), 0);
					SockUtil.sendCmd(cmdSock, cmd, addr.getBroadcast(), cmdPort);
					log.debug("bc cmd >>> " + cmd);
				} catch (IOException e) {
					log.error("broadcast for master error, on interface: " + addr, e);
				}
			}
		}
	};

	/**
	 * @param startTime task submit time.
	 * @param timeout   waiting for cluster ready timeout(milli-second).
	 *                  <code>0</code> represent for never timeout.
	 *                  <code>negative</code> throws {@link ClusterExp.Unready} immediately .
	 */
	private IFaceHolder<IClusterService> waitForClusterPreparing(
					final long startTime, final int timeout) throws ClusterExp.Unready, InterruptedException {
		if (timeout < 0) throw new ClusterExp.Unready();

		IFaceHolder<IClusterService> cs = null;
		while (this.running && (cs = this.service) == null) {
			this.prepareClusterService();
			Thread.sleep(10);
			if (timeout > 0 && timeout <= System.currentTimeMillis() - startTime)
				throw new ClusterExp.Unready();
		}

		return cs;
	}

	private static boolean isClusterUnready(Exception e) {
		return ClusterNode.isNodeUnavailable(e)
						||
						ExpUtil.isCausedBy(e, ClusterExp.NotMaster.class, ClusterExp.NoWorkers.class,
										InterruptedException.class) != null
						;
	}
}