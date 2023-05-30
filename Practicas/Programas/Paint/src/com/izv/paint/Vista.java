package com.izv.paint;

	import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;

public class Vista extends View{
	
	private static Paint pincel=new Paint();
	private int ancho;
	private int alto;
	private float x0=10, xi, y0, yi;
	private static Bitmap mapaDeBits;
	private static Canvas lienzoFondo;
	private Bitmap icon;
	//private Path rectaPoligonal=new Path();
	private Path rectaPoligonal1=new Path();
	private Path rectaPoligonal2=new Path();
	private float xp1, xp2;
	private float yp1, yp2;
	private int color=Color.GRAY;
	//private ArrayList <Float> rectas=new ArrayList<Float>();
	
	public static Bitmap getBitmap(){
		return mapaDeBits;
	}
	
	public static void setBitmap(Bitmap mapa){
		mapaDeBits=mapa;
		lienzoFondo = new Canvas(mapaDeBits);
	}
	
	public Vista(Context contexto) {
		super(contexto);
		pincel.setColor(color);
		pincel.setAntiAlias(true);
		pincel.setStrokeWidth(5);
		pincel.setStyle(Paint.Style.STROKE);
		icon=BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
	}
	
	@Override
	public void onDraw(Canvas lienzo) {
		//Primera Forma
		//float x1=10, x2=100, y1=10, y2=100;
		//lienzo.drawRect(x1, y1, x2, y2, pincel);
		//for(int i=0; i<rectas.size(); i=i+4){
		//	lienzo.drawLine(rectas.get(i), rectas.get(i+1), rectas.get(i+2), rectas.get(i+3), pincel);
		//}
		//Dibujar Linea
		//lienzo.drawBitmap(mapaDeBits, 0, 0, null);
		//lienzo.drawLine(x0, y0, xi, yi, pincel);
		//Dibujar Circulo
		//double radius=Math.sqrt((xi-x0)*(xi-x0)+(yi-y0)*(yi-y0));
		//lienzo.drawCircle(x0, y0, (float)radius, pincel);
		lienzo.drawBitmap(mapaDeBits, 0, 0, null);
		lienzo.drawPath(rectaPoligonal1, pincel);
		lienzo.drawPath(rectaPoligonal2, pincel);
		//lienzo.drawPath(rectaPoligonal, pincel);
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		ancho = w;
		alto = h;
		mapaDeBits = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		lienzoFondo = new Canvas(mapaDeBits);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*float x = event.getX();
		float y = event.getY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				//x0=x;
				//y0=y;
				//xi=x;
				//yi=y;
				xi = x;
				yi = y;
				rectaPoligonal.reset();
				rectaPoligonal.moveTo(xi, yi);
				break;
			case MotionEvent.ACTION_MOVE:
				//xi=x;
				//yi=y;
				//lienzoFondo.drawLine(x0, y0, xi, yi, pincel);
				//lienzoFondo.drawPoint(xi, yi, pincel);
				//x0=xi;
				//y0=yi;
				rectaPoligonal.quadTo(xi, yi, (x + xi)/2, (y + yi)/2);
				xi = x;
				yi = y;
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				//xi=x;
				//yi=y;
				//rectas.add(x0);
				//rectas.add(y0);
				//rectas.add(xi);
				//rectas.add(yi);
				//lienzoFondo.drawLine(x0, y0, xi, yi, pincel);
				//lienzoFondo.drawBitmap(icon, xi, yi, null);
				lienzoFondo.drawPath(rectaPoligonal, pincel);
				rectaPoligonal.reset();
				invalidate();
				break;
		}
		return true;*/
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
				case MotionEvent.ACTION_POINTER_UP:
				case MotionEvent.ACTION_CANCEL:
					break;
			}
		}
		invalidate();
		return true;
	}
	
	public void read(){
		File carpeta=new File(Environment.getExternalStorageDirectory().getPath());
		File archivo = new File (carpeta, "Dibujo.png");
		mapaDeBits = Bitmap.createBitmap(ancho, alto, Bitmap.Config.ARGB_8888);
		if (archivo.exists()){
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inMutable=true;
			mapaDeBits=BitmapFactory.decodeFile(archivo.getAbsolutePath(),options);
		}
		lienzoFondo = new Canvas(mapaDeBits);
		invalidate();
	}
	
	public void save() throws FileNotFoundException{
		Bitmap mapa=Vista.getBitmap();
		File carpeta=new File(Environment.getExternalStorageDirectory().getPath());
		File archivo = new File (carpeta, "Dibujo.png");
		FileOutputStream fos = new FileOutputStream (archivo);
		mapa.compress(CompressFormat.PNG, 90, fos);
	}
	
	public void setColor(int color){
		this.color=color;
		pincel.setColor(color);
	}
	
	public int getColor(){
		return color;
	}
	
}