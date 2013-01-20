/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.form;

import in.raster.mayam.context.ApplicationContext;
import in.raster.mayam.model.ServerHL7Model;
import in.raster.mayam.model.table.PresetTableModel;
import in.raster.mayam.model.table.ServerHL7TableModel;
import in.raster.mayam.model.table.renderer.CellRenderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author mariano
 */
public class HL7ServerManager  extends javax.swing.JPanel implements KeyListener {

/** Creates new form WindowingManagerPanel */
    //private JFrame outerContainer;
    private ServerChangeListener serverChangeListener;

    public HL7ServerManager(JFrame outerContainer) {
        //this.outerContainer = outerContainer;
        initComponents();
        initializeDefault();
    }

    public HL7ServerManager() {
        initComponents();
        initializeDefault();
    }

    public void initializeDefault() {
        setServerTableModel();
        serverListTable.addKeyListener(this);
        changeTabActionForTable(serverListTable);
    }

    public void addServerChangeListener(ServerChangeListener serverChangeListener) {
        this.serverChangeListener = serverChangeListener;
        ((ServerHL7TableModel) serverListTable.getModel()).addChangeListener(serverChangeListener);
    }

    public void addListenerToModel() {
        ((ServerHL7TableModel) serverListTable.getModel()).addChangeListener(serverChangeListener);
    }

    private void setServerTableModel() {
        ServerHL7TableModel serverTableModel = new ServerHL7TableModel();
        serverTableModel.setData(ApplicationContext.databaseRef.getServerHL7List());
        serverListTable.setModel(serverTableModel);
        setServerRetrieveComboEditor();
        serverListTable.getColumnModel().getColumn(0).setMinWidth(80);
        serverListTable.getColumnModel().getColumn(1).setMinWidth(80);
        serverListTable.getColumnModel().getColumn(2).setMinWidth(80);
        serverListTable.getColumnModel().getColumn(3).setMinWidth(80);
        if (serverChangeListener != null) {
            addListenerToModel();
        }

    }

    private void setServerRetrieveComboEditor() {
        String[] retrieveTypeArray = {"MLLP"}; //podria soportar mas protocolos:HTTP,TCP, etc.
        JComboBox comboBox = new JComboBox(retrieveTypeArray);
        comboBox.setMaximumRowCount(4);
        TableCellEditor editor = new DefaultCellEditor(comboBox);
        serverListTable.getColumnModel().getColumn(3).setCellEditor(editor);
    }

    private void addOrDeleteServerNotification() {
        serverChangeListener.onServerChange();
    }

