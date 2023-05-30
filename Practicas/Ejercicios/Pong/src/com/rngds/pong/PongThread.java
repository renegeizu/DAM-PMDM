package com.rngds.pong;

	import android.content.Context;
	import android.content.res.Resources;
	import android.content.res.TypedArray;
	import android.graphics.Canvas;
	import android.graphics.Color;
	import android.graphics.DashPathEffect;
	import android.graphics.Paint;
	import android.os.Bundle;
	import android.os.Handler;
	import android.os.Message;
	import android.os.SystemClock;
	import android.util.AttributeSet;
	import android.view.MotionEvent;
	import android.view.SurfaceHolder;
	import android.view.View;
	import java.util.Random;

public class PongThread extends Thread {

    public static final int ESTADO_PAUSA=0;
    public static final int ESTADO_LISTO=1;
    public static final int ESTADO_FUNCIONANDO=2;
    public static final int ESTADO_DERROTA=3;
    public static final int ESTADO_VICTORIA=4;
    private static final int VELOCIDAD_PELOTA=23;
    private static final int VELOCIDAD_PALETA=23;
    private static final int FPS=60;
    private static final double ANGULO=5*Math.PI/12;
    private static final int FRAMES_COLISION=5;
    private static final String JUGADOR="Jugador";
    private static final String CPU="CPU";
    private static final String BOLA="Bola";
    private static final String ESTADO="Estado";
    private final SurfaceHolder mSurfaceHolder;
    private final Handler mStatusHandler;
    private final Handler mScoreHandler;
    private final Context conexto;
    private boolean run;
    private final Object runLock;
    private int estado;
    private Jugador jugador;
    private Jugador cpu;
    private Bola bola;
    private Paint pincelLinea;
    private Paint pincelCanvas;
    private int canvasAlto;
    private int canvasAncho;
    private Random random;
    private float movimientoCPU;
    
    PongThread(final SurfaceHolder surfaceHolder, final Context context,
    		final Handler statusHandler, final Handler scoreHandler,
            final AttributeSet attributeSet) {
        mSurfaceHolder=surfaceHolder;
        mStatusHandler=statusHandler;
        mScoreHandler=scoreHandler;
        conexto=context;
        run=false;
        runLock=new Object();
        TypedArray a = context.obtainStyledAttributes(attributeSet, R.styleable.PongView);
        int paddleHeight = a.getInt(R.styleable.PongView_paddleHeight, 85);
        int paddleWidth = a.getInt(R.styleable.PongView_paddleWidth, 25);
        int ballRadius = a.getInt(R.styleable.PongView_ballRadius, 15);
        a.recycle();
        Paint humanPlayerPaint = new Paint();
        humanPlayerPaint.setAntiAlias(true);
        humanPlayerPaint.setColor(Color.GREEN);
        jugador = new Jugador(paddleWidth, paddleHeight, humanPlayerPaint);
        Paint computerPlayerPaint = new Paint();
        computerPlayerPaint.setAntiAlias(true);
        computerPlayerPaint.setColor(Color.GREEN);
        cpu = new Jugador(paddleWidth, paddleHeight, computerPlayerPaint);
        Paint ballPaint = new Paint();
        ballPaint.setAntiAlias(true);
        ballPaint.setColor(Color.GREEN);
        bola = new Bola(ballRadius, ballPaint);
        pincelLinea = new Paint();
        pincelLinea.setAntiAlias(true);
        pincelLinea.setColor(Color.GREEN);
        pincelLinea.setAlpha(80);
        pincelLinea.setStyle(Paint.Style.FILL_AND_STROKE);
        pincelLinea.setStrokeWidth(2.0f);
        pincelLinea.setPathEffect(new DashPathEffect(new float[]{5, 5}, 0));
        pincelCanvas = new Paint();
        pincelCanvas.setAntiAlias(true);
        pincelCanvas.setColor(Color.GREEN);
        pincelCanvas.setStyle(Paint.Style.STROKE);
        pincelCanvas.setStrokeWidth(1.0f);
        canvasAlto = 1;
        canvasAncho = 1;
        random = new Random();
        movimientoCPU = 0.6f;
    }

    /* Bucle del Juego */
    
    @Override
    public void run() {
        long siguienteGolpe=SystemClock.uptimeMillis();
        int golpeo=1000/FPS;
        while(run){
            Canvas lienzo=null;
            try {
                lienzo=mSurfaceHolder.lockCanvas(null);
                if(lienzo!=null){
                    synchronized(mSurfaceHolder){
                        if (estado==ESTADO_FUNCIONANDO){
                            recargarPosiciones();
                        }
                        synchronized(runLock){
                            if(run){
                                actualizarVista(lienzo);
                            }
                        }
                    }
                }
            }finally{
                if(lienzo!=null){
                    mSurfaceHolder.unlockCanvasAndPost(lienzo);
                }
            }
            siguienteGolpe=siguienteGolpe+golpeo;
            long descanso=siguienteGolpe-SystemClock.uptimeMillis();
            if(descanso>0){
                try{
                    Thread.sleep(descanso);
                }catch(InterruptedException e){
                }
            }
        }
    }

