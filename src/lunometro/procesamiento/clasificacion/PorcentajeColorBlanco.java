package lunometro.procesamiento.clasificacion;

import java.awt.Color;
import lunometro.objeto.Objeto;
import lunometro.objeto.RasgoClase;
import lunometro.objeto.RasgoObjeto;


public class PorcentajeColorBlanco extends EvaluadorRasgo{

	public PorcentajeColorBlanco(RasgoClase rasgo, Double minimo, Double maximo) {
		super(rasgo, minimo, maximo);
	}

	/**
	 * 
	 */
	public RasgoObjeto calcularValor(Objeto objeto) {
		Color colorPromedio = objeto.colorPromedio();
		Double colorAmarrillo = (double)( colorPromedio.getGreen() + colorPromedio.getRed() + colorPromedio.getBlue())/3; 
		return new RasgoObjeto(this.getRasgoClase().getRasgo(),colorAmarrillo);
	}

}
