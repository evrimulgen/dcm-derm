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
package in.raster.mayam.model.table;

import in.raster.mayam.context.ApplicationContext;
import in.raster.mayam.model.StudyModel;
import java.util.MissingResourceException;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author  BabuHussain
 * @version 0.5
 *
 */
public class StudyListModel extends AbstractTableModel {

    String columnName[] = {ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_1"),
            ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_2"), 
            ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_3"),
            ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_4"),
            ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_5"),
            ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_6"),
            ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_7"),
            ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_8")};
    
    Class columnType[] = {String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class};
    Vector studyList;

    public StudyListModel() {
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
                return row.getDob();
            case 3:
                return row.getAccessionNo();
            case 4:
                return row.getStudyDate();
            case 5:
                return row.getStudyDescription();
            case 6:
                return row.getModalitiesInStudy();
            case 7:
                return row.getStudyLevelInstances();
            case 8:
                return row.getStudyUID();
            case 9:
                return row.getPatientSex();
        }
        return "";
    }

    @Override
    public String getColumnName(int column) {
        try{
            switch(column){
                case 0:
                    return ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_1");
                case 1:
                    return ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_2");
                case 2:
                    return ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_3");
                case 3:
                    return ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_4");
                case 6:
                    return ApplicationContext.resBundle.getString("MainScreen.studyListModel.text_7");
            }
        } catch(MissingResourceException exception){
            return columnName[column];
        }

        return columnName[column];
    }

    public Class getColumnClass(int c) {
        return columnType[c];
    }

    public boolean isCellEditable(int r, int c) {
        return false;
    }

    public void setValueAt(Object aValue, int r, int c) {
    }
}