    void ejecutando(boolean ejecucion){
        synchronized (runLock) {
            run=ejecucion;
        }
    }

    void guardarEstado(Bundle b){
        synchronized (mSurfaceHolder){
            b.putFloatArray(JUGADOR, new float[]{jugador.paleta.left,
            		jugador.paleta.top, jugador.marcador});
            b.putFloatArray(CPU, new float[]{cpu.paleta.left,
                    cpu.paleta.top, cpu.marcador});
            b.putFloatArray(BOLA, new float[]{bola.cx, bola.cy, bola.dx, bola.dy});
            b.putInt(ESTADO, estado);
        }
    }

    void restaurarEstado(Bundle b) {
        synchronized (mSurfaceHolder) {
            float[] datosJugador=b.getFloatArray(JUGADOR);
            jugador.marcador=(int)datosJugador[2];
            moverJugador(jugador, datosJugador[0], datosJugador[1]);
            float[] datosCPU=b.getFloatArray(CPU);
            cpu.marcador=(int)datosCPU[2];
            moverJugador(cpu, datosCPU[0], datosCPU[1]);
            float[] ballData = b.getFloatArray(BOLA);
            bola.cx = ballData[0];
            bola.cy = ballData[1];
            bola.dx = ballData[2];
            bola.dy = ballData[3];
            int estado = b.getInt(ESTADO);
            setEstado(estado);
        }
    }

    void setEstado(int mode) {
        synchronized (mSurfaceHolder) {
            estado=mode;
            Resources res=conexto.getResources();
            switch (estado) {
                case ESTADO_LISTO:
                    nuevaRonda();
                    break;
                case ESTADO_FUNCIONANDO:
                    ocultarEstado();
                    break;
                case ESTADO_VICTORIA:
                    mostrarEstado(res.getString(R.string.modo_victoria));
                    jugador.marcador++;
                    nuevaRonda();
                    break;
                case ESTADO_DERROTA:
                    mostrarEstado(res.getString(R.string.modo_derrota));
                    cpu.marcador++;
                    nuevaRonda();
                    break;
                case ESTADO_PAUSA:
                    mostrarEstado(res.getString(R.string.modo_pausa));
                    break;
            }
        }
    }

    void pausar() {
        synchronized (mSurfaceHolder) {
            if (estado == ESTADO_FUNCIONANDO) {
                setEstado(ESTADO_PAUSA);
            }
        }
    }

    void continuar() {
        synchronized (mSurfaceHolder) {
            setEstado(ESTADO_FUNCIONANDO);
        }
    }

    /* Resetear Marcador e Iniciar Juego Nuevo */
    
    void nuevoJuego() {
        synchronized (mSurfaceHolder) {
            jugador.marcador = 0;
            cpu.marcador = 0;
            nuevaRonda();
            setEstado(ESTADO_FUNCIONANDO);
        }
    }

    /* Devuelve Verdadero si estas en estado victoria, derrota o pausa */
    
    boolean entreRondas() {
        return estado!=ESTADO_FUNCIONANDO;
    }

    boolean tocandoPaletaJugador(MotionEvent event) {
        return jugador.paleta.contains(event.getX(), event.getY());
    }

    void moverPaletaJugador(float dy) {
        synchronized (mSurfaceHolder) {
            moverJugador(jugador,
                       jugador.paleta.left,
                       jugador.paleta.top + dy);
        }
    }

    void setSurfaceTamaño(int width, int height) {
        synchronized (mSurfaceHolder) {
            canvasAncho = width;
            canvasAlto = height;
            nuevaRonda();
        }
    }

    /* Recargar paleta y posiciones de las paletas
     * Comprobar las colisiones, victoria y derrota
     */
    
    private void recargarPosiciones(){
        if (jugador.colision>0){
            jugador.colision--;
        }
        if (cpu.colision>0){
            cpu.colision--;
        }
        if (colision(jugador, bola)) {
            capturarColision(jugador, bola);
            jugador.colision=FRAMES_COLISION;
        }else if(colision(cpu, bola)) {
            capturarColision(cpu, bola);
            cpu.colision=FRAMES_COLISION;
        }else if(colisionPelotaParedAltoAncho()){
            bola.dy = -bola.dy;
        }else if(colisionPelotaParedIzquierda()){
            setEstado(ESTADO_VICTORIA);
            return;
        }else if(colisionPelotaParedDerecha()){
            setEstado(ESTADO_DERROTA);
            return;
        }
        if(random.nextFloat()<movimientoCPU){
            IA();
        }
        moverPelota();
    }

