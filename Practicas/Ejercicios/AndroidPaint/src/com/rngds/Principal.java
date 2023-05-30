package com.rngds;

	import java.util.Calendar;

import com.rngds.ColorPickerDialog.OnColorChangedListener;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class Principal extends Activity {
	
	private AndroidPaint apaint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		apaint=new AndroidPaint(this);
		setContentView(R.layout.principal);
		LinearLayout lienzo=(LinearLayout) findViewById(R.id.canvas);
		lienzo.addView(apaint);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.action_guardar:
				try {
					final Dialog d=new Dialog(this);
	    			d.setContentView(R.layout.dialogo_guardar);
	    			d.setTitle("Guarda Tu Dibujo");
	    			final EditText et=(EditText) d.findViewById(R.id.etGuardar);
	    			Button bt=(Button)d.findViewById(R.id.btGuardar);
	    			bt.setOnClickListener(new OnClickListener() {
	    				@Override
	    				public void onClick(View v) {
	    					String insertar=et.getText().toString();
	    					if(!insertar.equals("")){
	    						try{
	    							apaint.save(insertar);
	    						}catch(Exception e){}
		    					d.cancel();
	    					}
	    				}
	    			});
	    			d.show();
				} catch (Exception e) {
				}
				return true;
			case R.id.action_color:
				ColorPickerDialog dialogo;
				dialogo=new ColorPickerDialog(this, new OyenteDialogo(), apaint.getColor());
				dialogo.show();
				return true;
			case R.id.action_nuevo:
				apaint.setBorrar();
				return true;
			case R.id.action_borrar:
				apaint.setColor(Color.WHITE);
				return true;
			case R.id.action_compartir:
				String pathofBmp = Images.Media.insertImage(getContentResolver(), 
						AndroidPaint.getBitmap(), generarNombre(), null);
			    Uri bmpUri = Uri.parse(pathofBmp);
			    Intent share = new Intent();
			    share.setAction(Intent.ACTION_SEND);
			    share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    share.putExtra(Intent.EXTRA_STREAM, bmpUri);
			    share.setType("image/png");
			    startActivity(share); 
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class OyenteDialogo implements OnColorChangedListener{

		@Override
		public void colorChanged(int color) {
			apaint.setColor(color);
		}
		
	}
	
	public String generarNombre(){
		String nombreGenerado="";
		Calendar c = Calendar.getInstance();
		String agno = c.get(Calendar.YEAR)+"";
		String mes = c.get(Calendar.MONTH)+1+"";
		if(Integer.parseInt(mes)<10){
			mes="0"+mes;
		}
		String dia = c.get(Calendar.DAY_OF_MONTH)+"";
		if(Integer.parseInt(dia)<10){
			dia="0"+dia;
		}
		String hora = c.get(Calendar.HOUR_OF_DAY)+"";
		if(Integer.parseInt(hora)<10){
			hora="0"+hora;
		}
		String minuto = c.get(Calendar.MINUTE)+"";
		if(Integer.parseInt(minuto)<10){
			minuto="0"+minuto;
		}
		String segundo = c.get(Calendar.SECOND)+"";
		if(Integer.parseInt(segundo)<10){
			segundo="0"+segundo;
		}
		nombreGenerado="/IMG_"+agno+mes+dia+"_"+hora+minuto+segundo+".png";
		return nombreGenerado;
	}

}