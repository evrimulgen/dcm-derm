/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.form.dialogs;

import in.raster.mayam.context.ApplicationContext;
import in.raster.mayam.form.ViewTrackFrame;
import in.raster.mayam.models.TrackingModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mariano
 */
public class TrackingViewDialog extends javax.swing.JDialog {

    private TrackingModel tracking = null;
    private String localPatientId = null;
    /**
     * Creates new form StudyListDialog
     */
    public TrackingViewDialog(java.awt.Frame parent, boolean modal, String localPatientId) {
        super(parent, modal);
        initComponents();
        TrackingDetailTableModel model = new TrackingDetailTableModel();
        ArrayList<TrackingModel> list = ApplicationContext.databaseRef.listAllTrackingByPatientId(localPatientId);
        if (list.isEmpty()) {
            this.viewButton.setEnabled(false);
        }
        model.setData(list);
        this.localPatientId = localPatientId;
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

        cancel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        viewButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(ApplicationContext.currentBundle.getString("TrackingDialog.title")); // NOI18N
        setResizable(false);

        cancel.setText(ApplicationContext.currentBundle.getString("WadoInformation.cancelButton.text")); // NOI18N
        cancel.setPreferredSize(new java.awt.Dimension(92, 29));
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

        viewButton.setText(ApplicationContext.currentBundle.getString("TrackingView.title")); // NOI18N
        viewButton.setPreferredSize(new java.awt.Dimension(92, 29));
        viewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(240, 240, 240)
                .addComponent(viewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(viewButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelActionPerformed
         tracking = null;
         setVisible(false);
    }//GEN-LAST:event_cancelActionPerformed

    private void viewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewButtonActionPerformed
        int selection = this.jTable1.getSelectedRow();
        if (selection != -1) {
           tracking = (TrackingModel) jTable1.getModel().getValueAt(selection, 3);
           setVisible(false);
           ViewTrackFrame trackFrame = new ViewTrackFrame(localPatientId, tracking);
           trackFrame.setLocationRelativeTo(this);
//           trackFrame.setAlwaysOnTop(true);
           trackFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,ApplicationContext.currentBundle.getString("Alert.trackingNotSelected"),
                ApplicationContext.currentBundle.getString("Alert.warning.text"),JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_viewButtonActionPerformed

    public TrackingModel getSelectedTracking() {
        return tracking;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton viewButton;
    // End of variables declaration//GEN-END:variables

    private class TrackingDetailTableModel extends AbstractTableModel {

        String[] columnNames = {ApplicationContext.currentBundle.getString("CreateDicomFrame.text_3"),
        ApplicationContext.currentBundle.getString("TrackingDialog.description"),
        ApplicationContext.currentBundle.getString("TrackingDialog.creationdate")};
        Class columnTypes[] = new Class[]{String.class, String.class, String.class};
        ArrayList<TrackingModel> tracks = new ArrayList<TrackingModel>();
        
        
        public void setData(ArrayList<TrackingModel> data) {
            this.tracks = data;
        }
        
        @Override
        public int getRowCount() {
            if (tracks != null) {
                return tracks.size();
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
            TrackingModel tracking = (TrackingModel) tracks.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return tracking.getPatientId();
                case 1:
                    return tracking.getDescription();
                case 2:
                    return tracking.getCreationDate();
                case 3:
                    return tracking;
            }
            return "";
        }
    }
    
}
