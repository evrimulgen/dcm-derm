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
import in.raster.mayam.delegates.ImageGenerator;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Devishree
 * @version 2.0
 */
public class ViewerPreviewPanel extends javax.swing.JPanel {

    int totalImages, totalHeight;
    Thumbnail[] thumbnails;
    String studyInstanceUid, seriesInstanceUid, seriesDescription, selectedButton;
    ImageIcon imgOne = new ImageIcon(getClass().getResource("/in/raster/mayam/form/images/one.png"));
    ImageIcon imgThree = new ImageIcon(getClass().getResource("/in/raster/mayam/form/images/three.png"));
    ImageIcon imgAll = new ImageIcon(getClass().getResource("/in/raster/mayam/form/images/all.png"));
    static final String three = "three", one = "one", all = "all";
    ArrayList<JLabel> labelList = new ArrayList<JLabel>();
    int labelPanelHeight;
    MouseAdapter mouseClickAdapter1 = null;
    boolean isMultiframe = false;
    String sopUid;
    ArrayList<Integer> selectedInstances = new ArrayList<Integer>(0);

    /**
     * Creates new form ViewerPreviewPanel
     */
    public ViewerPreviewPanel(String studyInstanceUid, String seriesInstanceUid, String seriesDescription, int totalImages, Thumbnail[] thumbnails, boolean isMultiframe, String instanceUid) {
        initComponents();
        this.studyInstanceUid = studyInstanceUid;
        this.seriesInstanceUid = seriesInstanceUid;
        this.seriesDescription = seriesDescription;
        this.totalImages = totalImages;
        if (totalImages == 0) {
            totalImages = 1;
        }
        this.thumbnails = thumbnails;
        this.SeriesLabel.setText(seriesDescription + ", " + totalImages);
        this.selectedButton = three;
        this.isMultiframe = isMultiframe;
        this.sopUid = instanceUid;
        setLayout(null);
        createComponents();
        addMouseAdapter();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SeriesLabel = new javax.swing.JLabel();
        labelPanel = new javax.swing.JPanel();
        button = new javax.swing.JButton();
        imagePanel = new javax.swing.JPanel();

        SeriesLabel.setFont(ApplicationContext.textFont);
        SeriesLabel.setText("jLabel1");

        javax.swing.GroupLayout labelPanelLayout = new javax.swing.GroupLayout(labelPanel);
        labelPanel.setLayout(labelPanelLayout);
        labelPanelLayout.setHorizontalGroup(
            labelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );
        labelPanelLayout.setVerticalGroup(
            labelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 72, Short.MAX_VALUE)
        );

