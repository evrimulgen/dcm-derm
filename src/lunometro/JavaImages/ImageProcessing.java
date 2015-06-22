package lunometro.JavaImages;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JTextArea;

 
public class ImageProcessing {

        static private String status;
        static private ArrayList<String> allStatus=new ArrayList<String>();
        static private BufferedImage currentImage;
        static private ArrayList<BufferedImage> allImages=new ArrayList<BufferedImage>();
        static private ArrayList<String> activityLog=new ArrayList<String>();
        static private String infolastOpenedImage;
        static private BufferedImage lastOpenedImage;
        static private ArrayList<String> allInfoOpenedImages=new ArrayList<String>();
        static private ArrayList<BufferedImage> allOpenedImages=new ArrayList<BufferedImage>();
        static private int counterOpenedImages=-1;
        static private int counterImages=-1;
        static private JTextArea txtArea;
        
        /**
        * Enumeration with the file extensions
        */
        static public enum imageFormat{all,all_images,bmp,gif,jpg,png}

               
    
    private boolean checkingSize(ArrayList arrayChecking){
        boolean flag;
        if (arrayChecking.size()>0){
            flag=true;
        }else{
            flag=false;
        }
        return flag;
    }
    
    private String getCurrentTime() {
    Date now = new Date();
    SimpleDateFormat formated = new SimpleDateFormat("hh:mm:ss");
    return formated.format(now).toString();
}
    
    /**
     * @return a String that contains the status of the last modification made.
     * If no status stored, returns null
     */
    public String getStatus() {
        if (checkingSize(ImageProcessing.allStatus)){
            ImageProcessing.status=allStatus.get(ImageProcessing.allStatus.size()-1);
            return ImageProcessing.status;
        }else{
            return null;
        }
    }

    /**
     * @return a Arraylist<String> that contains all the states of the modifications
     */
    public ArrayList<String> getAllStatus() {
        return allStatus;
    }

    /**
     * @return a BufferedImage that contains the last image received by the class.
     * If no images stored, returns null
     */
    public BufferedImage getCurrentImage() {
        if (checkingSize(ImageProcessing.allImages)){
        ImageProcessing.currentImage=allImages.get(ImageProcessing.allImages.size()-1);
        return ImageProcessing.currentImage;
         }else{
            return null;
        }
    }

    /**
     * @return a Arraylist<BufferedImage> that contains all the images received by the class
     */
    public ArrayList<BufferedImage> getAllImages() {
        return allImages;
    }
     
    /**
     * @return a Arraylist<String> that contains all states and errors processed
     */
    public ArrayList<String> getActivityLog() {
        return activityLog;
    }
    
    /**
     * @return a Arraylist<String> that contains all the information of the images open
     */
    public static ArrayList<String> getinfoOpenedImages() {
        return allInfoOpenedImages;
    }

    /**
     * @return a Arraylist<BufferedImage> that contains all the images open
     */
    public static ArrayList<BufferedImage> getAllOpenedImages() {
        return allOpenedImages;
    }
    
    /**
     * 
     * @return that contains the last image open by the program.
     * If no images stored, returns null
     */
    public BufferedImage lastOpenedImage(){
        if(ImageProcessing.counterOpenedImages>=0){
           ImageProcessing.lastOpenedImage=ImageProcessing.allOpenedImages.get(ImageProcessing.counterOpenedImages);
           this.updateImage("Recovered original image",ImageProcessing.lastOpenedImage);
        }
       return ImageProcessing.lastOpenedImage;
    }
    
   /**
     * 
     * @return that contains the last information of the images open
     */
    public String lastInfoOpenedImage(){
        if(ImageProcessing.counterOpenedImages>=0){
           ImageProcessing.infolastOpenedImage=ImageProcessing.allInfoOpenedImages.get(ImageProcessing.counterOpenedImages);
        }
       return ImageProcessing.infolastOpenedImage;
    }    
    
   /**
     * Method that stores all the information about the program, eg errors.
     * Do not use if you have already called the function JavaImages.ImageProcessing.updateImage()
     * @param message String with the information you want to store
     * @see JavaImages.ImageProcessing#updateImage(java.lang.String, java.awt.image.BufferedImage) 
     */
   protected void updateActivityLog(String message){
        ImageProcessing.activityLog.add(message);
        if (txtArea!=null){
            ImageProcessing.txtArea.setText(ImageProcessing.txtArea.getText()  + this.getCurrentTime() + " - " + message+ "\n");
        }
   }
   
