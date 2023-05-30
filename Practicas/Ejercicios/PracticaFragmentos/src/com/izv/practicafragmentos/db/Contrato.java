package com.izv.practicafragmentos.db;

	import android.provider.BaseColumns;

public class Contrato {

	private Contrato (){
		
	}
	
	public static abstract class Anime implements BaseColumns{
		
		public static final String TABLA = "Anime";
		public static final String NOMBRE = "Nombre";
		public static final String GENERO = "Genero";
		public static final String DESCRIPCION = "Descripcion";
		
	}
	
}