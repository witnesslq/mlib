/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysh.dev.codegen.ui;

import mysh.codegen.CodeUtil;
import mysh.util.UIs;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author hzzhangzhixian
 */
public class BeanPropCopy2 extends javax.swing.JFrame {

    /**
     * Creates new form FieldGetSet
     */
    public BeanPropCopy2() {
        initComponents();

        DocumentListener genTrigger = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                genCode();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                genCode();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                genCode();
            }
        };
        this.dstVarTxt.getDocument().addDocumentListener(genTrigger);
        this.srcVarTxt.getDocument().addDocumentListener(genTrigger);
        this.fieldDefineTxt.getDocument().addDocumentListener(genTrigger);
    }

    private void genCode() {
        this.codeGenTxt.setText(CodeUtil.genPropCopy(dstVarTxt.getText(), srcVarTxt.getText(), fieldDefineTxt.getText()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dstVarTxt = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        srcVarTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        fieldDefineTxt = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        codeGenTxt = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        dstVarTxt.setToolTipText("dstVar");

        jLabel1.setText(".setProp(");

        srcVarTxt.setToolTipText("srcVar");

        jLabel2.setText(".getProp())");

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        fieldDefineTxt.setColumns(20);
        fieldDefineTxt.setRows(5);
        fieldDefineTxt.setText("\n    /**\n     * 商品编辑表id\n     */\n    @AnnonOfField(desc = \"商品编辑表id\", dbFieldName = \"edit_goods_id\", type = \"BIGINT\", primary = true)\n    private Long editGoodsId;");
        jScrollPane1.setViewportView(fieldDefineTxt);

        jSplitPane1.setTopComponent(jScrollPane1);

        codeGenTxt.setEditable(false);
        codeGenTxt.setColumns(20);
        codeGenTxt.setRows(5);
        codeGenTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                codeGenTxtMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(codeGenTxt);

        jSplitPane1.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(dstVarTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(srcVarTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap())
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dstVarTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(srcVarTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void codeGenTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_codeGenTxtMouseClicked
        UIs.copyToSystemClipboard(codeGenTxt.getText());
    }//GEN-LAST:event_codeGenTxtMouseClicked

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
            java.util.logging.Logger.getLogger(BeanPropCopy2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BeanPropCopy2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BeanPropCopy2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BeanPropCopy2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BeanPropCopy2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea codeGenTxt;
    private javax.swing.JTextField dstVarTxt;
    private javax.swing.JTextArea fieldDefineTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField srcVarTxt;
    // End of variables declaration//GEN-END:variables
}