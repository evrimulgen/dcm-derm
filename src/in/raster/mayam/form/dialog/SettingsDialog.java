/* ***** BEGIN LICENSE BLOCK *****
* Version: MPL 1.1/GPL 2.0/LGPL 2.1
*
* The contents of this file are subject to the Mozilla Public License Version
* 1.1 (the "License"); you may not use this file except in compliance with
* the License. You may obtain a copy of the License at
* http://www.mozilla.org/MPL/
*
* Software distributed under the License is distributed on an "AS IS" basis,
* WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
* for the specific language governing rights and limitations under the
* License.
*
*
* The Initial Developer of the Original Code is
* Raster Images
* Portions created by the Initial Developer are Copyright (C) 2009-2010
* the Initial Developer. All Rights Reserved.
*
* Contributor(s):
* Babu Hussain A
* Meer Asgar Hussain B
* Prakash J
* Suresh V
*
* Alternatively, the contents of this file may be used under the terms of
* either the GNU General Public License Version 2 or later (the "GPL"), or
* the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
* in which case the provisions of the GPL or the LGPL are applicable instead
* of those above. If you wish to allow use of your version of this file only
* under the terms of either the GPL or the LGPL, and not to allow others to
* use your version of this file under the terms of the MPL, indicate your
* decision by deleting the provisions above and replace them with the notice
* and other provisions required by the GPL or the LGPL. If you do not delete
* the provisions above, a recipient may use your version of this file under
* the terms of any one of the MPL, the GPL or the LGPL.
*
* ***** END LICENSE BLOCK ***** */
package in.raster.mayam.form.dialog;


import in.raster.mayam.form.*;
import in.raster.mayam.context.ApplicationContext;
import java.awt.CardLayout;
import java.util.Locale;
import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author  BabuHussain
 * @version 0.5
 *
 */
public class SettingsDialog extends javax.swing.JDialog {

    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;
    DefaultTreeModel tm;
    MainScreen m;
    LayoutManagerPanel layoutManagerPanel1;
    WindowingManagerPanel windowingManagerPanel1;
    ServerManager serverManager;
    HL7ServerManager hl7ServerManager;

