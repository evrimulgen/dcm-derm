package lunometro.procesamiento;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.media.jai.PlanarImage;
import javax.media.jai.TiledImage;

import lunometro.objeto.Pixel;

/**
 * Comando que realiza la operaci�n rellenar agujero de la region de interes.
 *
 */
public class FillHole extends AbstractImageCommand {
	private static int blanco[] = {255,255,255};
	private static int negro[] = {0,0,0};
	
	private int umbral;
	
	public FillHole(PlanarImage image, int umbral) {
		super(image);
		this.umbral = umbral;
	}

	/**
	 * Retorna si un pixel es fondo de la imagen
	 * 
	 * @param pixel
	 * @return
	 */
	private boolean isFondo(Pixel pixel) {
		return pixel.getCol().getRed() < this.umbral;
	}
	
	/**
	 * Retorna el pixel adyacente a uno dado en una direcci�n determinada
	 * 
	 * @param pixel
	 *            Pixel actual
	 * @param direccion
	 *            Direcci�n para recuperar el adyacente
	 * @return Pixel adyacente
	 */
	public Pixel getAdyacente(Pixel pixel, int direccion, PlanarImage image) {
		Pixel ady = pixel.getAdyacente(direccion, image.getWidth(), image.getHeight());
		if (ady != null) {
			return getPixel(ady.getX(), ady.getY(), image);
		}
		return null;
	}

	/**
	 * Retorna la cantidad de vecinos del pixel que no son fondo
	 * @param pixel
	 * @param image
	 * @return
	 */
	private int vecinosNoFondo(Pixel pixel, PlanarImage image){
		int cont = 0;
		for (int dir = 0; dir< 8; dir++){
			Pixel p = getAdyacente(pixel,dir , image);
			if (p != null && !isFondo(p))
				cont++;
		}
		return cont;
	}
	
	/**
	 * Recupera el pixel (x,y) de la imagen
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param nbands
	 * @param pixels
	 * @return
	 */
	public Pixel getPixel(int x, int y, PlanarImage image) {
		int[] pixel = ImageUtil.readPixel(x, y, (TiledImage) image);
		int r = pixel[0];
		int g = pixel[0];
		int b = pixel[0];

		if (pixel.length == 3){
			g = pixel[1];
			b = pixel[2];
		}
		Color colorPixel = new Color(r, g, b);
		return new Pixel(x, y, colorPixel,getImage().getMaxX(),getImage().getMaxY());
	}
	
	public PlanarImage execute() {
		if (getImage() != null) {
                    int width = getImage().getWidth();
                    int height = getImage().getHeight();	
                    BufferedImage bi = new BufferedImage(width, height, getImage().getAsBufferedImage().getType());
		    
                    for (int th = 0; th < width; th++){
			for (int tw = 0; tw < height; tw++) {
                            if (Color.WHITE.getRGB() == getImage().getAsBufferedImage().getRGB(th, tw)) {
                                bi.setRGB(th, tw, Color.WHITE.getRGB());
                            } else {
                                break;
                            }
                        }
                    }
                    for (int tw = 0; tw < height; tw++){
			for (int th = 0; th < width; th++) {
                            if (Color.WHITE.getRGB() == getImage().getAsBufferedImage().getRGB(th, tw)) {
                                bi.setRGB(th, tw, Color.WHITE.getRGB());
                            } else {
                                break;
                            }
                        }
                    }
                        
//                		Pixel pixel = getPixel(th, tw, tiledImage);
//					if (!isFondo(pixel)){
//						int vecinos = vecinosNoFondo(pixel, tiledImage);
//						if(vecinos < 3){
//							wr.setPixel(th, tw, negro);
//						}else{
//							wr.setPixel(th, tw, blanco);
//						}	
//						
//					}
//				}
//			}
					
//			tiledImage.releaseWritableTile(width, height);
//			return tiledImage;
                    return PlanarImage.wrapRenderedImage(bi);
		}
                return null;
	}

	public String getCommandName() {
		return "FillHole";
	}

	public void postExecute() {

	}

}
