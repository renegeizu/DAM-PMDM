package com.izv.juegoprimitivo;

	import android.annotation.SuppressLint;
	import android.graphics.Canvas;

@SuppressLint("WrongCall")
public class HebraJuego extends Thread {

	private VistaJuego vista;
	private boolean funcionando = false;
	private static final long FPS = 30;

	public HebraJuego(VistaJuego vj) {
		this.vista = vj;
	}
	
	public void setFuncionando(boolean f) {
		funcionando = f;
	}
	
	@Override
	public void run() {
		long inicio;
		long ticksPS=1000/FPS;
		long tiempoEspera;
		Canvas canvas;
		while (funcionando) {
			canvas=null;
			inicio=System.currentTimeMillis();
			try {
				canvas=vista.getHolder().lockCanvas();
				if(canvas!=null){
					synchronized (vista.getHolder()) {
						vista.onDraw(canvas);
					}
				}
			}finally {
				if (canvas!=null) {
					vista.getHolder().unlockCanvasAndPost(canvas);
				}
			}
			tiempoEspera=ticksPS-(System.currentTimeMillis()-inicio);
			try {
				if (tiempoEspera>0)
					sleep(tiempoEspera);
				else
					sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}
	
}