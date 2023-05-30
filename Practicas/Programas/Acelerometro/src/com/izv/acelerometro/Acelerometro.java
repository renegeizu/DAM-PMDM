package com.izv.acelerometro;

	import java.util.List;
import java.util.Locale;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Acelerometro extends Activity implements SensorEventListener{
	
	private TextView tvXmin, tvXmax, tvXactual, 
			tvYmin, tvYmax, tvYactual, tvZmin, tvZmax, tvZactual;
	private CheckBox cbX, cbY, cbZ;
	private SensorManager gestorSensores;
	private Sensor sensorAceleracion;
	private float xmax=0, ymax=0, zmax=0;
	private float xmin=0, ymin=0, zmin=0;
	private float lastx=0, lasty=0, lastz=0;
	private Vista juego;
	private LinearLayout ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acelerometro);
		gestorSensores=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
		sensorAceleracion=gestorSensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		tvXmin=(TextView) findViewById(R.id.tvXmin);
		tvXmax=(TextView) findViewById(R.id.tvXmax);
		tvXactual=(TextView) findViewById(R.id.tvXactual); 
		tvYmin=(TextView) findViewById(R.id.tvYmin);
		tvYmax=(TextView) findViewById(R.id.tvYmax);
		tvYactual=(TextView) findViewById(R.id.tvYactual); 
		tvZmin=(TextView) findViewById(R.id.tvZmin); 
		tvZmax=(TextView) findViewById(R.id.tvZmax); 
		tvZactual=(TextView) findViewById(R.id.tvZactual);
		cbX=(CheckBox) findViewById(R.id.cbX);
		cbY=(CheckBox) findViewById(R.id.cbY);
		cbZ=(CheckBox) findViewById(R.id.cbZ);
		List<Sensor> sensores=gestorSensores.getSensorList(Sensor.TYPE_ALL);
		String todo="";
		for(int i=0; i<sensores.size(); i++){
			todo+=sensores.get(i).getName()+" "+sensores.get(i).getVendor()+"\n";
		}
		Toast.makeText(this, todo, Toast.LENGTH_LONG).show();
		juego=new Vista(this);
		ll=(LinearLayout) findViewById(R.id.lienzo);
		ll.addView(juego);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.acelerometro, menu);
		return true;
	}
	
	public void start(View v){
		gestorSensores.registerListener(this, sensorAceleracion, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void stop(View v){
		gestorSensores.unregisterListener(this);
	}
	
	public void reset(View v){
		tvXactual.setText("00.00");
		tvXmax.setText("00.00");
		tvXmin.setText("00.00");
		tvYactual.setText("00.00");
		tvYmax.setText("00.00");
		tvYmin.setText("00.00");
		tvZactual.setText("00.00");
		tvZmax.setText("00.00");
		tvZmin.setText("00.00");
		xmax=ymax=zmax=xmin=ymin=zmin=lastx=lasty=lastz=0;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
				
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if(cbX.isChecked() && cbY.isChecked()){
			juego.setXY(-event.values[0], event.values[1]);
		}else if(cbX.isChecked()){
			juego.setXY(-event.values[0], 0);
		}else if(cbY.isChecked()){
			juego.setXY(0, event.values[1]);
		}
		if(cbX.isChecked()){
			if(Math.abs(event.values[0]-lastx)>1){
				lastx=event.values[0];
				tvXactual.setText(getNumeroDecimal(lastx,2)+"");
			}
			if(event.values[0]>xmax){
				xmax=event.values[0];
				tvXmax.setText(getNumeroDecimal(xmax,2)+"");
			}
			if(event.values[0]<xmin){
				xmin=event.values[0];
				tvXmin.setText(getNumeroDecimal(xmin,2)+"");
			}
		}
		if(cbY.isChecked()){
			if(Math.abs(event.values[1]-lasty)>1){
				lasty=event.values[1];
				tvYactual.setText(getNumeroDecimal(lasty,2)+"");
			}
			if(event.values[1]>ymax){
				ymax=event.values[1];
				tvYmax.setText(getNumeroDecimal(ymax,2)+"");
			}
			if(event.values[1]<ymin){
				ymin=event.values[1];
				tvYmin.setText(getNumeroDecimal(ymin,2)+"");
			}
		}
		if(cbZ.isChecked()){
			if(Math.abs(event.values[2]-lastz)>1){
				lastz=event.values[2];
				tvXactual.setText(getNumeroDecimal(lastz,2)+"");
			}
			if(event.values[2]>zmax){
				zmax=event.values[2];
				tvZmax.setText(getNumeroDecimal(zmax,2)+"");
			}
			if(event.values[2]<zmin){
				zmin=event.values[2];
				tvZmin.setText(getNumeroDecimal(zmin,2)+"");
			}
		}
	}
	
	private static String getNumeroDecimal(float f, int decimales){
		return String.format(Locale.getDefault(), "%."+decimales+"f", f);
	}

}
