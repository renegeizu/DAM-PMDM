package com.izv.practicafragmentos;

import com.izv.practicafragmentos.pojo.Anime;

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
		Anime serie=(Anime) e.getSerializable("Anime");
		TextView tvNombre = (TextView) findViewById(R.id.tvNombre);
		TextView tvGenero = (TextView) findViewById(R.id.tvGenero);
		TextView tvDescripcion = (TextView) findViewById(R.id.tvDescripcion);
		tvNombre.setText(serie.getNombre());
		tvGenero.setText(serie.getGenero());
		tvDescripcion.setText(serie.getDescripcion());
	}

}
