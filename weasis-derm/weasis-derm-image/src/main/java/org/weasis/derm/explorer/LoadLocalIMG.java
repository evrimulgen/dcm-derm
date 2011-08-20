package org.weasis.derm.explorer;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import javax.imageio.ImageIO;
import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;
import org.dcm4che2.data.VR;
import org.dcm4che2.io.DicomOutputStream;
import org.dcm4che2.util.UIDUtils;


/**
 *
 * @author mariano
 */
public class LoadLocalIMG {

    static void convertImgToDICOM(File src, File dst, BasicDcmDataBean data) {
        
        BufferedImage jpegImage = null;
        try {

            jpegImage = ImageIO.read(src);
            
            if (jpegImage == null)  
                throw new Exception("Invalid file.");

            int colorComponents = jpegImage.getColorModel().getNumColorComponents();
            int bitsPerPixel = jpegImage.getColorModel().getPixelSize();
            int bitsAllocated = (bitsPerPixel / colorComponents);
            int samplesPerPixel = colorComponents;

            DicomObject dicom = new BasicDicomObject();  
            //datos imagen
            dicom.putString(Tag.SpecificCharacterSet, VR.CS, "ISO_IR 100");  
            dicom.putString(Tag.PhotometricInterpretation, VR.CS, samplesPerPixel == 3 ? "YBR_FULL_422" : "MONOCHROME2");  
            dicom.putInt(Tag.SamplesPerPixel, VR.US, samplesPerPixel);           
            dicom.putInt(Tag.Rows, VR.US, jpegImage.getHeight());  
            dicom.putInt(Tag.Columns, VR.US, jpegImage.getWidth());  
            dicom.putInt(Tag.BitsAllocated, VR.US, bitsAllocated);  
            dicom.putInt(Tag.BitsStored, VR.US, bitsAllocated);  
            dicom.putInt(Tag.HighBit, VR.US, bitsAllocated-1);  
            dicom.putInt(Tag.PixelRepresentation, VR.US, 0);
            //datos paciente
            dicom.putString(Tag.PatientID, VR.UI, data.getPatientId());
            dicom.putInt(Tag.PatientAge, VR.IS, data.getPatientAge());
            dicom.putString(Tag.PatientName, VR.PN, data.getPatientName()+" "+
                data.getPatientLastname());
            dicom.putFloat(Tag.PatientWeight, VR.FL, data.getPatientWeight());
            dicom.putString(Tag.PatientSize, VR.SH, data.getPatientHeight());
            dicom.putString(Tag.PatientSex, VR.SH, data.getPatientSex());
            dicom.putString(Tag.PatientBirthDate, VR.DA, data.getPatientBirthdate());
            //datos estudio
            dicom.putString(Tag.StudyDescription, VR.UT, data.getStudyDescription());
            dicom.putString(Tag.StudyDate, VR.DA, data.getStudyDate());
            dicom.putString(Tag.StudyTime, VR.TM, data.getStudyTime());
            dicom.putString(Tag.StudyID, VR.UI, data.getStudyId());
            dicom.putString(Tag.StudyInstanceUID, VR.UI, UIDUtils.createUID());
            //datos serie  
            dicom.putString(Tag.SeriesDescription, VR.UT, data.getSerieDescription());
            dicom.putString(Tag.SeriesInstanceUID, VR.UI, UIDUtils.createUID());  
            dicom.putString(Tag.SeriesDate, VR.DA, data.getSerieDate());
            dicom.putString(Tag.SeriesTime, VR.TM, data.getSerieTime());
            //datos instancia
            dicom.putDate(Tag.InstanceCreationDate, VR.DA, new Date());  
            dicom.putDate(Tag.InstanceCreationTime, VR.DA, new Date()); 
            
            dicom.putString(Tag.SOPInstanceUID, VR.UI, UIDUtils.createUID());

            dicom.initFileMetaInformation(UID.JPEGBaseline1);

            FileOutputStream fos = new FileOutputStream(dst);  
            BufferedOutputStream bos = new BufferedOutputStream(fos);  
            DicomOutputStream dos = new DicomOutputStream(bos);  
            dos.writeDicomFile(dicom);

            dos.writeHeader(Tag.PixelData, VR.OB, -1);
            dos.writeHeader(Tag.Item, null, 0);

            /* 
            According to Gunter from dcm4che team we have to take care that  
            the pixel data fragment length containing the JPEG stream has  
            an even length. */  
            int jpgLen = (int) src.length();   
            dos.writeHeader(Tag.Item, null, (jpgLen+1)&~1);

            FileInputStream fis = new FileInputStream(src);  
            BufferedInputStream bis = new BufferedInputStream(fis);  
            DataInputStream dis = new DataInputStream(bis);  

            byte[] buffer = new byte[65536];         
            int b;  
            while ((b = dis.read(buffer)) > 0) {  
               dos.write(buffer, 0, b);  
            }  

            /*
            According to Gunter from dcm4che team we have to take care that 
            the pixel data fragment length containing the JPEG stream has 
            an even length. So if needed the line below pads JPEG stream with 
            odd length with 0 byte.
            */
            if ((jpgLen&1) != 0) dos.write(0); 
            dos.writeHeader(Tag.SequenceDelimitationItem, null, 0);
            dos.close();

        } catch(Exception e) {
                e.printStackTrace();
                System.out.println("ERROR: "+ e.getMessage());
        }
    }
  
}
