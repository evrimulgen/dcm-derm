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
import in.raster.mayam.models.InstanceDisplayModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

/**
 *
 * @author Devishree
 * @version 2.0
 */
public class PreviewPanel extends javax.swing.JPanel {

    int totalImages;
    InstanceDisplayModel[] threeInstanceDetails;
    String dest, studyInstanceUid, seriesInstanceUid;
    Calendar todayInfo;
    Thumbnail[] threeThumbnails = null;
    int totalHeight = 0;
    boolean isLocal;

    /**
     * Creates new form PreviewPanel
     */
    public PreviewPanel(String studyInstanceUid, String seriesInstanceUid, String seriesDescription, int totalImages, InstanceDisplayModel[] threeInstanceDetails) {
        this.totalImages = totalImages;
        this.threeInstanceDetails = threeInstanceDetails;
        this.studyInstanceUid = studyInstanceUid;
        this.seriesInstanceUid = seriesInstanceUid;
        initComponents();
        if (!seriesDescription.equals("Multiframe") && !seriesDescription.equals("Video")) {
            seriesLabel.setText(seriesDescription + ", Images:" + totalImages);
        } else {
            seriesLabel.setText(seriesDescription + ", Frames:" + totalImages);
        }
        threeThumbnails = new Thumbnail[3];
        todayInfo = Calendar.getInstance();
        this.isLocal = false;
        dest = ApplicationContext.listenerDetails[2] + File.separator + todayInfo.get(Calendar.YEAR) + File.separator + todayInfo.get(Calendar.MONTH) + File.separator + todayInfo.get(Calendar.DATE) + File.separator + studyInstanceUid + File.separator + seriesInstanceUid + File.separator + "Thumbnails" + File.separator;
        setLayout(null);
        createComponents();
    }

    public PreviewPanel(String studyInstanceUid, String seriesInstanceUid, String seriesDescription, int totalImages, Thumbnail[] threeThumbnails) {
        this.totalImages = totalImages;
        this.studyInstanceUid = studyInstanceUid;
        this.seriesInstanceUid = seriesInstanceUid;
        initComponents();
        if (!seriesDescription.contains("Multiframe") && !seriesDescription.contains("Video")) {
            seriesLabel.setText(seriesDescription + ", Images:" + totalImages);
        } else {
            seriesLabel.setText(seriesDescription);
        }
        this.threeThumbnails = threeThumbnails;
        this.isLocal = true;
        setLayout(null);
        createComponents();
        addListenerForThumbnails();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imagePanel = new javax.swing.JPanel();
        seriesLabel = new javax.swing.JLabel();

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 248, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        seriesLabel.setFont(ApplicationContext.textFont);
        seriesLabel.setText("Series Info");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(seriesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(seriesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel imagePanel;
    private javax.swing.JLabel seriesLabel;
    // End of variables declaration//GEN-END:variables

   private void createComponents() {
        seriesLabel.setBounds(0, 0, 220, 20);
        loadThreeThumbnails();
        totalHeight = 23 + 76;
    }

    public void loadThreeThumbnails() {
        imagePanel.removeAll();
        imagePanel.setLayout(null);
        int xPos = 0, yPos = 0, hei = 76;
        if (isLocal) {
            for (int i = 0; i < threeThumbnails.length; i++) {
                threeThumbnails[i].setBounds(xPos, yPos, 76, 76);
                imagePanel.add(threeThumbnails[i]);
                xPos += 76;
            }
        } else {
            File file;
            int i = 0;
            try {
                for (i = 0; i < threeInstanceDetails.length; i++) {
                    if (!threeInstanceDetails[i].isIsVideo()) {
                        file = new File(dest + threeInstanceDetails[i].getIuid());
                        threeThumbnails[i] = new Thumbnail(threeInstanceDetails[i].getIuid());
                        threeThumbnails[i].setImage(ImageIO.read(file));
                    } else {
                        threeThumbnails[i] = new Thumbnail(threeInstanceDetails[i].getIuid());
                        threeThumbnails[i].setVideoImage();
                    }

                    threeThumbnails[i].setBounds(xPos, yPos, 76, 76);
                    xPos += 76;
                    imagePanel.add(threeThumbnails[i]);
                }
            } catch (IOException ex) {
                threeThumbnails[i] = new Thumbnail(threeInstanceDetails[i].getIuid());
                threeThumbnails[i].setDefaultImage();
            }
        }
        imagePanel.setBounds(0, 25, 220, hei);
    }

    public Thumbnail[] getThreeThumbnails() {
        return threeThumbnails;
    }

    public int getTotalHeight() {
        return totalHeight;
    }

    private void addListenerForThumbnails() {
        for (int i = 0; i < threeThumbnails.length; i++) {
            if (i == 0) {
                addMouseListener(threeThumbnails[i], 0);
            } else if (i == 1) {
                addMouseListener(threeThumbnails[i], totalImages / 2);
            } else {
                addMouseListener(threeThumbnails[i], totalImages - 1);
            }
        }
    }

    private void addMouseListener(Thumbnail thumbnail, final int instanceIdentificationNo) {
        thumbnail.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (me.getClickCount() == 2 && ApplicationContext.isLocal) {
                    String sopUid = ((JLabel) me.getSource()).getName();
                    String filePath = ApplicationContext.databaseRef.getFileLocation(studyInstanceUid, seriesInstanceUid, sopUid);
                    if (!seriesLabel.getText().contains("Video")) {
                        boolean alreadyOpenedStudy = ApplicationContext.openImageView(filePath, studyInstanceUid, ApplicationContext.mainScreenObj.getCurrentImagePreviewPanel().getLabelInfo(), instanceIdentificationNo - 5);
                        if (!alreadyOpenedStudy) {
                            ApplicationContext.layeredCanvas.imgpanel.setCurrentInstanceNo(instanceIdentificationNo);
                            ApplicationContext.setImageIdentification();
                            ApplicationContext.layeredCanvas.textOverlay.getTextOverlayParam().setCurrentInstance(instanceIdentificationNo);
                        }
                    } else {
                        ApplicationContext.openVideo(filePath, studyInstanceUid, ApplicationContext.mainScreenObj.getCurrentImagePreviewPanel().getLabelInfo());
                    }
                }
            }
        });
    }
}