        button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/in/raster/mayam/form/images/three.png"))); // NOI18N

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SeriesLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(button)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SeriesLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button)
                    .addComponent(labelPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SeriesLabel;
    private javax.swing.JButton button;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JPanel labelPanel;
    // End of variables declaration//GEN-END:variables

    private void createComponents() {
        SeriesLabel.setBounds(0, 0, 220, 20);
        labelPanelHeight = 9;
        labelPanel.setLayout(null);
        button.setBounds(207, 21, 14, 14);
        if (totalImages > 3) {
            button.setName(three);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    performButtonAction();
                }
            });
        } else {
            button.setIcon(imgAll);
            colorLabels(all);
        }

        int xPos = 0, yPos = 0;
        for (int l = 0; l < totalImages; l++) {
            JLabel label = new JLabel(" ");
            label.setOpaque(true);
            label.setBounds(xPos, yPos, 5, 5);
            if (xPos + 7 < 200) {
                xPos += 7;
            } else {
                xPos = 0;
                yPos += 7;
                labelPanelHeight += 7;
            }
            labelList.add(label);
            labelPanel.add(label);
            labelPanel.setBounds(0, 23, 205, labelPanelHeight);
        }
        colorLabels(selectedButton);
        imagePanel.setLayout(null);
        int imagePanelHeight = loadThreePreviewImages();
        imagePanel.setBounds(0, 23 + labelPanelHeight + 5, 230, imagePanelHeight);
        totalHeight = 23 + labelPanelHeight + imagePanelHeight + 5;
        selectedInstances.add(0);
    }

    private void performButtonAction() {
        if (three.equals(button.getName())) {
            button.setIcon(imgOne);
            button.setName(one);
            selectedButton = one;
            colorLabels(selectedButton);
            ApplicationContext.setImageIdentification();
            if (thumbnails.length > 1) {
                for (int i = 1; i < thumbnails.length; i++) {
                    thumbnails[i].setVisible(false);
                }
            }
        } else if (one.equals(button.getName())) {
            button.setIcon(imgAll);
            button.setName(all);
            selectedButton = all;
            colorLabels(selectedButton);
            ApplicationContext.setImageIdentification();
            int imagePanelHeight = loadAllPreviewImages();
            alignComponents(imagePanelHeight);
        } else {
            button.setIcon(imgThree);
            button.setName(three);
            selectedButton = three;
            colorLabels(selectedButton);
            ApplicationContext.setImageIdentification();
            int imagePanelHeight = loadThreePreviewImages();
            alignComponents(imagePanelHeight);
        }
    }

    public int getTotalHeight() {
        return totalHeight;
    }

    public int getLabelPanelHeight() {
        return labelPanelHeight;
    }

    private void colorLabels(String selectedButton) {
        for (int i = 0; i < labelList.size(); i++) {
            labelList.get(i).setBackground(Color.lightGray);
        }
        if ("one".equals(selectedButton)) {
            labelList.get(0).setBackground(Color.BLUE);
        } else if ("three".equals(selectedButton)) {
            if (totalImages >= 3) {
                labelList.get(0).setBackground(Color.BLUE);
                labelList.get(totalImages / 2).setBackground(Color.BLUE);
                labelList.get(totalImages - 1).setBackground(Color.BLUE);
            } else {
                for (int i = 0; i < labelList.size(); i++) {
                    labelList.get(i).setBackground(Color.BLUE);
                }
            }
        } else {
            for (int i = 0; i < labelList.size(); i++) {
                labelList.get(i).setBackground(Color.BLUE);
            }
        }
    }

    public int loadThreePreviewImages() {
        int xPos = 0, yPos = 0, hei = 76;
        imagePanel.removeAll();
        imagePanel.setLayout(null);
        if (thumbnails.length <= 3) {
            for (int i = 0; i < thumbnails.length; i++) {
                imagePanel.add(thumbnails[i]);
                thumbnails[i].setVisible(true);
                thumbnails[i].setBounds(xPos, yPos, 76, 76);
                xPos += 76;
            }
        } else {
            imagePanel.add(thumbnails[0]);
            thumbnails[0].setVisible(true);
            thumbnails[0].setBounds(xPos, yPos, 76, 76);
            xPos += 76;

            imagePanel.add(thumbnails[thumbnails.length / 2]);
            thumbnails[thumbnails.length / 2].setVisible(true);
            thumbnails[thumbnails.length / 2].setBounds(xPos, yPos, 76, 76);
            xPos += 76;

            imagePanel.add(thumbnails[thumbnails.length - 1]);
            thumbnails[thumbnails.length - 1].setVisible(true);
            thumbnails[thumbnails.length - 1].setBounds(xPos, yPos, 76, 76);
            xPos += 76;
        }
        return hei;
    }

    public int loadAllPreviewImages() {
        imagePanel.removeAll();
        imagePanel.setLayout(null);
        int xPos = 0, yPos = 0, hei = 0;

        for (int i = 0; i < thumbnails.length; i++) {
            imagePanel.add(thumbnails[i]);
            thumbnails[i].setVisible(true);
            thumbnails[i].setBounds(xPos, yPos, 76, 76);

            //To position the images
            if (xPos + 76 < 220) {
                xPos += 76;
            } else {
                hei += 76;
                xPos = 0;
                yPos += 76;
            }
        }
        if (totalImages % 3 != 0) {
            hei += 76;
        }
        imagePanel.setBounds(0, 23 + labelPanelHeight + 5, 230, hei);
        return hei;
    }

    private void alignComponents(int imagePanelHeight) {
        int locationY = 0, pos, size = 0;
        int modifiedPanel = Integer.parseInt(getName());
        JPanel parent = (JPanel) this.getParent();
        Component[] components = parent.getComponents();
        totalHeight = 23 + this.getLabelPanelHeight() + imagePanelHeight + 5;
        if (modifiedPanel != 0) {
            for (int i = 0; i < modifiedPanel; i++) {
                locationY += ((ViewerPreviewPanel) components[i]).getTotalHeight() + 5;
            }
        }
        this.setBounds(0, locationY, 230, totalHeight);
        parent.revalidate();
        parent.repaint();
        pos = ((ViewerPreviewPanel) components[modifiedPanel]).getY() + totalHeight + 5;
        for (int i = modifiedPanel + 1; i < components.length; i++) {
            ((ViewerPreviewPanel) components[i]).setLocation(0, pos);
            pos += ((ViewerPreviewPanel) components[i]).getTotalHeight() + 5;
            parent.revalidate();
            parent.repaint();
        }
        for (int i = 0; i < components.length; i++) {
            size += ((ViewerPreviewPanel) components[i]).getTotalHeight() + 5;
        }
        ((ImagePreviewPanel) ((JScrollPane) ((JViewport) ((JPanel) parent.getParent()).getParent()).getParent()).getParent()).paint(size);
    }

    public String getSeriesInstanceUid() {
        return seriesInstanceUid;
    }

    private void addMouseAdapter() {
        mouseClickAdapter1 = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                ApplicationContext.imgView.getImageToolbar().deselectLoopChk();
                ApplicationContext.imgView.getImageToolbar().resetCineTimer();
                String iuid = ((JLabel) me.getSource()).getName();
                if (ApplicationContext.selectedPanel.getComponentCount() == 1) {
                    if (sopUid == null) {
                        if (ApplicationContext.layeredCanvas.imgpanel.getSeriesUID().equals(seriesInstanceUid) && !ApplicationContext.layeredCanvas.imgpanel.isMultiFrame()) {
                            for (int i = 0; i < thumbnails.length; i++) {
                                if (thumbnails[i].getName().equals(iuid)) {
                                    ApplicationContext.layeredCanvas.imgpanel.selectImage(i);
                                    break;
                                }
                            }
                        } else {
                            for (int i = 0; i < thumbnails.length; i++) {
                                if (thumbnails[i].getName().equals(iuid)) {
                                    ApplicationContext.layeredCanvas.imgpanel.changeSeries(studyInstanceUid, seriesInstanceUid, iuid, i);
                                    break;
                                }
                            }
                        }
                    } else {
                        ApplicationContext.layeredCanvas.imgpanel.changeSeries(studyInstanceUid, seriesInstanceUid, iuid, 0);
                    }
                } else {
                    int clickedImg = 0;
                    for (int i = 0; i < thumbnails.length; i++) {
                        if (thumbnails[i].getName().equals(iuid)) {
                            clickedImg = i + 1;
                            int x = clickedImg % ApplicationContext.selectedPanel.getComponentCount();
                            if (seriesInstanceUid.equals(ApplicationContext.layeredCanvas.imgpanel.getSeriesUID())) {
                                if (x > 0) {
                                    setImageUpdatorArgs(clickedImg - x);
                                    ApplicationContext.layeredCanvas.imgpanel.displayImages(ApplicationContext.selectedPanel, clickedImg - x, true);
                                } else {
                                    setImageUpdatorArgs(clickedImg - ApplicationContext.selectedPanel.getComponentCount());
                                    ApplicationContext.layeredCanvas.imgpanel.displayImages(ApplicationContext.selectedPanel, clickedImg - ApplicationContext.selectedPanel.getComponentCount(), true);
                                }
                            } else {
                                ApplicationContext.layeredCanvas.imgpanel.changeSeries(studyInstanceUid, seriesInstanceUid, iuid, i, ApplicationContext.selectedPanel);
                            }
                            break;
                        }
                    }
                }
            }
        };
        for (int i = 0; i < thumbnails.length; i++) {
            thumbnails[i].addMouseListener(mouseClickAdapter1);
        }
    }

    private void setImageUpdatorArgs(int selectedImage) {
        if (totalImages > ApplicationContext.imgBuffer.getDefaultBufferSize()) {
            ApplicationContext.imgBuffer.clearBuffer();
            ApplicationContext.imageUpdator.terminateThread();
            int tiles = ApplicationContext.selectedPanel.getComponentCount();
            ApplicationContext.imageUpdator = new ImageGenerator(ApplicationContext.imgBuffer, ApplicationContext.imgBuffer.getImagePanelRef(), true);
            if (selectedImage - tiles > 0 && selectedImage + tiles < totalImages) {
                ApplicationContext.imageUpdator.setParameters(selectedImage - tiles, selectedImage + tiles + tiles, true);
            } else {
                ApplicationContext.imageUpdator.setParameters(totalImages - tiles, tiles + tiles, true);
            }
            ApplicationContext.imageUpdator.start();
        }
    }

    public void clearAllSelectedColor() {
        colorLabels(selectedButton);
    }

    public boolean isMultiframe() {
        return isMultiframe;
    }

    public String getSopUid() {
        return sopUid;
    }

    public void clearSelectedInstances() {
        for (int i = 0; i < selectedInstances.size(); i++) {
            if (totalImages <= 3 || selectedButton.equals(all) || selectedInstances.get(i) == 0 || (!selectedButton.equals(one) && selectedInstances.get(i) == totalImages / 2) || (!selectedButton.equals(one) && selectedInstances.get(i) == totalImages - 1)) {
                labelList.get(selectedInstances.get(i)).setBackground(Color.BLUE);
            } else {
                labelList.get(selectedInstances.get(i)).setBackground(Color.lightGray);
            }
        }
        selectedInstances.clear();
    }

    public void setSelectedInstance(int instanceNumber) {
        if (instanceNumber >= 0 && instanceNumber < totalImages) {
            selectedInstances.add(instanceNumber);
            labelList.get(instanceNumber).setBackground(Color.RED);
        }
    }
}
