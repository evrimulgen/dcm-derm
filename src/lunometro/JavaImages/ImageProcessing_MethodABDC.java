package JavaImages;

import java.awt.Color;
import java.awt.image.BufferedImage;

import objeto.Objeto;
import objeto.Pixel;
import procesamiento.RgbHsv;

public class ImageProcessing_MethodABDC extends ImageProcessing{
    
    private BufferedImage imageTemp;
    
	private int indicadorA = 0;
	private int indicadorB = 0;
	private int indicadorC = 0;
	private Double indicadorD = 0.0;
	
	
	public ImageProcessing_MethodABDC() {
	}

	
	public int getIndicadorA() {
		return indicadorA;
	}

	public void setIndicadorA(int indicadorA) {
		this.indicadorA = indicadorA;
	}

	public int getIndicadorB() {
		return indicadorB;
	}

	public void setIndicadorB(int indicadorB) {
		this.indicadorB = indicadorB;
	}

	public int getIndicadorC() {
		return indicadorC;
	}

	public void setIndicadorC(int indicadorC) {
		this.indicadorC = indicadorC;
	}

	public Double getIndicadorD() {
		return indicadorD;
	}

	public void setIndicadorD(Double indicadorD) {
		this.indicadorD = indicadorD;
	}

	/**
	 * Calculo del metodo ABDC
	 * @return 
	 */
	public BufferedImage calcularABCD(Objeto mancha){
		this.setIndicadorA(isSimetric(mancha));
		
		//Aca Podriamos calcular La sigmoide para cada banda de color RGB
		//(1/( 1 + Math.pow(Math.E,(-1*x))));
		this.setIndicadorB(howIrregularIs(mancha));
		
		this.setIndicadorC(getCantColores(mancha));
		
		/*El valor de las Diferencias estructurales, D, depende del número de estructuras que
		posee la lesión. Desde áreas homogéneas, cadenas, venas, puntos y/o glóbulos, por lo
		que el valor de D varía entre 1 y 5.*/
		this.setIndicadorD(Math.rint(mancha.getRadio()*2*0.2645833333333*100)/100);
		
        //super.updateImage("Calculo Metodo ABDC ", imageTemp);
        return imageTemp;
		
	}
	
	/**
	 * Chequea que tan irregular es la mancha segun el indice de
	 * circularidad, cuanto mas cercana a 4PI mas perfecta. 
	 * @param mancha
	 * @return
	 */
	private int howIrregularIs(Objeto mancha) {
		Double indice = mancha.getCircularidad()/(Math.PI*4); 
		int irregular = 0;
		if(indice <= 0.1) irregular=0;
		if(indice > 0.1 && indice <= 0.2) irregular=1;
		if(indice > 0.2 && indice <= 0.3) irregular=2;
		if(indice > 0.3 && indice <= 0.4) irregular=3;
		if(indice > 0.4 && indice <= 0.5) irregular=4;
		if(indice > 0.5 && indice <= 0.6) irregular=5;
		if(indice > 0.6 && indice <= 0.7) irregular=6;
		if(indice > 0.7 && indice <= 0.8) irregular=7;
		if(indice > 0.8 && indice <= 1) irregular=8;
		
		super.updateActivityLog("Chequea que tan irregular es la mancha: (B="+ irregular+")");
		
		return irregular;
	}

	/**
	 * Retorna la cantidad de colores que pertenecen a la imagen (0-6)
	 * @param mancha
	 * @return
	 */
	private int getCantColores(Objeto mancha) {
		int[] colorCantidad = {0,0,0,0,0,0};
		for(Pixel p : mancha.getPuntos()){
			float[] hsvRange = RgbHsv.RGBtoHSV(p.getCol().getRed(), p.getCol().getGreen(), p.getCol().getBlue());
			
			if(p.getCol().equals(Color.WHITE)){//Blanco
				colorCantidad[0]++;
			}else if(hsvRange[0] > 0 && hsvRange[0] < 7){//Rojo
				colorCantidad[1]++;
			}else if(hsvRange[0] > 23 && hsvRange[0] < 39){//Marrón Claro
				colorCantidad[2]++;
			}else if(p.getCol().equals(new Color(114, 35, 14))){//Marrón oscuro
				colorCantidad[3]++;
			}else if(p.getCol().equals(new Color(30, 85, 83))){//Azul Grisáceo,
				colorCantidad[4]++;
			}else if(p.getCol().equals(Color.BLACK)){//Negro
				colorCantidad[5]++;
			}
		}
		
		int sumaColores = 0;
		for(int i=0; i<6;i++){
			if(colorCantidad[i]!=0) sumaColores++;
		}
		
		super.updateActivityLog("Gama de colores en la mancha: (C="+ sumaColores+")");
		return sumaColores;
	}
	

	/**
	 * Calcula si un objeto es simetrico o no.
	 * @return
	 */
	private int isSimetric(Objeto mancha) {
		 
		Pixel medio = mancha.getPixelMedio();

		int arriba = 0;
		int abajo = 0;
		
		for(Pixel p : mancha.getPuntos()) {
			if(p.getY() <= medio.getY()){
				arriba++;
			}else{
				abajo++;
			}
		}
		
		for(Pixel p : mancha.getContorno()) {
			if(p.getY() <= medio.getY()){
				arriba++;
			}else{
				abajo++;
			}
		}
		
		super.updateActivityLog("Calcula si un objeto es simetrico o no (A= "+ (arriba/abajo < 0.5?'1':'0')+")");
		
		if(arriba/abajo < 0.5) return 1;
		return 0;
	}
	
	/**
	 * Calcular el indicador FDS = A × 1.3 + B × 0.1 + C × 0.5 + D × 0.5
	 *  FDS > 5.45 -> Probable "melanoma maligno"
	 *  4.75 < FDS < 5.45 ->  Moderada de ser melanoma maligno
	 *  FDS < 4.75 -> Tipo benigno
	 * @return
	 */
	public Double calcularFDS(){
		Double indicadorFDS = this.getIndicadorA()*1.3 + this.getIndicadorB()*0.1 + this.getIndicadorC()*0.5 + this.getIndicadorD()*0.5;
		
		super.updateActivityLog("Calcula FDS "+ indicadorFDS);
		
		return indicadorFDS;
	}
	

	
	
}



