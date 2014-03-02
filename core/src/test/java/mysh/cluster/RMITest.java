package mysh.cluster;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Mysh
 * @since 14-1-28 下午5:26
 */
@Ignore
public class RMITest implements Serializable {
	private static final Logger log = LoggerFactory.getLogger(RMITest.class);
	private static final long serialVersionUID = -2777633205201793307L;

	static {
//		http://docs.oracle.com/javase/7/docs/technotes/guides/rmi/sunrmiproperties.html
//		System.setProperty("sun.rmi.transport.tcp.responseTimeout", "10000");
	}

	public static interface RI extends Remote {
		int getValue(CustomObj obj) throws RemoteException;
	}

	public static interface CustomObj extends Serializable {
		int getValue();
	}

	public static class RIImpl implements RI {
		int v = 1;

		@Override
		public int getValue(CustomObj obj) {
			System.out.println("RIImpl getValue");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return v + obj.getValue();
		}
	}

	public static interface PureObj extends Remote, Serializable {
		int getValue();
	}

	public static void main(String[] args) throws InterruptedException, RemoteException, AlreadyBoundException {
		new RMITest().server();
	}

	@Test
	public void server() throws RemoteException, AlreadyBoundException, InterruptedException {
		int port = 8030;
		Registry registry = LocateRegistry.createRegistry(port);

		RIImpl ro = new RIImpl();
		Remote expRO = UnicastRemoteObject.exportObject(ro, port + 1);
		registry.bind("ro", expRO);

		registry.bind("po", (PureObj) () -> {
			System.out.println("PureObj getValue");
			return 1;
		});

		int i = 1;
		while (true) {
			Thread.sleep(1_000);
			ro.v = i++;
		}
	}

	@Test
	public void clientRemoteObj() throws RemoteException, NotBoundException, InterruptedException {
		int port = 8030;
		Registry registry = LocateRegistry.getRegistry("mysh", port, ClusterNode.clientSockFact);


		try {
			RI riClient = (RI) registry.lookup("ro");
			while (true) {
				Thread.sleep(10_000);
				log.info("invoke remote method...");
				System.out.println(riClient.getValue(() -> 10000));
//			Thread.sleep(120_000);
			}
		} catch (Exception e) {
			log.error("invoke error.", e);
		}
	}

	@Test
	public void clientPureObj() throws RemoteException, NotBoundException {
		int port = 8030;
		Registry registry = LocateRegistry.getRegistry("192.168.1.100", port);
		PureObj po = (PureObj) registry.lookup("po");

		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println(po.getValue());
		}
	}

}