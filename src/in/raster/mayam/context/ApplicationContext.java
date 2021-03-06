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
package in.raster.mayam.context;

import in.raster.mayam.delegates.Buffer;
import in.raster.mayam.delegates.CommunicationDelegate;
import in.raster.mayam.delegates.ReceiveDelegate;
import in.raster.mayam.facade.ApplicationFacade;
import in.raster.mayam.facade.Platform;
import in.raster.mayam.form.ImagePreviewPanel;
import in.raster.mayam.form.ImageView;
import in.raster.mayam.form.LayeredCanvas;
import in.raster.mayam.form.MainScreen;
import in.raster.mayam.form.VideoPanel;
import in.raster.mayam.form.ViewerPreviewPanel;
import in.raster.mayam.form.tab.component.ButtonTabComp;
import in.raster.mayam.models.QueryInformation;
import in.raster.mayam.models.treetable.TreeTable;
import in.raster.mayam.util.database.DatabaseHandler;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.dcm4che.util.DcmURL;
import org.dcm4che2.tool.dcm2xml.Dcm2Xml;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import uk.co.caprica.vlcj.component.EmbeddedMediaListPlayerComponent;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;

/**
 *
 * @author Devishree
 * @version 2.0
 */
public class ApplicationContext {
    //Reference Objects and Variables    

    public static MainScreen mainScreenObj = null;
    public static Locale currentLocale = null;
    public static ResourceBundle currentBundle = null;
    public static CommunicationDelegate communicationDelegate = new CommunicationDelegate();
    public static String applicationName = "MayamDerm";
    public static String[] listenerDetails;
    public static String activeTheme = null;
    public static ArrayList<JButton> searchButtons = new ArrayList<JButton>();//To keep track of instant query buttons
    public static String currentServer;
    public static DcmURL currentQueryUrl;
    public static Font textFont = new Font("Lucida Grande", Font.BOLD, 12);
    public static Font labelFont = new Font("Lucida Grande", Font.BOLD, 14);
    public static JLabel appNameLabel = null;
    public static JButton moreButton = null;
    public static boolean serverStarted = false;
    public static boolean isLocal = true, isJnlp = false;
    public static TreeTable currentTreeTable = null;
    public static ArrayList<QueryInformation> queryInformations = new ArrayList<QueryInformation>();//To preserve the studies found and selected button details of the server
    //For viewer
    public static ImageView imgView = null;
    public static JPanel selectedPanel = null;
    public static LayeredCanvas layeredCanvas = null;
    public static JTabbedPane tabbedPane;
    public static Buffer buffer = null;
    static ReceiveDelegate rcvDelegate;
    public static String DATE_FORMAT="dd/MM/yyyy";
    public static DatabaseHandler databaseRef = DatabaseHandler.getInstance();
    
    public static String getAppDirectory() {
        return Platform.getAppDirectory(applicationName).getAbsolutePath();
    }

    public static void setAppLocale() {
        databaseRef.openOrCreateDB();
        listenerDetails = databaseRef.getListenerDetails();
        activeTheme = databaseRef.getActiveTheme();
        startListening();
        String[] appLocale = databaseRef.getActiveLanguage();
        currentLocale = new Locale(appLocale[2], appLocale[0]);
        currentBundle = ResourceBundle.getBundle("in/raster/mayam/form/i18n/Bundle", currentLocale);
    }

    /*
     * Used to disable the quick search buttons when the local tab was selected
     */
    public static void setButtonsDisabled() {
        for (int i = 0; i < searchButtons.size(); i++) {
            searchButtons.get(i).setVisible(false);
        }
    }

    /*
     * Will creates the ImageView Form
     */
    public static void createImageView() {
        imgView = new ImageView();
        GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        if (screenDevices.length > 1) {
            imgView.setLocation((screenDevices[0].getDisplayMode().getWidth()) + 1, 0);
        }
        imgView.setVisible(true);
    }

    /*
     * Returns True if the ImageView form exist
     */
    public static boolean isImageViewExist() {
        return imgView != null;
    }

    public static void applyLocaleChange() {
        appNameLabel.setText(ApplicationContext.currentBundle.getString("MainScreen.appNameLabel.text"));
        if (moreButton != null) {
            moreButton.setText(ApplicationContext.currentBundle.getString("MainScreen.moreButton.text"));
        }
    }

