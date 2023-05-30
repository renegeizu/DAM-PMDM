package com.izv.dadoseiscaras;

import java.util.Random;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Principal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        //Para ver el layout con scroll siempre que no exista nada programado para el
        //layout principal
        //setContentView(R.layout.barra_desplazamiento);
        Button bt = (Button) findViewById(R.id.bt_Dado);
        bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View vista) {
					Log.v("REGISTRONUEVO", "Botón Pulsado");
					Random r=new Random();
					TextView tv=(TextView) findViewById(R.id.tvNumero);
					switch(r.nextInt(6)) { 
						case 0 : tv.setText("1"); 
								 break; 
						case 1 : tv.setText("2");
						 		 break;  
						case 2 : tv.setText("3");
						 		 break; 
						case 3 : tv.setText("4"); 
						 		 break; 
						case 4 : tv.setText("5"); 
						 		 break; 
						case 5 : tv.setText("6"); 
						 		 break; 
					}
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }
    
}
