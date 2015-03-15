/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mariano
 */
public class TrackingModel {
    private int trackId = -1;
    private String description;
    private String creationDate;
    private String patientId;
    private List<TrackingStudy> studies;
    
    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date creation = null;
        try {
            creation = sdf.parse(creationDate);
        } catch (ParseException ex) {
            Logger.getLogger(TrackingModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return creation;
    }

    public String getCreationDateString() {
        return creationDate;
    }
    
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public List<TrackingStudy> getStudies() {
        return studies;
    }

    public void setStudies(List<TrackingStudy> studies) {
        this.studies = studies;
    }
 
}
