package com.izv.controles;

	import java.util.Calendar;
	import android.os.Bundle;
	import android.app.ActionBar;
	import android.app.Activity;
	import android.app.DatePickerDialog;
	import android.app.TimePickerDialog;
	import android.app.DatePickerDialog.OnDateSetListener;
	import android.app.TimePickerDialog.OnTimeSetListener;
	import android.content.Intent;
	import android.view.Menu;
	import android.view.MenuInflater;
	import android.view.MenuItem;
	import android.view.MenuItem.OnMenuItemClickListener;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.DatePicker;
	import android.widget.ImageView;
	import android.widget.PopupMenu;
	import android.widget.TextView;
	import android.widget.TimePicker;
	import android.widget.Toast;

public class Secundaria extends Activity {
	
	//private ActionMode modoAccion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secundaria);
		ActionBar actionBar = getActionBar(); 
		actionBar.setHomeButtonEnabled(true);
		ImageView iv=(ImageView) findViewById(R.id.ivAndroid);
		//this.registerForContextMenu(iv);
		Button btHora=(Button) findViewById(R.id.button1);
		btHora.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Calendar c = Calendar.getInstance();	
				int hora = c.get(Calendar.HOUR_OF_DAY);
				int minuto = c.get(Calendar.MINUTE);
				TimePickerDialog tpd = new TimePickerDialog(Secundaria.this, new ObtenerHora(), hora, minuto, true); 
				tpd.show();
			}
		});
		Button btFecha=(Button) findViewById(R.id.button2);
		btFecha.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Calendar c = Calendar.getInstance();	
				int ano = c.get(Calendar.YEAR);
				int mes = c.get(Calendar.MONTH);//0 a 11
				int dia = c.get(Calendar.DAY_OF_MONTH);
				DatePickerDialog dpd = new DatePickerDialog(Secundaria.this, new ObtenerFecha(), ano, mes, dia);
				dpd.show();
			}
		});
		/*iv.setOnLongClickListener(new View.OnLongClickListener() {
			public boolean onLongClick(View view) {
				if (modoAccion != null) {
					return false;
				}
				modoAccion = Secundaria.this.startActionMode(respuestaMenu);
				view.setSelected(true);
				return true;
			}
		});*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.secundaria, menu);
		return true;
	}
	
	public class ObtenerHora implements OnTimeSetListener {
		@Override
		public void onTimeSet(TimePicker tp, int hour, int minute){
			TextView tv=(TextView) findViewById(R.id.textView1);
			tv.setText(hour+":"+minute);
		}
	}
	
	public class ObtenerFecha implements OnDateSetListener{
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			TextView tv=(TextView) findViewById(R.id.textView2);
			tv.setText(dayOfMonth+"-"+monthOfYear+"-"+year);
		}
	}
	/*
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_contextual,  menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		//AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
			case R.id.accion_1:
				tostada("Accion 1");
				return true;
			case R.id.accion_2: 
				tostada("Accion 2");
				return true;
			case R.id.accion_3: 
				tostada("Accion 3");
				return true;
			default: 
				tostada("Por Defecto");
				return super.onContextItemSelected(item);
		}
	}*/
	
	public void tostada(String s){
		Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
	}
	
	/*private ActionMode.Callback respuestaMenu = new ActionMode.Callback() {
		
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.menu_contextual,  menu);
			return true;
		}
		
		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}
		
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
				case R.id.accion_1:
					mode.finish();
					return true;
				default:
					return false;
			}
		}
		
		@Override
		public void onDestroyActionMode(ActionMode mode) {
			modoAccion = null;
		}
	};
	*/
	
	public void popup(View v) {
		PopupMenu popup = new PopupMenu(this, v);
		MenuInflater inflater = popup.getMenuInflater();
		inflater.inflate(R.menu.menu_contextual, popup.getMenu());
		popup.show();
	}
	
	class Listener implements OnMenuItemClickListener{

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			switch (item.getItemId()){
				case R.id.accion_1:
					tostada("Accion 1");
					return true;
			}
			return false;
		}
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				Intent intent = new Intent(this, Principal.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
