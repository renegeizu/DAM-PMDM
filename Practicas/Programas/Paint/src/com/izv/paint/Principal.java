package com.izv.paint;

	import java.io.FileNotFoundException;

import com.izv.paint.ColorPickerDialog.OnColorChangedListener;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class Principal extends Activity {

	private Vista v;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Segunda Forma
		v=new Vista(this);
		setContentView(R.layout.activity_principal);
		LinearLayout ll=(LinearLayout) findViewById(R.id.llDibujar);
		ll.addView(v);
		//Primera Forma
		//setContentView(new Vista(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.action_cargar:
				v.read();
				return true;
			case R.id.action_guardar:
				try {
					v.save();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				return true;
			case R.id.action_color:
				ColorPickerDialog dialogo;
				dialogo=new ColorPickerDialog(this, new OyenteDialogo(), v.getColor());
				dialogo.show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class OyenteDialogo implements OnColorChangedListener{

		@Override
		public void colorChanged(int color) {
			v.setColor(color);
		}
		
	}

}
