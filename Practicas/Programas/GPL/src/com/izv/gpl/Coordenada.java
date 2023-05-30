package com.izv.gpl;

import android.location.Location;

public class Coordenada{
	
	private double latitud, longitud; 
	private float precision;
	
	public Coordenada(Location loc) {
		this.latitud=loc.getLatitude();
		this.longitud=loc.getLongitude();
		this.precision=loc.getAccuracy();
	}

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public float getPrecision() {
		return precision;
	}

	public void setPrecision(float precision) {
		this.precision = precision;
	}
	
}