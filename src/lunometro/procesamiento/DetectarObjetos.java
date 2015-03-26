package procesamiento;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.media.jai.PlanarImage;
import javax.media.jai.TiledImage;

import objeto.Objeto;
import objeto.Pixel;

/**
 * Comando que realiza la detección de los objetos. 
 *
 */
public class DetectarObjetos extends AbstractImageCommand { 
	
	private static PlanarImage originalImage;
	private HSVRange hsvRange;
	private List<Objeto> objetos = new ArrayList<Objeto>();
	

	/**
	 * Constructor
	 * @param image Imagen a procesar
	 * @param originalImage Imagen original sin ningún procesamiento
	 * @param hsvRange Rango de valores HSV para detectar el fondo de la imagen
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public DetectarObjetos(PlanarImage image, PlanarImage originalImage,
			HSVRange hsvRange) throws Exception {
		super(image);
		this.originalImage = originalImage;
		this.hsvRange = hsvRange;
		init();
	}
	
	/**
	 * Inicialización de la clase
	 * @throws Exception 
	 */
	protected void init() throws Exception{
	}

	/**
	 * Implementa el algoritmo de detección de los objetos.
	 * Reliza la siguiente secuencia de procesamiento:
	 * 	1- Elimina el fondo de la imagen
	 * 	2- Binariza la imagen
	 * 	3- Opening de la imagen
	 * 	4- Closing de la imagen
	 * 	5- Detecta el contorno el contorno grueso de los objetos
	 * 	6- Detecta el contorno exterior de 1 pixel de los objetos
	 */
	public PlanarImage execute() {
		if (getOriginalImage() != null && getHsvRange() != null) {
			Binarizar ef = new Binarizar(getOriginalImage(), getHsvRange());
			PlanarImage binaryImage = ef.execute();
			PlanarImage output = binaryImage;
			ef.postExecute();
			
			DetectarContorno dc = new DetectarContorno(output, getOriginalImage(), new Color(0, 0, 0), Color.RED);
			dc.setBinaryImage(binaryImage);
			dc.setRangeFondo(getHsvRange());
			output = dc.execute();

			List<Objeto> objetos = dc.getObjetos();			
			dc.postExecute();
			
			setObjetos(objetos);
			
			output = pintarContorno(getOriginalImage());
			
			return output; 
		}
		return null;
	}
	
	/**
	 * Pinta el pixel (x,y) de la imagen con el color pasado como parámetro
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @param color
	 */
	public void pintarPixel(TiledImage image, int x, int y, Color color) {
		int pixel[] = {color.getRed(), color.getGreen(), color.getBlue()};
		ImageUtil.writePixel(x, y, pixel, image);
	}

	
	/**
	 * Pinta el contorno de los objetos detectados
	 * @return
	 */
	public PlanarImage pintarContorno(PlanarImage image) {
		if (getOriginalImage() != null){
			TiledImage ti = ImageUtil.createTiledImage(image, ImageUtil.tileWidth, ImageUtil.tileHeight);
			Graphics2D g = ti.createGraphics();
			for (Objeto obj: objetos) {
				for (Pixel p : obj.getContorno()) {
					pintarPixel(ti, p.getX(), p.getY(), Color.WHITE);
				}

				Pixel medio = obj.getPixelMedio();
				g.setColor(Color.red);
				g.drawString(obj.getName(),(int) medio.getX()-10,(int)medio.getY()-10);
			}

			return ti;
		}
		return null;

	}
	

	/*
	 * (non-Javadoc)
	 * @see procesamiento.ImageComand#getCommandName()
	 */
	public String getCommandName() {
		return "Contorno";
	}

	public static PlanarImage getOriginalImage() {
		return originalImage;
	}

	@SuppressWarnings("static-access")
	public void setOriginalImage(PlanarImage originalImage) {
		this.originalImage = originalImage;
	}

	public HSVRange getHsvRange() {
		return hsvRange;
	}

	public void setHsvRange(HSVRange hsvRange) {
		this.hsvRange = hsvRange;
	}

	public List<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<Objeto> objetos) {
		this.objetos = objetos;
	}

	/*
	 * (non-Javadoc)
	 * @see procesamiento.ImageComand#postExecute()
	 */
	public void postExecute() {
		super.postExecute();
		originalImage = null;
		objetos = null;
	}

}
