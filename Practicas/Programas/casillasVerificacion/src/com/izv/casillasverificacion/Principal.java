package com.izv.casillasverificacion;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Principal extends Activity {

	CheckBox cb;
	Switch sw;
	ToggleButton tb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		cb=(CheckBox) findViewById(R.id.cb);
		sw=(Switch) findViewById(R.id.s);
		tb=(ToggleButton) findViewById(R.id.tb);
		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			public void onCheckedChanged( CompoundButton buttonView,boolean isChecked) {
				if(cb.isChecked()){
					TextView tv=(TextView) findViewById(R.id.tv1);
					tv.setText(R.string.Activado);
				}else{
					TextView tv=(TextView) findViewById(R.id.tv1);
					tv.setText(R.string.Desactivado);
				}
			}
		});
		sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			public void onCheckedChanged( CompoundButton buttonView,boolean isChecked) {
				if(cb.isChecked()){
					TextView tv=(TextView) findViewById(R.id.tv3);
					tv.setText(R.string.Activado);
				}else{
					TextView tv=(TextView) findViewById(R.id.tv3);
					tv.setText(R.string.Desactivado);
				}
			}
		});
		tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			public void onCheckedChanged( CompoundButton buttonView,boolean isChecked) {
				if(cb.isChecked()){
					TextView tv=(TextView) findViewById(R.id.tv5);
					tv.setText(R.string.Activado);
				}else{
					TextView tv=(TextView) findViewById(R.id.tv5);
					tv.setText(R.string.Desactivado);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	public void btCheck(View v){
		cb.toggle();
	}
	
	public void btSwitch(View v){
		if(sw.isChecked()){
			sw.setChecked(false);
		}else{
			sw.setChecked(true);
		}
	}
	
	public void btToggle(View v){
		tb.toggle();
	}
	
	public void clickCheck(View v){
		if(cb.isChecked()){
			TextView tv=(TextView) findViewById(R.id.tv2);
			tv.setText(R.string.Activado);
		}else{
			TextView tv=(TextView) findViewById(R.id.tv2);
			tv.setText(R.string.Desactivado);
		}
	}
	
	public void clickSwitch(View v){
		if(sw.isChecked()){
			TextView tv=(TextView) findViewById(R.id.tv4);
			tv.setText(R.string.Activado);
		}else{
			TextView tv=(TextView) findViewById(R.id.tv4);
			tv.setText(R.string.Desactivado);
		}
	}
	
	public void clickToggle(View v){
		if(tb.isChecked()){
			TextView tv=(TextView) findViewById(R.id.tv6);
			tv.setText(R.string.Activado);
		}else{
			TextView tv=(TextView) findViewById(R.id.tv6);
			tv.setText(R.string.Desactivado);
		}
	}

}
