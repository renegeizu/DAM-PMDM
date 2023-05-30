package com.izv.callbacks;

	import android.app.Activity;
	import android.os.Bundle;
	import android.util.Log;
	import android.view.Menu;

public class Secundario extends Activity {
	
	@Override
	protected void onStart() {
		super.onStart();
		Log.v("Layout 2", "onStart");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.v("Layout 2", "onResume");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.v("Layout 2", "onPause");
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Log.v("Layout 2", "onStop");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v("Layout 2", "onDestroy");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secundary);
		Log.v("Layout 2", "onCreate");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

}