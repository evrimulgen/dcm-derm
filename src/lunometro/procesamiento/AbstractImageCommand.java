package lunometro.procesamiento;

import javax.media.jai.PlanarImage;

/**
 * Clase abstracta que define el comportamiento com�n para implementar comandos
 * sobre una imagen
 *
 */
public abstract class AbstractImageCommand implements ImageComand {
	/**
	 * Imagen a procesar
	 */
	private PlanarImage image;
	
	/**
	 * Constructor
	 * @param image Imagen a procesar
	 */
	public AbstractImageCommand(PlanarImage image) {
		this.image = image;
	}

	/*
	 * (non-Javadoc)
	 * @see procesamiento.ImageComand#undo()
	 */
	public PlanarImage undo() {
		return getImage();
	}

	public PlanarImage getImage() {
		return image;
	}

	public void setImage(PlanarImage image) {
		this.image = image;
	}

	@Override
	public void postExecute() {
		//this.image = null;		
	}

	/*
	 * (non-Javadoc)
	 * @see procesamiento.ImageComand#getInfo()
	 */
	public String getInfo() {
		return "";
	}

}
