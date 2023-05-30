package com.izv.usoorientaciones;

import java.io.Serializable;

public class Contacto implements Serializable {
	
	private String nombre;
	private String telefono;
	private String otros;
	
	public Contacto() {
		super();
	}

	public Contacto(String nombre, String telefono, String otros) {
		super();
		this.nombre = nombre;
		this.telefono = telefono;
		this.otros = otros;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getOtros() {
		return otros;
	}

	public void setOtros(String otros) {
		this.otros = otros;
	}

	@Override
	public String toString() {
		return "Nombre="+nombre+"\n"+"Telefono="+telefono+"\n"+"Otros="+otros;
	}

	public int compareTo(String arg0) {
		return nombre.compareTo(arg0);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((otros == null) ? 0 : otros.hashCode());
		result = prime * result
				+ ((telefono == null) ? 0 : telefono.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contacto other = (Contacto) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (otros == null) {
			if (other.otros != null)
				return false;
		} else if (!otros.equals(other.otros))
			return false;
		if (telefono == null) {
			if (other.telefono != null)
				return false;
		} else if (!telefono.equals(other.telefono))
			return false;
		return true;
	}

}
