/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dcm.derm.human.viewer;

import in.raster.mayam.context.ApplicationContext;

/**
 *
 * @author Mariano
 */
public class BodyChooser extends javax.swing.JDialog {

    /**
     * Creates new form BodyChooser
     */
    public BodyChooser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        maleButton = new javax.swing.JButton();
        femaleButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        maleButton.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        maleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/in/raster/mayam/form/images/male1.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("in/raster/mayam/form/i18n/Bundle",ApplicationContext.currentLocale); // NOI18N
        maleButton.setText(bundle.getString("BodyChooser.maleButton.text")); // NOI18N
        maleButton.setBorderPainted(false);
        maleButton.setContentAreaFilled(false);
        maleButton.setFocusPainted(false);
        maleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        maleButton.setIconTextGap(5);
        maleButton.setMaximumSize(new java.awt.Dimension(69, 59));
        maleButton.setMinimumSize(new java.awt.Dimension(69, 59));
        maleButton.setPreferredSize(new java.awt.Dimension(52, 50));
        maleButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/in/raster/mayam/form/images/male2.png"))); // NOI18N
        maleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        maleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maleButtonActionPerformed(evt);
            }
        });

        femaleButton.setFont(new java.awt.Font("Lucida Grande", 1, 12)); // NOI18N
        femaleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/in/raster/mayam/form/images/fem1.png"))); // NOI18N
        femaleButton.setText(bundle.getString("BodyChooser.femaleButton.text")); // NOI18N
        femaleButton.setBorderPainted(false);
        femaleButton.setContentAreaFilled(false);
        femaleButton.setFocusPainted(false);
        femaleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        femaleButton.setIconTextGap(5);
        femaleButton.setMaximumSize(new java.awt.Dimension(69, 59));
        femaleButton.setMinimumSize(new java.awt.Dimension(69, 59));
        femaleButton.setPreferredSize(new java.awt.Dimension(52, 50));
        femaleButton.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/in/raster/mayam/form/images/fem2.png"))); // NOI18N
        femaleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        femaleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                femaleButtonActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(maleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(femaleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addComponent(maleButton, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
            .addComponent(femaleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void maleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maleButtonActionPerformed
        HumanViewer hViewer = new HumanViewer("M");
        this.setVisible(false);
        hViewer.setVisible(true);
    }//GEN-LAST:event_maleButtonActionPerformed

    private void femaleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femaleButtonActionPerformed
        HumanViewer hViewer = new HumanViewer("F");
        this.setVisible(false);
        hViewer.setVisible(true);
    }//GEN-LAST:event_femaleButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BodyChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BodyChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BodyChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BodyChooser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                BodyChooser dialog = new BodyChooser(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton femaleButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton maleButton;
    // End of variables declaration//GEN-END:variables
}