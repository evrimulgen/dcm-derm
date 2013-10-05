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
 * Devishree V
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
package in.raster.mayam.form;

import in.raster.mayam.context.ApplicationContext;
import in.raster.mayam.models.SeriesDisplayModel;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

/**
 *
 * @author Devishree
 * @version 2.0
 */
public class ImageView extends javax.swing.JFrame {

    public ArrayList<SeriesDisplayModel> selectedSeriesDisplays = null;//Contains all studies opened in viewer
    public int selectedStudy = 0;

    /**
     * Creates new form ImageView
     */
    public ImageView() {
        initComponents();
        imageToolbar.addKeyEventToViewer();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        applyLocale();
        selectedSeriesDisplays = new ArrayList<SeriesDisplayModel>();
        jTabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                try {
                    if (((JSplitPane) ApplicationContext.tabbedPane.getSelectedComponent()).getRightComponent() instanceof VideoPanel) {
                        imageToolbar.disableImageTools();
                        ApplicationContext.layeredCanvas = null;
                        ApplicationContext.selectedPanel = null;
                        for (int i = 0; i < selectedSeriesDisplays.size(); i++) {
                            if (selectedSeriesDisplays.get(i).getTabIndex() == ApplicationContext.tabbedPane.getSelectedIndex()) {
                                selectedStudy = i;
                                break;
                            }
                        }
                    } else {
                        imageToolbar.enableImageTools();
                        for (int i = 0; i < selectedSeriesDisplays.size(); i++) {
                            if (selectedSeriesDisplays.get(i).getTabIndex() == ApplicationContext.tabbedPane.getSelectedIndex()) {
                                selectedStudy = i;
                                ApplicationContext.layeredCanvas = null;
                                ApplicationContext.layeredCanvas = selectedSeriesDisplays.get(i).getLastSelectedCanvas();
                                ApplicationContext.selectedPanel = ((JPanel) ApplicationContext.layeredCanvas.getParent());
                                ApplicationContext.layeredCanvas.getCanvas().setSelectionColoring();
                                break;
                            }
                        }
                    }
                    imageToolbar.setWindowing();
                } catch (NullPointerException npe) {
                    System.out.println("Null pointer exception : " + npe.getMessage());
                    //Null pointer exception occurs when the first time the tab was opened
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        imageToolbar = imageToolbar = new in.raster.mayam.form.ImageToolbar(this);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(ApplicationContext.currentBundle.getString("ImageView.title.text")); // NOI18N
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/in/raster/mayam/form/images/fav_mayam.png")));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                windowCloseHandler(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addComponent(imageToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(imageToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void windowCloseHandler(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_windowCloseHandler
        onWindowClose();
    }//GEN-LAST:event_windowCloseHandler
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private in.raster.mayam.form.ImageToolbar imageToolbar;
    public javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables

    public ImageToolbar getImageToolbar() {
        return this.imageToolbar;
    }

    public void applyLocale() {
        setTitle(ApplicationContext.currentBundle.getString("ImageView.title.text"));
        imageToolbar.applyLocale();
        try {
            for (int i = 0; i < ApplicationContext.tabbedPane.getTabCount(); i++) {
                JPanel panel = ((JPanel) ((JSplitPane) ApplicationContext.tabbedPane.getComponentAt(i)).getRightComponent());
                for (int j = 0; j < panel.getComponentCount(); j++) {
                    if (!(panel instanceof VideoPanel)) {
                        JPanel seriesLevelPanel = (JPanel) panel.getComponent(j);
                        for (int k = 0; k < seriesLevelPanel.getComponentCount(); k++) {
                            if (seriesLevelPanel.getComponent(k) instanceof LayeredCanvas) {
                                LayeredCanvas tempCanvas = (LayeredCanvas) seriesLevelPanel.getComponent(k);
                                if (tempCanvas.textOverlay != null) {
                                    tempCanvas.textOverlay.repaint();
                                    tempCanvas.imgpanel.displayZoomLevel();
                                }
                            }
                        }
                    } else {
                        ((VideoPanel) panel).updateLocale();
                    }
                }
            }
        } catch (NullPointerException npe) {
            //ignore Null pointer occurs when jtabbed pane has no components
        }
    }

    private void saveAnnotations() {
        for (int i = 0; i < ApplicationContext.tabbedPane.getTabCount(); i++) {
            JPanel panel = ((JPanel) ((JSplitPane) ApplicationContext.tabbedPane.getComponentAt(i)).getRightComponent());
            if (!(panel instanceof VideoPanel)) {
                for (int j = 0; j < panel.getComponentCount(); j++) {
                    JPanel seriesLevelPanel = (JPanel) panel.getComponent(j);
                    for (int k = 0; k < seriesLevelPanel.getComponentCount(); k++) {
                        if (seriesLevelPanel.getComponent(k) instanceof LayeredCanvas) {
                            LayeredCanvas tempCanvas = (LayeredCanvas) seriesLevelPanel.getComponent(k);
                            try {
                                tempCanvas.imgpanel.storeAnnotation();
                                tempCanvas.imgpanel.storeMultiframeAnnotation();
                            } catch (NullPointerException ex) {
                                //Null pointer exception occurs when there is no image panel
                            }
                        }
                    }
                }
            } else {
                ((VideoPanel) panel).stopTimer();
                EmbeddedMediaPlayerComponent mediaPlayerComp = (EmbeddedMediaPlayerComponent) ((JPanel) panel.getComponent(0)).getComponent(0);
                mediaPlayerComp.getMediaPlayer().stop();
                mediaPlayerComp.getMediaPlayer().release();
                mediaPlayerComp.getMediaPlayerFactory().release();
                mediaPlayerComp.release();
                panel.removeAll();
            }
        }
        for (int i = 0; i < selectedSeriesDisplays.size(); i++) {
            writeToFile(selectedSeriesDisplays.get(i));
        }
    }

    public void writeToFile(SeriesDisplayModel annotationInfo) {
        if (annotationInfo.getStudyDir() != null) {
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;
            try {
                File storeFile = new File(annotationInfo.getStudyDir(), "info.ser");
                fos = new FileOutputStream(storeFile);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(annotationInfo.getStudyAnnotation());
            } catch (FileNotFoundException ex) {
                //ignore
            } catch (IOException ex) {
                Logger.getLogger(ImageView.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ImageView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void onWindowClose() {
        if (ApplicationContext.tabbedPane != null) {
            saveAnnotations();
            selectedSeriesDisplays = null; //To clear the memory
            ImagePanel.setDisplayScout(false);
            imageToolbar.resetCineTimer();
            imageToolbar = null;
            ApplicationContext.imgView = null;
            ApplicationContext.tabbedPane = null;
            if (ApplicationContext.isJnlp) {
                System.exit(0);
            }
        }
    }
}
