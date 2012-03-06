
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


import in.raster.mayam.delegate.EchoService;
import in.raster.mayam.form.display.Display;
import org.dcm4che.util.DcmURL;

/**
 *
 * @author  BabuHussain
 * @version 0.5
 *
 */
public class AEManagementDialog extends javax.swing.JDialog {
    /** A return status code - returned if Cancel button has been pressed */
    public static final int RET_CANCEL = 0;
    /** A return status code - returned if OK button has been pressed */
    public static final int RET_OK = 1;
    
    public String keyName="";
    
    /** Creates new form AEManagementDialog */
    public AEManagementDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    /** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
    public int getReturnStatus() {
        return returnStatus;
    }
    /**
     *
     * @param serverName-server name of the dicom server
     * @param host-host name of the dicom server
     * @param location-location of the dicom server
     * @param aeTitle-aeTitle of the dicom server
     * @param port-port of the dicom server
     * @param headerPort-header port of the dicom server
     * @param imagePort-image port of the dicom server
     */
    public void setFormValues(String serverName,String host,String location,String aeTitle,int port,int headerPort,int imagePort)
    {      
        serverNameText.setText(serverName);
        hostText.setText(host);      
        aeTitleText.setText(aeTitle);
        portText.setText(Integer.toString(port));
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        serverNameText = new javax.swing.JTextField();
        hostText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        aeTitleText = new javax.swing.JTextField();
        portText = new javax.swing.JTextField();
        verifyButton = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("AE Management"));

        jLabel1.setText("Server Name");

        jLabel2.setText("Host");

        jLabel4.setText("AE Title");

        jLabel5.setText("Port");

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(104, 104, 104)
                        .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jLabel5)
                            .add(jLabel2)
                            .add(jLabel4)))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(78, 78, 78)
                        .add(jLabel1)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, serverNameText, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, portText, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, aeTitleText, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, hostText, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
                .add(32, 32, 32))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(serverNameText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(26, 26, 26)
                        .add(jLabel2))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(hostText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(28, 28, 28)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel4)
                    .add(aeTitleText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(26, 26, 26)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(portText, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel5))
                .addContainerGap())
        );

        verifyButton.setText("Verify");
        verifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(104, 104, 104)
                        .add(verifyButton)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(okButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 67, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(cancelButton))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {cancelButton, okButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 28, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cancelButton)
                    .add(okButton)
                    .add(verifyButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     *
     * @param evt-action performed event
     */
    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
       /* boolean NoException=true;
        if(this.getTitle().equalsIgnoreCase("Edit Server"))
        {
            try{       
           int port=(portText.getText().equalsIgnoreCase(""))?0:Integer.parseInt(portText.getText());
           //temp.doEditServerRecords(serverNameText.getText(), hostText.getText(), aeTitleText.getText(),port,keyName);
             ApplicationContext.databaseRef.doEditServerRecords(serverNameText.getText(), hostText.getText(), aeTitleText.getText(),port,keyName);
            }
            catch(NumberFormatException nm)
            {
                NumberFormatValidator valitatorInfoDialog=new NumberFormatValidator(temp, true);
               Display.alignScreen(valitatorInfoDialog);
                valitatorInfoDialog.setVisible(true);
                NoException=false;
            }
            }
        else
        {
           try{        
           int port=(portText.getText().equalsIgnoreCase(""))?0:Integer.parseInt(portText.getText());
           //temp.doCreateServerRecords(serverNameText.getText(), hostText.getText(), aeTitleText.getText(),port);
           ApplicationContext.databaseRef.doCreateServerRecords(serverNameText.getText(), hostText.getText(), aeTitleText.getText(),port);
           }
            catch(NumberFormatException nm)
            {
                NumberFormatValidator valitatorInfoDialog=new NumberFormatValidator(temp, true);
                Display.alignScreen(valitatorInfoDialog);
                valitatorInfoDialog.setVisible(true);
                NoException=false;
            }
        }
         if(NoException)
         doClose(RET_OK);*/
    }//GEN-LAST:event_okButtonActionPerformed
    /**
     *
     * @param evt-action performed event
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog
    /**
     * This routine used to check the status of the dicom aetitle
     * @param evt
     */
    private void verifyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verifyButtonActionPerformed
        try {
            String aeTitle = "";
            String hostName = "";
            String port = "";
            if (aeTitleText.getText() != null) {
                aeTitle = aeTitleText.getText();
            }
            if (hostText.getText() != null) {
                hostName = hostText.getText();
            }
            if (portText.getText() != null) {
                port = portText.getText();
            }

            if (!aeTitle.equalsIgnoreCase("") & !hostName.equalsIgnoreCase("") & !port.equalsIgnoreCase("")) {

                DcmURL url = new DcmURL("dicom://" + aeTitle + "@" + hostName + ":" + port);

                EchoService echo = new EchoService();
                EchoStatus echoStatus = new EchoStatus(this, true);
               Display.alignScreen(echoStatus);
                echo.checkEcho(url);
                echoStatus.setTitle("Echo Status");
                try {
                    if (echo.getStatus().trim().equalsIgnoreCase("EchoSuccess")) {
                        echoStatus.status.setText("Echo dicom://" + aeTitle + "@" + hostName + ":" + port + " successfully!");
                        echoStatus.setVisible(true);
                    } else {
                        echoStatus.status.setText("Echo dicom://" + aeTitle + "@" + hostName + ":" + port + " not successfully!");
                        echoStatus.setVisible(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                FieldValidator sd=new FieldValidator(this, true);
                Display.alignScreen(sd);
                sd.setVisible(true);

            }



        } catch (Exception e) {
            System.out.println("Choosed tree node is not valid for this process");
        }

}//GEN-LAST:event_verifyButtonActionPerformed

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
                AEManagementDialog dialog = new AEManagementDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField aeTitleText;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField hostText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField portText;
    private javax.swing.JTextField serverNameText;
    private javax.swing.JButton verifyButton;
    // End of variables declaration//GEN-END:variables
    
    private int returnStatus = RET_CANCEL;
}
