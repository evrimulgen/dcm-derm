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
package in.raster.mayam.util.database;

import com.jme3.math.Vector3f;
import in.raster.mayam.body.CoordBean;
import in.raster.mayam.context.ApplicationContext;
import in.raster.mayam.delegates.ThumbnailConstructor;
import in.raster.mayam.facade.ApplicationFacade;
import in.raster.mayam.models.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import org.apache.derby.jdbc.EmbeddedSimpleDataSource;
import org.dcm4che.dict.Tags;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;

/**
 *
 * @author Devishree
 * @version 2.0
 */
public class DatabaseHandler {
    //Named Constants for Database,driver,protocol

    private static final String driver = "org.apache.derby.jdbc.EmbeddedDriver", protocol = "jdbc:derby:", databasename = "mayamdb";
    //Named Constants for username and password of Database 
    private static final String username = "mayam", password = "mayam";
    //Database Connection creator and executor variables 
    private Connection conn;
    private Statement statement;
    //Boolean variables 
    private boolean dbExists = false;
    //Datasouce declaration
    private EmbeddedSimpleDataSource ds;
    //Other variables
    DateFormat dateFormat = new SimpleDateFormat(ApplicationContext.DATE_FORMAT), timeFormat = new SimpleDateFormat("kk:mm:ss");
    ExecutorService executor = null;

    public static DatabaseHandler getInstance() {
        return new DatabaseHandler();
    }

    public boolean checkDBexists(String tem) {
        File[] listFiles = new File(tem).listFiles();
        for (int l = 0; l < listFiles.length; l++) {
            if (listFiles[l].getName().equalsIgnoreCase(databasename)) {
                return true;
            }
        }
        return false;
    }

    public void openOrCreateDB() {
        try {
            System.setProperty("derby.system.home", ApplicationContext.getAppDirectory());
            try {
                Class.forName(driver).newInstance();
            } catch (InstantiationException e) {
                StringWriter str = new StringWriter();
                e.printStackTrace(new PrintWriter(str));
            } catch (IllegalAccessException e) {
                StringWriter str = new StringWriter();
                e.printStackTrace(new PrintWriter(str));
            } catch (ClassNotFoundException e) {
                StringWriter str = new StringWriter();
                e.printStackTrace(new PrintWriter(str));
            }
            try {
                ds = new org.apache.derby.jdbc.EmbeddedSimpleDataSource();
                ds.setDatabaseName(databasename);
            } catch (NoClassDefFoundError e) {
                System.err.println("ERROR: ClassNotFoundException:" + e.getMessage());
                ApplicationFacade.exitApp("ERROR: ClassNotFoundException:" + e.getMessage() + ": Exiting the program");
            }
            openConnection();
            statement = conn.createStatement();
            if (!dbExists) {
                createTables();
                insertDefaultLisenerDetails();
                insertModalities();
                insertDefaultPresets();
                insertDefaultThemes();
                insertButton(new ButtonsModel("All", "", "", "", false, false));
                insertDefaultLocales();
                insertMiscellaneous();
                conn.commit();
            } else {
                upgradeDatabase();
            }
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            StringWriter str = new StringWriter();
            e.printStackTrace(new PrintWriter(str));
        }
    }

    private void openConnection() {
        this.dbExists = checkDBexists(ApplicationContext.getAppDirectory());
        try {
            if (!dbExists) {
                conn = DriverManager.getConnection(protocol + ApplicationContext.getAppDirectory() + File.separator + databasename + ";create=true", username, password);
            } else {
                conn = DriverManager.getConnection(protocol + databasename + ";create=false", username, password);
            }
        } catch (Exception e) {
            if (dbExists && conn == null) {
                System.err.println("ERROR: Database connection cannot be created:" + e.getMessage());
                System.err.println("An instance of application is already running");
                ApplicationFacade.exitApp("An instance of Mayam is already running: Exiting the program");
            }
        }
    }

