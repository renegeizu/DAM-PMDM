package com.izv.juegoprimitivo;

	import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.VelocityTrackerCompat;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.VelocityTracker;

@SuppressLint("WrongCall")
public class VistaJuego extends SurfaceView{

	private SurfaceHolder contenedorSuperficie;
	private Bitmap bmp, bmp2, bmp3, bmp4;
	private int alto, ancho;
	private HebraJuego hebraJuego;
	private int ejeY = 0;
	private int direccionY;
	private int ejeX = 0;
	private int direccionX;
	private Figura f=null, g=null, h=null;
	private long ultimoClick=0, ultimoSplash=0;
	private VelocityTracker controlVelocidad = null;
	private Sprite s=null;
	
	public VistaJuego(Context context) {
		super(context);
		contenedorSuperficie=getHolder();
		GestorSuperficie gestor=new GestorSuperficie();
		contenedorSuperficie.addCallback(gestor);
		bmp = BitmapFactory.decodeResource(getResources(),R.drawable.figura);
		bmp2 = BitmapFactory.decodeResource(getResources(),R.drawable.figura2);
		bmp3 = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
		bmp4 = BitmapFactory.decodeResource(getResources(),R.drawable.anim4x2);
		hebraJuego = new HebraJuego(this);
	}
	
	private class GestorSuperficie implements SurfaceHolder.Callback{

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			alto=height;
			ancho=width;
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			hebraJuego.setFuncionando(true);
			hebraJuego.start();
			//Canvas lienzo=contenedorSuperficie.lockCanvas(null);
			//onDraw(lienzo);
			//contenedorSuperficie.unlockCanvasAndPost(lienzo);
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			boolean reintentar = true;
			hebraJuego.setFuncionando(false);
			while (reintentar) {
				try {
					hebraJuego.join();
					reintentar = false;
				} catch (InterruptedException e) {
				}
			}
		}
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//canvas.drawBitmap(bmp, 10, 10, null);
		canvas.drawColor(Color.LTGRAY);
		if(f==null)
			f=new Figura(this, bmp);
		f.dibujar(canvas);
		if(g==null)
			g=new Figura(this, bmp2);
		g.dibujar(canvas);
		if(h!=null){
			h.dibujar(canvas);
			if (System.currentTimeMillis()-ultimoClick>1000){
				h=null;
			}
		}
		if(s==null)
			s=new Sprite(this, bmp4);
		s.dibujar(canvas);
		/*if (ejeY >= getHeight()-bmp.getHeight()) {
			direccionY=-10;
		} else if (ejeY<=0) {
			direccionY=10;
		}
		if (ejeX >= getWidth() - bmp.getWidth()) {
			direccionX = -10;
		} else if (ejeX <= 0) {
			direccionX = 10;
		}
		ejeX = ejeX + direccionX;
		ejeY = ejeY+direccionY;
		canvas.drawBitmap(bmp, ejeX, ejeY, null);*/
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/*if (System.currentTimeMillis()-ultimoClick>300) {
				ultimoClick = System.currentTimeMillis();
				float x,y;
				x=event.getX();
				y=event.getY();
				synchronized (getHolder()) {
					if(f.tocado(x, y)){
						f=new Figura(this, bmp);
					}
					if(g.tocado(x, y)){
						g=new Figura(this, bmp2);
					}
				}
		}*/
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				float x,y;
				x=event.getX();
				y=event.getY();
				synchronized (getHolder()) {
					if(f.tocado(x, y)){
						h=new Figura(this, bmp3, false, (int) x, (int) y);
						ultimoSplash=System.currentTimeMillis();
						f=new Figura(this, bmp);
					}
					if(g.tocado(x, y)){
						if(controlVelocidad == null){
							controlVelocidad = VelocityTracker.obtain();
						}else{
							controlVelocidad.clear();
						}
						controlVelocidad.addMovement(event);
					}
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if(controlVelocidad!=null){
					controlVelocidad.addMovement(event);
					controlVelocidad.computeCurrentVelocity(1000);
					float velX = VelocityTrackerCompat.getXVelocity(controlVelocidad, 
							event.getPointerId(event.getActionIndex()));
					float velY = VelocityTrackerCompat.getYVelocity(controlVelocidad, 
							event.getPointerId(event.getActionIndex()));
					g.setVelocidad(velX, velY);
				}
				break;
			default:
				break;
		}
		return true;
	}
	
}