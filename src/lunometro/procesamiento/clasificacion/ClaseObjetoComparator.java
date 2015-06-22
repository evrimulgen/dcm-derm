package lunometro.procesamiento.clasificacion;

import java.util.Comparator;
import java.util.List;
import lunometro.objeto.ClaseObjeto;
import lunometro.objeto.RasgoClase;

/**
 * Comparador de clases de un objeto seg�n la distancia promedio
 *
 */
public class ClaseObjetoComparator implements Comparator<ClaseObjeto> {

	public int compare(ClaseObjeto clase1, ClaseObjeto clase2) {
		List<RasgoClase> rasgos1 = clase1.getClase().getRasgosDeterminantes();
		List<RasgoClase> rasgos2 = clase2.getClase().getRasgosDeterminantes();
		if (rasgos1.size() > rasgos2.size())
			return -1;
		if (rasgos2.size() > rasgos1.size())
			return 1;
		if (clase1.getDistanciaPromedio() != null && clase2.getDistanciaPromedio() != null){
			int compare = clase1.getDistanciaPromedio().compareTo(clase2.getDistanciaPromedio());
			if (compare != 0)
				return compare;
			else
				return clase1.getClase().getOrdenEvaluacion().compareTo(clase2.getClase().getOrdenEvaluacion());
				
		}
		if (clase1.getDistanciaPromedio() != null)
			return -1;
		if (clase2.getDistanciaPromedio() != null)
			return 1;
		return clase1.getClase().getOrdenEvaluacion().compareTo(clase2.getClase().getOrdenEvaluacion());
	}

}
