package com.izv.fragments.db;

	import com.izv.fragments.pojo.Anime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GestionAnime {

	private Ayudante helper;
	private SQLiteDatabase db;
	
	public void close() {
		helper.close();
	}
	
	public int delete(Anime anime) {
		return delete(anime.getId());
	}

	public int delete(long id) {
		String condicion = Contrato.Anime._ID + " = ?";
		String[] argumentos = { id + "" };
		int cuenta = db.delete(Contrato.Anime.TABLA, condicion, argumentos);
		return cuenta;
	}
	
	public int delete(String nombre){
		String condicion = Contrato.Anime.NOMBRE + " = ?";
		String[] argumentos = { nombre };
		int cuenta = db.delete(Contrato.Anime.TABLA, condicion, argumentos);
		return cuenta;
	}

	public GestionAnime(Context c) {
		helper = new Ayudante(c);
	}
	
	public Cursor getCursor(){
		return getCursor(null, null);
	}
	
	public Cursor getCursor(String condicion, String [] parametrosPrepared){
		Cursor cursor = db.query(Contrato.Anime.TABLA, null, condicion, parametrosPrepared, null, null, 
				Contrato.Anime.NOMBRE);
		return cursor;
	}
	
	public int getNumRow(){
		Cursor c=getCursor();
		return c.getCount();
	}
	
	public Anime getRow(Cursor c) {
		try{
			Anime anime = new Anime();
			anime.setId(c.getLong(0));
			anime.setNombre(c.getString(1));
			anime.setGenero(c.getString(2));
			anime.setDescripcion(c.getString(3));
			anime.setVisto(c.getString(4));
			return anime;
		} catch(Exception e){
			return null;
		}
	}

	public Anime getRow(Long id) {
		String[] proyeccion=null;
		String where = Contrato.Anime._ID + " = ?";
		String[] parametros = new String[] { id+"" };
		String groupby = null; 
		String having = null;
		String orderby = Contrato.Anime.NOMBRE + " ASC";
		Cursor c = db.query(Contrato.Anime.TABLA, proyeccion, where, parametros, groupby , having, orderby);
		c.moveToFirst();
		Anime anime = getRow(c);
		c.close();
		return anime;
	}

	public Anime getRow(String nombre) {
		String[] parametros = new String[] { nombre };
		Cursor c = db.rawQuery("select * from " + Contrato.Anime.TABLA
			+ " where " + Contrato.Anime.NOMBRE+ " = ?", parametros);
		c.moveToFirst();
		Anime anime = getRow(c);
		c.close();
		return anime;
	}

	public long insert(Anime anime) {
		ContentValues valores = new ContentValues();
		valores.put(Contrato.Anime.NOMBRE, anime.getNombre());
		valores.put(Contrato.Anime.GENERO, anime.getGenero());
		valores.put(Contrato.Anime.DESCRIPCION, anime.getDescripcion());
		valores.put(Contrato.Anime.VISTO, anime.getVisto());
		long id = db.insert(Contrato.Anime.TABLA, null, valores);
		return id;
	}

	public void open() {
		db = helper.getWritableDatabase();
	}
	
	public void openRead() {
		db = helper.getReadableDatabase();
	}

	public int update(Anime anime) {
		ContentValues valores = new ContentValues();
		valores.put(Contrato.Anime.NOMBRE, anime.getNombre());
		valores.put(Contrato.Anime.GENERO, anime.getGenero());
		valores.put(Contrato.Anime.DESCRIPCION, anime.getDescripcion());
		valores.put(Contrato.Anime.VISTO, anime.getVisto());
		String condicion = Contrato.Anime._ID + " = ?";
		String[] argumentos = { anime.getId() + "" };
		int cuenta = db.update(Contrato.Anime.TABLA, valores, condicion, argumentos);
		return cuenta;
	}	
	
}