package com.izv.actividades;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class Secundaria extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secundaria);
		TextView tv=(TextView) findViewById(R.id.tv);
		Bundle b = getIntent().getExtras();
		tv.setText("Hola "+b.getString("NombreSaludo"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.secundaria, menu);
		return true;
	}

	public void volver(View v){
		finish();
	}
	
}
