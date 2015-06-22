package lunometro.procesamiento;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.media.jai.PlanarImage;
import javax.media.jai.TiledImage;
import lunometro.objeto.Objeto;
import lunometro.objeto.Pixel;


/**
 * Comando que realiza la detecci�n de los objetos. 
 *
 */
public class DetectarObjetos extends AbstractImageCommand { 
	
	private static PlanarImage originalImage;
//	private HSVRange hsvRange;
	private List<Objeto> objetos = new ArrayList<Objeto>();
	

	/**
	 * Constructor
	 * @param image Imagen a procesar
	 * @param originalImage Imagen original sin ning�n procesamiento
	 * @param hsvRange Rango de valores HSV para detectar el fondo de la imagen
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public DetectarObjetos(PlanarImage image, PlanarImage originalImage,
			HSVRange hsvRange) throws Exception {
		super(image);
		this.originalImage = originalImage;
//		this.hsvRange = hsvRange;
		init();
	}
	
	/**
	 * Inicializaci�n de la clase
	 * @throws Exception 
	 */
	protected void init() throws Exception{
	}

	/**
	 * Implementa el algoritmo de deteccion de los objetos.
	 * Realiza la siguiente secuencia de procesamiento:
	 * 	1- Convierte a escala de gris
	 * 	2- Binariza la imagen usando el otsuMethod
	 * 	3- Rellena huecos (fillHole)
	 * 	3- Opening de la imagen
	 * 	4- Closing de la imagen
	 * 	5- Detecta el contorno el contorno grueso de los objetos
	 * 	6- Detecta el contorno exterior de 1 pixel de los objetos
	 */
	public PlanarImage execute() {
		if (getOriginalImage() != null) {
			
//			GrayScale gs = new GrayScale(this.getImage());
//			OtsuBinarize obRed = new OtsuBinarize(gs.execute(), "RED");
//			
//			PlanarImage binaryImage = obRed.execute();
//			
//			FillHole fh = new FillHole(binaryImage, 255);
//			PlanarImage output = fh.execute(); 
			
			DetectarContorno dc = new DetectarContorno(getOriginalImage(), getOriginalImage(), new Color(0, 0, 0), Color.RED);
			dc.setBinaryImage(getOriginalImage());
//			dc.setRangeFondo(getHsvRange());
			PlanarImage output = dc.execute();

			List<Objeto> objetos = dc.getObjetos();			
			dc.postExecute();
			
			setObjetos(objetos);
			
			output = pintarContorno(getOriginalImage());
			
			return output; 
		}
		return null;
	}
	
	/**
	 * Pinta el pixel (x,y) de la imagen con el color pasado como par�metro
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

//	public HSVRange getHsvRange() {
//		return hsvRange;
//	}
//
//	public void setHsvRange(HSVRange hsvRange) {
//		this.hsvRange = hsvRange;
//	}

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
