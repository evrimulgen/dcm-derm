package org.weasis.derm.explorer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import org.dcm4che2.data.BasicDicomObject;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.UID;
import org.dcm4che2.data.VR;
import org.dcm4che2.imageio.plugins.dcm.DicomStreamMetaData;
import org.dcm4che2.imageioimpl.plugins.dcm.DicomImageWriterSpi;
import org.dcm4che2.util.UIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author mariano
 */
public class LoadLocalIMG {

    private static final Logger log = LoggerFactory.getLogger(LoadLocalIMG.class);
    
    /**
     * 
     * @param src
     * @param dst
     * @param data 
     */
    static void convertImgToDICOM(File[] src, File dst, BasicDcmDataBean data) {
        
        log.info("Creating Multiframe File...");
        try {
            // sea chequea que las imagenes seleccionadas contengan las mismas
            // propiedades.
            if (src.length > 1 && !imagesContainSameInformation(src)) {
                throw new Exception("Error creating DICOM: All images must have the same pixel information.");
            }
            
            // se crea instancia DICOM image writer instance y se setea su salida
            ImageWriter writer = new DicomImageWriterSpi().createWriterInstance();
            FileImageOutputStream output = new FileImageOutputStream(dst);
            writer.setOutput(output);
            
            // se crea un nuevo dataset (header/metadata) para el DICOM image writer
            DicomObject ds = createDicomHeader(src[0], src.length, data);
            
            // se setea la metadata al DICOM image writer and se prepara para codificar la secuencia multiframe
            DicomStreamMetaData writeMeta = (DicomStreamMetaData) writer.getDefaultStreamMetadata(null);
            writeMeta.setDicomObject(ds);
            writer.prepareWriteSequence(writeMeta);
            
            log.info("Start of Write Sequence...");
            
            for (int i = 0; i < src.length; i++) {
                log.info("Encoding frame # "+ (i+1));
                BufferedImage frame = ImageIO.read(src[i]);
                // se crea un nuevo IIOImage para ser guardado a la secuencia multiframe DICOM
                IIOImage iioimage = new IIOImage(frame, null, null);
                // se escribe la imagen a la secuencia multiframe DICOM
                writer.writeToSequence(iioimage, null);
            }

            log.info("End of Write Sequence.");
            // una vez creado el archivo multiframe se finaliza la secuencia y se cierra el output stream.
            writer.endWriteSequence();
            output.close();

            log.info("Multiframe File Created.");

        } catch(Exception e) {
            log.error("ERROR creating DICOM: Exception.", e);
        }
    }

    /**
     * 
     * @param src
     * @return
     * @throws IOException 
     */
    private static boolean imagesContainSameInformation(File[] src) throws IOException {
        BufferedImage jpegImage = null;
        boolean result = true;
        int colorComponentsBase = 0;
        int bitsPerPixelBase = 0;
        int colorComponents = 0;
        int bitsPerPixel = 0;
        
        jpegImage = ImageIO.read(src[0]);
        if (jpegImage == null) {
            throw new IOException("Error creating DICOM: IO error reading src image.");
        }
        colorComponentsBase = jpegImage.getColorModel().getNumColorComponents();
        bitsPerPixelBase = jpegImage.getColorModel().getPixelSize();
        log.info("Frame 1: Color Component:"+colorComponentsBase+" Bits Per Pixel:"+bitsPerPixelBase);
        for(int i=1; i < src.length; i++) {
            jpegImage = ImageIO.read(src[i]);
            if (jpegImage == null) {
                throw new IOException("Error creating DICOM: IO error reading src image.");
            }
            colorComponents = jpegImage.getColorModel().getNumColorComponents();
            bitsPerPixel = jpegImage.getColorModel().getPixelSize();
            
            log.info("Frame "+(i+1)+": Color Component:"+colorComponents+" Bits Per Pixel:"+bitsPerPixel);
            if ( colorComponentsBase != colorComponents 
                    || bitsPerPixelBase != bitsPerPixel) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 
     * @param src
     * @param numberOfFrames
     * @param data
     * @return 
     */
    private static DicomObject createDicomHeader(File src, int numberOfFrames,
            BasicDcmDataBean data) {
        BufferedImage jpegImage = null;
        DicomObject dicom = new BasicDicomObject();  
        try {
            jpegImage = ImageIO.read(src);

            if (jpegImage == null) {
                throw new Exception("Error creating DICOM: IO error reading src image.");
            }

            int colorComponents = jpegImage.getColorModel().getNumColorComponents();
            int bitsPerPixel = jpegImage.getColorModel().getPixelSize();
            int bitsAllocated = (bitsPerPixel / colorComponents);
            int samplesPerPixel = colorComponents;

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

             //datos del multiframe
            dicom.putInt(Tag.StartTrim, VR.US, 1);                   // comenzar en el frame 1
            dicom.putInt(Tag.StopTrim, VR.US, numberOfFrames);       // parar en el frame N
            dicom.putString(Tag.FrameTime, VR.DS, "33.33");          // milliseconds (30 frames por segundo)
            dicom.putString(Tag.FrameDelay, VR.DS, "0.0");           // no frame dalay
            dicom.putInt(Tag.NumberOfFrames, VR.UL, numberOfFrames); // el numero de frames
            dicom.putInt(Tag.RecommendedDisplayFrameRate, VR.US, 3); 
            dicom.putInt(Tag.FrameIncrementPointer, null, Tag.FrameTime);
            //fin datos del multiframe

            dicom.initFileMetaInformation(UID.JPEGLSLossless);

        }
        catch(Exception e) {
            log.error("ERROR creating DICOM: Exception.", e);
        }
        return dicom;
    }
  
}
