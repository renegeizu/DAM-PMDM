package com.izv.dadoncaras;

import java.util.Random;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bt = (Button) findViewById(R.id.btDado);
	    bt.setOnClickListener(new OnClickListener() {
	    	@Override
			public void onClick(View vista) {
	    		Random r=new Random();
	    		TextView tv=(TextView) findViewById(R.id.tvNumero);
	    		EditText et=(EditText) findViewById(R.id.etNumero);
	    		int caras=Integer.parseInt(et.getText().toString().trim());
	    		if(caras==0 || caras==1){
	    			tv.setText("No existe");
	    		}else{
					int tirada=r.nextInt(caras);
					if(tirada==0){
						tv.setText("1");
					}else{
						tv.setText(""+tirada);
					}
	    		}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
