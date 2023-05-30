package com.izv.actividades2;

	import android.os.Bundle;
	import android.app.Activity;
	import android.content.Intent;
	import android.view.Menu;
	import android.view.MenuItem;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.TextView;
	import android.widget.Toast;

public class Principal extends Activity {
	
	public static final int LUCHADOR1=0, LUCHADOR2=1; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		asignarEventos();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.Batalla:
				Toast.makeText(getApplicationContext(), R.string.BatallaA, Toast.LENGTH_SHORT).show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int ctePeticion, int cteResultado, Intent datos){
		if(ctePeticion==LUCHADOR1){
			if(cteResultado==Activity.RESULT_OK){
				TextView tv=(TextView) findViewById(R.id.tv2);
				String luchador1=datos.getStringExtra("luchador_1");
				tv.setText("Luchador 1: "+luchador1);
				mostrarResultado();
			}else if(cteResultado==Activity.RESULT_CANCELED){
				tostada("Operación Cancelada");
			}
		}else if(ctePeticion==LUCHADOR2){
			if(cteResultado==Activity.RESULT_OK){
				TextView tv=(TextView) findViewById(R.id.tv3);
				String luchador2=datos.getStringExtra("luchador_2");
				tv.setText("Luchador 2: "+luchador2);
				mostrarResultado();
			}else if(cteResultado==Activity.RESULT_CANCELED){
				tostada("Operación Cancelada");
			}
		}
	}
	
	private void mostrarResultado(){
		TextView tv1=(TextView) findViewById(R.id.tv2);
		TextView tv2=(TextView) findViewById(R.id.tv3);
		TextView tv3=(TextView) findViewById(R.id.tv4);
		String a, b;
		a=tv1.getText().toString();
		int aux=a.lastIndexOf(":")+2;
		b=tv2.getText().toString();
		int aux2=b.lastIndexOf(":")+2;
		if(a.length()>0 && b.compareTo("")!=0){
			tv3.setText("Batalla: "+a.substring(aux, a.length())+" Vs "+b.substring(aux2, b.length()));
		}
	}
	
	private void tostada(String s){
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
	}

	private void asignarEventos(){
		Button btL1, btL2;
		btL1=(Button) findViewById(R.id.btL1);
		btL2=(Button) findViewById(R.id.btL2);
		btL1.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				Intent intencion= new Intent(getApplicationContext(), Secundaria.class);
				startActivityForResult(intencion, LUCHADOR1);
			}
		});
		btL2.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v){
				Intent intencion= new Intent(getApplicationContext(), Terciaria.class);
				startActivityForResult(intencion, LUCHADOR2);
			}
		});
	}
	
}