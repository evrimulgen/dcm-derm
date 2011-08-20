package org.weasis.derm.explorer;


/**
 *
 * @author mariano
 */
public class BasicDcmDataBean {
    
    private String patientId;
    private String patientName;
    private String patientLastname;
    private String patientBirthdate;
    private String patientSex;
    private Float patientWeight;
    private String patientHeight;
    private String studyDate;
    private String studyDescription;

    public String getSerieTime() {
        return serieTime;
    }

    public void setSerieTime(String serieTime) {
        this.serieTime = serieTime;
    }

    public String getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(String studyTime) {
        this.studyTime = studyTime;
    }
    private String serieDate;
    private String serieTime;
    private String serieDescription;
    private String studyId;
    private String studyTime;
    private int patientAge = -1;
    
    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public String getPatientBirthdate() {
        return patientBirthdate;
    }

    public void setPatientBirthdate(String patientBirthdate) {
        this.patientBirthdate = patientBirthdate;
    }

    public String getPatientHeight() {
        return patientHeight;
    }

    public void setPatientHeight(String patientHeight) {
        this.patientHeight = patientHeight;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientLastname() {
        return patientLastname;
    }

    public void setPatientLastname(String patientLastname) {
        this.patientLastname = patientLastname;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public Float getPatientWeight() {
        return patientWeight;
    }

    public void setPatientWeight(Float patientWeight) {
        this.patientWeight = patientWeight;
    }

    public String getSerieDate() {
        return serieDate;
    }

    public void setSerieDate(String serieDate) {
        this.serieDate = serieDate;
    }

    public String getSerieDescription() {
        return serieDescription;
    }

    public void setSerieDescription(String serieDescription) {
        this.serieDescription = serieDescription;
    }

    public String getStudyDate() {
        return studyDate;
    }

    public void setStudyDate(String studyDate) {
        this.studyDate = studyDate;
    }

    public String getStudyDescription() {
        return studyDescription;
    }

    public void setStudyDescription(String studyDescription) {
        this.studyDescription = studyDescription;
    }
    
    public int getPatientAge() {
        return patientAge;
    }
    
    public void setPatientAge(int patientAge) {
        this.patientAge = patientAge;
    }
}
