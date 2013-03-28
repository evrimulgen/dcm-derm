/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dcm.derm.human.viewer;

import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class BodyManager extends Observable implements ListSelectionListener {
    
    private static BodyManager instance = null;
    private CoordBean currentCoord = null;
    ArrayList<CoordBean> coordList = null;
   
    private BodyManager() {
        coordList = new ArrayList<CoordBean>();
        currentCoord = new CoordBean();
    }
    
    public static BodyManager getInstance() {
        if (instance == null) {
            instance = new BodyManager();
        }
        return instance;
    }
    
    public void setCoord(Vector3f coord) {
        currentCoord.setPoint(coord);
        CoordBean cb = new CoordBean();
        cb.setPoint(coord);
        coordList.add(cb);
        setChanged();
        notifyObservers();
    }
    
    public Vector3f getCoord() {
        return currentCoord.getPoint();
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting()) {
            JList list = (JList)lse.getSource();
            int i = list.getSelectedIndex();
            if(i > -1) {
                currentCoord = (CoordBean) coordList.get(i);
            }
        }
    }
    
    public void reset() {
        currentCoord.setPoint(null);
        coordList.clear();
    }

    void setCoordList(ArrayList<CoordBean> coordList) {
        this.coordList = (ArrayList<CoordBean>) coordList.clone();
    }
    
} 
