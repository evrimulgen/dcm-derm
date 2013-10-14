/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package in.raster.mayam.body;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author mariano
 */
public class FramePreview extends JPanel {

    private BufferedImage bi = null;
    
    FramePreview() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bi,0,0,getWidth(),getHeight(),null);
    }
    
    public void setImage(BufferedImage bi) {
        this.bi = bi;
        repaint();
    }

}
