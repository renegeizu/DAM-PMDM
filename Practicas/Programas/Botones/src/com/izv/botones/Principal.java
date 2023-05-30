package com.izv.botones;

	import android.os.Bundle;
	import android.app.Activity;
	import android.view.Menu;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.EditText;
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
	
	public void Click(View v){
		int id=v.getId();
		TextView tv=(TextView)findViewById(R.id.tv);
		switch(id){
			case R.id.bt1:
				tv.setText(getString(R.string.bt1));
				break;
			case R.id.bt2:
				tv.setText(getString(R.string.bt2));
				break;
			case R.id.bt3:
				tv.setText(getString(R.string.bt3));
				break;
			case R.id.bt4:
				tv.setText(getString(R.string.bt4));
				break;
		}
	}

}
