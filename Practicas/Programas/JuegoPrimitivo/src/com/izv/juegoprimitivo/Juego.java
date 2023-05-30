package com.izv.juegoprimitivo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Juego extends Activity {
	
	private VistaJuego vj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		vj=new VistaJuego(this);
		setContentView(vj);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.juego, menu);
		return true;
	}

}