package procesamiento;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.ParameterBlock;
import java.util.HashMap;
import java.util.List;

import javax.media.jai.ImageLayout;
import javax.media.jai.Interpolation;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.media.jai.TiledImage;

import objeto.Objeto;
import objeto.Pixel;

import com.sun.media.jai.codec.TIFFEncodeParam;

public class ImageUtil {
	public static final int tileWidth = 256;
	public static final int tileHeight = 256;

	public static PlanarImage sumImage(PlanarImage image1, PlanarImage image2) {
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(image1);
		pb.addSource(image2);
		return JAI.create("add", pb);
	}
	
	
	/**
	 * Binariza la imagen pintando los objetos con el color pasado como par�metro
	 * @param image Imagen a binarizar
	 * @param fondo Color del fondo
	 * @param nuevoColor Nuevo color de los objetos
	 * @return
	 */
	public static PlanarImage binarize(PlanarImage image, Color fondo, Color nuevoColor) {
		TiledImage ti = ImageUtil.createTiledImage(image, tileWidth, tileHeight);
		int width = image.getWidth();
		int height = image.getHeight();

		int [] newPixel = {nuevoColor.getRed(), nuevoColor.getGreen(), nuevoColor.getBlue()};
		for (int h = 0; h < height; h++)
			for (int w = 0; w < width; w++) {
				int[] pixel = ImageUtil.readPixel(w, h, ti);
				int r = pixel[0];
				int g = pixel[1];
				int b = pixel[2];

				Color color = new Color(r, g, b);
				if (!color.equals(fondo)) {
					ImageUtil.writePixel(w, h, newPixel, ti);
				}
			}
		return ti;
	}

	/**
	 * Calcula el momento de inercia u(k,l) del contorno de un objeto
	 * 
	 * @param k
	 * @param l
	 * @return
	 */
	public static double momento(int k, int l, Objeto objeto) {
		if (objeto != null && objeto.getContorno() != null) {
			List<Pixel> contorno = objeto.getContorno();
			// double x_centro = objeto.getPixelMedio().getXDouble();
			// double y_centro = objeto.getPixelMedio().getYDouble();
			double suma = 0;
			for (Pixel pixel : contorno) {
				suma += Math.pow(pixel.getXDouble(), k)
						* Math.pow(pixel.getYDouble(), l);
			}
			return suma;
		}
		return 1;
	}

	/**
	 * Reformatea una imagen creando tiles de una dimension dada
	 * 
	 * @param img
	 *            Imagen a reformatear
	 * @param tileDim
	 *            Dimensiones de los tiles ej: 256 x 256
	 * @return
	 */
	public static RenderedOp reformatImage(PlanarImage img, Dimension tileDim) {
		int tileWidth = tileDim.width;
		int tileHeight = tileDim.height;
		ImageLayout tileLayout = new ImageLayout(img);
		tileLayout.setTileWidth(tileWidth);
		tileLayout.setTileHeight(tileHeight);

		HashMap map = new HashMap();
		map.put(JAI.KEY_IMAGE_LAYOUT, tileLayout);
		map.put(JAI.KEY_INTERPOLATION, Interpolation.getInstance(Interpolation.INTERP_BICUBIC));
		RenderingHints tileHints = new RenderingHints(map);

		ParameterBlock pb = new ParameterBlock();
		pb.addSource(img);
		return JAI.create("format", pb, tileHints);
	}

	/**
	 * Crea un TiledImage de un PlanarImage
	 * 
	 * @param image
	 *            PlanarImage
	 * @return TiledImage
	 */
	public static TiledImage createDisplayImage(PlanarImage image) {
		SampleModel sampleModel = image.getSampleModel();
		ColorModel colorModel = image.getColorModel();

		TiledImage ti = new TiledImage(image.getMinX(), image.getMinY(), image
				.getWidth(), image.getHeight(), image.getTileGridXOffset(),
				image.getTileGridYOffset(), sampleModel, colorModel);
		ti.setData(image.copyData());
		return ti;
	}

	/**
	 * Setea el valor de un pixel de un TiledImage
	 * @param x
	 * @param y
	 * @param pixelValue
	 * @param image
	 */
	public static void writePixel(int x, int y, int[] pixelValue, TiledImage image) {
		if (x >= image.getMinX() &&
			x <= image.getMaxX() &&
			y >= image.getMinY() &&
			y <= image.getMaxY()){
			int xIndex = image.XToTileX(x);
			int yIndex = image.YToTileY(y);
			
			WritableRaster tileRaster = image.getWritableTile(xIndex, yIndex);
			if (tileRaster != null)
				//System.out.println("pixel: " + x +" , " + y);
				tileRaster.setPixel(x, y, pixelValue);
			//image.releaseWritableTile(xIndex, yIndex);
		}
	}
	
