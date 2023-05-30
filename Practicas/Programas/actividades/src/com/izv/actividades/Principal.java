package com.izv.actividades;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	public void cambiar(View v){
		EditText et=(EditText) findViewById(R.id.et);
		String valor=et.getText().toString();
		Intent i = new Intent(this,Secundaria.class);
		Bundle b = new Bundle();
		b.putString("NombreSaludo", valor);
		i.putExtras(b);
		startActivity(i);
	}

}
