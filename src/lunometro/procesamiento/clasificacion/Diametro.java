
package lunometro.procesamiento.clasificacion;

import lunometro.objeto.Objeto;
import lunometro.objeto.RasgoClase;
import lunometro.objeto.RasgoObjeto;

/**
 * Evalua el alto de un objeto 
 */
public class Diametro extends EvaluadorRasgo {
	public Diametro() {
		super();
	}

	public Diametro(RasgoClase rasgo, Double minimo, Double maximo) {
		super(rasgo, minimo, maximo);
	}

	public RasgoObjeto calcularValor(Objeto objeto) {
		try {
			Double diametro = objeto.getDiametro() * ObjetoReferencia.getRelacionPixelMM();
			return new RasgoObjeto(this.getRasgoClase().getRasgo(),diametro);
		}
		catch(Exception e){
			return new RasgoObjeto(this.getRasgoClase().getRasgo(),0.0);
		}
	}
}
