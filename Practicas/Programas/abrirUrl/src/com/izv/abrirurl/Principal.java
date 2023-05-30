package com.izv.abrirurl;

	import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		Button bt=(Button) findViewById(R.id.bt);
		bt.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				EditText et=(EditText) findViewById(R.id.et);
				String url=et.getText().toString();
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

}
