package com.izv.autocomplete;

	import android.os.Bundle;
	import android.app.Activity;
	import android.view.Menu;
	import android.widget.ArrayAdapter;
	import android.widget.AutoCompleteTextView;

public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		AutoCompleteTextView actv = (AutoCompleteTextView)  findViewById(R.id.actvPaises);
		String[] paises = getResources().getStringArray(R.array.array_paises);
		ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,  paises);
		actv.setAdapter(adaptador);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
}