    private void moverPelota(){
        bola.cx=bola.cx+bola.dx;
        bola.cy=bola.cy+bola.dy;
        if (bola.cy<bola.radio){
            bola.cy=bola.radio;
        } else if (bola.cy+bola.radio>=canvasAlto){
            bola.cy=canvasAlto-bola.radio-1;
        }
    }

    /* Mover la paleta de la cpu para golpear la pelota */
    
    private void IA(){
        if(cpu.paleta.top>bola.cy){
            //Mover Arriba
            moverJugador(cpu, cpu.paleta.left, cpu.paleta.top-VELOCIDAD_PALETA);
        } else if(cpu.paleta.top+cpu.paletaAlto<bola.cy){
            //Mover Abajo
            moverJugador(cpu, cpu.paleta.left, cpu.paleta.top+VELOCIDAD_PALETA);
        }
    }

    private boolean colisionPelotaParedDerecha(){
        return bola.cx<=bola.radio;
    }

    private boolean colisionPelotaParedIzquierda(){
        return bola.cx+bola.radio>=canvasAncho-1;
    }

    private boolean colisionPelotaParedAltoAncho(){
        return bola.cy<=bola.radio||bola.cy+bola.radio>=canvasAlto-1;
    }

    /* Dibujar el marcador, las paletas y la pelota */
    
    private void actualizarVista(Canvas lienzo){
        lienzo.drawColor(Color.BLACK);
        lienzo.drawRect(0, 0, canvasAncho, canvasAlto, pincelCanvas);
        final int middle=canvasAncho/2;
        lienzo.drawLine(middle, 1, middle, canvasAlto - 1, pincelLinea);
        textoMarcador(jugador.marcador+"    "+cpu.marcador);
        golpeo(jugador);
        golpeo(cpu);
        lienzo.drawRoundRect(jugador.paleta, 5, 5, jugador.pincel);
        lienzo.drawRoundRect(cpu.paleta, 5, 5, cpu.pincel);
        lienzo.drawCircle(bola.cx, bola.cy, bola.radio, bola.pincel);
    }

    private void golpeo(Jugador player){
        if(player.colision>0) {
            player.pincel.setShadowLayer(player.paletaAncho/2, 0, 0, player.pincel.getColor());
        }else{
            player.pincel.setShadowLayer(0, 0, 0, 0);
        }
    }

    /* Resetear paletas y bola para una nueva ronda */
    
    private void nuevaRonda(){
        bola.cx=canvasAncho/2;
        bola.cy=canvasAlto/2;
        bola.dx=-VELOCIDAD_PELOTA;
        bola.dy=0;
        moverJugador(jugador, 2, (canvasAlto - jugador.paletaAlto)/2);
        moverJugador(cpu, canvasAncho-cpu.paletaAncho-2, (canvasAlto-cpu.paletaAlto)/2);
    }

    private void mostrarEstado(String text){
        Message mnsj = mStatusHandler.obtainMessage();
        Bundle b = new Bundle();
        b.putString("text", text);
        b.putInt("vis", View.VISIBLE);
        mnsj.setData(b);
        mStatusHandler.sendMessage(mnsj);
    }

    private void ocultarEstado(){
        Message mnsj = mStatusHandler.obtainMessage();
        Bundle b = new Bundle();
        b.putInt("vis", View.INVISIBLE);
        mnsj.setData(b);
        mStatusHandler.sendMessage(mnsj);
    }

    private void textoMarcador(String text){
        Message mnsj=mScoreHandler.obtainMessage();
        Bundle b=new Bundle();
        b.putString("text", text);
        mnsj.setData(b);
        mScoreHandler.sendMessage(mnsj);
    }

    private void moverJugador(Jugador player, float izq, float alto){
        if(izq<2){
            izq=2;
        }else if(izq+player.paletaAncho>=canvasAncho-2){
            izq=canvasAncho-player.paletaAncho-2;
        }
        if(alto<0){
            alto=0;
        }else if(alto+player.paletaAlto>=canvasAlto){
            alto=canvasAlto-player.paletaAlto - 1;
        }
        player.paleta.offsetTo(izq, alto);
    }

    private boolean colision(Jugador player, Bola ball){
        return player.paleta.intersects(ball.cx-bola.radio, ball.cy-bola.radio,
        		ball.cx+bola.radio, ball.cy+bola.radio);
    }

    /* Direccion de la pelota tras chocar con la paleta */
    
    private void capturarColision(Jugador player, Bola ball){
        float interseccionY=player.paleta.top+player.paletaAlto/2-ball.cy;
        float interseccionY2=interseccionY/(player.paletaAlto/2);
        double angulo=interseccionY2*ANGULO;

        ball.dx=(float)(-Math.signum(ball.dx)*VELOCIDAD_PELOTA*Math.cos(angulo));
        ball.dy=(float)(VELOCIDAD_PELOTA*-Math.sin(angulo));
        if(player==jugador){
            bola.cx=jugador.paleta.right+bola.radio;
        }else{
            bola.cx=cpu.paleta.left-bola.radio;
        }
    }

}
