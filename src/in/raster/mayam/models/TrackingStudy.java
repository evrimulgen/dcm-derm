/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.models;

/**
 *
 * @author mariano
 */
public class TrackingStudy {
    private String trackId;
    private String studyUID;
    private int orderNumber;

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getStudyUID() {
        return studyUID;
    }

    public void setStudyUID(String studyUID) {
        this.studyUID = studyUID;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    
}
