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
public class DermExplorer {

	private static final Logger log = LoggerFactory.getLogger(DermExplorer.class);
//    
    public final static String NAME = Messages.getString("DermExplorer.title");
    public final static String BUTTON_NAME = Messages.getString("DermExplorer.btn_title");
    public final static String DESCRIPTION = Messages.getString("DermExplorer.desc");
    private final DicomModel model;
//    
//    private final AbstractAction importAction = new AbstractAction(
//        Messages.getString("DermExplorer.to")) { 
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                log.info("DERM Action command: " + e.getActionCommand());
//                
//                ImgImportDialog dialog = new ImgImportDialog(null, true, model);
//                JMVUtils.showCenterScreen(dialog);
//            }
//    };
    
    public DermExplorer() {
        this(null);
    }
    
    public DermExplorer(DicomModel model) {
        this.model = model == null ? new DicomModel() : model;
        log.info("DermExplorer initialized.");
    }
    

    public void dispose() {
    }


    public DataExplorerModel getDataExplorerModel() {
         return model;
    }


    public Action getOpenImportDialogAction() {
        return null;
    }


    public Action getOpenExportDialogAction() {
        return null;
    }


    public void propertyChange(PropertyChangeEvent pce) {
        log.info("DERM property change");
    }


    public String getUIName() {
        return DermExplorer.NAME;
    }


    public String getDescription() {
        return DermExplorer.DESCRIPTION;
    }


    public Icon getIcon() {
        return null;
    }
}
