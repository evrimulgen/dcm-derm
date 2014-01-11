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
package in.raster.mayam.models;

import in.raster.mayam.context.ApplicationContext;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author  BabuHussain
 * @version 0.5
 *
 */
public class EMRStudyModel extends AbstractTableModel {

    String columnName[] = {ApplicationContext.currentBundle.getString("CreateDicomFrame.text_3"),
            ApplicationContext.currentBundle.getString("CreateDicomFrame.text_2"), 
            ApplicationContext.currentBundle.getString("CreateDicomFrame.text_5"),
            ApplicationContext.currentBundle.getString("CreateDicomFrame.text_4"),
            ApplicationContext.currentBundle.getString("CreateDicomFrame.text_7"),
            ApplicationContext.currentBundle.getString("CreateDicomFrame.text_10"),
            ApplicationContext.currentBundle.getString("CreateDicomFrame.text_8")};
    
    Class columnType[] = {String.class, String.class, String.class, String.class, String.class, String.class, String.class};
    Vector studyList;

    public EMRStudyModel() {
    }

    public void setData(Vector v) {
        studyList = v;
        super.fireTableDataChanged();
    }

    @Override
    public int getColumnCount() {
        return columnName.length;
    }

    @Override
    public int getRowCount() {
        if (studyList != null) {
            return studyList.size();
        } else {
            return 0;
        }
    }

    public Vector getStudyList() {
        return studyList;
    }

    @Override
    public String getValueAt(int nRow, int nCol) {
        if (nRow < 0 || nRow >= getRowCount()) {
            return "";
        }
        StudyModel row = (StudyModel) studyList.elementAt(nRow);
        switch (nCol) {
            case 0:
                return row.getPatientId();
            case 1:
                return row.getPatientName();
            case 2:
                return row.getSex();
            case 3:    
                return row.getDob();
            case 4:
                return row.getStudyUID();
            case 5:
                return row.getStudyDescription();
            case 6:
                return row.getStudyDate();
         }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }

    @Override
    public Class getColumnClass(int c) {
        return columnType[c];
    }

    @Override
    public boolean isCellEditable(int r, int c) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int r, int c) {
    }
    
    public String getPatientId(int index) {
        return getValueAt(index, 0);
    }
    
    public String getPatientName(int index) {
        return getValueAt(index, 1);
    }
    
    public String getPatientSex(int index) {
        return getValueAt(index, 2);
    }

    public String getPatientDoB(int index) {
        return getValueAt(index, 3);
    }

    public String getStudyId(int index) {
        return getValueAt(index, 4);
    }

    public String getStudyDescription(int index) {
        return getValueAt(index, 5);
    }

    public String getStudyDate(int index) {
        return getValueAt(index, 6);
    }
}
