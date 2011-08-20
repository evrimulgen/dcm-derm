package org.weasis.derm.explorer.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weasis.core.api.explorer.DataExplorerView;
import org.weasis.core.api.service.BundlePreferences;
import org.weasis.core.ui.docking.UIManager;
import org.weasis.derm.explorer.DermExplorer;
import org.weasis.dicom.explorer.DicomModel;


/**
 * 
 * @author mariano
 */

public class Activator implements BundleActivator {
    
    //public final static BundlePreferences PREFERENCES = new BundlePreferences();
    private static final Logger log = LoggerFactory.getLogger(Activator.class);
    
    public void start(BundleContext context) {
        //PREFERENCES.init(context);
        log.info("Starting DermExplorer.");
    }

    
    public void stop(BundleContext context) {
        //DicomManager.getInstance().savePreferences();
        //PREFERENCES.close();
        DataExplorerView explorer = UIManager.getExplorerplugin(DermExplorer.NAME);
        if (explorer instanceof DermExplorer) {
            DermExplorer dexp = (DermExplorer) explorer;
            ((DicomModel) dexp.getDataExplorerModel()).dispose();
        }
        log.info("Stopped DermExplorer.");
    }

}
