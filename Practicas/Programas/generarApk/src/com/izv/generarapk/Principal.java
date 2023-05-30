package com.izv.generarapk;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	//Con Project/Clean forzamos a que genere el archivo R
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_opcion1:
				return true;
			case R.id.action_opcion2:
				return true;
			case R.id.menu_opcion_firmar:
				Toast.makeText(getApplicationContext(), R.string.menu_firma, Toast.LENGTH_SHORT).show();
				//Con el int tostada(R.string.menu_firma;
				//Con el String tostada(getString(R.string.menu_firma));
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void tostada(String s){
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
	
	private void tostada(int s){
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
}
