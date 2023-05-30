package com.izv.calculadorav1;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Principal extends Activity {
	
	boolean estado=false;
	int cifras=0;

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
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.Autor:
				Toast.makeText(getApplicationContext(), R.string.action_settings, Toast.LENGTH_SHORT).show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void numeros(View v){
		Button b=(Button) v;
		String seleccionado=b.getText().toString();
		TextView tv=(TextView) findViewById(R.id.tv);
		String existente=tv.getText().toString();
		if(existente.equals("0")==true){
			tv.setText("");
		}
		existente=tv.getText().toString();
		if(existente.length()<=12){
			if(cifras<9){
				tv.setText(existente+seleccionado);
				cifras++;
			}
		}
	}
	
	public void simbolos(View v){
		TextView tv=(TextView) findViewById(R.id.tv);
		String cadenaActual=tv.getText().toString();
		if(cadenaActual.equals("0")!=true){
			if(cadenaActual.length()<=12){
				String ultimo=cadenaActual.substring(cadenaActual.length()-1);
				Button b=(Button) v;
				String seleccionado=b.getText().toString().trim();
				if(ultimo.equals("X")!=true && ultimo.equals("/")!=true && ultimo.equals("+")!=true &&
						ultimo.equals("-")!=true && ultimo.equals("=")!=true && ultimo.equals(".")!=true){
					if(seleccionado.equals(".")==true ){
						if(estado==false){
							tv.setText(cadenaActual+seleccionado);
							estado=true;
							cifras=0;
						}
					}else{
						tv.setText(cadenaActual+seleccionado);
						estado=false;
						cifras=0;
					}
				}
			}
		}
	}

	public void limpiar(View v){
		TextView tv=(TextView) findViewById(R.id.tv);
		String cadenaActual=tv.getText().toString();
		if(cadenaActual.equals("")!=true){
			tv.setText(cadenaActual.substring(0, cadenaActual.length()-1));
		}
		cadenaActual=tv.getText().toString();
		if(cadenaActual.equals("")==true){
			tv.setText(0+"");
		}
		
	}

	private String valor;
	
    @Override
    protected void onSaveInstanceState(Bundle savingInstanceState) {
    	TextView tv=(TextView) findViewById(R.id.tv);
		savingInstanceState.putString("Operacion", tv.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    valor=savedInstanceState.getString("Operacion").toString();
	    TextView tv=(TextView) findViewById(R.id.tv);
		tv.setText(valor);
    }

}
