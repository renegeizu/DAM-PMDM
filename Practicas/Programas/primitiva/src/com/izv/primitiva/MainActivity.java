package com.izv.primitiva;

import java.util.Random;
import java.util.Vector;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bt = (Button) findViewById(R.id.btGenerar);
	    bt.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View vista) {
	    		Random r=new Random();
	    		TextView tv1=(TextView) findViewById(R.id.tvNumero1);
	    		TextView tv2=(TextView) findViewById(R.id.tvNumero2);
	    		TextView tv3=(TextView) findViewById(R.id.tvNumero3);
	    		TextView tv4=(TextView) findViewById(R.id.tvNumero4);
	    		TextView tv5=(TextView) findViewById(R.id.tvNumero5);
	    		TextView tv6=(TextView) findViewById(R.id.tvNumero6);
	    		Vector <Integer> vec=new Vector <Integer> (6);
	    		for(int cont=0;cont<6;cont++){
	    			vec.add(cont,0);
	    		}
	    		for (int c=0;c<6;c++){
	    			int numero=r.nextInt(49)+1;
	    			boolean salida=true;
	    			while(salida==true){
	    				if(numero!=vec.get(0)&&numero!=vec.get(1)&&numero!=vec.get(2)&&
	    				   numero!=vec.get(3)&&numero!=vec.get(4)&&numero!=vec.get(5)){
	    				   salida=false;
	    				   vec.add(c,numero);
	    				}else{
	    				   numero=r.nextInt(49)+1;
	    				   salida=true;
	    				}
	    			}
	    		}
	    		tv1.setText(""+vec.get(0));
	    		tv2.setText(""+vec.get(1));
	    		tv3.setText(""+vec.get(2));
	    		tv4.setText(""+vec.get(3));
	    		tv5.setText(""+vec.get(4));
	    		tv6.setText(""+vec.get(5));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
