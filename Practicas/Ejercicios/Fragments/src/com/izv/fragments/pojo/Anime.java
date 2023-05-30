package com.izv.fragments.pojo;

	import java.io.Serializable;

public class Anime implements Serializable{
	
	private Long Id;
	private String Nombre, Genero, Descripcion, Visto;

	public Anime() {
		this("Nombre", "Genero", "Descripcion", "Visto");
	}

	public Anime(String nombre, String genero, String descripcion, String visto) {
		super();
		this.Nombre = nombre;
		this.Genero = genero;
		this.Descripcion = descripcion;
		this.Visto = visto;
	}
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		this.Id = id;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		this.Nombre = nombre;
	}

	public String getGenero() {
		return Genero;
	}

	public void setGenero(String genero) {
		this.Genero = genero;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.Descripcion = descripcion;
	}

	public String getVisto() {
		return Visto;
	}

	public void setVisto(String visto) {
		this.Visto = visto;
	}

	@Override
	public String toString() {
		return "Anime --> Nombre=" + Nombre + ", Genero=" + Genero
				+ ", Descripcion=" + Descripcion + ", Visto=" + Visto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((Descripcion == null) ? 0 : Descripcion.hashCode());
		result = prime * result + ((Genero == null) ? 0 : Genero.hashCode());
		result = prime * result + ((Nombre == null) ? 0 : Nombre.hashCode());
		result = prime * result + ((Visto == null) ? 0 : Visto.hashCode());
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
		Anime other = (Anime) obj;
		if (Descripcion == null) {
			if (other.Descripcion != null)
				return false;
		} else if (!Descripcion.equals(other.Descripcion))
			return false;
		if (Genero == null) {
			if (other.Genero != null)
				return false;
		} else if (!Genero.equals(other.Genero))
			return false;
		if (Nombre == null) {
			if (other.Nombre != null)
				return false;
		} else if (!Nombre.equals(other.Nombre))
			return false;
		if (Visto == null) {
			if (other.Visto != null)
				return false;
		} else if (!Visto.equals(other.Visto))
			return false;
		return true;
	}

	public int compareTo(String name) {
		return Nombre.compareTo(name);
	}

}
