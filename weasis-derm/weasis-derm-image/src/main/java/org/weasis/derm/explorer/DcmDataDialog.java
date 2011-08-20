/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DcmDataDialog.java
 *
 * Created on 07/08/2011, 18:18:52
 */
package org.weasis.derm.explorer;

import java.io.File;
import java.util.Calendar;
import javax.swing.text.MaskFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weasis.core.api.explorer.DataExplorerView;
import org.weasis.core.ui.docking.UIManager;
import org.weasis.dicom.explorer.DicomExplorer;
import org.weasis.dicom.explorer.DicomModel;
import org.weasis.dicom.explorer.LoadLocalDicom;

/**
 *
 * @author mariano
 */
public class DcmDataDialog extends javax.swing.JDialog {

    /** Creates new form DcmDataDialog */
    public DcmDataDialog(java.awt.Frame parent, boolean modal,
            DicomModel model, boolean loadInDcmExp, File inputFile, File outputFile) {
        super(parent,"Set basic DICOM data", modal);
        dicomModel = model;
        srcFile = inputFile;
        dstFile = outputFile;
        loadInDicomExplorer = loadInDcmExp;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        patientWeightField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        patientHeightField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        studyDateField = null;
        try {
            studyDateField = new javax.swing.JFormattedTextField(new MaskFormatter("####.##.##"));
            jLabel11 = new javax.swing.JLabel();
            studyHourField = null;
            try {
                studyHourField = new javax.swing.JFormattedTextField(new MaskFormatter("##:##:##.####"));
                jLabel12 = new javax.swing.JLabel();
                jScrollPane1 = new javax.swing.JScrollPane();
                studyDescriptionField = new javax.swing.JTextArea();
                jLabel13 = new javax.swing.JLabel();
                jSeparator1 = new javax.swing.JSeparator();
                jLabel14 = new javax.swing.JLabel();
                serieDateField = null;
                try {
                    serieDateField = new javax.swing.JFormattedTextField(new MaskFormatter("####.##.##"));
                    jLabel15 = new javax.swing.JLabel();
                    serieHourField = null;
                    try {
                        serieHourField = new javax.swing.JFormattedTextField(new MaskFormatter("##:##:##.####"));
                        jLabel16 = new javax.swing.JLabel();
                        jScrollPane2 = new javax.swing.JScrollPane();
                        serieDescriptionField = new javax.swing.JTextArea();
                        jSeparator2 = new javax.swing.JSeparator();
                        jLabel18 = new javax.swing.JLabel();
                        jLabel19 = new javax.swing.JLabel();
                        jLabel20 = new javax.swing.JLabel();
                        jLabel21 = new javax.swing.JLabel();
                        jPanel2 = new javax.swing.JPanel();
                        patientIdField = new javax.swing.JTextField();
                        jLabel1 = new javax.swing.JLabel();
                        jLabel2 = new javax.swing.JLabel();
                        patientNameField = new javax.swing.JTextField();
                        jLabel3 = new javax.swing.JLabel();
                        patientLastnameField = new javax.swing.JTextField();
                        jLabel4 = new javax.swing.JLabel();
                        jLabel5 = new javax.swing.JLabel();
                        patientGenreField = new javax.swing.JComboBox();
                        patientBirthdateField = null;
                        try {
                            patientBirthdateField = new javax.swing.JFormattedTextField(new MaskFormatter("####.##.##"));
                            jLabel17 = new javax.swing.JLabel();
                            closeButton = new javax.swing.JButton();
                            convertButton = new javax.swing.JButton();
                            info = new javax.swing.JProgressBar();

                            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

                            jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(Messages.getString("DcmDataDialog.study_info")));

                            jLabel6.setFont(new java.awt.Font("Ubuntu", 2, 15));
                            jLabel6.setText(Messages.getString("DcmDataDialog.patient"));

                            jLabel7.setText(Messages.getString("DcmDataDialog.weight"));

                            jLabel8.setText(Messages.getString("DcmDataDialog.height"));

                            jLabel9.setFont(new java.awt.Font("Ubuntu", 2, 15));
                            jLabel9.setText(Messages.getString("DcmDataDialog.study"));

                            jLabel10.setText(Messages.getString("DcmDataDialog.date"));

                        } catch (Exception e) {
                            studyDateField = new javax.swing.JFormattedTextField();
                        }

                        jLabel11.setText(Messages.getString("DcmDataDialog.hour"));

                    } catch (Exception e) {
                        studyHourField = new javax.swing.JFormattedTextField();
                    }

                    jLabel12.setText(Messages.getString("DcmDataDialog.description"));

                    studyDescriptionField.setColumns(20);
                    studyDescriptionField.setRows(5);
                    jScrollPane1.setViewportView(studyDescriptionField);

                    jLabel13.setFont(new java.awt.Font("Ubuntu", 2, 15));
                    jLabel13.setText(Messages.getString("DcmDataDialog.serie"));

                    jLabel14.setText(Messages.getString("DcmDataDialog.date"));

                } catch (Exception e) {
                    serieDateField = new javax.swing.JFormattedTextField();
                }

                jLabel15.setText(Messages.getString("DcmDataDialog.hour"));

            } catch (Exception e) {
                serieHourField = new javax.swing.JFormattedTextField();
            }

            jLabel16.setText(Messages.getString("DcmDataDialog.description"));

            serieDescriptionField.setColumns(20);
            serieDescriptionField.setRows(5);
            jScrollPane2.setViewportView(serieDescriptionField);

            jLabel18.setText(Messages.getString("DcmDataDialog.yyyymmdd"));

            jLabel19.setText(Messages.getString("DcmDataDialog.yyyymmdd"));

            jLabel20.setText(Messages.getString("DcmDataDialog.hhmmssfrac"));

            jLabel21.setText(Messages.getString("DcmDataDialog.hhmmssfrac"));

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(53, 53, 53)
                                    .addComponent(jLabel16))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel14)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel19)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(serieDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(41, 41, 41)
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel21)
                                    .addGap(22, 22, 22)
                                    .addComponent(serieHourField, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel12)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 57, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(43, 43, 43)
                                            .addComponent(jLabel7))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel10)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jLabel18)))))
                            .addGap(12, 12, 12)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(patientWeightField, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel8)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(patientHeightField, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(studyDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(35, 35, 35)
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabel20)
                                    .addGap(27, 27, 27)
                                    .addComponent(studyHourField, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE)
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(patientHeightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(patientWeightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(8, 8, 8)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jLabel10)
                        .addComponent(jLabel18)
                        .addComponent(studyDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(studyHourField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel20))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(serieHourField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel19)
                            .addComponent(serieDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel16)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(Messages.getString("DcmDataDialog.patient_info")));

            patientIdField.setPreferredSize(new java.awt.Dimension(90, 28));

            jLabel1.setText(Messages.getString("DcmDataDialog.id"));

            jLabel2.setText(Messages.getString("DcmDataDialog.Name"));

            jLabel3.setText(Messages.getString("DcmDataDialog.Lastname"));

            jLabel4.setText(Messages.getString("DcmDataDialog.birthdate"));

            jLabel5.setText(Messages.getString("DcmDataDialog.genre"));

            patientGenreField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { Messages.getString("DcmDataDialog.female"), Messages.getString("DcmDataDialog.male") }));

        } catch (Exception e) {
            patientBirthdateField = new javax.swing.JFormattedTextField();
        }

        jLabel17.setText(Messages.getString("DcmDataDialog.yyyymmdd"));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(patientIdField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(patientNameField, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(patientBirthdateField))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(patientLastnameField)
                    .addComponent(patientGenreField, 0, 107, Short.MAX_VALUE))
                .addGap(62, 62, 62))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(patientIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(patientNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(patientLastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel17)
                    .addComponent(patientBirthdateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(patientGenreField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap())
        );

        closeButton.setText(Messages.getString("Button.close"));
        closeButton.setMaximumSize(new java.awt.Dimension(68, 30));
        closeButton.setMinimumSize(new java.awt.Dimension(68, 30));
        closeButton.setPreferredSize(new java.awt.Dimension(68, 30));
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        convertButton.setText(Messages.getString("Button.convert"));
        convertButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                convertButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, 471, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(convertButton)
                        .addGap(18, 18, 18)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, 0, 658, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(convertButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
    dispose();
}//GEN-LAST:event_closeButtonActionPerformed

private void convertButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_convertButtonActionPerformed
    if (srcFile != null) {
        //convierto la imagen en DICOM
        LoadLocalIMG.convertImgToDICOM(srcFile, dstFile, getBasicDcmDataBean()); //tratar errores, si no hay errores cerrar la ventana
        
        if (loadInDicomExplorer) {
            //mdiaz - Seteo el modelo DICOM generado a partir de una imagen al
            //plugin DicomExplorer para poder visualizar las series
            try {
                DataExplorerView dcmExplorer = UIManager.getExplorerplugin(DicomExplorer.NAME);
                if ( dcmExplorer != null ) { 
                    log.info("DicomExplorer plugin is not NULL");
                    ((DicomExplorer) dcmExplorer).setDicomModel(dicomModel);
                    dicomModel.addPropertyChangeListener(dcmExplorer);
                    LoadLocalDicom dicom = new LoadLocalDicom(new File[]{dstFile}, false, dicomModel, false);
                    dicom.setProgressBar(info);
                    DicomModel.loadingExecutor.execute(dicom);
                } else {
                    log.error("DicomExplorer plugin is NULL"); //mostrar ventana de error
                }
            } catch(Exception e) {
                log.error("Error: ", e); //mostrar ventana de error
            }
        }
    }
    else {
        log.error("srcFile is null"); //mostrar ventana de error
    }
}//GEN-LAST:event_convertButtonActionPerformed

private BasicDcmDataBean getBasicDcmDataBean() {
    BasicDcmDataBean bean = new BasicDcmDataBean();
    setPatientAgeAndBirthdate(bean);
    bean.setPatientSex((String)patientGenreField.getSelectedItem());
    bean.setPatientHeight(patientHeightField.getText());
    bean.setPatientId(patientIdField.getText());
    bean.setPatientLastname(patientLastnameField.getText());
    bean.setPatientName(patientNameField.getText());
    bean.setSerieDate(serieDateField.getText());
    bean.setSerieTime(serieHourField.getText());
    bean.setSerieDescription(serieDescriptionField.getText());
    bean.setStudyDate(studyDateField.getText());
    bean.setStudyTime(studyHourField.getText());
    bean.setStudyDescription(studyDescriptionField.getText());
    bean.setPatientWeight(new Float(patientWeightField.getText()));
    return bean;
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JButton convertButton;
    private javax.swing.JProgressBar info;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JFormattedTextField patientBirthdateField;
    private javax.swing.JComboBox patientGenreField;
    private javax.swing.JTextField patientHeightField;
    private javax.swing.JTextField patientIdField;
    private javax.swing.JTextField patientLastnameField;
    private javax.swing.JTextField patientNameField;
    private javax.swing.JTextField patientWeightField;
    private javax.swing.JFormattedTextField serieDateField;
    private javax.swing.JTextArea serieDescriptionField;
    private javax.swing.JFormattedTextField serieHourField;
    private javax.swing.JFormattedTextField studyDateField;
    private javax.swing.JTextArea studyDescriptionField;
    private javax.swing.JFormattedTextField studyHourField;
    // End of variables declaration//GEN-END:variables
    private final DicomModel dicomModel;
    private boolean loadInDicomExplorer;
    private static final Logger log = LoggerFactory.getLogger(DcmDataDialog.class);
    private File srcFile;
    private File dstFile;

    private void setPatientAgeAndBirthdate(BasicDcmDataBean bean) {
        bean.setPatientBirthdate(patientBirthdateField.getText());
        String[] date = patientBirthdateField.getText().split("\\.");
        Calendar dob = Calendar.getInstance();
        dob.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date[2]));
        dob.set(Calendar.MONTH, Integer.valueOf(date[1]));
        dob.set(Calendar.YEAR, Integer.valueOf(date[0]));
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) <= dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        bean.setPatientAge(age);
    }
    
}