    private void createTables() {
        try {
            statement.executeUpdate("create table patient (PatientId varchar(255) NOT NULL CONSTRAINT PatientId_pk PRIMARY KEY,"+"PatientName varchar(255)," + "PatientBirthDate varchar(30)," + "PatientSex varchar(10))");
            statement.executeUpdate("create table study (StudyInstanceUID varchar(255) NOT NULL CONSTRAINT StudyInstanceUID_pk PRIMARY KEY," + "StudyDate varchar(30)," + "StudyTime varchar(30)," + "AccessionNo varchar(50)," + "RefferingPhysicianName varchar(255)," + "StudyDescription varchar(80)," + "ModalitiesInStudy varchar(10)," + "NoOfSeries integer," + "NoOfInstances integer," + "RecdImgCnt integer," + "SendImgCnt integer," + "RetrieveAET varchar(50)," + "StudyType varchar(75)," + "DownloadStatus boolean," + "PatientId varchar(255), foreign key(PatientId) references Patient(PatientId),"+"StudyId varchar(255) NOT NULL, visible integer)");
            statement.executeUpdate("create table series (SeriesInstanceUID varchar(255) NOT NULL CONSTRAINT SeriesInstanceUID_pk PRIMARY KEY," + "SeriesNo varchar(50)," + "SeriesDate varchar(30)," + "SeriesTime varchar(30)," + "Modality varchar(10)," + "SeriesDescription varchar(100)," + "BodyPartExamined varchar(100)," + "InstitutionName varchar(255)," + "NoOfSeriesRelatedInstances integer," + "PatientId varchar(255), foreign key(PatientId) references Patient(PatientId)," + "StudyInstanceUID varchar(255),foreign key(StudyInstanceUID) references Study(StudyInstanceUID))");
            statement.executeUpdate("create table image (SopUID varchar(255) NOT NULL CONSTRAINT SopUID_pk PRIMARY KEY," + "SOPClassUID varchar(255)," + "InstanceNo integer," + "multiframe boolean," + "totalframe varchar(50)," + "SendStatus varchar(50)," + "ForwardDateTime varchar(30)," + "ReceivedDateTime varchar(30)," + "ReceiveStatus varchar(50)," + "FileStoreUrl varchar(1000)," + "SliceLocation integer," + "EncapsulatedDocument varchar(50)," + "ThumbnailStatus boolean," + "FrameOfReferenceUID varchar(128)," + "ImagePosition varchar(64)," + "ImageOrientation varchar(128)," + "ImageType varchar(30)," + "PixelSpacing varchar(64)," + "SliceThickness varchar(16)," + "NoOfRows integer," + "NoOfColumns integer," + "ReferencedSopUid varchar(128)," + "PatientId varchar(255),foreign key(PatientId) references Patient(PatientId)," + "StudyInstanceUID varchar(255),foreign key(StudyInstanceUID) references Study(StudyInstanceUID)," + "SeriesInstanceUID varchar(255),foreign key(SeriesInstanceUID) references Series(SeriesInstanceUID))");
            statement.executeUpdate("create table listener (pk integer primary key GENERATED ALWAYS AS IDENTITY,aetitle varchar(255),port varchar(255),storagelocation varchar(255))");
            statement.executeUpdate("create table servers(pk integer primary key GENERATED ALWAYS AS IDENTITY,logicalname varchar(255) NOT NULL UNIQUE,aetitle varchar(255),hostname varchar(255),port integer,retrievetype varchar(100),showpreviews boolean,wadocontext varchar(100),wadoport integer,wadoprotocol varchar(100),retrievets varchar(255))");
            statement.executeUpdate("create table theme(pk integer primary key GENERATED ALWAYS AS IDENTITY,name varchar(255),status boolean)");
            statement.executeUpdate("create table buttons(pk integer primary key GENERATED ALWAYS AS IDENTITY,buttonno integer,description varchar(255),modality varchar(255),datecriteria varchar(255),timecriteria varchar(255),iscustomdate boolean,iscustomtime boolean)");
            statement.executeUpdate("create table modality(pk integer primary key GENERATED ALWAYS AS IDENTITY,logicalname varchar(255),shortname varchar(255),status boolean)");
            statement.executeUpdate("create table presets(pk integer primary key GENERATED ALWAYS AS IDENTITY,presetname varchar(255),windowwidth numeric,windowlevel numeric,modality_fk integer,foreign key(modality_fk) references modality(pk))");
            statement.executeUpdate("create table locale (pk integer primary key GENERATED ALWAYS AS IDENTITY,countrycode varchar(10),country varchar(255),languagecode varchar(10),language varchar(255),localeid varchar(255),status boolean)");
            statement.executeUpdate("create table miscellaneous(Loopback boolean,JNLPRetrieveType varchar(25),AllowDynamicRetrieveType boolean)");
            statement.executeUpdate("create table emrservers(pk integer primary key GENERATED ALWAYS AS IDENTITY,logicalname varchar(255) NOT NULL UNIQUE,hostname varchar(255),port integer,subprotocol varchar(255))"); //MDIAZ
            statement.executeUpdate("create table coords (patientId varchar(255) NOT NULL, sopuid varchar(255) NOT NULL, x float, y float, z float, framenumber integer NOT NULL, thumb BLOB NOT NULL)"); //MDIAZ
            statement.executeUpdate("create table patient_sopuid (patientId varchar(255) NOT NULL, sopuid varchar(255) NOT NULL)"); //MDIAZ - relaciona un id local de paciente con un SOP Instance UID
            statement.executeUpdate("create table tracking (trackId integer primary key GENERATED ALWAYS AS IDENTITY, description varchar(255), createDate varchar(30),patientId varchar(255), foreign key(patientId) references Patient(patientId))"); //MDIAZ
            statement.executeUpdate("create table tracking_study(trackId integer, foreign key(trackId) references tracking(trackId), studyUID varchar(255), orderNumber integer)"); //MDIAZ
            statement.executeUpdate("create table study_results(sr_id integer primary key GENERATED ALWAYS AS IDENTITY, patientId varchar(255), foreign key(patientId) references patient(patientId), studyIUID varchar(255), foreign key(studyIUID) references study(StudyInstanceUID), valueA varchar(10), valueB varchar(10), valueC varchar(10), valueD varchar(10), TDS varchar(10))"); //MDIAZ
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkRecordExists(String tablename, String fieldname, String compareWith) {
        try {
            ResultSet rs = conn.createStatement().executeQuery("select count(" + fieldname + ") from " + tablename + " where " + fieldname + " = '" + compareWith.trim() + "'");
            rs.next();
            if (rs.getInt(1) > 0) {
                rs.close();
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
        } 
        return false;
    }

    /**
     * MDIAZ
     * @param studyInstanceUID
     * @return 
     */
    public boolean checkStudyRecordExists(String studyInstanceUID) {
        try {
            ResultSet rs = conn.createStatement().executeQuery("select count(*) from STUDY where StudyInstanceUID = '" + studyInstanceUID.trim() + "' and Visible=1");
            rs.next();
            if (rs.getInt(1) > 0) {
                rs.close();
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    public void upgradeDatabase() {
        try {
            ResultSet tableInfo = conn.createStatement().executeQuery("select * from miscellaneous");
            ResultSetMetaData metaData = tableInfo.getMetaData();
            tableInfo.close();
            if (metaData.getColumnCount() == 1) {
                //From version 2.0 to 2.1
                conn.createStatement().execute("alter table miscellaneous add column JNLPRetrieveType varchar(25)");
                conn.createStatement().execute("alter table miscellaneous add column AllowDynamicRetrieveType boolean");
                conn.createStatement().execute("alter table image add column SOPClassUID varchar(255)");
                conn.createStatement().execute("update miscellaneous set JNLPRetrieveType='C-GET',AllowDynamicRetrieveType=false");
                conn.createStatement().execute("update listener set StorageLocation='" + ApplicationContext.getAppDirectory() + File.separator + "archive'");
                addNewLocale("it_IT");
            }
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //insertions
    private void insertDefaultLisenerDetails() throws SQLException {
        conn.createStatement().execute("insert into listener(aetitle,port,storagelocation) values('MAYAM','1025','" + ApplicationContext.getAppDirectory() + File.separator + "archive')");
    }

    private void insertModalities() throws SQLException {
        String modality[] = {"CT", "MR", "XA", "CR", "SC", "NM", "RF", "DX", "US", "PX", "OT", "DR", "SR", "MG", "RG"};
        for (int i = 0; i < modality.length; i++) {
            conn.createStatement().execute("insert into modality(logicalname,shortname,status) values('Dummy','" + modality[i] + "',true)");
        }
    }

    private void insertDefaultPresets() throws SQLException {
        ResultSet rs = conn.createStatement().executeQuery("select pk from modality where shortname='CT'");
        rs.next();
        int pk = rs.getInt("pk");
        rs.close();
        conn.createStatement().execute("insert into presets(presetname,windowwidth,windowlevel,modality_fk)values('CT Abdomen',40,350," + pk + ")");
        conn.createStatement().execute("insert into presets(presetname,windowwidth,windowlevel,modality_fk)values('CT Lung',1500,-600," + pk + ")");
        conn.createStatement().execute("insert into presets(presetname,windowwidth,windowlevel,modality_fk)values('CT Brain',80,40," + pk + ")");
        conn.createStatement().execute("insert into presets(presetname,windowwidth,windowlevel,modality_fk)values('CT Bone',2500,480," + pk + ")");
        conn.createStatement().execute("insert into presets(presetname,windowwidth,windowlevel,modality_fk)values('CT Head/Neck',350,90," + pk + ")");
    }

    private void insertDefaultThemes() throws SQLException {
        conn.createStatement().execute("insert into theme(name,status)values('Nimrod',true)");
        conn.createStatement().execute("insert into theme(name,status)values('Motif',false)");
        conn.createStatement().execute("insert into theme(name,status)values('System',false)");
    }

    public void insertServer(ServerModel serverModel) {
        try {
            conn.createStatement().execute("insert into servers(logicalname,aetitle,hostname,port,retrievetype,showpreviews,wadocontext,wadoport,wadoprotocol,retrievets) values('" + serverModel.getDescription() + "','" + serverModel.getAeTitle() + "','" + serverModel.getHostName() + "'," + serverModel.getPort() + ",'" + serverModel.getRetrieveType() + "'," + serverModel.isPreviewEnabled() + ",'" + serverModel.getWadoURL() + "'," + serverModel.getWadoPort() + ",'" + serverModel.getWadoProtocol() + "','" + serverModel.getRetrieveTransferSyntax() + "')");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * MDIAZ
     * 
     * @param serverModel 
     */
    public void insertEMRServer(EMRServerModel serverModel) {
        try {
            conn.createStatement().execute("insert into emrservers(logicalname,hostname,port,subprotocol) values('" + serverModel.getDescription() + "','" + serverModel.getHostName() + "'," + serverModel.getPort() + ",'" + serverModel.getSubprotocol() + "')");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertButton(ButtonsModel buttonsModel) {
        try {
            ResultSet noInfo = conn.createStatement().executeQuery("select max(buttonno) from buttons");
            noInfo.next();
            conn.createStatement().execute("insert into buttons(buttonno,description,modality,datecriteria,timecriteria,iscustomdate,iscustomtime) values(" + (noInfo.getInt(1) + 1) + ",'" + buttonsModel.getButtonlable() + "','" + buttonsModel.getModality() + "','" + buttonsModel.getStudyDate() + "','" + buttonsModel.getStudyTime() + "','" + buttonsModel.isCustomDate() + "','" + buttonsModel.isCustomTime() + "')");
            noInfo.close();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertPreset(PresetModel presetModel, String modality) {
        try {
            ResultSet modalityInfo = conn.createStatement().executeQuery("select pk from modality where shortname='" + modality + "'");
            modalityInfo.next();
            conn.createStatement().execute("insert into presets(presetname,windowwidth,windowlevel,modality_fk)values('" + presetModel.getPresetName() + "'," + presetModel.getWindowWidth() + "," + presetModel.getWindowLevel() + "," + modalityInfo.getInt("pk") + ")");
            modalityInfo.close();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertDefaultLocales() throws SQLException {
        conn.createStatement().execute("insert into locale (countrycode,country,languagecode,language,localeid,status) values('AR','Argentina','es','Español','es_AR',true)");
        addNewLocale("en_GB");
        addNewLocale("ta_IN");
        addNewLocale("it_IT");
    }

    private void addNewLocale(String localeid) throws SQLException {
        String languagecode = "", countrycode = "";
        String languageAndCountry[] = localeid.split("_");
        if (languageAndCountry.length >= 2) {
            languagecode = languageAndCountry[0];
            countrycode = languageAndCountry[1];
        }
        Locale locale = new Locale(languagecode, countrycode);
        String language = locale.getDisplayLanguage();
        String country = locale.getDisplayCountry();
        insertLocale(language, country, languagecode, countrycode, localeid);
    }

    private void insertLocale(String language, String country, String languagecode, String countrycode, String localeid) throws SQLException {
        conn.createStatement().execute("insert into locale(countrycode,country,languagecode,language,localeid,status) values('" + countrycode + "','" + country + "','" + languagecode + "','" + language + "','" + localeid + "',false)");
    }

    private void insertMiscellaneous() throws SQLException {
        conn.createStatement().execute("insert into miscellaneous(Loopback,JNLPRetrieveType,AllowDynamicRetrieveType) values(true,'C-GET',false)");
    }

    public synchronized void writeDatasetInfo(DicomObject dataset, String localPatientId, String filePath, boolean updateMainScreen) {
        try {
            insertPatientInfo(dataset, localPatientId, updateMainScreen);
            insertStudyInfo(dataset, false, localPatientId);
            insertSeriesInfo(dataset, updateMainScreen, localPatientId);
            insertImageInfo(dataset, filePath, false, updateMainScreen, localPatientId);
        } catch (Exception e) {
            System.out.println("Failed to update Patient info : " + e.getMessage());
        }
        if (ApplicationContext.mainScreenObj != null) {
            if (dataset.getString(Tags.NumberOfFrames) != null) {
                ApplicationContext.mainScreenObj.setProgressIndeterminate();
            }
            ApplicationContext.mainScreenObj.incrementProgressValue();
        }
    }

    /**
     * MDIAZ - Custom
     * @param dataset
     * @param localPatientId
     * @param updateMainScreen 
     */
    private void insertPatientInfo(DicomObject dataset, String localPatientId, boolean updateMainScreen) {
        if (!(checkRecordExists("patient", "PatientId", localPatientId/*dataset.getString(Tags.PatientID)*/))) {
            String date = "";
            if (dataset.getString(Tags.PatientBirthDate) != null && dataset.getString(Tags.PatientBirthDate).length() > 0) {
                date = (dataset.getDate(Tags.PatientBirthDate) != null) ? dateFormat.format(dataset.getDate(Tags.PatientBirthDate)) : "";
            }
            try {
                PreparedStatement insertStmt = conn.prepareStatement("insert into patient values(?,?,?,?)");
                insertStmt.setString(1, localPatientId);
                insertStmt.setString(2, dataset.getString(Tags.PatientName));
                insertStmt.setString(3, date);
                insertStmt.setString(4, dataset.getString(Tags.PatientSex));
                insertStmt.execute();
                insertStmt.close();
                conn.commit();
            } catch (SQLException ex) {
                System.out.println("Exception in inserting patient info : " + ex.getMessage());
                return;
            }
        }
        
        try { //si existe paciente
            ResultSet rs = conn.createStatement().executeQuery("select count(*) from patient_sopuid where patientId = '" + 
                    localPatientId + "' and sopUID='" + dataset.getString(Tags.SOPInstanceUID) + "'");
            rs.next();
            if (rs.getInt(1) == 0) { //si no existe estudio
                PreparedStatement insertStmt = conn.prepareStatement("insert into patient_sopuid values(?,?)");
                insertStmt.setString(1, localPatientId);
                insertStmt.setString(2, dataset.getString(Tags.SOPInstanceUID));
                insertStmt.execute();
                insertStmt.close();
                rs.close();
                conn.commit();
            }
        } catch (SQLException ex) {
               System.out.println("Exception in inserting patient info : " + ex.getMessage());
        }

        if (executor == null && (updateMainScreen || !ApplicationContext.isJnlp && !ApplicationContext.mainScreenObj.isInProgress())) {
            executor = Executors.newFixedThreadPool(3);
        }
    }

    /**
     * MDIAZ - Custom
     * @param dataset
     * @param saveAsLink
     * @param localPatientId 
     */
    private void insertStudyInfo(DicomObject dataset, boolean saveAsLink, String localPatientId) {
        if (!(checkRecordExists("study", "StudyInstanceUID", dataset.getString(Tags.StudyInstanceUID)))) {
            try {
                String date = (dataset.getDate(Tags.StudyDate) != null && dataset.getString(Tags.StudyDate).length() > 0) ? dateFormat.format(dataset.getDate(Tags.StudyDate)) : "";
                String time = (dataset.getDate(Tags.StudyTime) != null && dataset.getString(Tags.StudyTime).length() > 0) ? timeFormat.format(dataset.getDate(Tags.StudyTime)) : "";
                String accessionNo = (dataset.getString(Tags.AccessionNumber) != null && dataset.getString(Tags.AccessionNumber).length() > 0) ? dataset.getString(Tags.AccessionNumber) : "";
                String refName = (dataset.getString(Tags.ReferringPhysicianName) != null && dataset.getString(Tags.ReferringPhysicianName).length() > 0) ? dataset.getString(Tags.ReferringPhysicianName) : "";
                String retAe = (dataset.getString(Tags.RetrieveAET) != null && dataset.getString(Tags.RetrieveAET).length() > 0) ? dataset.getString(Tags.RetrieveAET) : "";
                String studyDesc = (dataset.getString(Tags.StudyDescription) != null && dataset.getString(Tags.StudyDescription).length() > 0) ? dataset.getString(Tags.StudyDescription) : "";
                String studyId = (dataset.getString(Tags.StudyID) != null && dataset.getString(Tags.StudyID).length() > 0) ? dataset.getString(Tags.StudyID) : "";
                String studyType = saveAsLink ? "link" : "local";
                conn.createStatement().execute("insert into study values('" + dataset.getString(Tags.StudyInstanceUID) + "','" + date + "','" + time + "','" + accessionNo + "','" + refName + "','" + studyDesc.replace('/', ' ') + "','" + dataset.getString(Tags.Modality) + "'," + 0 + "," + 0 + "," + 0 + "," + 0 + ",'" + retAe + "','" + studyType + "'," + "false,'" + localPatientId/*dataset.getString(Tags.PatientID)*/ + "','"+studyId+"',"+ 1 +")");
                conn.commit();
            } catch (SQLException ex) {
                System.out.println("Exception in inserting study info: "+ ex.getMessage());
            }
        } else {
            try {
                conn.createStatement().executeUpdate("update study set visible=1 where studyinstanceuid='"+dataset.getString(Tags.StudyInstanceUID)+"' and patientId='"+localPatientId+"'");
                conn.commit();
            } catch(SQLException ex) {
                System.out.println("Exception in inserting study info: "+ ex.getMessage());
            }
        }
    }

    /**
     * MDIAZ - Custom
     * @param dataset
     * @param updateMainScreen
     * @param localPatientId 
     */
    private void insertSeriesInfo(final DicomObject dataset, final boolean updateMainScreen, String localPatientId) {
        if (!(checkRecordExists("series", "SeriesInstanceUID", dataset.getString(Tags.SeriesInstanceUID)))) {
            String date = (dataset.getString(Tags.SeriesDate) != null && dataset.getString(Tags.SeriesDate).length() > 0) ? dateFormat.format(dataset.getDate(Tags.SeriesDate)) : "";
            String time = (dataset.getString(Tags.SeriesTime) != null && dataset.getString(Tags.SeriesTime).length() > 0) ? timeFormat.format(dataset.getDate(Tags.SeriesTime)) : "";
            int numSeries = (dataset.getString(Tags.NumberOfSeriesRelatedInstances) != null && dataset.getString(Tags.NumberOfSeriesRelatedInstances).length() > 0) ? dataset.getInt(Tags.NumberOfSeriesRelatedInstances) : 0;
            String institution = (dataset.getString(Tags.InstitutionName) != null && dataset.getString(Tags.InstitutionName).length() > 0) ? dataset.getString(Tags.InstitutionName) : "";
            String seriesNo = (dataset.getString(Tags.SeriesNumber) != null && dataset.getString(Tags.SeriesNumber).length() > 0) ? dataset.getString(Tags.SeriesNumber) : "";
            String modality = (dataset.getString(Tags.Modality) != null && dataset.getString(Tags.Modality).length() > 0) ? dataset.getString(Tags.Modality) : "";
            String seriesDesc = (dataset.getString(Tags.SeriesDescription) != null && dataset.getString(Tags.SeriesDescription).length() > 0) ? dataset.getString(Tags.SeriesDescription) : "";
            String bodyPartExamined = (dataset.getString(Tags.BodyPartExamined) != null && dataset.getString(Tags.BodyPartExamined).length() > 0) ? dataset.getString(Tags.BodyPartExamined) : "";
            try {
                conn.createStatement().execute("insert into series values('" + dataset.getString(Tags.SeriesInstanceUID) + "','" + seriesNo + "','" + date + "','" + time + "','" + modality + "','" + seriesDesc + "','" + bodyPartExamined + "','" + institution + "'," + numSeries + ",'" + localPatientId/*dataset.getString(Tags.PatientID)*/ + "','" + dataset.getString(Tags.StudyInstanceUID) + "')");
                conn.commit();
            } catch (SQLException ex) {
                System.out.println("Exception in inserting series info");
            }
        }
        if (ApplicationContext.mainScreenObj != null && updateMainScreen || (!ApplicationContext.isJnlp && !ApplicationContext.mainScreenObj.isInProgress())) {
            update("study", "NoOfSeries", getStudyLevelSeries(dataset.getString(Tags.StudyInstanceUID)), "StudyInstanceUID", dataset.getString(Tags.StudyInstanceUID));
            SwingUtilities.invokeLater(refresher);
        }
    }

    /**
     * MDIAZ - Custom
     * @param dataset
     * @param filePath
     * @param isLink
     * @param updateMainScreen
     * @param localPatientId 
     */
    public void insertImageInfo(DicomObject dataset, String filePath, boolean isLink, boolean updateMainScreen, String localPatientId) {
        if (!(checkRecordExists("image", "SopUID", dataset.getString(Tags.SOPInstanceUID)))) {
            boolean multiframe = false;
            int totalFrame = 0;
            boolean encapsulatedPDF = false;

            if (dataset.getString(Tags.SOPClassUID) != null && dataset.getString(Tags.SOPClassUID).equalsIgnoreCase("1.2.840.10008.5.1.4.1.1.104.1")) {
                encapsulatedPDF = true;
            }
            if (dataset.getString(Tags.NumberOfFrames) != null && Integer.parseInt(dataset.getString(Tags.NumberOfFrames)) > 1) {
                multiframe = true;
                totalFrame = dataset.getInt(Tags.NumberOfFrames);
            }
            String frameOfRefUid = dataset.getString(Tags.FrameOfReferenceUID) != null ? dataset.getString(Tags.FrameOfReferenceUID) : "";
            String imgPos = dataset.getBytes(Tags.ImagePosition) != null ? new String(dataset.getBytes(Tags.ImagePosition)) : "";
            String imgOrientation = dataset.getBytes(Tags.ImageOrientation) != null ? new String(dataset.getBytes(Tags.ImageOrientation)) : "null";
            String pixelSpacing = dataset.getBytes(Tags.PixelSpacing) != null ? new String(dataset.getBytes(Tags.PixelSpacing)) : "";
            int row = dataset.getInt(Tags.Rows) != 0 ? dataset.getInt(Tags.Rows) : 1;
            int columns = dataset.getInt(Tags.Columns) != 0 ? dataset.getInt(Tags.Columns) : 1;
            String referSopInsUid = "", image_type = "";
            String sliceThickness = dataset.getBytes(Tags.SliceThickness) != null ? new String(dataset.getBytes(Tags.SliceThickness)) : "";
            //To get the Referenced SOP Instance UID
            DicomElement refImageSeq = dataset.get(Tag.ReferencedImageSequence);
            if (refImageSeq != null) {
                if (refImageSeq.hasItems()) {
                    DicomObject dcmObj1 = refImageSeq.getDicomObject();
                    referSopInsUid = dcmObj1.get(Tag.ReferencedSOPInstanceUID) != null ? new String(dcmObj1.get(Tag.ReferencedSOPInstanceUID).getBytes()) : "";
                }
            }
            //To get the Image Type (LOCALIZER / AXIAL / OTHER)
            image_type = dataset.getBytes(Tags.ImageType) != null ? new String(dataset.getBytes(Tags.ImageType)) : "";
            String[] imageTypes = image_type.split("\\\\");
            if (imageTypes.length >= 3) {
                image_type = imageTypes[2];
            }
            String[] imagePosition = dataset.getStrings(Tags.ImagePosition);
            String sliceLoc = imagePosition != null && imagePosition[2] != null ? imagePosition[2] : "0";
            try {
                conn.createStatement().executeUpdate("insert into image(SopUID,SOPClassUID,InstanceNo,multiframe,totalframe,SendStatus,ForwardDateTime,ReceivedDateTime,ReceiveStatus,FileStoreUrl,SliceLocation,EncapsulatedDocument,ThumbnailStatus,FrameOfReferenceUID,ImagePosition,ImageOrientation,ImageType,PixelSpacing,SliceThickness,NoOfRows,NoOfColumns,ReferencedSopUid,PatientId,StudyInstanceUID,SeriesInstanceUID) values('" + dataset.getString(Tags.SOPInstanceUID) + "','" + dataset.getString(Tags.SOPClassUID) + "'," + dataset.getInt(Tags.InstanceNumber) + ",'" + multiframe + "','" + totalFrame + "','" + "partial" + "','" + " " + "','" + " " + "','" + "partial" + "','" + filePath + "'," + sliceLoc + ",'" + encapsulatedPDF + "',false,'" + frameOfRefUid + "','" + imgPos + "','" + imgOrientation + "','" + image_type + "','" + pixelSpacing + "','" + sliceThickness + "'," + row + "," + columns + ",'" + referSopInsUid.trim() + "','" + localPatientId/*dataset.getString(Tags.PatientID)*/ + "','" + dataset.getString(Tags.StudyInstanceUID) + "','" + dataset.getString(Tags.SeriesInstanceUID) + "')");
                conn.commit();
                boolean isVideo = false;
                if (dataset.getString(Tags.SOPClassUID)!=null && (dataset.getString(Tags.SOPClassUID).equals(UID.VideoEndoscopicImageStorage) || dataset.getString(Tags.SOPClassUID).equals(UID.VideoMicroscopicImageStorage) || dataset.getString(Tags.SOPClassUID).equals(UID.VideoPhotographicImageStorage))) {
                        String storeLoc = isLink ? ApplicationContext.getAppDirectory() + File.separator + "Videos" + File.separator + dataset.getString(Tags.SOPInstanceUID) + "_V" : new File(filePath).getParentFile() + File.separator + dataset.getString(Tags.SOPInstanceUID) + "_V";
                        ApplicationContext.convertVideo(filePath, storeLoc, dataset.getString(Tags.SOPInstanceUID));
                        isVideo = true;
                }
                if (ApplicationContext.mainScreenObj != null && updateMainScreen || (!ApplicationContext.isJnlp && !ApplicationContext.mainScreenObj.isInProgress())) {
                    if(!isVideo) {
                        String storeLoc = isLink ? ApplicationContext.getAppDirectory() + File.separator + "Thumbnails" + File.separator + dataset.getString(Tags.StudyInstanceUID) + File.separator + dataset.getString(Tags.SOPInstanceUID) : filePath.substring(0, filePath.lastIndexOf(File.separator)) + File.separator + "Thumbnails" + File.separator + dataset.getString(Tags.SOPInstanceUID);
                        executor.submit(new ThumbnailConstructor(filePath, storeLoc));
                        update("image", "ThumbnailStatus", true, "SopUID", dataset.getString(Tags.SOPInstanceUID));
                    }
                    addInstanceCount(dataset.getString(Tags.StudyInstanceUID), dataset.getString(Tags.SeriesInstanceUID));
                    if (multiframe) {
                        SwingUtilities.invokeLater(refresher);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Exception in inserting image info " + ex.getMessage());
            }
        } else {
            if (ApplicationContext.mainScreenObj != null && updateMainScreen || (!ApplicationContext.isJnlp && !ApplicationContext.mainScreenObj.isInProgress())) {
                boolean isVideo = false;
                if (dataset.getString(Tags.SOPClassUID)!=null && (dataset.getString(Tags.SOPClassUID).equals(UID.VideoEndoscopicImageStorage) || dataset.getString(Tags.SOPClassUID).equals(UID.VideoMicroscopicImageStorage) || dataset.getString(Tags.SOPClassUID).equals(UID.VideoPhotographicImageStorage))) {
                    isVideo = true;
                }
                if(!isVideo) {
                    String storeLoc = isLink ? ApplicationContext.getAppDirectory() + File.separator + "Thumbnails" + File.separator + dataset.getString(Tags.StudyInstanceUID) + File.separator + dataset.getString(Tags.SOPInstanceUID) : filePath.substring(0, filePath.lastIndexOf(File.separator)) + File.separator + "Thumbnails" + File.separator + dataset.getString(Tags.SOPInstanceUID);
                    executor.submit(new ThumbnailConstructor(filePath, storeLoc));
                }
                if (dataset.getString(Tags.NumberOfFrames) != null && Integer.parseInt(dataset.getString(Tags.NumberOfFrames)) > 1) {
                    SwingUtilities.invokeLater(refresher);
                }
            }
        }
    }
    
    /**
     * MDIAZ
     * @param sopInstanceUID
     * @param coords
     * @throws SQLException 
     */
    private void insertCoords(ArrayList<CoordBean> coords) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("insert into coords values(?,?,?,?,?,?,?)");
        for(CoordBean cb : coords) {
            try {
                stm.setString(1, cb.getPatientId());
                stm.setString(2, cb.getSOPId());
                stm.setFloat(3, cb.getPoint().getX());
                stm.setFloat(4, cb.getPoint().getY());
                stm.setFloat(5, cb.getPoint().getZ());
                stm.setInt(6, cb.getFrameNumber());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(cb.getThumb(),"png", baos);
                baos.flush();
                baos.close();
                InputStream is = new ByteArrayInputStream(baos.toByteArray());
                stm.setBlob(7, is);
                stm.executeUpdate();
            } catch (IOException ex) {
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        stm.close();
        conn.commit();
    }
    
    /**
     * MDIAZ
     * @param serverModel 
     */
    public void insertTrackRecord(TrackingModel trackingModel) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("insert into tracking (description, createDate, patientId) values('" + trackingModel.getDescription() + "','" + trackingModel.getCreationDateString() + "','" + trackingModel.getPatientId() + "')"
                    , Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                for (TrackingStudy study : trackingModel.getStudies()) {
                    conn.createStatement().executeUpdate("insert into tracking_study(trackId, studyUID, orderNumber) "
                            + "values ("+id +",'"+ study.getStudyUID() +"',"+ study.getOrderNumber()+")");
                }
            }
            rs.close();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Accessing Data  

    public String[] getListenerDetails() {
        String detail[] = new String[3];
        try {
            ResultSet listenerInfo = conn.createStatement().executeQuery("select * from listener");
            while (listenerInfo.next()) {
                detail[0] = listenerInfo.getString("aetitle");
                detail[1] = listenerInfo.getString("port");
                detail[2] = listenerInfo.getString("storagelocation");
            }
            listenerInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detail;
    }

    public ArrayList<ServerModel> getServerList() {
        ArrayList<ServerModel> serverList = new ArrayList<ServerModel>();
        try {
            ResultSet serverInfo = conn.createStatement().executeQuery("select * from servers");
            while (serverInfo.next()) {
                serverList.add(new ServerModel(serverInfo.getInt("pk"), serverInfo.getString("logicalname"), serverInfo.getString("aetitle"), serverInfo.getString("hostname"), serverInfo.getInt("port"), serverInfo.getString("retrievetype"), serverInfo.getString("wadocontext"), serverInfo.getInt("wadoport"), serverInfo.getString("wadoprotocol"), serverInfo.getString("retrievets"), serverInfo.getBoolean("showpreviews")));
            }
            serverInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serverList;
    }
    
    /**
     * MDIAZ
     * 
     * @return 
     */
    public ArrayList<EMRServerModel> getEMRServerList() {
        ArrayList<EMRServerModel> serverList = new ArrayList<EMRServerModel>();
        try {
            ResultSet serverInfo = conn.createStatement().executeQuery("select * from emrservers");
            while (serverInfo.next()) {
                serverList.add(new EMRServerModel(serverInfo.getInt("pk"), serverInfo.getString("logicalname"), serverInfo.getString("hostname"), serverInfo.getInt("port"), serverInfo.getString("subprotocol")));
            }
            serverInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serverList;
    }

    public ArrayList<String> getAllServerNames() {
        ArrayList<String> serverNames = new ArrayList<String>();
        try {
            ResultSet serverInfo = conn.createStatement().executeQuery("select logicalname from servers");
            while (serverInfo.next()) {
                serverNames.add(serverInfo.getString("logicalname"));
            }
            serverInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serverNames;
    }
    
    /**
     * MDIAZ
     * 
     * @return 
     */
    public ArrayList<String> getAllEMRServerNames() {
        ArrayList<String> serverNames = new ArrayList<String>();
        try {
            ResultSet serverInfo = conn.createStatement().executeQuery("select logicalname from emrservers");
            while (serverInfo.next()) {
                serverNames.add(serverInfo.getString("logicalname"));
            }
            serverInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return serverNames;
    }

    public String getRetrieveType(String serverName) {
        try {
            ResultSet serverNameInfo = conn.createStatement().executeQuery("select retrievetype from servers where logicalname='" + serverName + "'");
            serverNameInfo.next();
            return serverNameInfo.getString("retrievetype");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean isPreviewsEnabled(String serverName) {
        try {
            ResultSet serverInfo = conn.createStatement().executeQuery("select showpreviews from servers where logicalname='" + serverName + "'");
            serverInfo.next();
            return serverInfo.getBoolean("showpreviews");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isDownloadPending() {
        try {
            ResultSet pendingInfo = conn.createStatement().executeQuery("select StudyInstanceUID from study where DownloadStatus=false");
            if (pendingInfo.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ServerModel getServerDetails(String serverName) {
        try {
            ResultSet serverInfo = conn.createStatement().executeQuery("select * from servers where logicalname='" + serverName + "'");
            while (serverInfo.next()) {
                return new ServerModel(serverInfo.getString("logicalname"), serverInfo.getString("aetitle"), serverInfo.getString("hostname"), serverInfo.getInt("port"), serverInfo.getString("retrievetype"), serverInfo.getString("wadocontext"), serverInfo.getInt("wadoport"), serverInfo.getString("wadoprotocol"), serverInfo.getString("retrievets"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * MDIAZ 
     * 
     * @param serverName
     * @return 
     */
    public EMRServerModel getEMRServerDetails(String serverName) {
        try {
            ResultSet serverInfo = conn.createStatement().executeQuery("select * from emrservers where logicalname='" + serverName + "'");
            while (serverInfo.next()) {
                return new EMRServerModel(serverInfo.getString("logicalname"), serverInfo.getString("hostname"), serverInfo.getInt("port"), serverInfo.getString("subprotocol"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<ButtonsModel> getAllQueryButtons() {
        ArrayList<ButtonsModel> buttons = new ArrayList<ButtonsModel>();
        try {
            ResultSet buttonsInfo = conn.createStatement().executeQuery("select * from buttons order by buttonno");
            while (buttonsInfo.next()) {
                buttons.add(new ButtonsModel(buttonsInfo.getString("description"), buttonsInfo.getString("modality"), buttonsInfo.getString("datecriteria"), buttonsInfo.getString("timecriteria"), buttonsInfo.getBoolean("iscustomdate"), buttonsInfo.getBoolean("iscustomtime")));
            }
            buttonsInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buttons;
    }

    public ArrayList<String> getAllButtonNames() {
        ArrayList<String> buttonNames = new ArrayList<String>();
        try {
            ResultSet buttonsInfo = conn.createStatement().executeQuery("select description from buttons order by buttonno");
            while (buttonsInfo.next()) {
                buttonNames.add(buttonsInfo.getString("description"));
            }
            buttonsInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return buttonNames;
    }

    public ButtonsModel getButtonDetails(String description) {
        try {
            ResultSet buttonInfo = conn.createStatement().executeQuery("select * from buttons where description='" + description + "'");
            buttonInfo.next();
            return new ButtonsModel(buttonInfo.getString("description"), buttonInfo.getString("modality"), buttonInfo.getString("datecriteria"), buttonInfo.getString("timecriteria"), buttonInfo.getBoolean("iscustomdate"), buttonInfo.getBoolean("iscustomtime"));
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getActiveTheme() {
        try {
            ResultSet activeThemeInfo = conn.createStatement().executeQuery("select name from theme where status=true");
            activeThemeInfo.next();
            return activeThemeInfo.getString("name");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<String> getThemes() {
        ArrayList<String> themeNames = new ArrayList<String>();
        try {
            ResultSet themeInfo = conn.createStatement().executeQuery("select name from theme");
            while (themeInfo.next()) {
                if (!themeInfo.getString("name").equals("System")) {
                    themeNames.add(themeInfo.getString("name"));
                } else {
                    themeNames.add(System.getProperty("os.name"));
                }
            }
            themeInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return themeNames;
    }

    public ArrayList<String> getActiveModalities() {
        ArrayList<String> modalities = new ArrayList<String>();
        try {
            ResultSet modalityInfo = conn.createStatement().executeQuery("select shortname from modality where status=true");
            while (modalityInfo.next()) {
                modalities.add(modalityInfo.getString("shortname"));
            }
            modalityInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modalities;
    }

    public Vector<String> getAllModalities() {
        Vector<String> modalities = new Vector<String>();
        try {
            ResultSet modalityInfo = conn.createStatement().executeQuery("select shortname from modality");
            while (modalityInfo.next()) {
                modalities.add(modalityInfo.getString("shortname"));
            }
            modalityInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modalities;
    }

    public boolean isModalityActive(String shortname) {
        try {
            ResultSet isActive = conn.createStatement().executeQuery("select status from modality where shortname='" + shortname + "'");
            isActive.next();
            return isActive.getBoolean("status");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<PresetModel> getPresetsForModality(String modality) {
        ArrayList<PresetModel> presets = new ArrayList<PresetModel>();
        try {
            ResultSet modalityInfo = conn.createStatement().executeQuery("select pk from modality where shortname='" + modality + "'");
            if (modalityInfo.next()) {
                ResultSet presetInfo = conn.createStatement().executeQuery("select * from presets where modality_fk=" + modalityInfo.getInt("pk"));
                while (presetInfo.next()) {
                    PresetModel preset = new PresetModel(presetInfo.getInt("pk"), modality, presetInfo.getString("presetname"), presetInfo.getString("windowwidth"), presetInfo.getString("windowlevel"));
                    presets.add(preset);
                }
                modalityInfo.close();
                presetInfo.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return presets;
    }

    public String[] getActiveLanguage() {
        try {
            ResultSet resultSet = conn.createStatement().executeQuery("select * from locale where status=true");
            while (resultSet.next()) {
                return new String[]{resultSet.getString("countrycode"), resultSet.getString("country"), resultSet.getString("languagecode"), resultSet.getString("language"), resultSet.getString("localeid")};
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[] getCountryList() {
        try {
            ResultSet count = conn.createStatement().executeQuery("select count(distinct country) from locale");
            count.next();
            String[] countryList = new String[count.getInt(1)];
            int index = 0;
            ResultSet result = conn.createStatement().executeQuery("select distinct country from locale");
            while (result.next()) {
                countryList[index] = result.getString("country");
                index++;
            }
            count.close();
            result.close();
            return countryList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[] getLanguagesOfCountry(String country) {
        try {
            ResultSet count = conn.createStatement().executeQuery("select count(distinct language) from locale where country='" + country + "'");
            count.next();
            String languageList[] = new String[count.getInt(1)];
            ResultSet result = conn.createStatement().executeQuery("select distinct language from locale where country='" + country + "'");
            int index = 0;
            while (result.next()) {
                languageList[index] = result.getString("language");
                index++;
            }
            count.close();
            result.close();
            return languageList;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String[] getLocaleIDForCountryAndLanguage(String country, String language) {
        try {
            ResultSet count = conn.createStatement().executeQuery("select count(localeid) from locale where country='" + country + "' and language='" + language + "'");
            count.next();
            String[] localeId = new String[count.getInt(1)];
            ResultSet result = conn.createStatement().executeQuery("select localeid from locale where country='" + country + "' and language='" + language + "'");
            int index = 0;
            while (result.next()) {
                localeId[index] = result.getString("localeid");
                index++;
            }
            count.close();
            result.close();
            return localeId;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    /**
     * MDIAZ - Custom
     * @return 
     */
    public ArrayList<StudyModel> listAllLocalStudies() {
        ArrayList<StudyModel> studies = new ArrayList<StudyModel>();
        try {
            ResultSet studyInfo = conn.createStatement().executeQuery("select * from study where visible = 1");
            while (studyInfo.next()) {
                ResultSet patientInfo = conn.createStatement().executeQuery("select PatientName,PatientBirthDate,PatientSex from patient where PatientId='" + studyInfo.getString("PatientId") + "'");
                patientInfo.next();
                StudyModel study = new StudyModel(studyInfo.getString("PatientId"), patientInfo.getString("PatientName"), patientInfo.getString("PatientBirthDate"), studyInfo.getString("AccessionNo"), studyInfo.getString("StudyDate"), studyInfo.getString("StudyTime"), studyInfo.getString("StudyDescription"), studyInfo.getString("ModalitiesInStudy"), studyInfo.getString("NoOfSeries"), studyInfo.getString("NoOfInstances"), studyInfo.getString("StudyInstanceUID"));
                study.setSex(patientInfo.getString("PatientSex")); //MDIAZ
                studies.add(study);
                patientInfo.close();
            }
            studyInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studies;
    }

    public boolean getLoopbackStatus() {
        try {
            ResultSet loopBackStatus = conn.createStatement().executeQuery("select Loopback from miscellaneous");
            loopBackStatus.next();
            return loopBackStatus.getBoolean("Loopback");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public int getTotalInstances(String studyUid) {
        try {
            ResultSet totalInstancesInfo = conn.createStatement().executeQuery("select NoOfInstances from study where StudyInstanceUID='" + studyUid + "'");
            totalInstancesInfo.next();
            return totalInstancesInfo.getInt("NoOfInstances");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getStudyLevelInstances(String studyUid) {
        try {
            ResultSet totalInstancesInfo = conn.createStatement().executeQuery("select count(*) from image where StudyInstanceUID='" + studyUid + "'");
            totalInstancesInfo.next();
            return totalInstancesInfo.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public boolean isLink(String studyUid) {
        try {
            ResultSet linkInfo = conn.createStatement().executeQuery("select StudyType from study where StudyInstanceUID='" + studyUid + "'");
            linkInfo.next();
            return linkInfo.getString("StudyType").equals("link") ? true : false;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<Series> getSeriesList_SepMulti(String siuid) {
        ArrayList<Series> arr = new ArrayList();
        try {
            String sql = "select SeriesInstanceUID,SeriesNo,SeriesDescription,BodyPartExamined,SeriesDate,SeriesTime,NoOfSeriesRelatedInstances from series where StudyInstanceUID='" + siuid + "' order by SeriesNo";
            ResultSet rs = null;
            rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                Series series = new Series(siuid, rs.getString("SeriesInstanceUID"), rs.getString("SeriesNo"), rs.getString("SeriesDescription"), rs.getString("BodyPartExamined"), rs.getString("SeriesDate"), rs.getString("SeriesTime"), false, null, rs.getInt("NoOfSeriesRelatedInstances"));
                ResultSet rs1 = null;
                String sql1 = "select FileStoreUrl,totalframe,SopUID,InstanceNo,multiframe,EncapsulatedDocument,SOPClassUID from image where StudyInstanceUID='" + siuid + "' AND " + "SeriesInstanceUID='" + rs.getString("SeriesInstanceUID") + "'" + "AND multiframe=false" + " order by InstanceNo asc";
                rs1 = conn.createStatement().executeQuery(sql1);
                boolean allInstanceAreMultiframe = true;
                while (rs1.next()) {
                    allInstanceAreMultiframe = false;
                    Instance img = new Instance(rs1.getString("FileStoreUrl"), rs1.getString("SopUID"), rs1.getString("SopUID"), rs1.getBoolean("EncapsulatedDocument"), rs1.getString("SOPClassUID"), false);
                    series.getImageList().add(img);
                }
                if (!allInstanceAreMultiframe) {
                    arr.add(series);
                }
                arr.addAll(getMultiframeSeriesList(siuid, series.getSeriesInstanceUID(), rs.getString("SeriesNo"), rs.getString("BodyPartExamined"), rs.getString("SeriesDate"), rs.getString("SeriesTime")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public ArrayList<Series> getMultiframeSeriesList(String studyUID, String seriesUID, String seriesNo, String bodyPart, String seriesDate, String seriesTime) {
        ArrayList<Series> arr = new ArrayList();
        try {
            ResultSet rs1 = null;
            String sql1 = "select FileStoreUrl,totalframe,SopUID,InstanceNo,multiframe,EncapsulatedDocument,SOPClassUID from image where StudyInstanceUID='" + studyUID + "' AND " + "SeriesInstanceUID='" + seriesUID + "'" + " AND multiframe=true" + " order by InstanceNo asc";
            rs1 = conn.createStatement().executeQuery(sql1);
            while (rs1.next()) {
                int totalFrames = Integer.parseInt(rs1.getString("totalFrame"));
                Series series = new Series(studyUID, seriesUID, seriesNo, null, bodyPart, seriesDate, seriesTime, true, rs1.getString("SopUID"), totalFrames);
                Instance img = new Instance(rs1.getString("FileStoreUrl"), rs1.getString("SopUID"), rs1.getString("InstanceNo"), rs1.getBoolean("EncapsulatedDocument"), rs1.getString("SOPClassUID"), true);
                img.setTotalNumFrames(totalFrames);
                if (img.getSopClassUid() != null && (img.getSopClassUid().equals(UID.VideoEndoscopicImageStorage) || img.getSopClassUid().equals(UID.VideoMicroscopicImageStorage) || img.getSopClassUid().equals(UID.VideoPhotographicImageStorage))) {
                    series.setVideoStatus(true);
                    series.setSeriesDesc("Video:"+totalFrames+" Frames");
                } else {
                    series.setSeriesDesc("Multiframe:" + totalFrames + " Frames");
                }
                series.getImageList().add(img);
                arr.add(series);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arr;
    }

    public ArrayList<String> getLocationsBasedOnSeries(String studyUid, String seriesUid) {
        ArrayList<String> locations = new ArrayList<String>();
        try {
            ResultSet locationInfo = conn.createStatement().executeQuery("select FileStoreUrl from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "' and SOPClassUID not in('" + UID.VideoEndoscopicImageStorage + "','" + UID.VideoMicroscopicImageStorage + "','" + UID.VideoPhotographicImageStorage + "')");
            while (locationInfo.next()) {
                locations.add(locationInfo.getString("FileStoreUrl"));
            }
            locationInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return locations;
    }

    public ArrayList<Series> getSeriesList(String siuid) {
        ArrayList<Series> arr = new ArrayList();
        try {
            String sql = "select SeriesInstanceUID,SeriesNo,SeriesDescription,BodyPartExamined from series where StudyInstanceUID='" + siuid + "'" + " order by SeriesNo";
            ResultSet rs;
            rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                int seriesLevelIndex = 0;
                Series series = new Series();
                series.setStudyInstanceUID(siuid);
                series.setSeriesInstanceUID(rs.getString("SeriesInstanceUID"));
                series.setSeriesNumber(rs.getString("SeriesNo"));
                series.setSeriesDesc(rs.getString("SeriesDescription"));
                series.setBodyPartExamined(rs.getString("BodyPartExamined"));
                ResultSet rs1;
                String sql1 = "select FileStoreUrl,SopUID,SopClassUid,InstanceNo,multiframe,totalframe from image where StudyInstanceUID='" + siuid + "' AND " + "SeriesInstanceUID='" + rs.getString("SeriesInstanceUID") + "'" + " order by InstanceNo asc";
                rs1 = conn.createStatement().executeQuery(sql1);
                while (rs1.next()) {
                    int totalFrames = Integer.parseInt(rs1.getString("totalFrame"));
                    int tempi = 0;
                    seriesLevelIndex++;
                    do {
                        Instance img = new Instance();
                        img.setFilepath(rs1.getString("FileStoreUrl"));
                        img.setSop_iuid(rs1.getString("SopUID"));
                        img.setInstance_no(rs1.getString("InstanceNo"));
                        if (rs1.getString("SopClassUid").equals(UID.VideoEndoscopicImageStorage) || rs1.getString("SopClassUid").equals(UID.VideoMicroscopicImageStorage) || rs1.getString("SopClassUid").equals(UID.VideoPhotographicImageStorage)) {
                            series.setVideoStatus(true);
                        }
                        img.setMultiframe(new Boolean(rs1.getString("multiframe")));
                        img.setCurrentFrameNum(tempi);
                        img.setTotalNumFrames(totalFrames);
                        img.setSeriesLevelIndex(seriesLevelIndex);
                        series.getImageList().add(img);
                        tempi++;
                    } while (tempi < totalFrames);
                }
                arr.add(series);
                rs1.close();
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    public int getSeriesLevelInstance(String studyUid, String seriesUid) {
        int totalInstance = 0;
        try {
            ResultSet rs = conn.createStatement().executeQuery("select count(*) from image where StudyInstanceUID='" + studyUid + "' AND " + "SeriesInstanceUID='" + seriesUid + "' AND multiframe = false");
            rs.next();
            totalInstance = rs.getInt(1);
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return totalInstance;
    }

    public String getInstanceUIDBasedOnSliceLocation(String studyUid, String seriesUid, String sliceLocation, String sliceThickness) {
        String fileStoreUrl = null;
        try {
            double sliceLocTemp = 0, sliceThicknessTemp = 0;
            if (sliceLocation != null && !sliceLocation.equals("")) {
                sliceLocTemp = Double.parseDouble(sliceLocation);
                if (!sliceThickness.equals("")) {
                    sliceThicknessTemp = Double.parseDouble(sliceThickness);
                }
            }
            String sql = "select SopUid from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "' and SliceLocation between " + (sliceLocTemp - sliceThicknessTemp) + " and " + (sliceLocTemp + sliceThicknessTemp);
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                fileStoreUrl = rs.getString("SopUid");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileStoreUrl;
    }

    public ArrayList getSeriesInstancesLocation(String studyUid) {
        ArrayList locations = new ArrayList();
        try {
            ResultSet seriesInfo = conn.createStatement().executeQuery("select SeriesInstanceUID from Series where StudyInstanceUID='" + studyUid + "' order by SeriesNo");
            while (seriesInfo.next()) {
                ResultSet location = conn.createStatement().executeQuery("select FileStoreUrl from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesInfo.getString("SeriesInstanceUID") + "'" + " order by InstanceNo asc");
                ResultSet multiframesInfo = conn.createStatement().executeQuery("select FileStoreUrl from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesInfo.getString("SeriesInstanceUID") + "' and multiframe=true" + " order by InstanceNo asc");
                if (location.next()) {
                    locations.add(location.getString("FileStoreUrl"));
                }
                while (multiframesInfo.next()) {
                    locations.add(multiframesInfo.getString("FileStoreUrl"));
                }
                location.close();
                multiframesInfo.close();
            }
            seriesInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return locations;
    }

    public ArrayList<String> getInstancesLocation(String studyUid, String seriesUid) {
        ArrayList<String> locations = new ArrayList<String>();
        try {
            ResultSet instanceInfo = conn.createStatement().executeQuery("select FileStoreUrl from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "'" + " order by InstanceNo asc");
            while (instanceInfo.next()) {
                locations.add(instanceInfo.getString("FileStoreUrl"));
            }
            instanceInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return locations;

    }

    public String getFirstInstanceLocation(String studyUid, String seriesInstanceUid) {
        try {
            ResultSet locationInfo = conn.createStatement().executeQuery("select FileStoreUrl from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesInstanceUid + "'" + " order by InstanceNo asc");
            locationInfo.next();
            return locationInfo.getString("FileStoreUrl");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getFirstInstanceWithSOPCls(String studyUid, String seriesUid) {
        try {
            ResultSet locationInfo = conn.createStatement().executeQuery("select SopUID,SOPClassUID from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "'" + " order by InstanceNo asc");
            locationInfo.next();
            return locationInfo.getString("SopUID") + "," + locationInfo.getString("SOPClassUID");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getFileLocation(String studyUid, String seriesUid, String sopUid) {
        String path = null;
        try {
            ResultSet pathInfo = conn.createStatement().executeQuery("select FileStoreUrl from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "' and SopUid='" + sopUid + "'");
            pathInfo.next();
            path = pathInfo.getString("FileStoreUrl");
            pathInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return path;
    }

    public String getFileLocation(String sopUid) {
        try {
            ResultSet pathInfo = conn.createStatement().executeQuery("select FileStoreUrl from image where SopUid='" + sopUid + "'");
            pathInfo.next();
            return pathInfo.getString("FileStoreUrl");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getFileLocation(String studyUid, String seriesUid, int instanceNumber) {
        int i = -1;
        try {
            ResultSet pathInfo = conn.createStatement().executeQuery("select FileStoreUrl from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "' order by InstanceNo");
            while (pathInfo.next()) {
                i++;
                if (i == instanceNumber) {
                    return pathInfo.getString("FileStoreUrl");
                }
            }
            pathInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * MDIAZ - Custom
     * @param patientName
     * @param patientID
     * @param dob
     * @param accNo
     * @param studyDate
     * @param studyDesc
     * @param modality
     * @return 
     */
    public ArrayList<StudyModel> listStudies(String patientName, String patientID, String dob, String accNo, String studyDate, String studyDesc, String modality) {
        ArrayList<StudyModel> matchingStudies = new ArrayList<StudyModel>();
        ResultSet matchingInfo;
        try {
            matchingInfo = conn.createStatement().executeQuery("select * from patient inner join study on patient.PatientId=study.PatientId where upper(patient.PatientId) like '" + patientID + "' and upper(patient.PatientName) like '" + patientName + "' and patient.PatientBirthDate like '" + dob + "' and upper(study.AccessionNo) like '" + accNo + "' and study.StudyDate like '" + studyDate + "' and upper(study.StudyDescription) like '" + studyDesc + "' and upper(study.ModalitiesInStudy) like '" + modality + "' and study.visible=1");
            while (matchingInfo.next()) {
                StudyModel study = new StudyModel(matchingInfo.getString("PatientId"), matchingInfo.getString("PatientName"), matchingInfo.getString("PatientBirthDate"), matchingInfo.getString("AccessionNo"), matchingInfo.getString("StudyDate"), matchingInfo.getString("StudyTime"), matchingInfo.getString("StudyDescription"), matchingInfo.getString("ModalitiesInStudy"), matchingInfo.getString("NoOfSeries"), matchingInfo.getString("NoOfInstances"), matchingInfo.getString("StudyInstanceUID"));
                matchingStudies.add(study);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException nfe) {
        }
        return matchingStudies;
    }

    public String getJNLPRetrieveType() {
        try {
            ResultSet retrieveInfo = conn.createStatement().executeQuery("select JNLPRetrieveType from miscellaneous");
            retrieveInfo.next();
            return retrieveInfo.getString("JNLPRetrieveType");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean getDynamicRetrieveTypeStatus() {
        try {
            ResultSet retrieveInfo = conn.createStatement().executeQuery("select AllowDynamicRetrieveType from miscellaneous");
            retrieveInfo.next();
            return retrieveInfo.getBoolean("AllowDynamicRetrieveType");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //Added for Memeory Handling
    public ArrayList<String> getInstanceUidList(String studyUid, String seriesUid) {
        ArrayList<String> locations = new ArrayList<String>();
        try {
            ResultSet imageLocations = conn.createStatement().executeQuery("select SopUid from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "' and multiframe = false order by InstanceNo,FileStoreUrl");
            while (imageLocations.next()) {
                locations.add(imageLocations.getString("SopUid"));
            }
            imageLocations.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return locations;
    }

    public ScoutLineInfoModel[] getFirstAndLastInstances(String studyUid, String seriesUid) {
        ScoutLineInfoModel[] borderLines = new ScoutLineInfoModel[2];
        try {
            ResultSet scoutDetails = conn.createStatement().executeQuery("select ImagePosition,ImageOrientation,PixelSpacing,NoOfRows,NoOfColumns,FrameOfReferenceUID,ReferencedSopUid,ImageType,SliceLocation from Image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "' and InstanceNo in(select min(InstanceNo) from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "' and ImageType not in('LOCALIZER'))");
            scoutDetails.next();
            borderLines[0] = new ScoutLineInfoModel(scoutDetails.getString("ImagePosition"), scoutDetails.getString("ImageOrientation"), scoutDetails.getString("PixelSpacing"), scoutDetails.getInt("NoOfRows"), scoutDetails.getInt("NoOfColumns"), scoutDetails.getString("FrameOfReferenceUID"), scoutDetails.getString("ReferencedSopUid"), scoutDetails.getString("ImageType"), scoutDetails.getString("SliceLocation"));
            scoutDetails = conn.createStatement().executeQuery("select ImagePosition,ImageOrientation,PixelSpacing,NoOfRows,NoOfColumns,FrameOfReferenceUID,ReferencedSopUid,ImageType,SliceLocation from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "' and InstanceNo in(select max(InstanceNo) from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "') and ImageType not in('LOCALIZER')");
            scoutDetails.next();
            borderLines[1] = new ScoutLineInfoModel(scoutDetails.getString("ImagePosition"), scoutDetails.getString("ImageOrientation"), scoutDetails.getString("PixelSpacing"), scoutDetails.getInt("NoOfRows"), scoutDetails.getInt("NoOfColumns"), scoutDetails.getString("FrameOfReferenceUID"), scoutDetails.getString("ReferencedSopUid"), scoutDetails.getString("ImageType"), scoutDetails.getString("SliceLocation"));
            scoutDetails.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return borderLines;
    }

    public ScoutLineInfoModel getScoutLineDetails(String studyUid, String seriesUid, String instanceUid) {
        try {
            ResultSet scoutDetails = conn.createStatement().executeQuery("select ImagePosition,ImageOrientation,PixelSpacing,NoOfRows,NoOfColumns,FrameOfReferenceUID,ReferencedSopUid,ImageType,SliceLocation from Image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "' and SopUid='" + instanceUid + "'");
            while (scoutDetails.next()) {
                return new ScoutLineInfoModel(scoutDetails.getString("ImagePosition"), scoutDetails.getString("ImageOrientation"), scoutDetails.getString("PixelSpacing"), scoutDetails.getInt("NoOfRows"), scoutDetails.getInt("NoOfColumns"), scoutDetails.getString("FrameOfReferenceUID"), scoutDetails.getString("ReferencedSopUid"), scoutDetails.getString("ImageType"), scoutDetails.getString("SliceLocation"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getSlicePosition(String studyUid, String seriesUid, String instanceUid) {
        ResultSet sliceInfo = null;
        try {
            sliceInfo = conn.createStatement().executeQuery("select SliceLocation from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "' and SopUid='" + instanceUid + "'");
            sliceInfo.next();
            return sliceInfo.getString("SliceLocation");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                sliceInfo.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                //ignore
            }
        }
        return null;
    }

    public String getThumbnailLocation(String studyUid, String seriesUid) {
        try {
            ResultSet info = conn.createStatement().executeQuery("select FileStoreUrl,StudyInstanceUID from image where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "'");
            info.next();
            if (info.getString("FileStoreUrl").contains(ApplicationContext.getAppDirectory())) {
                String location = new File(info.getString("FileStoreUrl")).getParent();
                info.close();
                return location;
            } else {
                return ApplicationContext.getAppDirectory() + File.separator + "Thumbnails" + File.separator + info.getString("StudyInstanceUID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public int getStudyLevelSeries(String studyUid) {
        try {
            ResultSet seriesCount = conn.createStatement().executeQuery("select count(SeriesInstanceUID) from series where StudyInstanceUID='" + studyUid + "'");
            seriesCount.next();
            int toReturn = seriesCount.getInt(1);
            seriesCount.close();
            return toReturn;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * MDIAZ
     * @param sopInstanceUID
     * @return 
     */
    public ArrayList<CoordBean> getCoords(String patientId) { //FN es frame number
        String sql = "select x,y,z,framenumber,sopuid,thumb from coords where patientId ='" + patientId + "'";
        ArrayList<CoordBean> list = new ArrayList<CoordBean>();
        try {
            ResultSet rs = conn.createStatement().executeQuery(sql);
            while (rs.next()) {
                CoordBean cb = new CoordBean();
                Vector3f v = new Vector3f();
                v.setX(rs.getFloat("x"));
                v.setY(rs.getFloat("y"));
                v.setZ(rs.getFloat("z"));
                cb.setPoint(v);
                cb.setSOPId(rs.getString("sopuid"));
                cb.setPatientId(patientId);
                cb.setFrameNumber(rs.getInt("framenumber"));
                InputStream is = rs.getBlob("thumb").getBinaryStream(); 
                BufferedImage thumb = ImageIO.read(is);
                cb.setThumb(thumb);
                list.add(cb);
            }
            rs.close();
        }
        catch(SQLException e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * MDIAZ
     * @param localPatientId
     * @return 
     */
    public String getPatientNameByLocalId(String localPatientId) {
        try {
            ResultSet nameRs = conn.createStatement().executeQuery("select patientname from patient where patientid='" + localPatientId + "'");
            nameRs.next();
            String name = nameRs.getString(1);
            nameRs.close();
            return name;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    //Updations
    public void update(String tableName, String fieldName, int fieldValue, String whereField, String whereValue) {
        try {
            conn.createStatement().executeUpdate("update " + tableName + " set " + fieldName + "=" + fieldValue + " where " + whereField + "='" + whereValue + "'");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(String tableName, String fieldName, boolean fieldValue, String whereField, String whereValue) {
        try {
            conn.createStatement().executeUpdate("update " + tableName + " set " + fieldName + "=" + fieldValue + " where " + whereField + "='" + whereValue + "'");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(String tableName, String fieldName, String fieldValue, String whereField, String whereValue) {
        try {
            conn.createStatement().executeUpdate("update " + tableName + " set " + fieldName + "='" + fieldValue + "' where " + whereField + "='" + whereValue + "'");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addInstanceCount(String studyUid, String seriesUid) throws SQLException {
        ResultSet studyLevelInstances = conn.createStatement().executeQuery("select NoOfInstances from study where StudyInstanceUID='" + studyUid + "'");
        studyLevelInstances.next();
        ResultSet seriesLevelInstances = conn.createStatement().executeQuery("select NoOfSeriesRelatedInstances from series where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "'");
        seriesLevelInstances.next();
        conn.createStatement().executeUpdate("update study set NoOfInstances=" + (studyLevelInstances.getInt(1) + 1) + "where StudyInstanceUID='" + studyUid + "'");
        conn.createStatement().executeUpdate("update series set NoOfSeriesRelatedInstances=" + (seriesLevelInstances.getInt("NoOfSeriesRelatedInstances") + 1) + "where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "'");
        seriesLevelInstances.close();
        studyLevelInstances.close();
    }

    public void updateThumbnailStatus(String studyUid, String seriesUid, String sopUid) {
        try {
            conn.createStatement().executeUpdate("update image set ThumbnailStatus=true where StudyInstanceUID='" + studyUid + "' and SeriesInstanceUID='" + seriesUid + "' and SopUID='" + sopUid + "'");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateListener(String aetitle, String port) {
        try {
            ResultSet pk = conn.createStatement().executeQuery("select pk from listener");
            pk.next();
            conn.createStatement().executeUpdate("update listener set aetitle='" + aetitle + "',port='" + port + "' where pk=" + pk.getInt("pk"));
            pk.close();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateTheme(String themeName) {
        try {
            ResultSet activeInfo = conn.createStatement().executeQuery("select name from theme where status=true");
            activeInfo.next();
            conn.createStatement().executeUpdate("update theme set status=false where name='" + activeInfo.getString("name") + "'");
            conn.createStatement().executeUpdate("update theme set status=true where name='" + themeName + "'");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void reArrangeButtons(String buttonToMove, String buttonToReplace) {
        String selectQuery = "select buttonno from buttons where description='";
        try {
            ResultSet buttonToMoveInfo = conn.createStatement().executeQuery(selectQuery + buttonToMove + "'");
            buttonToMoveInfo.next();
            ResultSet buttonToReplaceInfo = conn.createStatement().executeQuery(selectQuery + buttonToReplace + "'");
            buttonToReplaceInfo.next();
            conn.createStatement().executeUpdate("update buttons set buttonno=" + buttonToMoveInfo.getInt("buttonno") + " where description='" + buttonToReplace + "'");
            conn.createStatement().executeUpdate("update buttons set buttonno=" + buttonToReplaceInfo.getInt("buttonno") + " where description='" + buttonToMove + "'");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean updateServer(ServerModel serverModel) {
        boolean duplicate = false;
        try {
            conn.createStatement().executeUpdate("update servers set logicalname='" + serverModel.getDescription() + "',aetitle='" + serverModel.getAeTitle() + "',hostname='" + serverModel.getHostName() + "',port=" + serverModel.getPort() + ",retrievetype='" + serverModel.getRetrieveType() + "',showpreviews=" + serverModel.isPreviewEnabled() + ",wadocontext='" + serverModel.getWadoURL() + "',wadoport=" + serverModel.getWadoPort() + ",wadoprotocol='" + serverModel.getWadoProtocol() + "',retrievets='" + serverModel.getRetrieveTransferSyntax() + "' where pk=" + serverModel.getPk());
            conn.commit();
        } catch (SQLException ex) {
            duplicate = true;
        }
        return duplicate;
    }

    /**
     * MDIAZ
     * @param serverModel
     * @return 
     */
    public boolean updateEMRServer(EMRServerModel serverModel) {
        boolean duplicate = false;
        try {
            conn.createStatement().executeUpdate("update emrservers set logicalname='" + serverModel.getDescription() + "',hostname='" + serverModel.getHostName() + "',port=" + serverModel.getPort() + ",subprotocol='" + serverModel.getSubprotocol()+"'");
            conn.commit();
        } catch (SQLException ex) {
            duplicate = true;
        }
        return duplicate;
    }
    
    public void updatePreset(PresetModel presetModel) {
        try {
            conn.createStatement().execute("update presets set presetname='" + presetModel.getPresetName() + "',windowwidth=" + presetModel.getWindowWidth() + ",windowlevel=" + presetModel.getWindowLevel() + " where pk=" + presetModel.getPk());
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateModalitiesStatus(String modality, boolean status) {
        try {
            conn.createStatement().execute("update modality set status=" + status + " where shortname='" + modality + "'");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setAllModalitiesIdle() {
        try {
            conn.createStatement().execute("update modality set status=false");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateDefaultLocale(String localeName) {
        try {
            conn.createStatement().execute("update locale set status=false");
            conn.createStatement().execute("update locale set status=true where localeid='" + localeName + "'");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateLoopBack(boolean isLoopback) {
        try {
            conn.createStatement().executeUpdate("update miscellaneous set Loopback=" + isLoopback);
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateJNLPRetrieveType(String retrieveType) {
        try {
            conn.createStatement().executeUpdate("update miscellaneous set JNLPRetrieveType='" + retrieveType + "'");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateDynamicRetrieveTypeStatus(boolean allow) {
        try {
            conn.createStatement().executeUpdate("update miscellaneous set AllowDynamicRetrieveType=" + allow);
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * MDIAZ
     * @param sopInstanceUID
     * @param coords 
     */
    public void updateCoords(String patientId, ArrayList<CoordBean> coords) { // MEJORAR ESTO!!!
        try {
            conn.createStatement().execute("delete from coords where patientId ='" + patientId + "'");
            //deleteRow("coords","sopuid",sopInstanceUID);
            conn.commit();
            insertCoords(coords);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateTrackRecord(TrackingModel trackingModel) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("delete from tracking_study where trackId="+trackingModel.getTrackId());
            for (TrackingStudy study : trackingModel.getStudies()) {
                    conn.createStatement().executeUpdate("insert into tracking_study(trackId, studyUID, orderNumber) "
                            + "values ("+trackingModel.getTrackId() +",'"+ study.getStudyUID() +"',"+ study.getOrderNumber()+")");
            }
            stmt.close();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateStudies(String studyUid) {
        update("study", "DownloadStatus", true, "StudyInstanceUID", studyUid);
        update("study", "NoOfInstances", getStudyLevelInstances(studyUid), "StudyInstanceUID", studyUid);
        update("image", "ThumbnailStatus", true, "StudyInstanceUID", studyUid);
    }

    //Deletions
    public void deleteButton(String description) {
        try {
            conn.createStatement().execute("delete from buttons where description='" + description + "'");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deletePreset(PresetModel presetModel) {
        try {
            conn.createStatement().execute("delete from presets where pk=" + presetModel.getPk());
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteServer(ServerModel serverModel) {
        try {
            conn.createStatement().execute("delete from servers where pk=" + serverModel.getPk());
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * MDIAZ
     * 
     * @param serverModel 
     */
    public void deleteEMRServer(EMRServerModel serverModel) {
        try {
            conn.createStatement().execute("delete from emrservers where pk=" + serverModel.getPk());
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * MDIAZ - Custom
     */
    public void rebuild() {
        //deleteRows();
        ApplicationContext.deleteDir(new File(ApplicationContext.listenerDetails[2]));
    }

//    private void deleteRows() {
//        try {
//            Statement stmt = conn.createStatement();
//            stmt.execute("delete from image");
//            stmt.execute("delete from series");
//            stmt.execute("delete from study");
//            stmt.execute("delete from patient");
//            conn.commit();
//        } catch (SQLException ex) {
//            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public void deleteLinkStudies() {
        try {
            ResultSet linkStudies = conn.createStatement().executeQuery("select StudyInstanceUID from study where StudyType='link'");
            while (linkStudies.next()) {
                ResultSet linkSeries = conn.createStatement().executeQuery("select SeriesInstanceUID from series where StudyInstanceUID='" + linkStudies.getString("StudyInstanceUID") + "'");
                while (linkSeries.next()) {
                    ResultSet linkInstances = conn.createStatement().executeQuery("select SopUid from image where StudyInstanceUID='" + linkStudies.getString("StudyInstanceUID") + "' and SeriesInstanceUID='" + linkSeries.getString("SeriesInstanceUID") + "'");
                    while (linkInstances.next()) {
                        deleteRow("image", "SopUid", linkInstances.getString("SopUid"));
                    }
                    deleteRow("series", "SeriesInstanceUID", linkSeries.getString("SeriesInstanceUID"));
                }
                deleteRow("study", "StudyInstanceUID", linkStudies.getString("StudyInstanceUID"));
            }
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteRow(String tableName, String whereField, String whereValue) throws SQLException {
        conn.createStatement().execute("delete from " + tableName + " where " + whereField + "='" + whereValue + "'");
    }
    Runnable refresher = new Runnable() {
        @Override
        public void run() {
            ApplicationContext.mainScreenObj.refreshLocalDB();
        }
    };

    /**
     * MDIAZ - Custom
     * @param patientID
     * @param studyInstanceUID 
     */
    public void deleteLocalStudy(String patientID, String studyInstanceUID) {
        try {
            //ResultSet fileInfo = conn.createStatement().executeQuery("select FileStoreUrl from image where StudyInstanceUID='" + studyInstanceUID + "'");
            //if (fileInfo.next()) {
                //ApplicationContext.deleteDir(new File(fileInfo.getString("FileStoreUrl")).getParentFile().getParentFile());
            //}
            //fileInfo.close();
            conn.createStatement().execute("update study set visible = 0 where StudyInstanceUID='" + studyInstanceUID + "'" );
            //conn.createStatement().execute("delete from image where StudyInstanceUID='" + studyInstanceUID + "'");
            //conn.createStatement().execute("delete from series where StudyInstanceUID='" + studyInstanceUID + "'");
            //conn.createStatement().execute("delete from study where StudyInstanceUID='" + studyInstanceUID + "'");

            //if (!checkRecordExists("study", "PatientId", patientID)) {
            //    conn.createStatement().execute("delete from patient where PatientID='" + patientID + "'");
            //}
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * MDIAZ
     * @param sopInstanceUID
     * @return 
     */
    public String getLocalPatientIdBySopInstanceUid(String sopInstanceUID) {
        String localPatientId = null;
        try {
            ResultSet pathInfo = conn.createStatement().executeQuery("select PATIENTID from PATIENT_SOPUID where SOPUID='" + sopInstanceUID + "'");
            if (pathInfo.next()) {
                localPatientId = pathInfo.getString("PATIENTID");
            }
            pathInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return localPatientId;
    }
    
    /**
     * MDIAZ
     * @return 
     */
    public ArrayList<PatientModel> listAllLocalPatients() {
        ArrayList<PatientModel> patients = new ArrayList<PatientModel>();
        try {
            ResultSet patientInfo = conn.createStatement().executeQuery("select p.* from patient p where exists (select s.studyid from study s where p.patientid = s.patientid and s.visible=1)");
            while (patientInfo.next()) {
                PatientModel patient = new PatientModel();
                patient.setPatientId(patientInfo.getString("PatientId"));
                patient.setPatientName(patientInfo.getString("PatientName"));
                patient.setDob(patientInfo.getString("PatientBirthDate"));
                patients.add(patient);
            }
            patientInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return patients;
    }
    
    /**
     * MDIAZ
     * @param localPatientId
     * @return 
     */
    public ArrayList<StudyModel> listLocalStudiesByPatientId(String localPatientId) {
        ArrayList<StudyModel> studies = new ArrayList<StudyModel>();
        try {
            ResultSet studyInfo = conn.createStatement().executeQuery("select * from study where patientId='"+localPatientId+"'");
            while (studyInfo.next()) {
                ResultSet patientInfo = conn.createStatement().executeQuery("select PatientName,PatientBirthDate,PatientSex from patient where PatientId='" + studyInfo.getString("PatientId") + "'");
                patientInfo.next();
                StudyModel study = new StudyModel(studyInfo.getString("PatientId"), patientInfo.getString("PatientName"), patientInfo.getString("PatientBirthDate"), studyInfo.getString("AccessionNo"), studyInfo.getString("StudyDate"), studyInfo.getString("StudyTime"), studyInfo.getString("StudyDescription"), studyInfo.getString("ModalitiesInStudy"), studyInfo.getString("NoOfSeries"), studyInfo.getString("NoOfInstances"), studyInfo.getString("StudyInstanceUID"));
                study.setSex(patientInfo.getString("PatientSex")); //MDIAZ
                studies.add(study);
                patientInfo.close();
            }
            studyInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studies;
    }
    
    /**
     * MDIAZ
     * @param localPatientId
     * @return 
     */
    public ArrayList<TrackingModel> listAllTrackingByPatientId(String localPatientId) {
        ArrayList<TrackingModel> tracks = new ArrayList<TrackingModel>();
        try {
            ResultSet trackInfo = conn.createStatement().executeQuery("select * from tracking where patientId='"+localPatientId+"'");
            while (trackInfo.next()) {
                TrackingModel track = new TrackingModel();
                track.setTrackId(trackInfo.getInt("trackId"));
                track.setDescription(trackInfo.getString("description"));
                track.setCreationDate(trackInfo.getString("createDate"));
                track.setPatientId(trackInfo.getString("patientId"));
                tracks.add(track);
            }
            trackInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tracks;
    }
    
    /**
     * 
     * @param trackId
     * @return 
     */
    public ArrayList<StudyModel> listStudiesByTrackId(int trackId) {
        ArrayList<StudyModel> studies = new ArrayList<StudyModel>();
        try {
            ResultSet trackInfo = conn.createStatement().executeQuery("select studyUID from tracking_study where trackId="+trackId+" order by ordernumber asc");
            while (trackInfo.next()) {
                ResultSet studyInfo = conn.createStatement().executeQuery("select * from study where studyInstanceUID='"+trackInfo.getString(1)+"'");
                if (studyInfo.next()) {
                    ResultSet patientInfo = conn.createStatement().executeQuery("select PatientName,PatientBirthDate,PatientSex from patient where PatientId='" + studyInfo.getString("PatientId") + "'");
                    patientInfo.next();
                    StudyModel study = new StudyModel(studyInfo.getString("PatientId"), patientInfo.getString("PatientName"), patientInfo.getString("PatientBirthDate"), studyInfo.getString("AccessionNo"), studyInfo.getString("StudyDate"), studyInfo.getString("StudyTime"), studyInfo.getString("StudyDescription"), studyInfo.getString("ModalitiesInStudy"), studyInfo.getString("NoOfSeries"), studyInfo.getString("NoOfInstances"), studyInfo.getString("StudyInstanceUID"));
                    study.setSex(patientInfo.getString("PatientSex")); //MDIAZ
                    studies.add(study);
                    patientInfo.close();
                }
                studyInfo.close();
            }
            trackInfo.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return studies;
    }

    /**
     * 
     * @param result 
     */
    public void insertResult(ResultModel result) {
        try {
            conn.createStatement().
                executeUpdate("insert into study_results (patientId, studyIUID, valueA, valueB, valueC, valueD, TDS) values('" + result.getPatientId()+ "','" + result.getStudyIUID() + "','" + result.getValueA() 
                    + "','" + result.getValueB() + "','" + result.getValueC() + "','" + result.getValueD() + "','"+ result.getTDS() + "')");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * 
     * @param result
     * @return 
     */
    public boolean checkResultExists(ResultModel result) {
         try {
            ResultSet rs = conn.createStatement().executeQuery("select count(*) from STUDY_RESULTS where StudyIUID = '" + result.getStudyIUID() + "' and patientId='" + result.getPatientId() + "'");
            rs.next();
            if (rs.getInt(1) > 0) {
                rs.close();
                return true;
            }
        } catch (Exception e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
    }

    /**
     * 
     * @param result 
     */
    public void updateResult(ResultModel result) {
        try {
            conn.createStatement().
                executeUpdate("update study_results set valueA='"+result.getValueA()+"',valueB='"+result.getValueB()+"',valueC='"+ result.getValueC()+"',valueD='"+result.getValueD()+"', TDS='"+result.getTDS()+"' where patientId='"+result.getPatientId()+"' and studyIUID='"+result.getStudyIUID()+"'");
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}