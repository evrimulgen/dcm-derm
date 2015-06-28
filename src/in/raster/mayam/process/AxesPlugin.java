/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.process;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import static ij.plugin.filter.PlugInFilter.DOES_RGB;
import static ij.plugin.filter.PlugInFilter.DOES_STACKS;
import static ij.plugin.filter.PlugInFilter.NO_UNDO;
import ij.process.ImageProcessor;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author mariano
 */
public class AxesPlugin implements PlugInFilter {

    ImageProcessor ip = null;
    double major,minor,angle,x,y;
    
    @Override
    public int setup(String args, ImagePlus ip) {
        String params[] = args.split(",");
        x = Double.valueOf(params[0]).doubleValue();
        y = Double.valueOf(params[1]).doubleValue();
        major = Double.valueOf(params[2]).doubleValue();
        minor = Double.valueOf(params[3]).doubleValue();
        angle = Double.valueOf(params[4]).doubleValue();
        return DOES_RGB+NO_UNDO+DOES_STACKS;
    }

    @Override
    public void run(ImageProcessor ip) {
      this.ip = ip;
      angle = angle*Math.PI/180;
      ip.setColor(Color.GREEN);
      ip.drawLine(Double.valueOf(x+(major/2)*Math.cos(angle)).intValue(),
              Double.valueOf(y-(major/2)*Math.sin(angle)).intValue(),
              Double.valueOf(x-(major/2)*Math.cos(angle)).intValue(),
              Double.valueOf(y+(major/2)*Math.sin(angle)).intValue());
      angle=angle+Math.PI/2;
      ip.drawLine(Double.valueOf(x+(minor/2)*Math.cos(angle)).intValue(),
              Double.valueOf(y-(minor/2)*Math.sin(angle)).intValue(),
              Double.valueOf(x-(minor/2)*Math.cos(angle)).intValue(),
              Double.valueOf(y+(minor/2)*Math.sin(angle)).intValue());
    }
    
    public BufferedImage getBufferedImage() {
        return ip.getBufferedImage();
    }
    
}
