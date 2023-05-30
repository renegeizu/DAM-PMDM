package com.rngds.pong;

	import android.graphics.Paint;

public class Bola {

    float cx;
    float cy;
    float dx;
    float dy;
    int radio;
    Paint pincel;

    Bola(int radio, Paint pincel) {
        this.radio=radio;
        this.pincel=pincel;
    }

}