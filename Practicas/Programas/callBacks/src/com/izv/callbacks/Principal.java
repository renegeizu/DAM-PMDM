package com.izv.callbacks;

	import android.os.Bundle;
	import android.app.Activity;
	import android.content.Intent;
	import android.util.Log;
	import android.view.Menu;
	import android.view.View;

public class Principal extends Activity {
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.v("Layout 1", "onStart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v("Layout 1", "onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.v("Layout 1", "onPause");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.v("Layout 1", "onStop");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v("Layout 1", "onDestroy");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		Log.v("Layout 1", "onCreate");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	public void Change(View v){
		Intent i = new Intent(this,Secundario.class);
		startActivity(i);
	}

}