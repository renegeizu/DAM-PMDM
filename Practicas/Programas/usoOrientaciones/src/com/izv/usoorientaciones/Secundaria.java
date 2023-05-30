package com.izv.usoorientaciones;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class Secundaria extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secundaria);
		Bundle e = getIntent().getExtras();
		Contacto c=(Contacto) e.getSerializable("contacto");
		TextView tv = (TextView) findViewById(R.id.tvContenido);
		tv.setText(c.toString());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.secundaria, menu);
		return true;
	}

}
