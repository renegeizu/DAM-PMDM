package com.izv.numerosaleatorios;

import java.util.Random;
import java.util.Vector;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
//import android.view.View.OnClickListener;
//import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	//private Button btGenerar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*btGenerar=(Button) findViewById(R.id.bt_Generar);
		btGenerar.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				generar(arg0);
			}
		});*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void generar(View v){
		EditText et1,et2,et3;
		TextView tv1;
		
		et1=(EditText)findViewById(R.id.et_Primero);
		et2=(EditText)findViewById(R.id.et_Ultimo);
		et3=(EditText)findViewById(R.id.et_Cantidad);
		tv1=(TextView)findViewById(R.id.tv_Numeros);
		
		String t1="",t2="",t3="";
		
		t1=et1.getText().toString();
		t2=et2.getText().toString();
		t3=et3.getText().toString();
		
		int i1=0,i2=0,i3=0;
		
		try{
			i1=Integer.parseInt(t1);
			i2=Integer.parseInt(t2);
			i3=Integer.parseInt(t3);
		}catch(NumberFormatException error){
			//tv1.setText(getString(R.string.txt_error));
			tv1.setText(R.string.txt_error);
		}
		
		if(i1>i2 || i3>i2-i1){
			tv1.setText("Error en los datos introducidos");
		}else{
			Vector <Integer> vec=new Vector <Integer> (i3);
			Random r=new Random();
			
		}
	}

}
