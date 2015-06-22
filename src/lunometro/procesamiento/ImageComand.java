package lunometro.procesamiento;

import javax.media.jai.PlanarImage;
/**
 * Interfaz para ejecutar comandos sobre una imagen
 *
 */
public interface ImageComand {
	/**
	 * M�todo que ejecuta una operaci�n sobre la imagen
	 * @return Imagen resultante del procesamiento
	 */
	public PlanarImage execute();
	
	/**
	 * Deshace los cambios realizados sobre la imagen en el m�todo execute
	 * @return Imagen antes de realizar el procesamiento
	 */
	public PlanarImage undo();
	
	/**
	 * Retorna el nombre del comando ejecutado
	 * @return
	 */
	public String getCommandName();
	
	/**
	 * Operaciones a realizar despu�s de ejecutar el comando
	 */
	public void postExecute();
	
	/**
	 * Retorna un texto que da informaci�n el procesamiento realizado sobre la imagen
	 * @return
	 */
	public String getInfo();
	
}
