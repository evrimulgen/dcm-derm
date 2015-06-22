package lunometro.procesamiento;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.media.jai.PlanarImage;
import javax.media.jai.TiledImage;

/**
 * Comando que realiza la operaci�n de Binarizar usando el algoritmo Otsu.
 *
 */
public class OtsuBinarize extends AbstractImageCommand {
	
	protected String color;
	private static String RED   = "RED";
	private static String GREEN = "GREEN";
	
	public OtsuBinarize(PlanarImage image, String c) {
		super(image);
		color = c;
	}
	
	// Return histogram of grayscale image
    public int[] imageHistogram(BufferedImage input) {
        int[] histogram = new int[256];
        for(int i=0; i<histogram.length; i++) histogram[i] = 0;
 
        for(int i=0; i<input.getWidth(); i++) {
            for(int j=0; j<input.getHeight(); j++) {
            	int color = getColor(input.getRGB(i, j));
                histogram[color]++;
            }
        }
        return histogram;
    }
    
    /**
     * 
     * @param rgb
     * @return
     */
    private int getColor(int rgb) {
    	if(RED.equals(color)){
    		return new Color(rgb).getRed();
    	}else if(GREEN.equals(color)){
    		return new Color(rgb).getGreen();
    	}else{
    		return new Color(rgb).getBlue();
    	}
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
    private static int colorToRGB(int alpha, int red, int green, int blue) {
 
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red; newPixel = newPixel << 8;
        newPixel += green; newPixel = newPixel << 8;
        newPixel += blue;
 
        return newPixel;
 
    }
    
	public PlanarImage execute() {
		if (getImage() != null) { 
			TiledImage tiledImage = ImageUtil.createTiledImage(getImage(), ImageUtil.tileWidth, ImageUtil.tileHeight);
			int tWidth = ImageUtil.tileWidth;
			int tHeight =  ImageUtil.tileHeight;
			
			int maxX = 0;
			int maxY = 0;
			int threshold = otsuTreshold(getImage().getAsBufferedImage());
			int newPixel;
			
			// We must process all tiles.
			for (int th = tiledImage.getMinTileY(); th <= tiledImage.getMaxTileY(); th++) 
				for (int tw = tiledImage.getMinTileX(); tw <= tiledImage.getMaxTileX(); tw++){
					// Get a raster for that tile.
					WritableRaster wr = tiledImage.getWritableTile(tw, th);

					for (int w = 0; w < tWidth; w++)
						for (int h = 0; h < tHeight; h++) {
							int x = tw * tWidth + w;
							int y = th * tHeight + h;
							if (x <= getImage().getMaxX() && y <= getImage().getMaxY()) {
								maxX = Math.max(maxX, x);
								maxY = Math.max(maxY, y);

								try {
									int[] pixel = null;
									
									pixel = wr.getPixel(x, y, pixel);
									int r = pixel[0];
									int g = pixel[0];
									int b = pixel[0];

									if (pixel.length == 3) {
										g = pixel[1];
										b = pixel[2];
									}
									
					                // Get pixels
									int color;
									if(RED.equals(this.color)){
										color = new Color(r,g,b).getRed();
									}else if(GREEN.equals(this.color)){
										color = new Color(r,g,b).getGreen();
									}else{
										color = new Color(r,g,b).getBlue();
									}
					                int alpha = new Color(r,g,b).getAlpha();
					                if(color > threshold) {
					                	newPixel = 255;
					                }
					                else {
					                	newPixel = 0;
					                }
					                
					                newPixel = colorToRGB(alpha, newPixel, newPixel, newPixel);
					                int grayScaleColor[] = {newPixel, newPixel, newPixel};
				                	wr.setPixel(x, y, grayScaleColor);					                
					                
									
								} catch (Exception e) {
									System.out.println("x: "+x + ", y: "+ y);
									e.printStackTrace();
									return null;
								}
							}
						}
					tiledImage.releaseWritableTile(tw, th);
				}
			return tiledImage;
		}
		
		return null;
	}

	public String getCommandName() {
		return "OtsuBinarize";
	}

	public void postExecute() {
				
	}

}
