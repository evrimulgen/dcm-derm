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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.point != null ? this.point.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CoordBean other = (CoordBean) obj;
        if (this.point != other.point && (this.point == null || !this.point.equals(other.point))) {
            return false;
        }
        return true;
    }

  
}
