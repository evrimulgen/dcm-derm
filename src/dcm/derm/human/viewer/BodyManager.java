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
    private Vector3f currentCoord = null;
    ArrayList coordList = null;
    
    private BodyManager() {
        coordList = new ArrayList();
    }
    
    public static BodyManager getInstance() {
        if (instance == null) {
            instance = new BodyManager();
        }
        return instance;
    }
    
    public void setCoord(Vector3f coord) {
        currentCoord = coord;
        coordList.add(coord);
        setChanged();
        notifyObservers();
    }
    
    public Vector3f getCoord() {
        return currentCoord;
    }

    public void valueChanged(ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting()) {
            JList list = (JList)lse.getSource();
            int i = list.getSelectedIndex();
            if(i > -1) {
                currentCoord = (Vector3f) coordList.get(i);
            }
        }
    }
    
    public void reset() {
        currentCoord = null;
        coordList.clear();
    }
} 
