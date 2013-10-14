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
package in.raster.mayam.delegates;

import in.raster.mayam.context.ApplicationContext;
import in.raster.mayam.form.SendingProgress;
import in.raster.mayam.form.display.Display;
import in.raster.mayam.models.ServerModel;
import in.raster.mayam.models.Instance;
import in.raster.mayam.models.Series;
import in.raster.mayam.util.core.DcmSnd;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author  BabuHussain
 * @author mdiaz
 * @version 0.5
 *
 */
public class SendingDelegate {

    String forwardAET = "";
    String forwardHost = "";
    int forwardPort;
    String studyUid = "";
    int seriesLevel = 0;
    ServerModel ae = null;
    SendingProgress sendingProgress;
    
   /**
     * This routine used to send the study to the specified AE
     * @param studyIUID
     * @param ae
     */
    public void send(String studyUid, int seriesLevel, ServerModel ae) throws Exception {
        sendingProgress = new SendingProgress();
        sendingProgress.updateBar(0);
        forwardAET = ae.getAeTitle();
        forwardHost = ae.getHostName();
        forwardPort = ae.getPort();
        int count = 0;
        sendingProgress.setProgressMaximum(seriesLevel);
        Display.alignScreen(sendingProgress);
        sendingProgress.setVisible(true);
        ArrayList<Series> seriesList = ApplicationContext.databaseRef.getSeriesList(studyUid);
        Iterator<Series> seriesItr = seriesList.iterator();
        try {
            while (seriesItr.hasNext()) {
                Series series = seriesItr.next();
                Iterator<Instance> imgitr = series.getImageList().iterator();
                while (imgitr.hasNext()) {
                    Instance img = imgitr.next();
                    File temp = new File(ApplicationContext.getAppDirectory() + File.separator + img.getFilepath());
                    String forwardParam[];
                    if (temp.isFile()) {
                        forwardParam = new String[]{forwardAET + "@" + forwardHost + ":" + forwardPort, temp.getAbsolutePath()};
                    } else {
                        temp = new File(img.getFilepath());
                        forwardParam = new String[]{forwardAET + "@" + forwardHost + ":" + forwardPort, temp.getAbsolutePath()};
                    }
                    DcmSnd.main(forwardParam);
                    count++;
                    sendingProgress.updateBar(count);
                }
            }
        } catch (Exception e) {
            sendingProgress.setVisible(false);
            throw e;
        }
        sendingProgress.setVisible(false);
    }

}
