package com.izv.juegoprimitivo;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {

	private VistaJuego vista;
	private Bitmap bmp;
	private int ancho, alto;
	private int ejeX, ejeY;
	private int direccionX, direccionY;
	private int frameActual=0;
	private static final  int COLUMNAS = 4;
	private static final int FILAS = 2;
	
	public Sprite(VistaJuego vista, Bitmap bmp) {
		this.vista = vista;
		this.bmp = bmp;
		this.ancho=bmp.getWidth();
		this.alto=bmp.getHeight();
		this.alto=bmp.getHeight()/FILAS;
		this.ancho=bmp.getWidth()/COLUMNAS;
		Random rnd = new Random();
		ejeX=rnd.nextInt(vista.getWidth()-this.ancho);
		ejeY=rnd.nextInt(vista.getHeight()-this.alto);
		direccionX=rnd.nextInt(21)-11;
		if(direccionX==0) 
			direccionX=1;
		direccionY=rnd.nextInt(12)-5;
		if(direccionY==0) 
			direccionY=1;
	}
	
	public Sprite(VistaJuego vista, Bitmap bmp, boolean movimiento, int x, int y) {
		this(vista, bmp);
		if(x!=0 && y!=0){
			ejeX=x-ancho/2;
			ejeY=y-alto/2;
		}
		if(!movimiento){
			direccionX=0;
			direccionY=0;
		}
	}
	
	public void setVelocidad(float x, float y){
		direccionX=(int) x;
		direccionY=(int) y;
	}
	
	public void dibujar(Canvas canvas) {
		movimiento();
		int origenx = frameActual * ancho;
		int origeny = 0;
		if(direccionX<0)
			origeny=alto;
		else
			origeny=0;
		Rect origenEnBitmap = new Rect(origenx, origeny,  origenx+ancho, origeny+alto);
		Rect destinoEnLienzo = new Rect(ejeX, ejeY, ejeX+ancho, ejeY+alto);
		canvas.drawBitmap(bmp, origenEnBitmap, destinoEnLienzo, null);
	}
	
	private void movimiento(){
		frameActual = ++frameActual % COLUMNAS;
		if (ejeX>vista.getWidth()-ancho-direccionX||ejeX+direccionX<0){
			direccionX=-direccionX;
		}
		ejeX=ejeX+direccionX;
		if(ejeY>vista.getHeight()-alto-direccionY||ejeY+direccionY<0){
			direccionY=-direccionY;
		}
		ejeY=ejeY+direccionY;
	}
	
	public boolean tocado(float x, float y){
		return x>ejeX && x<ejeX+ancho && y>ejeY && y<ejeY+alto;
	}
	
}