package com.izv.acelerometro;

	import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Vista extends View{
	
	private int ancho, alto;
	float x,y;
	private Paint pincel, pincel2;

	public Vista(Context context) {
		super(context);
		setBackgroundColor(Color.WHITE);
		pincel=new Paint();
		pincel.setColor(Color.BLUE);
		pincel.setAntiAlias(true);
		pincel.setStyle(Paint.Style.FILL_AND_STROKE);
		pincel.setStrokeWidth(5);
		pincel2=new Paint();
		pincel2.setColor(Color.BLACK);
		pincel2.setAntiAlias(true);
		pincel2.setStyle(Paint.Style.STROKE);
		pincel2.setStrokeWidth(2);
	}
	
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh){
		ancho=w;
		alto=h;
		x=ancho/2;
		y=alto/2;
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	@Override
	public void onDraw(Canvas lienzo){
		lienzo.drawCircle(ancho/2, alto/2, 25, pincel2);
		lienzo.drawCircle(x, y, 20, pincel);
		super.onDraw(lienzo);
	}
	
	public void setXY(float valorX, float valorY){
		x+=(valorX*-2);
		y+=(valorY*-2);
		if(this.x<0){
			this.x=0;
		}
		if(this.x>ancho){
			this.x=ancho;
		}
		if(this.y<0){
			this.y=0;
		}
		if(this.y>ancho){
			this.y=alto;
		}
		invalidate();
	}

}