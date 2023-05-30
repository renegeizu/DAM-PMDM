package com.rngds.pong;

	import android.graphics.Paint;
	import android.graphics.RectF;

public class Jugador {

    int paletaAncho;
    int paletaAlto;
    Paint pincel;
    int marcador;
    RectF paleta;
    int colision;

    Jugador(int paletaAncho, int paletaAlto, Paint pincel) {
        this.paletaAncho=paletaAncho;
        this.paletaAlto=paletaAlto;
        this.pincel=pincel;
        this.marcador=0;
        this.paleta=new RectF(0, 0, paletaAncho, paletaAlto);
        this.colision=0;
    }

}