	/**
	 * Setea el valor de un pixel de un TiledImage
	 * @param p Pixel
	 * @param image Imagen
	 */
	public static void writePixel(Pixel p, TiledImage image) {
		if (p.getCol() != null){
			int[] newPixel = { p.getCol().getRed(), p.getCol().getGreen(), p.getCol().getBlue()};
			writePixel(p.getX(), p.getY(), newPixel, image);
		}
	}
		
	/**
	 * Setea el valor de un pixel de un TiledImage
	 * @param x
	 * @param y
	 * @param pixelValue
	 * @param image
	 */
	public static void writePixel(int x, int y, int[] pixelValue, TiledImage image, WritableRaster rasterActual, int tileXActual, int tileYActual) {
		int xIndex = image.XToTileX(x);
		int yIndex = image.YToTileY(y);
		WritableRaster tileRaster = null;
		if (xIndex == tileXActual && yIndex == tileYActual)
			tileRaster = rasterActual; 
		else
			tileRaster = image.getWritableTile(xIndex, yIndex);
		
		if (tileRaster != null)
			//System.out.println("pixel: " + x +" , " + y);
			tileRaster.setPixel(x, y, pixelValue);
		image.releaseWritableTile(xIndex, yIndex);
	}
	
	/**
	 * Lee el valor de un pixel de un TiledImage
	 * @param x
	 * @param y
	 * @param image
	 */
	public static int[] readPixel(int x, int y, PlanarImage image) {
		int[] pixelValue = null;
		int xIndex = image.XToTileX(x);
		int yIndex = image.YToTileY(y);
		Raster tileRaster = image.getTile(xIndex, yIndex);
		if (tileRaster != null)
			pixelValue = tileRaster.getPixel(x, y, pixelValue);
		//image.releaseWritableTile(xIndex, yIndex);
		return pixelValue;
	}
	
	/**
	 * Lee el valor de un pixel de un TiledImage
	 * @param x
	 * @param y
	 * @param image
	 */
	public static int[] readPixel(int x, int y, TiledImage image, Raster rasterActual, int tileXActual, int tileYActual) {
		int[] pixelValue = null;
		int xIndex = image.XToTileX(x);
		int yIndex = image.YToTileY(y);
		JAI instance = JAI.getDefaultInstance();
		
		
		
		Raster tileRaster = null;
		if (xIndex == tileXActual && yIndex == tileYActual)
			tileRaster = rasterActual; 
		else{
			tileRaster = instance.getTileCache().getTile(image, xIndex, yIndex);  //image.getTile(xIndex, yIndex);
			if (tileRaster == null){
				tileRaster = image.getTile(xIndex, yIndex);
				instance.getTileCache().add(image, xIndex, yIndex, tileRaster);
			}
		}
			
		
		if (tileRaster != null)
			pixelValue = tileRaster.getPixel(x, y, pixelValue);
		//image.releaseWritableTile(xIndex, yIndex);
		return pixelValue;
	}
	
	/**
	 * Crea un TiledImage de un PlanarImage
	 * 
	 * @param image
	 *            PlanarImage
	 * @return TiledImage
	 */
	public static TiledImage createTiledImage(PlanarImage image, int tyleWidth, int tyleHeight) {
		TiledImage ti = new TiledImage(image,tyleWidth,tyleHeight);
		return ti;
	}
	
	
	public static Color getColorPunto(Pixel pixel, PlanarImage ti) {
		if (pixel.getX() >= ti.getMinX() &&
			pixel.getX() <= ti.getMaxX() &&
			pixel.getY() >= ti.getMinY() &&
			pixel.getY() <= ti.getMaxY()){
			int[] pix = ImageUtil.readPixel(pixel.getX(), pixel.getY(), ti);
			if (pix != null){
				int r = pix[0];
				int g = pix[0];
				int b = pix[0];
				if (pix.length == 3) {
					g = pix[1];
					b = pix[2];
				}
				return new Color(r, g, b);
			}
		}
		return null;
	}
	
	/**
	 * Inicializa la una imagen con un color de fondo
	 * @param image
	 * @param fondo
	 */
	public static void inicializarImagen(TiledImage image, Color fondo){
		int[] color = {fondo.getRed(), fondo.getGreen(), fondo.getBlue()};
		for(int i = 0; i < image.getWidth(); i++ )
			for(int j = 0; j < image.getHeight(); j++ ){
				writePixel(i, j, color, image);
			}
	}
	
	/**
	 * Guarda imagen en formato tiff en el disco
	 * @param image
	 * @param fileName
	 */
	public static void saveTIFFImage(PlanarImage image, String fileName){
		TIFFEncodeParam param = new TIFFEncodeParam();
		param.setTileSize(ImageUtil.tileWidth, ImageUtil.tileHeight);
		param.setWriteTiled(true);
		
		ParameterBlock pb = new ParameterBlock();
        pb.addSource(image);
        pb.add(fileName);
        pb.add("tiff");
        pb.add(param);
        RenderedOp r = JAI.create("filestore",pb);
        r.dispose();
	}
}
