package lunometro.JavaImages;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageProcessing_BasicFilters extends ImageProcessing {
    
    private BufferedImage imageTemp;
    private int colorSRGB;
    
    /**
     * Enumerating all filters available
     * @see JavaImages.ImageProcessing_BasicFilters#basicFilters(java.awt.image.BufferedImage, JavaImages.ImageProcessing_BasicFilters.filtersAvailable) 
     */
    public enum filtersAvailable{red,green,blue}
    /**
     * Enumerating all possible changes of RGB 
     * @see JavaImages.ImageProcessing_BasicFilters#RGBto(java.awt.image.BufferedImage, JavaImages.ImageProcessing_BasicFilters.RGBTransformAvailable) 
     */
    public enum RGBTransformAvailable{GBR,GRB,BRG,BGR,RBG}
    /**
     * Enumerating all available channels to invert an image
     * @see JavaImages.ImageProcessing_BasicFilters#invertColorsRGB(java.awt.image.BufferedImage, JavaImages.ImageProcessing_BasicFilters.invertColorsAvailable) 
     */
    public enum invertColorsAvailable{RGB,red,green,blue}
    
    
    private int calculateAverage(Color color){
        int averageColor;
        averageColor=(int)((color.getRed()+color.getGreen()+color.getBlue())/3);
        return averageColor;
    }
    private int checkthreshold(Color color, int threshold){
        if (this.calculateAverage(color)>=threshold){
            this.colorSRGB=super.colorRGBtoSRGB(new Color(255,255,255, color.getAlpha()));
        }else{
            this.colorSRGB=super.colorRGBtoSRGB(new Color(0, 0, 0, color.getAlpha()));
        }
        return this.colorSRGB;
    }
    private BufferedImage selectFilter(BufferedImage image,filtersAvailable colorFilter){
        imageTemp=super.cloneBufferedImage(image);
        Color auxColor;
        for( int i = 0; i < imageTemp.getWidth(); i++ ){
            for( int j = 0; j < imageTemp.getHeight(); j++ ){
                auxColor=new Color(imageTemp.getRGB(i, j));
                imageTemp.setRGB(i, j,this.selectFilterTransform(colorFilter, auxColor));
            }
        }
        super.updateImage("Image Transformation: " + colorFilter.toString() + " filter", imageTemp);
        return imageTemp;
    }
    private int selectFilterTransform(filtersAvailable filter,Color color){
        int colorReturn=0;
        int averageColor=this.calculateAverage(color);
        switch (filter){
            case red:
                colorReturn=super.colorRGBtoSRGB(new Color(averageColor,0,0,color.getAlpha()));
                break;
            case green:
                colorReturn=super.colorRGBtoSRGB(new Color(0,averageColor,0,color.getAlpha()));
                break;
            case blue:
                colorReturn=super.colorRGBtoSRGB(new Color(0,0,averageColor,color.getAlpha()));
                break;
            
        }
        return colorReturn;
    }
    private BufferedImage RGB_to(BufferedImage image,RGBTransformAvailable chanel){
        imageTemp=super.cloneBufferedImage(image);
        Color auxColor;
        for( int i = 0; i < imageTemp.getWidth(); i++ ){
            for( int j = 0; j < imageTemp.getHeight(); j++ ){
                auxColor=new Color(imageTemp.getRGB(i, j));
                this.colorSRGB=this.selectRGBTransform(chanel,auxColor);
                imageTemp.setRGB(i, j,colorSRGB);
            }
        }
        super.updateImage("Image Transformation: RGB to " + chanel.toString(), imageTemp);
        return imageTemp;
    }
    private int selectRGBTransform(RGBTransformAvailable chanel,Color color){
        int colorReturn=0;
        switch (chanel){
            case GBR:
                colorReturn=super.colorRGBtoSRGB(new Color(color.getGreen(), 
                        color.getBlue(), color.getRed(),color.getAlpha()));
                break;
            case GRB:
                colorReturn=super.colorRGBtoSRGB(new Color(color.getGreen(), 
                        color.getRed(), color.getBlue(),color.getAlpha()));
                break;
            case BRG:
                colorReturn=super.colorRGBtoSRGB(new Color(color.getBlue(), 
                        color.getRed(), color.getGreen(),color.getAlpha()));
                break;
            case BGR:
                colorReturn=super.colorRGBtoSRGB(new Color(color.getBlue(), 
                        color.getGreen(), color.getRed(),color.getAlpha()));
                break;
            case RBG:
                colorReturn=super.colorRGBtoSRGB(new Color(color.getRed(), 
                        color.getBlue(), color.getGreen(),color.getAlpha()));
                break;
        }
        return colorReturn;
    }
    private BufferedImage invertColors(BufferedImage image, invertColorsAvailable chanelInvert){
        imageTemp=super.cloneBufferedImage(image);
        Color auxColor;
        for( int i = 0; i < imageTemp.getWidth(); i++ ){
            for( int j = 0; j < imageTemp.getHeight(); j++ ){
                auxColor=new Color(imageTemp.getRGB(i, j));
                this.colorSRGB=this.selectInvertColors(chanelInvert, auxColor);
                imageTemp.setRGB(i, j,this.colorSRGB);
            }
        }
        super.updateImage("Image Transformation: invert colors (" + chanelInvert + ")", imageTemp);
        return imageTemp;
    }
    private int selectInvertColors(invertColorsAvailable chanelInvert, Color color){
        int colorReturn=0;
        switch (chanelInvert){
            case RGB:
                colorReturn=super.colorRGBtoSRGB(new Color(255-color.getRed(), 
                        255-color.getGreen(), 255-color.getBlue(),color.getAlpha()));
                break;
            case red:
                colorReturn=super.colorRGBtoSRGB(new Color(255-color.getRed(), 
                        color.getGreen(),color.getBlue(),color.getAlpha()));
                break;
            case green:
                colorReturn=super.colorRGBtoSRGB(new Color(color.getRed(), 
                        255-color.getGreen(),color.getBlue(),color.getAlpha()));
                break;
            case blue:
                colorReturn=super.colorRGBtoSRGB(new Color(color.getRed(), 
                        color.getGreen(), 255-color.getBlue(),color.getAlpha()));
               break;
        }
        return colorReturn;
    }
    
    /**
     * Converts an image to grayscale 
     * @param image BufferedImage is going to be transformed
     * @return returns a grayscale image
     */
    public BufferedImage grayScale(BufferedImage image){
        imageTemp=super.cloneBufferedImage(image);
        int averageColor;
        Color auxColor;
        for( int i = 0; i < imageTemp.getWidth(); i++ ){
            for( int j = 0; j < imageTemp.getHeight(); j++ ){
                auxColor=new Color(imageTemp.getRGB(i, j));
                averageColor=this.calculateAverage(auxColor);
                this.colorSRGB=super.colorRGBtoSRGB(new Color(averageColor, averageColor, averageColor,auxColor.getAlpha()));
                imageTemp.setRGB(i, j,colorSRGB);
            }
        }
        super.updateImage("Image Transformation: grayscale", imageTemp);
        return imageTemp;
    }
    
    /**
     * Converts an image to black and white 
     * @param image BufferedImage is going to be transformed
     * @param threshold Threshold to assign white or black
     * @return returns a black and white image
     */
    public BufferedImage blackAndWhite(BufferedImage image,int threshold){
        //TASK, se podría hacer que se seleccionase el color de salida
        imageTemp=super.cloneBufferedImage(image);
        Color auxColor;
        for( int i = 0; i < imageTemp.getWidth(); i++ ){
            for( int j = 0; j < imageTemp.getHeight(); j++ ){
                auxColor=new Color(imageTemp.getRGB(i, j));
                imageTemp.setRGB(i, j,this.checkthreshold(auxColor,threshold));
            }
        }
        super.updateImage("Image Transformation: black and white. Threshold: " + threshold, imageTemp);
        return imageTemp;
    }

    
    /**
     * Inverts the colors of image. (255 - currentColor)
     * @param image BufferedImage is going to be transformed
     * @return returns the inverted image
     * @see JavaImages.ImageProcessing_BasicFilters.invertColorsAvailable
     */
    public BufferedImage invertColorsRGB(BufferedImage image,invertColorsAvailable chanelInvert){
        return this.invertColors(image, chanelInvert);
    }
    
   
    
    /**
     * Red/Green/Blue filter is applied to the image. Eg, if you select red filter, it calculate the average of each pixel, 
     * and assign this average to the red channel. The value of the remaining two channels is 0
     * @param image BufferedImage is going to be transformed
     * @return returns the image with red filter
     * @see JavaImages.ImageProcessing_BasicFilters.filtersAvailable
     */
    public BufferedImage basicFilters(BufferedImage image,filtersAvailable colorFilter){
        return this.selectFilter(image, colorFilter);
    }
    
    /**
     * Changes the order of the RGB channels. eg, RGB becomes GBR
     * @param image BufferedImage is going to be transformed
     * @return returns GBR/GRB/RBG/BGR/BRG image
     * @see JavaImages.ImageProcessing_BasicFilters.RGBTransformAvailable
     */
    public BufferedImage RGBto(BufferedImage image,RGBTransformAvailable chanel){
        return this.RGB_to(image, chanel);
    }
        
   
    
}
