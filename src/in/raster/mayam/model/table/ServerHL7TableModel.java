/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.model.table;

import in.raster.mayam.context.ApplicationContext;
import in.raster.mayam.form.ServerChangeListener;
import in.raster.mayam.model.ServerHL7Model;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;


/**
 * PASAR TODO ESTO A ESTANDAR HL7
 * @author mariano
 */
public class ServerHL7TableModel extends AbstractTableModel {

    private boolean editable = true;
    String columnName[] = {"Server Name", "Server Address", "Port", "Subprotocol"};
    Class columnType[] = {String.class, String.class, String.class, String.class};
    ArrayList serverList;
    ServerChangeListener listener = null;
    
    public ServerHL7TableModel() {
    }
    
    public void setData(ArrayList v) {
        serverList = v;
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
        return columnName.length;
    }

    @Override
    public String getValueAt(int nRow, int nCol) {
        if (nRow < 0 || nRow >= getRowCount()) {
            return "";
        }
        ServerHL7Model row = (ServerHL7Model) serverList.get(nRow);
        switch (nCol) {
            case 0:
                return row.getServerName();
            case 1:
                return row.getServerAddr();
            case 2:
                return Integer.toString(row.getPort());
            case 3:
                return row.getSubProtocol();
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
        return editable;
    }

    public ServerHL7Model getRow(int r) {
        return (ServerHL7Model) serverList.get(r);
    }

    public void setValueAt(Object aValue, int r, int c) {
        ServerHL7Model row = (ServerHL7Model) serverList.get(r);
        switch (c) {
            case 0:
                try {
                    row.setServerName(aValue.toString());
                    ApplicationContext.databaseRef.updateServerHL7ListValues(row);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    row.setServerAddr(aValue.toString());
                    ApplicationContext.databaseRef.updateServerHL7ListValues(row);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    int port = Integer.parseInt(aValue.toString());
                    row.setPort(port);
                    ApplicationContext.databaseRef.updateServerHL7ListValues(row);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    row.setSubProtocol(aValue.toString());
                    ApplicationContext.databaseRef.updateServerHL7ListValues(row);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        if (listener != null) {
            listener.onServerChange();
        }
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void addChangeListener(ServerChangeListener listener) {
        this.listener = listener;
    }

    public ServerChangeListener getListener() {
        return this.listener;
    }
}
