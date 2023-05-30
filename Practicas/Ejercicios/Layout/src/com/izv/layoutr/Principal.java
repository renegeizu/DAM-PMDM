package com.izv.layoutr;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.Layout_1:
				setContentView(R.layout.activity_principal);
				return true;
			case R.id.Layout_2:
				setContentView(R.layout.activity_secundary);
				return true;
			case R.id.Autor:
				Toast.makeText(getApplicationContext(), R.string.Autor, Toast.LENGTH_SHORT).show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