    /** Creates new form SettingsDialog */
    public SettingsDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        m = (MainScreen) parent;
        initComponents();
        updateTreeModel();
        settingListenerDetail();
        initializePanel();
    }

    private void initializePanel() {
        layoutManagerPanel1 = new LayoutManagerPanel();
        jPanel4.add(layoutManagerPanel1, "card3");
        windowingManagerPanel1 = new WindowingManagerPanel();
        jPanel4.add(windowingManagerPanel1, "card2");
        serverManager = new ServerManager();
        serverManager.addServerChangeListener(m.getQueryScreen());
        jPanel4.add(serverManager, "card7");
        InternationalizationForm internationalizationForm=new InternationalizationForm();
        jPanel4.add(internationalizationForm, "card8");
        hl7ServerManager = new HL7ServerManager();
        hl7ServerManager.addServerChangeListener(m.getHL7QueryScreen());
        jPanel4.add(hl7ServerManager, "card9");

    }

    public void settingListenerDetail() {
        String s[] = ApplicationContext.databaseRef.getListenerDetails();
        dicomServerPanel1.setAeTitle(s[0]);
        dicomServerPanel1.setPort(s[1]);

    }

    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }

    private DefaultMutableTreeNode settingTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root");
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Date Format");
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("DICOM Listener");
        DefaultMutableTreeNode node3 = new DefaultMutableTreeNode("Layout");
        DefaultMutableTreeNode node4 = new DefaultMutableTreeNode("Window Configuration");
        DefaultMutableTreeNode node5 = new DefaultMutableTreeNode("Preset");
        DefaultMutableTreeNode node6 = new DefaultMutableTreeNode("DICOM Servers");
        DefaultMutableTreeNode node7 = new DefaultMutableTreeNode("Language");
        DefaultMutableTreeNode node8 = new DefaultMutableTreeNode("HL7 Servers");
        rootNode.add(node2);
        rootNode.add(node3);
        rootNode.add(node4);
        rootNode.add(node6);
        rootNode.add(node8);
        rootNode.add(node5);
        rootNode.add(node7);
        return rootNode;

    }

    private void updateTreeModel() {
        tm = new DefaultTreeModel(settingTree());
        jTree1.setModel(tm);
        jTree1.setRootVisible(false);
        jTree1.revalidate();
        jTree1.setSelectionRow(0);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        dicomServerPanel1 = new in.raster.mayam.form.DicomListenerPanel();
        dateFormatPanel1 = new in.raster.mayam.form.DateFormatPanel();
        windowConfigurationPanel1 = new in.raster.mayam.form.WindowConfigurationPanel();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("in/raster/mayam/form/i18n/Bundle",ApplicationContext.currentLocale); // NOI18N
        setTitle(bundle.getString("SettingsDialog.title_1_1")); // NOI18N
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jSplitPane1.setDividerLocation(160);
        jSplitPane1.setDividerSize(2);

        jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTree1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTree1);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
        );

        jSplitPane1.setLeftComponent(jPanel1);

        okButton.setText(bundle.getString("SettingsDialog.okButton.text_1_1")); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(bundle.getString("SettingsDialog.cancelButton.text_1_1")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(309, Short.MAX_VALUE)
                .add(okButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(cancelButton)
                .add(23, 23, 23))
        );

        jPanel3Layout.linkSize(new java.awt.Component[] {cancelButton, okButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(okButton)
                    .add(cancelButton))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setLayout(new java.awt.CardLayout());
        jPanel4.add(dicomServerPanel1, "card4");
        jPanel4.add(dateFormatPanel1, "card5");
        jPanel4.add(windowConfigurationPanel1, "card6");

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
            .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setRightComponent(jPanel2);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        int port = ((!dicomServerPanel1.getPortText().getText().equalsIgnoreCase("")) ? Integer.parseInt(dicomServerPanel1.getPortText().getText()) : 1025);
        ApplicationContext.databaseRef.insertListenerDetail(dicomServerPanel1.getAetitleText().getText(), port, "");
        String s[] = layoutManagerPanel1.getModalityLayoutDetail();
        ApplicationContext.databaseRef.insertLayoutDetail(Integer.parseInt(s[1]), Integer.parseInt(s[2]), s[0]);
        m.restartReceiver();
        layoutManagerPanel1.updateSeriesDisplayModification();
        //String appLocale[]= ApplicationContext.databaseRef.getActiveLanguageAndCountry();
        //ApplicationContext.currentLocale=new Locale(appLocale[0],appLocale[1]);
        ApplicationContext.setAppLocale();
        MainScreen.getInstance().reInitComponents();
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTree1ValueChanged
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
        if (node.getUserObject().toString().equalsIgnoreCase("Date Format")) {
            ((CardLayout) jPanel4.getLayout()).show(jPanel4, "card5");

        } else if (node.getUserObject().toString().equalsIgnoreCase("DICOM Listener")) {
            ((CardLayout) jPanel4.getLayout()).show(jPanel4, "card4");

        } else if (node.getUserObject().toString().equalsIgnoreCase("Layout")) {
            ((CardLayout) jPanel4.getLayout()).show(jPanel4, "card3");
        } else if (node.getUserObject().toString().equalsIgnoreCase("Window Configuration")) {
            ((CardLayout) jPanel4.getLayout()).show(jPanel4, "card6");

        } else if (node.getUserObject().toString().equalsIgnoreCase("Preset")) {
            ((CardLayout) jPanel4.getLayout()).show(jPanel4, "card2");

        } else if (node.getUserObject().toString().equalsIgnoreCase("DICOM Servers")) {
            ((CardLayout) jPanel4.getLayout()).show(jPanel4, "card7");

        }
        else if (node.getUserObject().toString().equalsIgnoreCase("Language")) {
            ((CardLayout) jPanel4.getLayout()).show(jPanel4, "card8");

        }
        else if (node.getUserObject().toString().equalsIgnoreCase("HL7 Servers")) {
            ((CardLayout) jPanel4.getLayout()).show(jPanel4, "card9");
        }
    }//GEN-LAST:event_jTree1ValueChanged

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        setVisible(false);
        dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                SettingsDialog dialog = new SettingsDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private in.raster.mayam.form.DateFormatPanel dateFormatPanel1;
    private in.raster.mayam.form.DicomListenerPanel dicomServerPanel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JButton okButton;
    private in.raster.mayam.form.WindowConfigurationPanel windowConfigurationPanel1;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}
