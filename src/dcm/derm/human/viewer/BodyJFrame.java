/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dcm.derm.human.viewer;

import com.jme3.math.Vector3f;
import com.jme3.system.JmeCanvasContext;
import com.pixelmed.display.SourceImage;
import in.raster.mayam.context.ApplicationContext;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author mariano
 */
public class BodyJFrame extends javax.swing.JFrame  implements Observer, ListSelectionListener {

    private String model = null;
    public static String male = "M";
    public static String female = "F";
    //private ArrayList<CoordBean> coordList = null;
    private String sIUID = null;
    private SourceImage srcImg = null;
    private Integer selectedFrameNumber = null;
    
    public BodyJFrame() {
        initComponents();
    }
    
    public BodyJFrame(boolean isNew, String gender, String sIUID, SourceImage srcImg) {
        if(male.equals(gender)) {
            model = "/assets/Models/man.j3o";
        }
        else {
            model = "/assets/Models/woman.j3o";
        }
        this.sIUID = sIUID;
        this.srcImg = srcImg;
        initComponents();
        jList1.addListSelectionListener(BodyManager.getInstance());
        jList1.addListSelectionListener(this);
        initCoords(isNew);
    }

    private Canvas getJmeCanvas() {
        BodyApp app = new BodyApp(model);
        app.setShowSettings(false);
        app.setDisplayStatView(false); 
        app.setDisplayFps(false);
        app.createCanvas();
        JmeCanvasContext ctx = (JmeCanvasContext) app.getContext();
        ctx.setSystemListener(app);
        //Dimension dim = new Dimension(530, 450);
        //ctx.getCanvas().setPreferredSize(dim);
        return ctx.getCanvas();
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
        canvas1 = getJmeCanvas();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        addButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        acceptButton = new javax.swing.JButton();
        removeAllButton = new javax.swing.JButton();
        thumbPanel = new FramePreview();
        frLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        canvas1.setPreferredSize(new java.awt.Dimension(530, 452));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        jList1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setFocusable(false);
        jList1.setPreferredSize(new java.awt.Dimension(35, 144));
        jScrollPane1.setViewportView(jList1);

        addButton.setText("Add");
        addButton.setEnabled(false);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("in/raster/mayam/form/i18n/Bundle",ApplicationContext.currentLocale); // NOI18N
        removeButton.setText(bundle.getString("BodyJFrame.removeButton.text")); // NOI18N
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });

        cancelButton.setText(bundle.getString("CancelButton")); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        acceptButton.setText(bundle.getString("AcceptButton")); // NOI18N
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        removeAllButton.setText(bundle.getString("BodyJFrame.removeAllButton.text")); // NOI18N
        removeAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeAllButtonActionPerformed(evt);
            }
        });

        thumbPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        thumbPanel.setPreferredSize(new java.awt.Dimension(112, 104));

        javax.swing.GroupLayout thumbPanelLayout = new javax.swing.GroupLayout(thumbPanel);
        thumbPanel.setLayout(thumbPanelLayout);
        thumbPanelLayout.setHorizontalGroup(
            thumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );
        thumbPanelLayout.setVerticalGroup(
            thumbPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(40, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(frLabel)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cancelButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(removeButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(acceptButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(removeAllButton, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(thumbPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(addButton)
                        .addGap(18, 18, 18)
                        .addComponent(removeButton)
                        .addGap(18, 18, 18)
                        .addComponent(removeAllButton)
                        .addGap(18, 18, 18)
                        .addComponent(cancelButton)
                        .addGap(48, 48, 48)
                        .addComponent(frLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(thumbPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(acceptButton))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 463, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        //parent.add
    }//GEN-LAST:event_addButtonActionPerformed

    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        int i = jList1.getSelectedIndex();
        if(i > -1) {
            BodyManager.getInstance().getCoordList().remove(i);
            ((DefaultListModel) jList1.getModel()).removeElementAt(i);
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    private void removeAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeAllButtonActionPerformed
        BodyManager.getInstance().reset();
        ((DefaultListModel) jList1.getModel()).removeAllElements();
    }//GEN-LAST:event_removeAllButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
       ApplicationContext.databaseRef.updateCoords(sIUID.trim(),
               BodyManager.getInstance().getCoordList()/*coordList*/);
       this.dispose();
    }//GEN-LAST:event_acceptButtonActionPerformed

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
            java.util.logging.Logger.getLogger(BodyJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BodyJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BodyJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BodyJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BodyJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JButton addButton;
    private javax.swing.JButton cancelButton;
    private java.awt.Canvas canvas1;
    private javax.swing.JLabel frLabel;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton removeAllButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JPanel thumbPanel;
    // End of variables declaration//GEN-END:variables
    
    private void setItem(String item) {
        DefaultListModel listModel; 
        if (jList1.getModel().getSize() == 0) {
            listModel = new DefaultListModel();
        }
        else {
            listModel = (DefaultListModel) jList1.getModel();
        }
        listModel.addElement(item);
        jList1.setModel(listModel);
    }

    @Override
    public void update(Observable o, Object o1) {
        Vector3f point = ((BodyManager)o).getCoord();
        displayImgSelector();
        if (selectedFrameNumber != null) { //si el usuario cancela el ImgSelector, esta variable es nula
            //CoordBean cb = new CoordBean();
            //cb.setFrameNuber(selectedFrameNumber);
            //cb.setPoint(point);
            //cb.setSOPId(sIUID);
            BodyManager.getInstance().getCoordList().get(
                    BodyManager.getInstance().getCoordList().size()-1).setFrameNuber(selectedFrameNumber);
            BodyManager.getInstance().getCoordList().get(
                    BodyManager.getInstance().getCoordList().size()-1).setSOPId(sIUID);
            setItem(ApplicationContext.resBundle.getString("BodyJFrame.point.text")+
                    String.valueOf(BodyManager.getInstance().getCoordList().size()));
        }
        else { //Si selectedFrameNumber es null, significa que se cancelo el selector de frames, y en ese caso
            // se debe eliminar el ultimo punto registrado en el BodyManager.
            BodyManager.getInstance().removeLastCoord();
        }
    }

    private void initCoords(boolean isNew) {
        ArrayList<CoordBean> coordList = new ArrayList<CoordBean>();
        if(!isNew) {
            for (CoordBean bean : ApplicationContext.databaseRef.getCoords(sIUID.trim())) {
                coordList.add(bean);
                setItem(ApplicationContext.resBundle.getString("BodyJFrame.point.text")+ String.valueOf(coordList.size()));
            }
            BodyManager.getInstance().setCoordList(coordList);
        }
    }

    //Muestra un seleccionador de imagenes del DICOM (una sola o multiframe)
    private void displayImgSelector() {
        BodyImgSelector imgSelector = new BodyImgSelector(srcImg,this,true); 
        imgSelector.setVisible(true);
    }
    
    protected void setSelectedFrameNumber(Integer fn) {
        this.selectedFrameNumber = fn;
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting()) {
            javax.swing.JList list = (javax.swing.JList)lse.getSource();
            int i = list.getSelectedIndex();
            if(i > -1) {
                CoordBean cb = (CoordBean) BodyManager.getInstance().getCoordList().get(i);
                BufferedImage bi = srcImg.getBufferedImage(cb.getFrameNuber());
                frLabel.setText(ApplicationContext.resBundle.getString("BodyJFrame.frame.text")+(cb.getFrameNuber()+1));
                ((FramePreview)thumbPanel).setImage(bi);
            }
        }
    }
}
