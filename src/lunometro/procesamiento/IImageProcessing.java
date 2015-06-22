package lunometro.procesamiento;

import java.awt.Window;
import java.awt.image.BufferedImage;

import javax.media.jai.PlanarImage;

/**
 * Interfaz que define m�todos para administrar comandos ejecutados sobre una imagen
 *
 */
public interface IImageProcessing {
	/**
	 * Obtiene la imagen original sin procesar
	 * @return
	 */
	public PlanarImage getOriginalImage();
	
	/**
	 * Obtiene la imagen actual
	 * @return
	 */
	public PlanarImage getImage();

	/**
	 * Setea la imagen actual
	 * @param image
	 */
	public void setImage(PlanarImage image);
	
	/**
	 * Agrega un comando ejecutado sobre la imagen
	 * @param comand Comando
	 * @param info Informaci�n del comando
	 * @param executeTime Tiempo de ejecuci�n
	 */
	public void addExecutedCommand(ImageComand comand, String info, long executeTime);
	
	/**
	 * Retorna el area de la imagen contenida en el rectangulo seleccionado
	 * @return
	 */
	public BufferedImage getSelectedRectangle();
	
	/**
	 * Retorna la ventana principal de la aplicacion
	 * @return
	 */
	public Window getMainWindow();

}
