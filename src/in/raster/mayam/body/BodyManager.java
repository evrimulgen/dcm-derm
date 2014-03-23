/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.body;

import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class BodyManager extends Observable implements ListSelectionListener {
    
    private static BodyManager instance = null;
    private Vector3f currentPoint = null;
    private ArrayList<CoordBean> coordList = null;
   
    private BodyManager() {
        coordList = new ArrayList<CoordBean>();
        currentPoint = new Vector3f();
    }
    
    public static BodyManager getInstance() {
        if (instance == null) {
            instance = new BodyManager();
        }
        return instance;
    }
    
    public void setCoord(Vector3f coord) {
        if(currentPoint == null) {
            currentPoint = new Vector3f();
        }
        currentPoint.set(coord);
        CoordBean cb = new CoordBean();
        cb.setPoint(coord);
        coordList.add(cb);
        setChanged();
        notifyObservers();
    }
    
    public Vector3f getCoord() {
        if(currentPoint != null) {
            return currentPoint.clone();
        }
        else {
            return null;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting()) {
            JList list = (JList)lse.getSource();
            int i = list.getSelectedIndex();
            if(i > -1) {
                CoordBean cb = (CoordBean)coordList.get(i);
                if(cb != null) {
                    if(currentPoint == null) {
                        currentPoint = new Vector3f();
                    }
                    currentPoint.set(cb.getPoint());
                }
            }
        }
    }
    
    public void reset() {
        currentPoint = null;
        coordList.clear();
    }

    public void setCoordList(ArrayList<CoordBean> coordList) {
        this.coordList = (ArrayList<CoordBean>) coordList.clone();
    }
    
    public ArrayList<CoordBean> getCoordList() {
        return coordList;
    }
    
    public void removeLastCoord() {
        currentPoint = null;
        if (this.coordList.size() > 0) {
            this.coordList.remove(this.coordList.size()-1);
        }
    }
    
} 
