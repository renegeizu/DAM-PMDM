package com.izv.actividades2;

	import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Terciaria extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terciaria);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.terciaria, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.Personaje_2:
				Toast.makeText(getApplicationContext(), R.string.PersonajeA_2, Toast.LENGTH_SHORT).show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void Seleccion(View v){
		Button b=(Button) v;
		String seleccionado=b.getText().toString();
		Intent i=new Intent();
		Bundle bundle=new Bundle();
		bundle.putString("luchador_2", seleccionado);
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
