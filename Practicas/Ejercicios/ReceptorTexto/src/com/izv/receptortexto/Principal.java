package com.izv.receptortexto;

	import java.io.BufferedReader;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.InputStreamReader;
	import android.net.Uri;
	import android.os.Bundle;
	import android.view.Menu;
	import android.view.MenuItem;
	import android.widget.TextView;
	import android.widget.Toast;
	import android.app.Activity;

public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		Uri data = getIntent().getData();
		String datos="";
		if(data!=null){
			//Tengo que seleccionar la subcadena pues empieza con un
			// file:// que no sirve. Al retirarlo nos queda la ruta.
			datos=data.toString();
			datos=datos.substring(7, datos.length());
		}
		//Toast.makeText(this, datos, Toast.LENGTH_LONG).show();
		TextView tv2=(TextView) findViewById(R.id.textView2);
		try{
			if(!datos.isEmpty()){
				tv2.setText(datos);
			}
			File file = new File(datos);
		    FileInputStream fIn = new FileInputStream(file);
		    InputStreamReader archivo = new InputStreamReader(fIn);
		    BufferedReader br = new BufferedReader(archivo);
		    String linea = br.readLine();
		    String todo = "";
		    while (linea != null) {
		    	todo = todo + linea + " ";
		        linea = br.readLine();
		    }
		    br.close();
		    archivo.close();
		    TextView tv=(TextView) findViewById(R.id.textView1);
		    tv.setText(todo);
		}catch(Exception e){}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.Autor:
				Toast.makeText(getApplicationContext(), "Rafael Bailón Robles 2ºDAM", Toast.LENGTH_SHORT).show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
}