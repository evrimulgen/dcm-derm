/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.process;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author mariano
 */
public class GrayScaleConverter {

    BufferedImage rgbRed = null;
    BufferedImage rgbGreen = null;
    BufferedImage rgbBlue = null;
    BufferedImage original = null;
    BufferedImage rgbMaxDecomp = null;
    
    public GrayScaleConverter(BufferedImage original) {
        this.original = original;
    }
    
    public void execute() {
        if (original == null) {
            return;
        }
        int alpha;
        int newPixel;
        int[] pixel = new int[3];
        rgbRed = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        rgbGreen = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        rgbBlue = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        rgbMaxDecomp = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        for(int i=0; i<original.getWidth(); i++) {
            for(int j=0; j<original.getHeight(); j++) {
                // Get pixels by R, G, B
                alpha = new Color(original.getRGB(i, j)).getAlpha();
                pixel[0] = new Color(original.getRGB(i, j)).getRed(); //red
                pixel[1] = new Color(original.getRGB(i, j)).getGreen(); //green
                pixel[2] = new Color(original.getRGB(i, j)).getBlue(); //blue

                //red channel
                int newval = pixel[0];
                newPixel = colorToRGB(alpha, newval, newval, newval);
                rgbRed.setRGB(i, j, newPixel);
                
                //green channel
                newval = pixel[1];
                newPixel = colorToRGB(alpha, newval, newval, newval);
                rgbGreen.setRGB(i, j, newPixel);
                
                //blue channel
                newval = pixel[2];
                newPixel = colorToRGB(alpha, newval, newval, newval);
                rgbBlue.setRGB(i, j, newPixel);
                
                //maximum decomposition
                newval = findMax(pixel);
                newPixel = colorToRGB(alpha, newval, newval, newval);
                rgbMaxDecomp.setRGB(i, j, newPixel);
             }
        }
    }
    
    public BufferedImage getRedChannel() {
        return rgbRed;
    }
    
    public BufferedImage getGreenChannel() {
        return rgbGreen;
    }
    
    public BufferedImage getBlueChannel() {
        return rgbBlue;
    }
    
    public BufferedImage getMaxDecomp() {
        return rgbMaxDecomp;
    }
    
    // Convert R, G, B, Alpha to standard 8 bit
    private int colorToRGB(int alpha, int red, int green, int blue) {
 
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;
 
        return newPixel;
 
    }
    
    private static int findMax(int[] pixel) {
 
        int max = pixel[0];
 
        for(int i=0; i<pixel.length; i++) {
            if(pixel[i] > max)
                    max = pixel[i];
        }
 
        return max;
 
    }

        
}