    private void initComponents() {

        serverLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        serverListTable = new javax.swing.JTable();
        addButton = new javax.swing.JButton();
        verifyButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        serverLabel.setBackground(new java.awt.Color(0, 0, 0));
        serverLabel.setFont(new java.awt.Font("Lucida Grande", 1, 14));
        serverLabel.setForeground(new java.awt.Color(255, 138, 0));
        serverLabel.setText("Servers");
        serverLabel.setOpaque(true);

        serverListTable.setModel(new PresetTableModel());
        serverListTable.setDefaultRenderer(Object.class, new CellRenderer());
        //serverListTable.getTableHeader().setPreferredSize(new Dimension(jScrollPane1.WIDTH,25));
        //serverListTable.setRowHeight(25);

        serverListTable.getTableHeader().setPreferredSize(new Dimension(this.getWidth(), 25));
        Font ff=new Font("Lucida Grande",Font.BOLD,12);
        serverListTable.getTableHeader().setFont(ff);
        serverListTable.setRowHeight(20);
        serverListTable.getTableHeader().setForeground(new Color(255,138,0));
        serverListTable.getTableHeader().setBackground(new Color(0,0,0));
        jScrollPane1.setViewportView(serverListTable);

        addButton.setText("Add");
        addButton.setFocusable(false);
        addButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        addButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        verifyButton.setText("Verify");
        verifyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.setFocusable(false);
        deleteButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        deleteButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(serverLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap(268, Short.MAX_VALUE)
                .add(verifyButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(addButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(deleteButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 89, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {addButton, deleteButton, verifyButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(serverLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(30, 30, 30)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(deleteButton)
                    .add(addButton)
                    .add(verifyButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );
    }                    

    public void changeTabActionForTable(JTable table) {
        InputMap im = table.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        //  Have the enter key work the same as the tab key

        KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
        //  KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        // im.put(enter, im.get(tab));

        //  Disable the right arrow key

        KeyStroke right = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
        im.put(right, "none");

        //  Override the default tab behaviour
        //  Tab to the next editable cell. When no editable cells goto next cell.

        final Action oldTabAction = table.getActionMap().get(im.get(tab));
        Action tabAction = new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                oldTabAction.actionPerformed(e);
                JTable table = (JTable) e.getSource();
                int rowCount = table.getRowCount();
                int columnCount = table.getColumnCount();
                int row = table.getSelectedRow();
                int column = table.getSelectedColumn();

                while (!table.isCellEditable(row, column)) {
                    column += 1;

                    if (column == columnCount) {
                        column = 0;
                        row += 1;
                    }

                    if (row == rowCount) {
                        row = 0;
                    }

                    //  Back to where we started, get out.

                    if (row == table.getSelectedRow()
                            && column == table.getSelectedColumn()) {
                        break;
                    }
                }
                table.changeSelection(row, column, true, false);
            }
        };
        table.getActionMap().put(im.get(tab), tabAction);
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {                                          
        ServerHL7Model serverModel = new ServerHL7Model();
        serverModel.setServerName("Server Name");
        //serverModel.setAeTitle("AETITLE");
        serverModel.setServerAddr("127.0.0.1");
        serverModel.setPort(104);
        serverModel.setSubProtocol("MLLP");
        //serverModel.setRetrieveTransferSyntax("");
        ApplicationContext.databaseRef.insertHL7Server(serverModel);
        setServerTableModel();
        addOrDeleteServerNotification();

}                                         

    private void verifyButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        /*try {
            String serverName = ((ServerTableModel) serverListTable.getModel()).getValueAt(serverListTable.getSelectedRow(), 0);
            AEModel ae = ApplicationContext.databaseRef.getServerDetail(serverName);
            DcmURL url = new DcmURL("dicom://" + ae.getAeTitle() + "@" + ae.getHostName() + ":" + ae.getPort());
            EchoService echo = new EchoService();
            EchoStatus echoStatus = new EchoStatus(outerContainer, true);
            Display.alignScreen(echoStatus);
            echo.checkEcho(url);
            echoStatus.setTitle("Echo Status");
            try {
                if (echo.getStatus().trim().equalsIgnoreCase("EchoSuccess")) {
                    echoStatus.status.setText("Echo dicom://" + ae.getAeTitle() + "@" + ae.getHostName() + ":" + ae.getPort() + " successfully!");
                    echoStatus.setVisible(true);
                } else {
                    echoStatus.status.setText("Echo dicom://" + ae.getAeTitle() + "@" + ae.getHostName() + ":" + ae.getPort() + " not successfully!");
                    echoStatus.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }                                            

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {                                             
        if (serverListTable.getSelectedRow() != -1) {
            int canDelete = 0;
            canDelete = JOptionPane.showConfirmDialog(null, "Are you sure want to delete the server", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
            if (canDelete == 0) {
                ServerHL7Model serverModel = ((ServerHL7TableModel) serverListTable.getModel()).getRow(serverListTable.getSelectedRow());
                ApplicationContext.databaseRef.deleteHL7Server(serverModel);
                setServerTableModel();
                addOrDeleteServerNotification();
            }
        }
    }                                            
    // Variables declaration - do not modify                     
    private javax.swing.JButton addButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel serverLabel;
    private javax.swing.JTable serverListTable;
    private javax.swing.JButton verifyButton;
    // End of variables declaration                   

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            int canDelete = 0;
            canDelete = JOptionPane.showConfirmDialog(null, "Are you sure want to delete the server", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
            if (canDelete == 0) {
                if (serverListTable.getSelectedRow() != -1) {
                    ServerHL7Model serverModel = ((ServerHL7TableModel) serverListTable.getModel()).getRow(serverListTable.getSelectedRow());
                    ApplicationContext.databaseRef.deleteHL7Server(serverModel);
                    setServerTableModel();
                    addOrDeleteServerNotification();
                }
            }
        }
    }
    
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
    
}
