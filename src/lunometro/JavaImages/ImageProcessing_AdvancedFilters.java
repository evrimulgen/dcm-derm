/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lunometro.JavaImages;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageProcessing_AdvancedFilters extends ImageProcessing{
    
    private BufferedImage imageTemp;
    private int colorSRGB;
    
    private int calculateAverage(Color color){
        int averageColor;
        averageColor=(int)((color.getRed()+color.getGreen()+color.getBlue())/3);
        return averageColor;
    }
    private int colorCheck(int color){
        color=(color>255)?255:color;
        color=(color<0)?0:color;
        return color;
    }
    
    private int smoothingCalculate(Color color, int threshold,int proportion){
        int averageColor=this.calculateAverage(color);
        int range,proportional,value;
        if (averageColor>=threshold){
            range=255-threshold;
            proportional=averageColor-threshold;
            value=(int)(255-((proportional*proportion)/range));
            this.colorSRGB=super.colorRGBtoSRGB(new Color(value, value,value, color.getAlpha()));
        }else{
            range=threshold;
            proportional=averageColor;
            value=(int)((proportional*proportion)/range);
            this.colorSRGB=super.colorRGBtoSRGB(new Color(value, value,value, color.getAlpha()));
        }
        return this.colorSRGB;
    }
    private double valueContrastAdjustment(double value){
         final double calculeValue;
         calculeValue=(1/((Math.PI)/4));
         value=(value+1)*(Math.PI/4);
         value*=calculeValue;
         return value;
     }
    
    private int contrastCalculateProportion(int color,double contrast){
        int colorValue;
        colorValue=(int)(((color-128)*contrast)+128);
        colorValue=colorCheck(colorValue);
        return colorValue;
    }
    private int contrastCalculate(Color color,double contrast){
        int red,green,blue;
        red=contrastCalculateProportion(color.getRed(),contrast);
        green=contrastCalculateProportion(color.getGreen(),contrast);
        blue=contrastCalculateProportion(color.getBlue(),contrast);
        this.colorSRGB=super.colorRGBtoSRGB(new Color(red, green,blue, color.getAlpha()));
        return this.colorSRGB;
    }
        
    /**
     * Converts an image to black and white smoothing. From the threshold, the scale divided
     * into two sections and calculates the output value via parameter Proportion. 
     * For example, when selecting Threshold = 128 and Proportion = 50, values ​​are reduced to 127 (255-Threshold) 50
     * @param image BufferedImage is going to be transformed
     * @param threshold Threshold to assign white or black
     * @param proportion Value that will reduce the scale
     * @return returns a black and white smoothing image
     */
    public BufferedImage blackAndWhiteSmoothing(BufferedImage image,int threshold,int proportion){
        imageTemp=super.cloneBufferedImage(image);
        if ((threshold>=proportion) && ((255-threshold)>=proportion)){
            Color auxColor;
            for(int i = 0; i < imageTemp.getWidth(); i++){
                for(int j = 0; j < imageTemp.getHeight(); j++){
                    auxColor=new Color(imageTemp.getRGB(i, j));
                    imageTemp.setRGB(i, j,this.smoothingCalculate(auxColor,threshold,proportion));
                }
            }
            super.updateImage("Image Transformation: black and white smoothing. Threshold: " + threshold + 
                    " Proportion: " + proportion, imageTemp);
        }else{
            super.updateActivityLog("Failed to perform transformation: the conditions are not met. Conditions: "
                    + "Threshold>=Proportion (range) && (255-Threshold)>=Proportion (range). Current values: "
                    + "Threshold=" + threshold + "  " + "Proportion (range)=" + proportion);
        }
        return imageTemp;
    }
  
    /**
     * Converts an image to contrast specified. 
     * @param image BufferedImage is going to be transformed
     * @param contrastValue Contrast value. Must be between -1 and 1 for optimum results.
     * @return returns an image with the modified contrast
     */
    public BufferedImage contrast(BufferedImage image, double contrastValue){
        imageTemp=super.cloneBufferedImage(image);
        double contrastValueTemp;
        contrastValueTemp=valueContrastAdjustment(contrastValue);
        Color auxColor;
        for(int i = 0; i < imageTemp.getWidth(); i++){
            for(int j = 0; j < imageTemp.getHeight(); j++){
                auxColor=new Color(imageTemp.getRGB(i, j));
                imageTemp.setRGB(i, j,this.contrastCalculate(auxColor,contrastValueTemp));
            }
        }
        super.updateImage("Image Transformation: modified contrast (" + contrastValue + ")", imageTemp);
        return imageTemp;
    }
}
