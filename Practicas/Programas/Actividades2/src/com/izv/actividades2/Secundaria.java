package com.izv.actividades2;

	import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Secundaria extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secundaria);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.secundaria, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.Personaje_1:
				Toast.makeText(getApplicationContext(), R.string.PersonajeA_1, Toast.LENGTH_SHORT).show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void Seleccion(View v){
		String seleccionado="";
		int id=v.getId();
		switch(id){
			case R.id.bt1:
				seleccionado=getString(R.string.bt1);
				break;
			case R.id.bt2:
				seleccionado=getString(R.string.bt2);
				break;
			case R.id.bt3:
				seleccionado=getString(R.string.bt3);
				break;
			case R.id.bt4:
				seleccionado=getString(R.string.bt4);
				break;
			case R.id.bt5:
				seleccionado=getString(R.string.bt5);
				break;
			case R.id.bt9:
				seleccionado=getString(R.string.bt9);
				break;
			case R.id.bt10:
				seleccionado=getString(R.string.bt10);
				break;
			case R.id.bt11:
				seleccionado=getString(R.string.bt11);
				break;
			case R.id.bt12:
				seleccionado=getString(R.string.bt12);
				break;
		}
		Intent i=new Intent();
		Bundle bundle=new Bundle();
		bundle.putString("luchador_1", seleccionado);
		i.putExtras(bundle);
		setResult(Activity.RESULT_OK, i);
		finish();
	}
	
	public void Cancelar(View v){
		Intent i=new Intent();
		setResult(Activity.RESULT_CANCELED, i);
		finish();
	}

}
