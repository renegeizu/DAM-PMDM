package com.izv.practicafragmentos.db;

	import android.content.Context;
	import android.database.sqlite.SQLiteDatabase;
	import android.database.sqlite.SQLiteOpenHelper;
	import android.util.Log;

public class Ayudante extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "Anime.db";
	public static final int DATABASE_VERSION = 1;

	public Ayudante(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql="CREATE TABLE IF NOT EXISTS "+Contrato.Anime.TABLA+" ("
			               +Contrato.Anime._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
			               +Contrato.Anime.NOMBRE+" VARCHAR(50), "
			               +Contrato.Anime.GENERO+" VARCHAR(30), "
			               +Contrato.Anime.DESCRIPCION+" VARCHAR(512) )";
		Log.v("SQL Anime", sql);
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
		onCreate(db);
	}
}