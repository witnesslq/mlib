/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysh.dev.codegen.ui;

import mysh.codegen.CodeUtil;
import mysh.util.UIs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * @author Allen
 */
public class NameConvert extends javax.swing.JFrame {
	private static final Logger log = LoggerFactory.getLogger(NameConvert.class);

	private static String lineSep = System.getProperty("line.separator");

	/**
	 * Creates new form NameConvert
	 */
	public NameConvert() {
		initComponents();
	}

	private void convertUnderLine() {
		try {
			String text = this.underlineName.getText().trim();
			if (text.length() == 0) return;

			String[] lines = text.split("[\r\n]+");
			StringBuilder hump = new StringBuilder();
			for (String line : lines) {
				hump.append(CodeUtil.underline2camel(line));
				hump.append(lineSep);
			}
			SwingUtilities.invokeLater(() -> {
				humpName.setText(hump.toString());
			});
		} catch (Exception e) {
			log.error("to underline error.", e);
		}
	}

	private void convertHump() {
		try {
			String text = this.humpName.getText();
			if (text.length() == 0) return;

			String[] lines = text.split("[\r\n]+");
			StringBuilder underline = new StringBuilder();
			for (String line : lines) {
				underline.append(CodeUtil.camel2underline(line));
				underline.append(lineSep);
			}
			SwingUtilities.invokeLater(() -> {
				underlineName.setText(underline.toString());
			});
		} catch (Exception e) {
			log.error("to hump error.", e);
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        underlineName = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        humpName = new javax.swing.JTextArea();
        under2humpBtn = new javax.swing.JButton();
        hump2underBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("命名变换");
        setFont(new java.awt.Font("Microsoft YaHei UI", 0, 12)); // NOI18N

        jPanel3.setFont(jPanel3.getFont().deriveFont(jPanel3.getFont().getSize()+2f));

        jScrollPane3.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("下划线名"));
        jScrollPane3.setFont(jScrollPane3.getFont());

        underlineName.setColumns(10);
        underlineName.setFont(new java.awt.Font("Droid Sans Mono", 0, 13)); // NOI18N
        underlineName.setRows(5);
        jScrollPane3.setViewportView(underlineName);

        jScrollPane4.setViewportBorder(javax.swing.BorderFactory.createTitledBorder("驼峰式名"));
        jScrollPane4.setFont(jScrollPane4.getFont());

        humpName.setColumns(10);
        humpName.setFont(new java.awt.Font("Droid Sans Mono", 0, 13)); // NOI18N
        humpName.setRows(5);
        jScrollPane4.setViewportView(humpName);

        under2humpBtn.setText("->");
        under2humpBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                under2humpBtnActionPerformed(evt);
            }
        });

        hump2underBtn.setText("<-");
        hump2underBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hump2underBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(under2humpBtn)
                    .addComponent(hump2underBtn))
                .addGap(6, 6, 6)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(under2humpBtn)
                .addGap(10, 10, 10)
                .addComponent(hump2underBtn)
                .addContainerGap(155, Short.MAX_VALUE))
            .addComponent(jScrollPane3)
            .addComponent(jScrollPane4)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

	private void under2humpBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_under2humpBtnActionPerformed
		this.convertUnderLine();
	}//GEN-LAST:event_under2humpBtnActionPerformed

	private void hump2underBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hump2underBtnActionPerformed
		this.convertHump();
	}//GEN-LAST:event_hump2underBtnActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
	      /* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(NameConvert.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(NameConvert.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(NameConvert.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(NameConvert.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		UIs.resetFont(null);

        /* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				NameConvert frame = new NameConvert();
				frame.setSize(750, 550);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton hump2underBtn;
    private javax.swing.JTextArea humpName;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton under2humpBtn;
    private javax.swing.JTextArea underlineName;
    // End of variables declaration//GEN-END:variables
}
