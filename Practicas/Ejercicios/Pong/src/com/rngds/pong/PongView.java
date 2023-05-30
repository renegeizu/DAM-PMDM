package com.rngds.pong;

	import android.content.Context;
	import android.os.Handler;
	import android.os.Message;
	import android.util.AttributeSet;
	import android.view.MotionEvent;
	import android.view.SurfaceHolder;
	import android.view.SurfaceView;
	import android.widget.TextView;

public class PongView extends SurfaceView implements SurfaceHolder.Callback {

    private PongThread hiloJuego;
    private TextView estado;
    private TextView marcador;
    private boolean enMovimiento;
    private float ultimoToque;

	public PongView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        hiloJuego = new PongThread(holder, context,
                new Handler() {
                    @Override
                    public void handleMessage(Message mensaje) {
                        estado.setVisibility(mensaje.getData().getInt("vis"));
                        estado.setText(mensaje.getData().getString("text"));
                    }
                },
                new Handler() {
                    @Override
                    public void handleMessage(Message mensaje) {
                        marcador.setText(mensaje.getData().getString("text"));
                    }
                },
                attributeSet
        );
        setFocusable(true);
    }

    public void setEstado(TextView tv) {
        estado=tv;
    }

    public void setMarcador(TextView tv) {
        marcador=tv;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!hasWindowFocus) {
            hiloJuego.pausar();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        hiloJuego.setSurfaceTamaño(width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        hiloJuego.ejecutando(true);
        hiloJuego.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean intentar=true;
        hiloJuego.ejecutando(false);
        while (intentar) {
            try {
                hiloJuego.join();
                intentar=false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (hiloJuego.entreRondas()) {
                    hiloJuego.setEstado(PongThread.ESTADO_FUNCIONANDO);
                } else {
                    if (hiloJuego.tocandoPaletaJugador(event)) {
                        enMovimiento=true;
                        ultimoToque=event.getY();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (enMovimiento) {
                    float y=event.getY();
                    float dy=y-ultimoToque;
                    ultimoToque=y;
                    hiloJuego.moverPaletaJugador(dy);
                }
                break;
            case MotionEvent.ACTION_UP:
                enMovimiento=false;
                break;
        }
        return true;
    }

    public PongThread getGameThread() {
        return hiloJuego;
    }

}