    /**
     * Method that stores information relating to an image processing
     * @param statusImage String that stores information concerning the transformation performed
     * @param currentImage BufferedImage that has been processed
     * @see JavaImages.ImageProcessing#getAllStatus()
     * @see JavaImages.ImageProcessing#getAllImages()
     */
    protected void updateImage(String statusImage,BufferedImage currentImage){
        allStatus.add(statusImage);
        allImages.add(currentImage);
        updateActivityLog(statusImage);
        ImageProcessing.counterImages+=1;
    }
    
    protected void newOpenImage(String infoImage,BufferedImage openedImage){
        ImageProcessing.allInfoOpenedImages.add(infoImage);
        ImageProcessing.allOpenedImages.add(openedImage);
        ImageProcessing.counterOpenedImages+=1;
    }
    
    /**
     * Delete all information stored into the class (images and status). This function 
     * can´t be undone
     */
    public void deleteAllStoredImages(){
        ImageProcessing.allImages.clear();
        ImageProcessing.allStatus.clear();
        ImageProcessing.allOpenedImages.clear();
        ImageProcessing.allInfoOpenedImages.clear();
        ImageProcessing.counterImages=-1;
        ImageProcessing.counterOpenedImages=-1;
        ImageProcessing.currentImage=null;
        ImageProcessing.status=null;
        ImageProcessing.lastOpenedImage=null;
        ImageProcessing.infolastOpenedImage=null;
        this.updateActivityLog("Deleted all stored images register");
    }
   /**
    * method that associates a JTextArea with all the information covered in the class, 
    * for example, information about the processing of the images, errors ...
    * @param textAreaAttached JTextArea where display information
    * @see JavaImages.ImageProcessing#getActivityLog()
    * @see JavaImages.ImageProcessing#getAllStatus() 
    */
    public void attachTextAreaStatus(JTextArea textAreaAttached){
        ImageProcessing.txtArea=textAreaAttached;
        this.updateActivityLog("Attached textArea");
    }
    
    /**
     * Function that returns the previous image with respect to the current image
     * @return Previous BufferedImage.
     * If no previous images, return null
     * @see JavaImages.ImageProcessing#redoImage() 
     * @see JavaImages.ImageProcessing#getCurrentImage() 
     * @see JavaImages.ImageProcessing#getAllImages() 
     */
    public BufferedImage undoImage(){
        if((ImageProcessing.counterImages)>0){
            ImageProcessing.counterImages-=1;
            ImageProcessing.currentImage=allImages.get(ImageProcessing.counterImages);
            this.updateActivityLog("Undo image. Information image: " + allStatus.get(ImageProcessing.counterImages) + ")");
            return currentImage;
        }else{
            return null;
        }
    }
    
   /**
     * Function that returns the next image with respect to the current image
     * @return Next BufferedImage.
     * If no next images return null
     * @see JavaImages.ImageProcessing#undoImage() 
     * @see JavaImages.ImageProcessing#getCurrentImage() 
     * @see JavaImages.ImageProcessing#getAllImages() 
     */
   public BufferedImage redoImage(){
        if((ImageProcessing.counterImages)<ImageProcessing.allImages.size()-1){
            ImageProcessing.counterImages+=1;
            ImageProcessing.currentImage=allImages.get(ImageProcessing.counterImages);
            this.updateActivityLog("Redo image. Information image: " + allStatus.get(ImageProcessing.counterImages) + ")");
            return currentImage;
        }else{
            return null;
        }
    }
   
   protected int colorRGBtoSRGB(Color colorValueRGB){
        int colorSRGB;
        colorSRGB=(colorValueRGB.getRed() << 16) | (colorValueRGB.getGreen() << 8) | colorValueRGB.getBlue();
        return colorSRGB;
   }
    
   protected BufferedImage cloneBufferedImage(BufferedImage bufferImage){
        BufferedImage copy=new BufferedImage (bufferImage.getWidth(),bufferImage.getHeight(),bufferImage.getType());
        copy.setData(bufferImage.getData());
        return copy;
    }
}
