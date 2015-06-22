/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.process;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author mariano
 */
public class RegionExtraction {
    
    BufferedImage binary = null;
    BufferedImage original = null;

    
    public RegionExtraction(BufferedImage original, BufferedImage binary) {
        this.original = original;
        this.binary = binary;
    }
    
    public BufferedImage execute(Point centroid) {
       if (original != null) {
           
           BufferedImage copy = new BufferedImage(original.getWidth(),original.getHeight(),BufferedImage.TYPE_INT_RGB);
           Graphics g = copy.createGraphics();
           g.drawImage(original, 0, 0, null);
           g.dispose();
           
           for (int i = 0; i < binary.getWidth(); i++) {
               for (int j = 0; j < binary.getHeight(); j++) {
                   if (binary.getRGB(i, j) == Color.WHITE.getRGB()) {
                       copy.setRGB(i, j, Color.WHITE.getRGB());
                   }
                   if (i == centroid.x && j == centroid.y) {
                       copy.setRGB(i, j, Color.GREEN.getRGB());
                   }
               }
           }
           
           return copy;
       }
       return null;
    }
}
