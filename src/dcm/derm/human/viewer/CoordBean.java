package dcm.derm.human.viewer;

import com.jme3.math.Vector3f;

public class CoordBean {
    
    private String SOPId = null;
    
    private Vector3f point = null;
    
    private Integer frameNuber = null;
    
    public CoordBean(){};

    public String getSOPId() {
        return SOPId;
    }

    public void setSOPId(String SOPId) {
        this.SOPId = SOPId;
    }

    public Vector3f getPoint() {
        return point;
    }

    public void setPoint(Vector3f point) {
        this.point = point;
    }

    public Integer getFrameNuber() {
        return frameNuber;
    }

    public void setFrameNuber(Integer frameNuber) {
        this.frameNuber = frameNuber;
    }
}
