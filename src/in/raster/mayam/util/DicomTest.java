/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.util;

import com.pixelmed.dicom.Attribute;
import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.AttributeTagAttribute;
import com.pixelmed.dicom.ClinicalTrialsAttributes;
import com.pixelmed.dicom.DecimalStringAttribute;
import com.pixelmed.dicom.DicomDictionary;
import com.pixelmed.dicom.DicomDictionaryBase;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.LongStringAttribute;
import com.pixelmed.dicom.TagFromName;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mariano
 */
public class DicomTest {
    
    public static void main(String[] params) {
        String srcFilePath = "/home/mariano/Ubuntu One/Tesis/IMG/2929JGGC";
        try {
            AttributeList list = new AttributeList();
            list.read(srcFilePath);
            
            AttributeTag abcdTag = new AttributeTag(0x0019,0x0010);
            Attribute abcdAttr = new LongStringAttribute(abcdTag);
            abcdAttr.addValue("ABCD Result values");
            list.put(abcdAttr);
            
            AttributeTag aTag = new AttributeTag(0x0019,0x1010);
            Attribute aAttr = new DecimalStringAttribute(aTag);
            aAttr.addValue("2.3");
            list.put(aAttr);
            
            AttributeTag bTag = new AttributeTag(0x0019,0x1020);
            Attribute bAttr = new DecimalStringAttribute(bTag);
            bAttr.addValue("0.12");
            list.put(bAttr);
            
            AttributeTag cTag = new AttributeTag(0x0019,0x1030);
            Attribute cAttr = new DecimalStringAttribute(cTag);
            cAttr.addValue("1.50");
            list.put(cAttr);
            
            AttributeTag dTag = new AttributeTag(0x0019,0x1040);
            Attribute dAttr = new DecimalStringAttribute(dTag);
            dAttr.addValue("0.02");
            list.put(dAttr);
            
            AttributeTag rTag = new AttributeTag(0x0019,0x1050);
            Attribute rAttr = new DecimalStringAttribute(rTag);
            rAttr.addValue("4.22");
            list.put(rAttr);

//          System.out.println(ClinicalTrialsAttributes.isSafePrivateAttribute(e.getTag().),list));
//            for (AttributeTag tag : list.keySet()) {
//                System.out.println(tag.toString()+":"+list.get(tag).getVRAsString()+list.get(tag).getVM());
//            }
            list.write(srcFilePath);
        } catch (IOException ex) {
            Logger.getLogger(DicomTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DicomException ex) {
            Logger.getLogger(DicomTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
