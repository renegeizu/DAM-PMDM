package com.izv.controles;

	import android.os.Bundle;
	import android.app.Activity;
	import android.content.Intent;
	import android.view.Menu;
	import android.view.MenuItem;
	import android.view.View;
	import android.widget.AdapterView;
	import android.widget.AdapterView.OnItemSelectedListener;
	import android.widget.ArrayAdapter;
	import android.widget.Spinner;
	import android.widget.TextView;

public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		final Spinner sp = (Spinner) findViewById(R.id.spinner1);
		final ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this,
		R.array.array_paises,  android.R.layout.simple_spinner_item);
		//Para obtener el string de values/srings
		//String[] datos=getResources().getStringArray(R.array.nombre);
		adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp.setAdapter(adaptador);
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {
					
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				TextView tv=(TextView) findViewById(R.id.textView1);
				tv.setText("El Personaje Seleccionado "+adaptador.getItem(position).toString());
				//Otra forma
				//tv.setText("El Personaje Seleccionado "+sp.getSelectedItem().toString());
			}
				
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				TextView tv=(TextView) findViewById(R.id.textView1);
				tv.setText("Vacio");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.Secundaria:
				Intent i = new Intent(this, Secundaria.class);
				startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

}
