/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *
 * Created on 2010-1-2, 21:55:47
 */
package mysh.dev.tcpportscanner;

import javax.swing.*;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author zzx
 */
public class TcpPortScannerUI extends javax.swing.JFrame implements Observer {

	/** Creates new form Main */
	public TcpPortScannerUI() {
		initComponents();
		this.setLocationRelativeTo(null);
	}

	/**
	 * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		show = new javax.swing.JTextPane();
		jLabel1 = new javax.swing.JLabel();
		host = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		portStart = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		portEnd = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		concurrentThread = new javax.swing.JTextField();
		start = new javax.swing.JButton();
		stop = new javax.swing.JButton();
		jLabel5 = new javax.swing.JLabel();
		timeout = new javax.swing.JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("TCPPortScanner");
		setResizable(false);

		show.setEditable(false);
		show.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		show.setText("");
		show.setMargin(new java.awt.Insets(0, 0, 0, 0));
		jScrollPane1.setViewportView(show);

		jLabel1.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		jLabel1.setText("扫描主机：");

		host.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		host.setToolTipText("主机域名 或 IP地址");

		jLabel2.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		jLabel2.setText("端口范围：");

		portStart.setColumns(5);
		portStart.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		portStart.setText("1");
		portStart.setToolTipText("1 - 65535");

		jLabel3.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		jLabel3.setText("-");

		portEnd.setColumns(5);
		portEnd.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		portEnd.setText("65535");
		portEnd.setToolTipText("1 - 65535");

		jLabel4.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		jLabel4.setText("并发线程数：");

		concurrentThread.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		concurrentThread.setText("1000");
		concurrentThread.setToolTipText("1 - 1000");

		start.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		start.setText("开始");
		start.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				startActionPerformed(evt);
			}
		});

		stop.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		stop.setText("停止");
		stop.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				stopActionPerformed(evt);
			}
		});

		jLabel5.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		jLabel5.setText("超时(ms)：");

		timeout.setFont(new java.awt.Font("微软雅黑", 0, 14)); // NOI18N
		timeout.setText("500");

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout
				.setHorizontalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																layout
																		.createSequentialGroup()
																		.addComponent(
																				jLabel1)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				host,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				322,
																				Short.MAX_VALUE))
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																layout
																		.createSequentialGroup()
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.TRAILING,
																								false)
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel4)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												concurrentThread))
																						.addGroup(
																								javax.swing.GroupLayout.Alignment.LEADING,
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel2)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												portStart,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												56,
																												javax.swing.GroupLayout.PREFERRED_SIZE)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												jLabel3)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																										.addComponent(
																												portEnd,
																												javax.swing.GroupLayout.PREFERRED_SIZE,
																												56,
																												javax.swing.GroupLayout.PREFERRED_SIZE)))
																		.addGap(
																				18,
																				18,
																				18)
																		.addGroup(
																				layout
																						.createParallelGroup(
																								javax.swing.GroupLayout.Alignment.LEADING)
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												start,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												74,
																												Short.MAX_VALUE)
																										.addGap(
																												18,
																												18,
																												18)
																										.addComponent(
																												stop,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												74,
																												Short.MAX_VALUE))
																						.addGroup(
																								layout
																										.createSequentialGroup()
																										.addComponent(
																												jLabel5)
																										.addPreferredGap(
																												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																										.addComponent(
																												timeout,
																												javax.swing.GroupLayout.DEFAULT_SIZE,
																												91,
																												Short.MAX_VALUE)))))
										.addContainerGap())
						.addComponent(jScrollPane1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 416,
								Short.MAX_VALUE));
		layout
				.setVerticalGroup(layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jLabel1)
														.addComponent(
																host,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																jLabel2)
														.addComponent(
																portStart,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jLabel3)
														.addComponent(
																portEnd,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																timeout,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																jLabel5))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																false)
														.addComponent(
																stop,
																0,
																0,
																Short.MAX_VALUE)
														.addComponent(
																start,
																0,
																0,
																Short.MAX_VALUE)
														.addGroup(
																layout
																		.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																		.addComponent(
																				jLabel4)
																		.addComponent(
																				concurrentThread)))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jScrollPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												229,
												Short.MAX_VALUE)));

		pack();
	}// </editor-fold>

	private void startActionPerformed(java.awt.event.ActionEvent evt) {
		String host = this.host.getText();

		int begin, end, timeout, concurrentThread;
		try {
			begin = Integer.parseInt(this.portStart.getText());
			end = Integer.parseInt(this.portEnd.getText());

			if (begin > 65535 || begin < 1 || end > 65535 || end < 1 || begin > end)
				throw new Exception();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "端口输入错误");
			return;
		}

		try {
			timeout = Integer.parseInt(this.timeout.getText());
			if (timeout < 1)
				throw new Exception();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "超时输入错误");
			return;
		}

		try {
			concurrentThread = Integer.parseInt(this.concurrentThread.getText());
			if (concurrentThread > 1000)
				throw new Exception("并发线程数不能超过 1000");
			if (concurrentThread < 1)
				throw new Exception("并发线程数不能小于 1");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "并发线程数输入错误：\r\n" + e.getMessage());
			return;
		}

		if (this.task != null) {
			stopTask();
		}

		try {
			this.task = new ScanTask().setHost(host).setStartPort(begin).setEndPort(end)
					.setTimeout(timeout).setConcurrentThread(concurrentThread);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(this, "域名无法解析");
			return;
		}
		this.task.addObserver(this);
		new Thread(this.task).start();
	}

	private void stopActionPerformed(java.awt.event.ActionEvent evt) {
		stopTask();
	}

	private void stopTask(){
		if(this.task == null) return;
		this.task.stop();
		this.task.removeObserver(this);
		System.gc();
	}
	
	/**
	 * @param args
	 *               the command line arguments
	 */
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(TcpPortScannerUI.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			Logger.getLogger(TcpPortScannerUI.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			Logger.getLogger(TcpPortScannerUI.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnsupportedLookAndFeelException ex) {
			Logger.getLogger(TcpPortScannerUI.class.getName()).log(Level.SEVERE, null, ex);
		}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new TcpPortScannerUI().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify
	private javax.swing.JTextField concurrentThread;
	private javax.swing.JTextField host;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextField portEnd;
	private javax.swing.JTextField portStart;
	private javax.swing.JTextPane show;
	private javax.swing.JButton start;
	private javax.swing.JButton stop;
	private javax.swing.JTextField timeout;
	// End of variables declaration

	private ScanTask task;

	@Override
	public void update(String state) {
		synchronized(this){
			this.show.setText(state);
		}
	}

}
