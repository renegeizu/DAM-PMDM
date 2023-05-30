package com.izv.pasoapaso;

	import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Principal extends Activity {
	
	//Cuando se va a usar en distintos métodos
	//private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
		        /*
		         * Forma 1 (No se usa) Corresponde al OyenteBoton.java
			        //Instancia de la clase OyenteBoton
			        OyenteBoton ob=new OyenteBoton();
			        //Se define dentro del método porque sólo se usará aquí
			        //Se relaciona el botón con el escuchador
			        //Se usa Cast para Convertir a Boton
			        Button bt = (Button) findViewById(R.id.bt_Saludo);
			        //Otra forma (Genérica)
			        //View v= findViewById(R.id.bt_Saludo);     
			        bt.setOnClickListener(ob);
			    *
		        */
        Button bt = (Button) findViewById(R.id.bt_Saludo);
        bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View vista) {
					Log.v("REGISTRONUEVO", "Botón Pulsado");
					EditText et=(EditText) findViewById(R.id.etNombre);
					String texto1=getString(R.string.texto_saludo2);
					String texto2=et.getText().toString();
					String texto3=texto1+texto2;
					TextView tv=(TextView) findViewById(R.id.tvSaludo);
					tv.setText(texto3);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }
    
    private String valor;
    
    //Guarda el estado antes de pausarse
    @Override
    protected void onSaveInstanceState(Bundle savingInstanceState) {
    	super.onSaveInstanceState(savingInstanceState);
    	TextView tv=(TextView) findViewById(R.id.tvSaludo);
		savingInstanceState.putString("cadena", tv.getText().toString());
    }
    
    //recupera el estado
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    valor=savedInstanceState.getString("cadena");
	    TextView tv=(TextView) findViewById(R.id.tvSaludo);
		tv.setText(valor);
    }
    
}
