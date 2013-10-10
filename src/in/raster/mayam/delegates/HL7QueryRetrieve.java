package in.raster.mayam.delegates;

import in.raster.mayam.context.ApplicationContext;
import in.raster.mayam.form.CreateDicomFrame;
import in.raster.mayam.models.EMRServerModel;
import in.raster.mayam.models.EMRStudyModel;
import in.raster.mayam.models.StudyModel;
import in.raster.mayam.models.table.EMRServerTableModel;
import in.raster.mayam.models.table.renderer.CellRenderer;
import in.raster.mayam.param.QueryParam;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


/**
 *
 * @author mariano
 */
public class HL7QueryRetrieve extends javax.swing.JFrame {
   
    private QueryParam queryParam = new QueryParam();
    private CreateDicomFrame parent;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel accessionLabel;
    private javax.swing.JSpinner birthDateSpinner;
    private javax.swing.JCheckBox dobLabel;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.ButtonGroup modalityGroup;
    private javax.swing.JLabel patientIDLabel;
    private javax.swing.JTextField patientIDText;
    private javax.swing.JLabel patientNameLabel;
    private javax.swing.JTextField patientNameText;
    private javax.swing.JButton queryButton;
    private javax.swing.JLabel queryFilterLabel;
    private javax.swing.JButton retrieveButton;
    private javax.swing.ButtonGroup searchDaysGroup;
    private javax.swing.JTable serverListTable;
    private javax.swing.JScrollPane serverlistScroll;
    private javax.swing.JTable studyListTable;
    private javax.swing.JButton verifyButton;
    // End of variables declaration//GEN-END:variables
    
 
    public HL7QueryRetrieve(CreateDicomFrame parent) {
        this.parent = parent;        
        initComponents();
        refreshModels();
    }
    
   /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        searchDaysGroup = new javax.swing.ButtonGroup();
        modalityGroup = new javax.swing.ButtonGroup();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        birthDateSpinner = new javax.swing.JSpinner();
        patientNameLabel = new javax.swing.JLabel();
        patientNameText = new javax.swing.JTextField();
        patientIDLabel = new javax.swing.JLabel();
        patientIDText = new javax.swing.JTextField();
        accessionLabel = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        dobLabel = new javax.swing.JCheckBox();
        serverlistScroll = new javax.swing.JScrollPane();
        serverListTable = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        verifyButton = new javax.swing.JButton();
        queryButton = new javax.swing.JButton();
        retrieveButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        studyListTable = new javax.swing.JTable();
        headerLabel = new javax.swing.JLabel();
        queryFilterLabel = new javax.swing.JLabel();

        setTitle(ApplicationContext.currentBundle.getString("HL7QueryRetrieve.title_1")); // NOI18N
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/in/raster/mayam/form/images/fav_mayam.png")));

        jPanel9.setMaximumSize(new java.awt.Dimension(1200, 1400));

        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        birthDateSpinner.setEnabled(false);

        patientNameLabel.setText(ApplicationContext.currentBundle.getString("HL7QueryRetrieve.patientNameLabel.text")); // NOI18N

        patientIDLabel.setText(ApplicationContext.currentBundle.getString("HL7QueryRetrieve.patientIDLabel.text")); // NOI18N

        accessionLabel.setText(ApplicationContext.currentBundle.getString("HL7QueryRetrieve.accessionLabel.text")); // NOI18N

