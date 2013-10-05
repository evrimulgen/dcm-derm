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
 * Devishree V
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

import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Devishree
 * @version 2.0
 */
public class SeriesAnnotations implements Serializable {

    private String seriesInstanceUid;
    private HashMap<Integer, Annotation> annotations;
    private HashMap<Integer, Annotation> multiframeAnnotations = null;

    public SeriesAnnotations(String seriesInstanceUid) {
        this.seriesInstanceUid = seriesInstanceUid;
        annotations = new HashMap<Integer, Annotation>();
    }

    public String getSeriesInstanceUid() {
        return seriesInstanceUid;
    }

    public void setSeriesInstanceUid(String seriesInstanceUid) {
        this.seriesInstanceUid = seriesInstanceUid;
    }

    public HashMap<Integer, Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(HashMap<Integer, Annotation> annotations) {
        this.annotations = annotations;
    }

    public void addAnnotation(int instanceNumber, Annotation annotation) {
        if (annotation != null) {
            annotations.put(instanceNumber, annotation);
        }
    }

    public Annotation getInstanceAnnotation(int instanceNumber) {
        return annotations.get(instanceNumber);
    }

    public boolean isAnnotaionPresent(int instanceNumber) {
        return annotations.containsKey(instanceNumber);
    }

    public void addMultiframeAnnotation(int instanceNumber, Annotation annotation) {
        if (annotation != null) {
            multiframeAnnotations.put(instanceNumber, annotation);
        }
    }

    public Annotation getMultiframeAnnotation(int instanceNumber) {
        return multiframeAnnotations.get(instanceNumber);
    }

    public void initializeMultiframeAnnotations() {
        multiframeAnnotations = new HashMap<Integer, Annotation>();
    }

    public boolean isMultiframeAnnotationsExist() {
        return multiframeAnnotations != null;
    }

    public void removeInstanceAnnotation(int instanceNumber) {
        annotations.remove(instanceNumber);
    }

    public void removeMultiframeAnnotation(int frameNumber) {
        multiframeAnnotations.remove(frameNumber);
    }
}