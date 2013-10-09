package in.raster.mayam.models.table;

import in.raster.mayam.context.ApplicationContext;
import in.raster.mayam.models.EMRServerModel;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author mariano
 */
public class EMRServerTableModel extends AbstractTableModel {

    String columnNames[] = new String[]{"Server Name", "Host Name", "Port", "Subprotocol"};
    Class columnTypes[] = new Class[]{String.class, String.class, String.class, String.class};
    ArrayList<EMRServerModel> serverList = new ArrayList<EMRServerModel>();
    boolean editable = true;
        
    public void setData(ArrayList<EMRServerModel> rowdata) {
        this.serverList = rowdata;
    }
    
    @Override
    public int getRowCount() {
        if (serverList != null) {
            return serverList.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return ApplicationContext.currentBundle.getString("Preferences.emrservers.serverNameColumn.text");
            case 1:
                return ApplicationContext.currentBundle.getString("Preferences.emrservers.hostnameColumn.text");
            case 2:
                return ApplicationContext.currentBundle.getString("Preferences.emrservers.portColumn.text");
            case 3:
                return ApplicationContext.currentBundle.getString("Preferences.emrservers.subprotocolColumn.text");
        }
        return columnNames[column];
    }
    
    @Override
    public Class getColumnClass(int c) {
        return columnTypes[c];
    }

    @Override
    public boolean isCellEditable(int r, int c) {
        return true;
    }

    public EMRServerModel getRow(int r) {
        return (EMRServerModel) serverList.get(r);
    }

    @Override
    public Object getValueAt(int row, int col) {
        if (row < 0 || row >= getRowCount()) {
            return "";
        }
        EMRServerModel server = (EMRServerModel) serverList.get(row);
        switch (col) {
            case 0:
                return server.getDescription();
            case 1:
                return server.getHostName();
            case 2:
                return Integer.toString(server.getPort());
            case 3:
                return server.getSubprotocol();
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int r, int c) {
        EMRServerModel row = serverList.get(r);
        switch (c) {
            case 0:
                String prevName = row.getDescription();
                row.setDescription(aValue.toString());
                boolean isDuplicate = ApplicationContext.databaseRef.updateEMRServer(row);
                if (!isDuplicate && !"Description".equals(row.getDescription())) {
//                    ApplicationContext.mainScreenObj.addOrEditEMRServer(prevName, row.getDescription());
                } else {
                    row.setDescription(prevName);
                    JOptionPane.showMessageDialog(null, "Server already exist", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 1:
                row.setHostName(aValue.toString());
                ApplicationContext.databaseRef.updateEMRServer(row);
                break;
            case 2:
                row.setPort(Integer.parseInt(aValue.toString()));
                ApplicationContext.databaseRef.updateEMRServer(row);
                break;
            case 3:
                row.setSubprotocol(aValue.toString());
                ApplicationContext.databaseRef.updateEMRServer(row);
                break;
        }
    }
}
