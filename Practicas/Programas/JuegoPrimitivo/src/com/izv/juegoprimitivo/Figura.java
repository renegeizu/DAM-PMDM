package com.izv.juegoprimitivo;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Figura {

	private VistaJuego vista;
	private Bitmap bmp;
	private int ancho, alto;
	private int ejeX, ejeY;
	private int direccionX, direccionY;
	
	public Figura(VistaJuego vista, Bitmap bmp) {
		this.vista = vista;
		this.bmp = bmp;
		this.ancho=bmp.getWidth();
		this.alto=bmp.getHeight();
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
	
	public Figura(VistaJuego vista, Bitmap bmp, boolean movimiento, int x, int y) {
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
		canvas.drawBitmap(bmp, ejeX, ejeY, null);
	}
	
	private void movimiento(){
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
	
	public boolean choca(Figura f){
		Rect r=new Rect(ejeX, ejeY, ejeX+ancho, ejeY+alto);
		Rect q=new Rect(f.ejeX, f.ejeY, f.ejeX+f.ancho, f.ejeY+f.alto);
		return Rect.intersects(r, q);
	}
	
}