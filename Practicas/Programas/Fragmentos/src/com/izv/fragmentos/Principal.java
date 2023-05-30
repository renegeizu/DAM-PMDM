package com.izv.fragmentos;

	import android.os.Bundle;
	import android.app.Activity;
	import android.view.Menu;
	import android.view.View;
	import android.widget.TextView;

public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	public void superior(View v){
		TextView tv=(TextView)findViewById(R.id.tvSuperior);
		tv.setText("Desde la Actividad");
	}

}