        dobLabel.setText(ApplicationContext.currentBundle.getString("HL7QueryRetrieve.dobLabel.text")); // NOI18N
        dobLabel.setName("Date Of Birth"); // NOI18N
        dobLabel.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dobLabelItemStateChanged(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel10Layout = new org.jdesktop.layout.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createSequentialGroup()
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel10Layout.createSequentialGroup()
                        .add(31, 31, 31)
                        .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(patientIDLabel)
                            .add(patientNameLabel)
                            .add(accessionLabel)))
                    .add(jPanel10Layout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(dobLabel)))
                .add(18, 18, 18)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(patientIDText)
                    .add(birthDateSpinner, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                    .add(jTextField3)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, patientNameText))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel10Layout.createSequentialGroup()
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(patientNameText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(patientNameLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(patientIDText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(patientIDLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(accessionLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(dobLabel)
                    .add(birthDateSpinner))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout jPanel7Layout = new org.jdesktop.layout.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(122, Short.MAX_VALUE)
                .add(jPanel10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(141, 141, 141))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jPanel10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        serverListTable.setModel(new EMRServerTableModel());
        serverListTable.setDefaultRenderer(Object.class, new CellRenderer());
        serverListTable.getTableHeader().setPreferredSize(new Dimension(this.getWidth(), 25));
        Font ff=new Font("Lucida Grande",Font.BOLD,12);
        serverListTable.getTableHeader().setFont(ff);
        serverListTable.setRowHeight(20);
        serverListTable.getTableHeader().setForeground(new Color(255,138,0));
        serverListTable.getTableHeader().setBackground(new Color(0,0,0));
        serverlistScroll.setViewportView(serverListTable);

        verifyButton.setText(ApplicationContext.currentBundle.getString("Preferences.servers.verifyButton.text")); // NOI18N

        queryButton.setText(ApplicationContext.currentBundle.getString("MainScreen.searchButton.text")); // NOI18N
        queryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queryButtonActionPerformed(evt);
            }
        });

        retrieveButton.setText(ApplicationContext.currentBundle.getString("MainScreen.importMenuItem.text")); // NOI18N
        retrieveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                retrieveButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel8Layout = new org.jdesktop.layout.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createSequentialGroup()
                .add(verifyButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(queryButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(retrieveButton)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(verifyButton)
                .add(queryButton)
                .add(retrieveButton))
        );

        studyListTable.setModel(new EMRStudyModel());
        studyListTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        studyListTable.setDefaultRenderer(Object.class, new CellRenderer());
        studyListTable.getTableHeader().setPreferredSize(new Dimension(this.getWidth(), 25));
        studyListTable.getTableHeader().setFont(new Font("Lucida Grande",Font.BOLD,12));
        studyListTable.setRowHeight(25);
        studyListTable.getTableHeader().setForeground(new Color(255,138,0));
        studyListTable.getTableHeader().setBackground(new Color(0,0,0));
        jScrollPane2.setViewportView(studyListTable);

        headerLabel.setBackground(new java.awt.Color(0, 0, 0));
        headerLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        headerLabel.setForeground(new java.awt.Color(255, 138, 0));
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerLabel.setText(ApplicationContext.currentBundle.getString("Preferences.emrServersPanel.text")); // NOI18N
        headerLabel.setOpaque(true);

        queryFilterLabel.setBackground(new java.awt.Color(0, 0, 0));
        queryFilterLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        queryFilterLabel.setForeground(new java.awt.Color(255, 138, 0));
        queryFilterLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        queryFilterLabel.setText(ApplicationContext.currentBundle.getString("HL7QueryRetrieve.queryFilterLabel.text")); // NOI18N
        queryFilterLabel.setOpaque(true);

        org.jdesktop.layout.GroupLayout jPanel9Layout = new org.jdesktop.layout.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane2)
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, serverlistScroll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, headerLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jPanel8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(queryFilterLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jPanel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(headerLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(queryFilterLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel9Layout.createSequentialGroup()
                        .add(jPanel7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jPanel8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(serverlistScroll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jPanel9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void refreshModels() {
        setServerTableModel();
        setSpinnerDateModel();
    }

    private void setServerTableModel() {
        EMRServerTableModel serverTableModel = new EMRServerTableModel();
        serverTableModel.setEditable(false);
        serverTableModel.setData(ApplicationContext.databaseRef.getEMRServerList());
        serverListTable.setModel(serverTableModel);
        if (this.serverListTable.getRowCount() > 0) {
            serverListTable.setRowSelectionInterval(0, 0);
        }
    }
    
    private void dobLabelItemStateChanged(java.awt.event.ItemEvent evt) {                                          
        if (evt.getStateChange() == 1) {
            birthDateSpinner.setEnabled(true);
        } else {
            birthDateSpinner.setEnabled(false);
        }
    }   

    private void setSpinnerDateModel() {
        SpinnerDateModel spm3 = new SpinnerDateModel();
        birthDateSpinner.setModel(spm3);
        birthDateSpinner.setEditor(new JSpinner.DateEditor(birthDateSpinner, "dd/MM/yyyy"));
    }
    
    private void queryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_queryButtonActionPerformed
        String serverName = (String)((EMRServerTableModel) serverListTable.getModel()).getValueAt(serverListTable.getSelectedRow(), 0);
        try {
            EMRServerModel sm = ApplicationContext.databaseRef.getEMRServerDetails(serverName);
            doQuery(sm);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Server is not available", "Server Status", JOptionPane.INFORMATION_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_queryButtonActionPerformed
        
    private void doQuery(EMRServerModel sm) throws Exception {
        
        int noFilterQuery = 0;
        
        setPatientInfoToQueryParam();
        
        //recupera todos los resultados via HL7
        if (queryParam.getPatientId().equalsIgnoreCase("") && queryParam.getPatientName().equalsIgnoreCase("") 
            && queryParam.getBirthDate().equalsIgnoreCase("") && queryParam.getAccessionNo().equalsIgnoreCase("")) {
                noFilterQuery = JOptionPane.showConfirmDialog(this, "No filters have been selected. It will take long time to query and display result...!", "Confirm Dialog", JOptionPane.YES_NO_OPTION);
        }
        
        if (noFilterQuery == 0) {
            HL7QueryService qs = new HL7QueryService();
            qs.setHostName(sm.getHostName());
            qs.setPort(sm.getPort());
            qs.setServerName(sm.getHostName());
            qs.setSubprotocol(sm.getSubprotocol());
            qs.find(queryParam);
            Vector studyList = new Vector();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            for (int pos = 0; pos < qs.getDatasetVector().size(); pos++) {
                try {
                    String[] dataSet = (String[]) qs.getDatasetVector().elementAt(pos);
                    StudyModel studyModel = new StudyModel();
                    studyModel.setPatientId(dataSet[3]);
                    studyModel.setPatientSex(dataSet[4]);
                    studyModel.setPatientName(dataSet[0]+" "+dataSet[1]);
                    studyModel.setStudyDate(""/*10/10/1010*/);
                    studyModel.setStudyDescription(""/*Study Description DUMMY*/);
                    String dob = "";
                    try {
                        dob = sdf.format(new SimpleDateFormat("yyyyMMdd").parse(dataSet[2]));
                    } catch (ParseException e) {
                        dob = "n/a";
                    }
                    studyModel.setDob(dob);
                    studyModel.setStudyUID(""/*11111*/);
                    studyModel.setAccessionNo(""/*22222*/);
                    studyList.addElement(studyModel);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            
            EMRStudyModel studyListModel = new EMRStudyModel();
            studyListModel.setData(studyList);
            studyListTable.setModel(studyListModel);
            TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(studyListModel);
            studyListTable.setRowSorter(sorter);
            
            
            //boolean dicomServerDetailAlreadyPresentInArray = false;
            /*if (dicomServerArray != null) {
                for (int i = 0; i < dicomServerArray.size(); i++) {
                    if (dicomServerArray.get(i).getName().equalsIgnoreCase(ae.getServerName())) {
                        dicomServerDetailAlreadyPresentInArray = true;
                        dicomServerArray.get(i).setAe(ae);
                        dicomServerArray.get(i).setStudyListModel(studyListModel);
                    }
                }
            }
            if (!dicomServerDetailAlreadyPresentInArray) {
                DicomServerDelegate dsd = new DicomServerDelegate(ae.getServerName());
                dsd.setAe(ae);
                dsd.setStudyListModel(studyListModel);
                dicomServerArray.add(dsd);
            }*/
        }
    }
    
    private void setPatientInfoToQueryParam() {
        queryParam.setPatientId(patientIDText.getText());
        queryParam.setPatientName(patientNameText.getText());
        queryParam.setAccessionNo(jTextField3.getText());
        if (!dobLabel.isSelected()) {
            resetBirthDate();
        } else {
            setBirthDate();
        }
    }
    
    public void setBirthDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date d1 = (Date) birthDateSpinner.getModel().getValue();
        String birthDate = sdf.format(d1);
        queryParam.setBirthDate(birthDate);
    }
    
    public void resetBirthDate() {
        queryParam.setBirthDate(null);
    }
    
    private void retrieveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_retrieveButtonActionPerformed
        int index= studyListTable.getSelectedRow();
        index = studyListTable.convertRowIndexToModel(index);
        EMRStudyModel studyListModel = (EMRStudyModel) studyListTable.getModel();
        parent.patientId.setText(studyListModel.getPatientId(index));
        parent.patientName.setText(studyListModel.getPatientName(index));
        parent.patientSex.setSelectedItem(studyListModel.getPatientSex(index));
        parent.dob.setText(studyListModel.getPatientDoB(index));
        parent.studyId.setText(studyListModel.getStudyId(index));
        parent.studyDesc.setText(studyListModel.getStudyDescription(index));
        parent.studyDate.setText(studyListModel.getStudyDate(index));
//      parent.accesionNumber.setText(studyListModel.getValueAt(index, 3));
//      parent.modality.setText(studyListModel.getValueAt(index, 4));
        this.setVisible(false);
    }//GEN-LAST:event_retrieveButtonActionPerformed
  
}
