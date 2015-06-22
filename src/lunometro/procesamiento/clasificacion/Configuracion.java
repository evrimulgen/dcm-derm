package lunometro.procesamiento.clasificacion;

import lunometro.procesamiento.HSVRange;


public class Configuracion {

	private Long id;
	
	/**
	 * Nombre del sistema
	 */
	private String nombreSistema;
	
	/**
	 * Valor H m�nimo
	 */
	private Float fondoHMin;
	
	/**
	 * Valor H m�ximo
	 */
	private Float fondoHMax;
	
	/**
	 * Valor S m�nimo
	 */
	private Float fondoSMin;
	
	/**
	 * Valor S m�ximo
	 */
	private Float fondoSMax;
	
	/**
	 * Valor V m�nimo
	 */
	private Float fondoVMin;
	
	/**
	 * Valor V m�ximo
	 */
	private Float fondoVMax;
	
	
	/**
	 * Diametro en mm del objeto de referencia
	 */
	private Double diametroObjetoReferencia;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNombreSistema() {
		return nombreSistema;
	}

	public void setNombreSistema(String nombreSistema) {
		this.nombreSistema = nombreSistema;
	}

	public Float getFondoHMin() {
		return fondoHMin;
	}

	public void setFondoHMin(Float fondoHMin) {
		this.fondoHMin = fondoHMin;
	}

	public Float getFondoHMax() {
		return fondoHMax;
	}

	public void setFondoHMax(Float fondoHMax) {
		this.fondoHMax = fondoHMax;
	}

	public Float getFondoSMin() {
		return fondoSMin;
	}

	public void setFondoSMin(Float fondoSMin) {
		this.fondoSMin = fondoSMin;
	}

	public Float getFondoSMax() {
		return fondoSMax;
	}

	public void setFondoSMax(Float fondoSMax) {
		this.fondoSMax = fondoSMax;
	}

	public Float getFondoVMin() {
		return fondoVMin;
	}

	public void setFondoVMin(Float fondoVMin) {
		this.fondoVMin = fondoVMin;
	}

	public Float getFondoVMax() {
		return fondoVMax;
	}

	public void setFondoVMax(Float fondoVMax) {
		this.fondoVMax = fondoVMax;
	}

	public Double getDiametroObjetoReferencia() {
		return diametroObjetoReferencia;
	}

	public void setDiametroObjetoReferencia(Double diametroObjetoReferencia) {
		this.diametroObjetoReferencia = diametroObjetoReferencia;
	}

	public HSVRange getHSVRange(){
		HSVRange range = new HSVRange();
		range.setHMin(getFondoHMin());
		range.setHMax(getFondoHMax());
		range.setSMin(getFondoSMin());
		range.setSMax(getFondoSMax());
		range.setVMin(getFondoVMin());
		range.setVMax(getFondoVMax());
		return range;
	}
	
	public void setHSVRange(HSVRange range){
		setFondoHMin(range.getHMin());
		setFondoHMax(range.getHMax());
		setFondoSMin(range.getSMin());
		setFondoSMax(range.getSMax());
		setFondoVMin(range.getVMin());
		setFondoVMax(range.getVMax());
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof Configuracion))
			return false;
		Configuracion c = (Configuracion) obj;
		if (getNombreSistema() != null)
			return getNombreSistema().equals(c.getNombreSistema());
		return false;
	}

	public String toString() {
		return getNombreSistema();
	}

	
}
