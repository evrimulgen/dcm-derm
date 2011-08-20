package org.weasis.derm.explorer;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weasis.core.api.explorer.DataExplorerView;
import org.weasis.core.api.explorer.model.DataExplorerModel;
import org.weasis.core.api.gui.util.JMVUtils;
import org.weasis.dicom.explorer.DicomModel;

/**
 *
 * @author mariano
 */
public class DermExplorer implements DataExplorerView {

    private static final Logger log = LoggerFactory.getLogger(DermExplorer.class);
    
    public final static String NAME = Messages.getString("DermExplorer.title");
    public final static String BUTTON_NAME = Messages.getString("DermExplorer.btn_title");
    public final static String DESCRIPTION = Messages.getString("DermExplorer.desc");
    private final DicomModel model;
    
    private final AbstractAction importAction = new AbstractAction(
        Messages.getString("DermExplorer.to")) { 

            @Override
            public void actionPerformed(ActionEvent e) {
                log.info("DERM Action command: " + e.getActionCommand());
                
                ImgImportDialog dialog = new ImgImportDialog(null, true, model);
                JMVUtils.showCenterScreen(dialog);
            }
    };
    
    public DermExplorer() {
        this(null);
    }
    
    public DermExplorer(DicomModel model) {
        this.model = model == null ? new DicomModel() : model;
        log.info("DermExplorer initialized.");
    }
    
    @Override
    public void dispose() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DataExplorerModel getDataExplorerModel() {
         return model;
    }

    @Override
    public Action getOpenImportDialogAction() {
        return importAction;
    }

    @Override
    public Action getOpenExportDialogAction() {
        return null;
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        log.info("DERM property change");
    }

    @Override
    public String getUIName() {
        return DermExplorer.NAME;
    }

    @Override
    public String getDescription() {
        return DermExplorer.DESCRIPTION;
    }

    @Override
    public Icon getIcon() {
        return null;
    }
}
