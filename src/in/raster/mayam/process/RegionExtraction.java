/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.process;

import ij.ImagePlus;
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
    BufferedImage isolated = null;
    BufferedImage outlined = null;
            
    public RegionExtraction(BufferedImage original, BufferedImage binary) {
        this.original = original;
        this.binary = binary;
    }
    
    public BufferedImage execute() {
       if (original != null) {
                                
           isolated = new BufferedImage(original.getWidth(),original.getHeight(),BufferedImage.TYPE_INT_RGB);
           outlined = new BufferedImage(original.getWidth(),original.getHeight(),BufferedImage.TYPE_INT_RGB);
           
           Graphics g = outlined.createGraphics();
           g.drawImage(original, 0, 0, null);
           g.dispose();
           
           g = isolated.createGraphics();
           g.drawImage(original, 0, 0, null);
           g.dispose();
           
           for (int i = 0; i < binary.getWidth(); i++) {
               for (int j = 0; j < binary.getHeight(); j++) {
                   if (binary.getRGB(i, j) == Color.WHITE.getRGB()) {
                       if (i>0 && i<binary.getWidth()-1 && j>0 && j<binary.getHeight()-1) {
                           if (binary.getRGB(i+1, j) == Color.BLACK.getRGB() || binary.getRGB(i-1, j ) == Color.BLACK.getRGB()
                                   || binary.getRGB(i,j+1) == Color.BLACK.getRGB() || binary.getRGB(i, j-1) == Color.BLACK.getRGB()) {
                               outlined.setRGB(i, j, Color.GREEN.getRGB());
                           }
                       }
                       isolated.setRGB(i, j, Color.LIGHT_GRAY.getRGB());
                   }
               }
           }
       }
       return null;
    }
    
    public ImagePlus getIsolatedImage() {
        return new ImagePlus("",isolated);
    }
    
    public ImagePlus getOutlinedImage() {
        return new ImagePlus("",outlined);
    }
}
