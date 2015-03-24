/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JavaImages;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageProcessing_Histogram extends ImageProcessing {
    
    /**
     * Enumeration with all available channels
     */
    protected enum chanelsAvailable{red,green,blue,alpha,grayscale}
    private int[] maxValue=new int[2];
    private int[] minValue=new int[2];
    
     /**
     * Requires first call the function to get the histogram 
     * @return an int[] where the position 0 indicates the maximum pixel and the 
     * position 1 indicates the number of pixels
     */
    public int[] getMaxValue() {
        return maxValue;
    }

     /**
     * Requires first call the function to get the histogram 
     * @return an int[] where the position 0 indicates the minimun pixel and the 
     * position 1 indicates the number of pixels
     */
    public int[] getMinValue() {
        return minValue;
    }
   
    private int[] copyHistogram(int[][] histogram,int row){
        int[] histReturn= new int[256];
        System.arraycopy(histogram[row], 0, histReturn, 0, histogram[row].length);
        return histReturn;
    }
    private int calculateAverage(Color color){
        int averageColor;
        averageColor=(int)((color.getRed()+color.getGreen()+color.getBlue())/3);
        return averageColor;
    }
    protected void calculateMaxMinValue(int[] histogramArray){
        int maxiValue=0,miniValue=0;
        int maxiPixel=0,miniPixel=0;
        for (int i = 0; i < histogramArray.length; i++) {
            if(histogramArray[i]>maxiValue){
                maxiValue=histogramArray[i];
                maxiPixel=i;
            }
            if(histogramArray[i]<maxiValue){
                miniValue=histogramArray[i];
                miniPixel=i;
            }
        }
        this.maxValue[0]=maxiPixel;
        this.maxValue[1]=maxiValue;
        this.minValue[0]=miniPixel;
        this.minValue[1]=miniValue;
    }
    
    protected int[][] histogram(BufferedImage image,chanelsAvailable chanel){
        Color auxColor;
        int histogramReturn[][]=new int[5][256];
        for( int i = 0; i < image.getWidth(); i++ ){
            for( int j = 0; j < image.getHeight(); j++ ){
                auxColor=new Color(image.getRGB(i, j));
                histogramReturn[0][auxColor.getRed()]+=1;
                histogramReturn[1][auxColor.getGreen()]+=1;
                histogramReturn[2][auxColor.getBlue()]+=1;
                histogramReturn[3][auxColor.getAlpha()]+=1;
                histogramReturn[4][calculateAverage(auxColor)]+=1;
            }
        }
        super.updateActivityLog("Image information: " + chanel.toString() + " histogram");
        return histogramReturn;
    }
    
    /**
     * Calculate the red frequency histogram from a BufferedImage
     * @param image BufferedImage you want to get the histogram
     * @return Returns a int[256] with the red frequency histogram
     */
    public int[] histogramRed(BufferedImage image){
        int[] histRed;
        histRed=copyHistogram(this.histogram(image, chanelsAvailable.red),0);
        calculateMaxMinValue(histRed);
        return histRed;
    }
    
    /**
     * Calculate the green frequency histogram from a BufferedImage
     * @param image BufferedImage you want to get the histogram
     * @return Returns a int[256] with the green frequency histogram
     */
    public int[] histogramGreen(BufferedImage image){
        int[] histGreen;
        histGreen=copyHistogram(this.histogram(image,chanelsAvailable.green),1);
        calculateMaxMinValue(histGreen);
        return histGreen;
    }
    
    /**
     * Calculate the blue frequency histogram from a BufferedImage
     * @param image BufferedImage you want to get the histogram
     * @return Returns a int[256] with the blue frequency histogram
     */
    public int[] histogramBlue(BufferedImage image){
        int[] histBlue;
        histBlue=copyHistogram(this.histogram(image,chanelsAvailable.blue),2);
        calculateMaxMinValue(histBlue);
        return histBlue;
    }
    
    /**
     * Calculate the alpha frequency histogram from a BufferedImage
     * @param image BufferedImage you want to get the histogram
     * @return Returns a int[256] with the alpha frequency histogram
     */
    public int[] histogramAlpha(BufferedImage image){
        int[] histAlpha;
        histAlpha=copyHistogram(this.histogram(image,chanelsAvailable.alpha),3);
        calculateMaxMinValue(histAlpha);
        return histAlpha;
    }
    
    /**
     * Calculate the grayscale frequency histogram from a BufferedImage
     * @param image BufferedImage you want to get the histogram
     * @return Returns a int[256] with the grayscale frequency histogram
     */
    public int[] histogramGrayscale(BufferedImage image){
        int[] histGrayscale;
        histGrayscale=copyHistogram(this.histogram(image,chanelsAvailable.grayscale),4);
        calculateMaxMinValue(histGrayscale);
        return histGrayscale;
    }

    
}