    public static void setVideoThumbnailIdentification(String studyUid) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (((JPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getRightComponent()) instanceof VideoPanel && studyUid.equals(((JPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getRightComponent()).getName())) {
                VideoPanel comp = ((VideoPanel) ((JSplitPane) ApplicationContext.tabbedPane.getSelectedComponent()).getRightComponent());
                ((ImagePreviewPanel) ((JSplitPane) ApplicationContext.tabbedPane.getSelectedComponent()).getLeftComponent()).setVideoIdentification(comp);
                break;
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public static void ImageView(String patientName, String studyUid, ImagePreviewPanel viewerPreview) {
        if (imgView == null) {
            createImageView();
        }
        if (tabbedPane != null) {
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                if (((JPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getRightComponent()).getName().equals(studyUid)) {
                    tabbedPane.setSelectedIndex(i);
                    return;
                }
            }
        }
        JPanel container = new JPanel(new GridLayout(1, 1));
        container.setBackground(Color.BLACK);
        container.setName(studyUid);
        JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, viewerPreview, container);
        splitpane.setName(patientName);
        splitpane.setOneTouchExpandable(true);
        splitpane.setDividerLocation(270);
        splitpane.setDividerSize(15);
        viewerPreview.setMinimumSize(new Dimension(270, splitpane.getHeight()));

        imgView.jTabbedPane1.add(splitpane);
        ButtonTabComp tabComp = new ButtonTabComp(ApplicationContext.imgView.jTabbedPane1);
        imgView.jTabbedPane1.setTabComponentAt(ApplicationContext.imgView.jTabbedPane1.getTabCount() - 1, tabComp);
        imgView.jTabbedPane1.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        if (tabbedPane == null) {
            tabbedPane = (JTabbedPane) tabComp.getTabbedComponent();
        }
        tabbedPane.setSelectedIndex(ApplicationContext.tabbedPane.getTabCount() - 1);
        if (isJnlp) {
            ApplicationFacade.hideSplash();
        }
    }

    public static void createLayeredCanvas(String filePath, String studyInstanceUID, int instanceBufferFrom, boolean setFirstPreviewRef) {
        File dicomFile = new File(filePath);
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (((JPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getRightComponent()).getName().equals(studyInstanceUID) && ((JPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getRightComponent()).getComponentCount() == 0) {
                if (!filePath.contains("_V")) {
                    selectedPanel = new JPanel(new GridLayout(1, 1));
                    layeredCanvas = new LayeredCanvas(new File(filePath), instanceBufferFrom, false);
                    selectedPanel.add(layeredCanvas);
                    ((JPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getRightComponent()).add(selectedPanel);
                    imgView.repaint();
                    if (!layeredCanvas.imgpanel.isMultiFrame() && setFirstPreviewRef) {
                        layeredCanvas.setPreviewRef((ViewerPreviewPanel) ((ImagePreviewPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getLeftComponent()).parent.getComponent(0));
                        layeredCanvas.setSelectedThumbnails();
                    }
                    layeredCanvas.canvas.setSelectionColoring();
                    imgView.readAnnotaionDetails(studyInstanceUID, dicomFile.getParentFile().getParentFile());
                    layeredCanvas.imgpanel.setCurrentSeriesAnnotation();
                    imgView.getImageToolbar().setWindowing();
                    break;
                } else {
                    try {
                        VideoPanel videoPanel = new VideoPanel();
                        videoPanel.setName(studyInstanceUID);
                        EmbeddedMediaPlayerComponent mediaPlayerComp = null;
                        try {
                            mediaPlayerComp = new EmbeddedMediaListPlayerComponent();
                        } catch (NoClassDefFoundError ex) {
                            //ignore
                        }
                        videoPanel.setMediaPlayer(mediaPlayerComp);
                        videoPanel.setUniqueIdentifier(filePath.substring(filePath.split("_")[0].lastIndexOf(File.separator) + 1, filePath.indexOf("_")));
                        videoPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 138, 0)));
                        ((JSplitPane) tabbedPane.getComponentAt(i)).setRightComponent(videoPanel);
                        mediaPlayerComp.getMediaPlayer().playMedia(filePath);
                        videoPanel.startTimer();
                        ApplicationContext.imgView.getImageToolbar().disableImageTools();
                        setVideoThumbnailIdentification(studyInstanceUID);
                        selectedPanel = videoPanel;
                        break;
                    } catch (Exception ex) {
                        if (Desktop.isDesktopSupported()) {
                            try {
                                if (tabbedPane == null || tabbedPane.getTabCount() <= 1) {
                                    imgView.setVisible(false);
                                    imgView.dispose();
                                    imgView = null;
                                }
                                Desktop.getDesktop().open(new File(filePath));
                            } catch (IOException ex1) {
                                Logger.getLogger(ApplicationContext.class.getName()).log(Level.SEVERE, null, ex1);
                            }
                        }
                    }
                }
            }
        }
    }

    public static void createVideoCanvas(String studyUid, String iuid) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (((JPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getRightComponent()).getName().equals(studyUid) && ((JPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getRightComponent()).getComponentCount() == 0) {
                try {
                    VideoPanel videoPanel = new VideoPanel();
                    videoPanel.setName(studyUid);
                    EmbeddedMediaPlayerComponent mediaPlayerComp = null;
                    try {
                        mediaPlayerComp = new EmbeddedMediaListPlayerComponent();
                    } catch (NoClassDefFoundError ex) {
                    }
                    videoPanel.setMediaPlayer(mediaPlayerComp);
                    videoPanel.setUniqueIdentifier(iuid);
                    videoPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 138, 0)));
                    ((JSplitPane) tabbedPane.getComponentAt(i)).setRightComponent(videoPanel);
                    ApplicationContext.imgView.getImageToolbar().disableImageTools();
                    setVideoThumbnailIdentification(studyUid);
                    selectedPanel = videoPanel;
                    break;
                } catch (Exception e) {
                    if (tabbedPane == null || tabbedPane.getTabCount() <= 1) {
                        imgView.setVisible(false);
                        imgView.dispose();
                        imgView = null;
                        selectedPanel = null;
                    }
                }
            }
        }
    }

    public static void displayPreview(String studyInstanceUID, String seriesInstanceUid) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (((JPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getRightComponent()).getName().equals(studyInstanceUID)) {
                ((ImagePreviewPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getLeftComponent()).displayPreiew(seriesInstanceUid);
                break;
            }
        }
    }

    public static void displayAllPreviews() {
        ((ImagePreviewPanel) ((JSplitPane) tabbedPane.getSelectedComponent()).getLeftComponent()).displayAllPreviews();
    }

    public static void createVideoPreviews(String studyUid) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            if (((JPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getRightComponent()).getName().equals(studyUid)) {
                ApplicationContext.databaseRef.update("study", "NoOfSeries", ((ImagePreviewPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getLeftComponent()).parent.getComponentCount(), "StudyInstanceUID", studyUid);
                ((ImagePreviewPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getLeftComponent()).displayVideoPreviews();
                break;
            }
        }
    }

    public static void setCorrespondingPreviews() {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            try {
                if (((JPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getRightComponent()).getName().equals(layeredCanvas.imgpanel.getStudyUID())) {
                    ((ImagePreviewPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getLeftComponent()).iteratePreviews();
                    break;
                }
            } catch (NullPointerException ex) {
                //Occurs when there is no layered canvas i.e video file
            }
        }
    }

    public static void setAllSeriesIdentification(String studyUid) {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            try {
                if (((JPanel) ((JSplitPane) tabbedPane.getComponentAt(i)).getRightComponent()).getName().equals(layeredCanvas.imgpanel.getStudyUID())) {
                    JPanel comp = ((JPanel) ((JSplitPane) ApplicationContext.tabbedPane.getSelectedComponent()).getRightComponent());
                    for (int j = 0; j < comp.getComponentCount(); j++) {
                        JPanel seriesLevelPanel = (JPanel) comp.getComponent(j);
                        for (int k = 0; k < seriesLevelPanel.getComponentCount(); k++) {
                            ((LayeredCanvas) seriesLevelPanel.getComponent(k)).clearThumbnailSelection();
                        }
                    }
                    for (int j = 0; j < comp.getComponentCount(); j++) {
                        JPanel seriesLevelPanel = (JPanel) comp.getComponent(j);
                        for (int k = 0; k < seriesLevelPanel.getComponentCount(); k++) {
                            if (((LayeredCanvas) seriesLevelPanel.getComponent(k)).isVisible()) {
                                ((LayeredCanvas) seriesLevelPanel.getComponent(k)).setSelectedThumbnails();
                            }
                        }
                    }
                    break;
                }
            } catch (NullPointerException ex) {
                //ignore : Occurs when there is no layered canvas i.e video file
            }
        }
    }

    public static void convertVideo(String fileToConvert, String dest, String iuid) {
        File videoFile = new File(dest + File.separator + "video.xml");
        videoFile.getParentFile().mkdirs();
        try {
            videoFile.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(ApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        Dcm2Xml.main(new String[]{fileToConvert, "-X", "-o", videoFile.getAbsolutePath()});


        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(videoFile);
            NodeList elementsByTagName1 = doc.getElementsByTagName("item");
            for (int i = 0; i < elementsByTagName1.getLength(); i++) {
                NamedNodeMap attributes = elementsByTagName1.item(i).getAttributes();
                if (attributes.getNamedItem("src") != null) {
                    videoFile = new File(dest + File.separator + attributes.getNamedItem("src").getNodeValue());
                    videoFile.renameTo(new File(videoFile.getAbsolutePath() + ".mpg"));
                    ApplicationContext.databaseRef.update("image", "FileStoreUrl", videoFile.getAbsolutePath() + ".mpg", "SopUID", iuid);
                }
            }
        } catch (SAXException ex) {
            Logger.getLogger(ApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ApplicationContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void startListening() {
        rcvDelegate = new ReceiveDelegate();
        try {
            rcvDelegate.start();
            System.out.println("Start Server listening on port " + rcvDelegate.getPort());
        } catch (Exception ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void stopListening() {
        System.out.println("Stop server listening on port " + rcvDelegate.getPort());
        rcvDelegate.stop();
    }

    public static void setCurrentLocale(Locale currentLocale) {
        ApplicationContext.currentLocale = currentLocale;
        ApplicationContext.currentBundle = ResourceBundle.getBundle("in/raster/mayam/form/i18n/Bundle", currentLocale);
    }
}