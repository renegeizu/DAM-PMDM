package com.izv.pasoapasov2;

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
        Button  bt=(Button) findViewById(R.id.btSaludar);
        bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText et=(EditText) findViewById(R.id.etNombre);
				TextView tv=(TextView) findViewById(R.id.tvSaludo);
				String cs=getString(R.string.txt_tvSaludo);
				String cet=et.getText().toString();
				tv.setText(cs+cet);
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
