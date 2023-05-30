package com.rngds;

	import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class AndroidPaint extends View{
	
	private static Paint pincel=new Paint();
	private ArrayList<Path> paths=new ArrayList();
	private int ancho;
	private int alto;
	private static Bitmap mapaDeBits;
	private static Canvas lienzoFondo;
	private Path rectaPoligonal1=new Path();
	private Path rectaPoligonal2=new Path();
	private float xp1, xp2;
	private float yp1, yp2;
	private int color=Color.BLACK;
	private static boolean nuevo=false;
	private Map<Path, Integer> colorsMap = new HashMap<Path, Integer>();

	public AndroidPaint(Context context) {
		super(context);
		pincel.setColor(color);
		pincel.setAntiAlias(true);
		pincel.setStrokeWidth(5);
		pincel.setStyle(Paint.Style.STROKE);
	}
	
	public void onDraw(Canvas c){
		if(nuevo==true){
			c.drawColor(0xffffffff);
			mapaDeBits = Bitmap.createBitmap(ancho, alto, Bitmap.Config.ARGB_8888);
			lienzoFondo = new Canvas(mapaDeBits);
			lienzoFondo.drawColor(0xffffffff);
			rectaPoligonal1.reset();
			rectaPoligonal2.reset();
			nuevo=false;
		}else{
			for (Path p : paths){
		        pincel.setColor(colorsMap.get(p));
		        c.drawPath(p, pincel);
		        Log.v("Dibujar", "Entra");
		    }
		    pincel.setColor(color);
			c.drawBitmap(mapaDeBits, 0, 0, null);
			c.drawPath(rectaPoligonal1, pincel);
			c.drawPath(rectaPoligonal2, pincel);
		}
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		ancho = w;
		alto = h;
		mapaDeBits = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		lienzoFondo = new Canvas(mapaDeBits);
		lienzoFondo.drawColor(0xffffffff);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int puntos = event.getPointerCount();
		for (int i = 0; i < puntos; i++) {
			int accion = event.getActionMasked();
			float x = event.getX(i);
			float y = event.getY(i);
			int puntero = event.getPointerId(i);
			switch (accion) {
				case MotionEvent.ACTION_DOWN:
				case MotionEvent.ACTION_POINTER_DOWN:
					if(puntero==0){
						xp1=x;
						yp1=y;
						rectaPoligonal1.moveTo(xp1,  yp1);
					}else if(puntero==1){
						xp2=x;
						yp2=y;
						rectaPoligonal2.moveTo(xp2,  yp2);
					}
					break;
				case MotionEvent.ACTION_MOVE:
					if(puntero==0){
						rectaPoligonal1.quadTo(xp1, yp1, (x+xp1)/2, (y+yp1)/2);
						xp1=x;
						yp1=y;
					}else if(puntero==1){
						rectaPoligonal2.quadTo(xp2, yp2, (x+xp2)/2, (y+yp2)/2);
						xp2=x;
						yp2=y;
					}
					break;
				case MotionEvent.ACTION_UP:
					paths.add(rectaPoligonal1);
					colorsMap.put(rectaPoligonal1, color);
					paths.add(rectaPoligonal2);
					colorsMap.put(rectaPoligonal2, color);
					lienzoFondo.drawPath(rectaPoligonal1, pincel);
					lienzoFondo.drawPath(rectaPoligonal2, pincel);
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_CANCEL:
					break;
			}
		}
		invalidate();
		return true;
	}
	
	public void save(String guardar) throws FileNotFoundException{
		Bitmap mapa=mapaDeBits;
		File carpeta=new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_PICTURES).getPath());
		File archivo = new File (carpeta, guardar+".png");
		FileOutputStream fos = new FileOutputStream (archivo);
		mapa.compress(CompressFormat.PNG, 90, fos);
	}
	
	public static Bitmap getBitmap(){
		return mapaDeBits;
	}
	
	public static void setBitmap(Bitmap mapa){
		mapaDeBits=mapa;
		lienzoFondo = new Canvas(mapaDeBits);
	}
	
	public void setColor(int color){
		this.color=color;
		pincel.setColor(color);
		rectaPoligonal1.reset();
		rectaPoligonal2.reset();
	}
	
	public int getColor(){
		return color;
	}
	
	public void setBorrar(){
		nuevo=true;
		invalidate();
	}

}