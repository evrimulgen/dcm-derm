/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.form.dialogs;

import in.raster.mayam.context.ApplicationContext;
import in.raster.mayam.models.StudyModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mariano
 */
public class StudyListDialog extends javax.swing.JDialog {

    private StudyModel study = null;
    
    /**
     * Creates new form StudyListDialog
     */
    public StudyListDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        StudyDetailTableModel model = new StudyDetailTableModel();
        model.setData(ApplicationContext.databaseRef.listAllLocalStudies());
        jTable1.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ok = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(ApplicationContext.currentBundle.getString("StudyDialog.title")); // NOI18N
        setResizable(false);

        ok.setText(ApplicationContext.currentBundle.getString("WadoInformation.okButton.text")); // NOI18N
        ok.setPreferredSize(new java.awt.Dimension(82, 29));
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        cancel.setText(ApplicationContext.currentBundle.getString("WadoInformation.cancelButton.text")); // NOI18N
        cancel.setPreferredSize(new java.awt.Dimension(82, 29));
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setColumnSelectionAllowed(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(206, 206, 206)
                .addComponent(ok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111)
                .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(207, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okActionPerformed
        int selection = this.jTable1.getSelectedRow();
        if (selection != -1) {
           study = (StudyModel) jTable1.getModel().getValueAt(selection, 3);
           setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this,ApplicationContext.currentBundle.getString("Alert.studyNotSelected"),
                ApplicationContext.currentBundle.getString("Alert.warning.text"),JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_okActionPerformed

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
         study = null;
         setVisible(false);
    }//GEN-LAST:event_cancelActionPerformed

    public StudyModel getSelectedStudy() {
        return study;
    }
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
            java.util.logging.Logger.getLogger(StudyListDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudyListDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudyListDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudyListDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                StudyListDialog dialog = new StudyListDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton cancel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton ok;
    // End of variables declaration//GEN-END:variables

    private class StudyDetailTableModel extends AbstractTableModel {

        String[] columnNames = {ApplicationContext.currentBundle.getString("CreateDicomFrame.text_7"),
        ApplicationContext.currentBundle.getString("CreateDicomFrame.text_3"),
        ApplicationContext.currentBundle.getString("CreateDicomFrame.text_2")};
        Class columnTypes[] = new Class[]{String.class, String.class, String.class};
        ArrayList<StudyModel> studies = new ArrayList<>();
        
        
        public void setData(ArrayList<StudyModel> data) {
            this.studies = data;
        }
        
        @Override
        public int getRowCount() {
            if (studies != null) {
                return studies.size();
            } else {
                return 0;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
        
        @Override
        public int getColumnCount() {
             return columnNames.length;
        }

        @Override
        public Class getColumnClass(int c) {
            return columnTypes[c];
        }
        
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
        
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (rowIndex < 0 || rowIndex >= getRowCount()) {
                return "";
            }
            StudyModel study = (StudyModel) studies.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return study.getStudyUID();
                case 1:
                    return study.getPatientId();
                case 2:
                    return study.getPatientName();
                case 3:
                    return study;
            }
            return "";
        }
    }
    
}
