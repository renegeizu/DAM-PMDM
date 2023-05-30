package com.izv.gpl;

	import java.io.Serializable;
	import java.util.Date;
	import android.location.Location;

public class Paseos implements Serializable{

	private Date fecha;
	private Coordenada posicion;
	
	public Paseos() {
	}

	public Paseos(Date fecha, Location posicion) {
		this.fecha = fecha;
		this.posicion = new Coordenada(posicion);
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Coordenada getPosicion() {
		return posicion;
	}

	public void setPosicion(Coordenada posicion) {
		this.posicion = posicion;
	}
	
}