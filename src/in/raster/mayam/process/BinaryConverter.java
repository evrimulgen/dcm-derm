/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.process;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.media.jai.PlanarImage;
import javax.media.jai.TiledImage;


/**
 *
 * @author mariano
 */
public class BinaryConverter {
    
    BufferedImage original = null;

    
    public BinaryConverter(BufferedImage original) {
        this.original = original;
    }
    
    public BufferedImage execute() {
       if (original != null) {
           int red;
           int newPixel;

           int threshold = otsuTreshold(original);

           BufferedImage binarized = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

           for (int i = 0; i < original.getWidth(); i++) {
               for (int j = 0; j < original.getHeight(); j++) {

                   // Get pixels
                   red = new Color(original.getRGB(i, j)).getRed();
                   int alpha = new Color(original.getRGB(i, j)).getAlpha();
                   if (red > threshold) {
                       newPixel = 255;
                   } else {
                       newPixel = 0;
                   }
                   newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
                   binarized.setRGB(i, j, newPixel);

               }
           }

           return binarized;
       }
       return null;
    }
    
    // Return histogram of grayscale image
    public int[] imageHistogram(BufferedImage input) {
        int[] histogram = new int[256];
        for(int i=0; i<histogram.length; i++) histogram[i] = 0;
 
        for(int i=0; i<input.getWidth(); i++) {
            for(int j=0; j<input.getHeight(); j++) {
                int red = new Color(input.getRGB(i, j)).getRed();
                histogram[red]++;
            }
        }
        return histogram;
    }
    
    // Get binary treshold using Otsu's method
    private int otsuTreshold(BufferedImage original) {
 
        int[] histogram = imageHistogram(original);
        int total = original.getHeight() * original.getWidth();
 
        float sum = 0;
        for(int i=0; i<256; i++) sum += i * histogram[i];
 
        float sumB = 0;
        int wB = 0;
        int wF = 0;
 
        float varMax = 0;
        int threshold = 0;
        
        for(int i=0 ; i<256 ; i++) {
            wB += histogram[i];
            if(wB == 0) continue;
            wF = total - wB;
 
            if(wF == 0) break;
 
            sumB += (float) (i * histogram[i]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;
 
            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
 
            if(varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }

        return threshold;
 
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
    
}
