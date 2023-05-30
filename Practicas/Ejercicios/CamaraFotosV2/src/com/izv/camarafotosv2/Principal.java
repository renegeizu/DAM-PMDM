package com.izv.camarafotosv2;

	import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class Principal extends Activity {
	
	public static ArrayList<String> datosAjustes=new ArrayList<String>();
	private static int AJUSTES=1, CAMARA=2;
	String direccion="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		leerAjustes();
		escribirAjustes();
		ImageButton bt=(ImageButton) findViewById(R.id.imageButton1);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, CAMARA);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.Ajustes:
				Intent iAjustes = new Intent(this, Ajustes.class);
				startActivityForResult(iAjustes, AJUSTES);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode == Activity.RESULT_OK && requestCode == AJUSTES) {
			String nombre=data.getStringExtra("Nombre");
			String ruta=data.getStringExtra("Ruta");
			datosAjustes.clear();
			datosAjustes.add(nombre);
			datosAjustes.add(ruta);
			escribirAjustes();
		}
		if (resultCode == Activity.RESULT_OK && requestCode == CAMARA) {
			Bitmap foto=(Bitmap)data.getExtras().get("data");
			FileOutputStream salida;
			File carpeta;
			String nombreCarpeta;
			String photo="";
			if(datosAjustes.get(0).equals("")){
				if(datosAjustes.get(1).equals("Publica")){
					carpeta=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
					nombreCarpeta=carpeta.getAbsolutePath();
					photo=generarNombre();
				}else{
					carpeta=getExternalFilesDir(null);
					nombreCarpeta=carpeta.getAbsolutePath();
					photo=generarNombre();
				}
			}else{
				if(datosAjustes.get(1).equals("Publica")){
					carpeta=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
					nombreCarpeta=carpeta.getAbsolutePath();
					photo="/"+datosAjustes.get(0)+".jpg";
				}else{
					carpeta=getExternalFilesDir(null);
					nombreCarpeta=carpeta.getAbsolutePath();
					photo="/"+datosAjustes.get(0)+".jpg";
				}
			}
			
			
			try{
				int contador=1;
				File archivo=new File(carpeta, photo);
				while(archivo.exists()){
					if(datosAjustes.get(1).equals("Publica")){
						photo="/"+datosAjustes.get(0)+"_"+contador+".jpg";
						contador++;
						archivo=new File(carpeta, photo);
					}else{
						photo="/"+datosAjustes.get(0)+"_"+contador+".jpg";
						contador++;
						archivo=new File(carpeta, photo);
					}
				}
				salida=new FileOutputStream(nombreCarpeta+photo);
				foto.compress(Bitmap.CompressFormat.JPEG, 90, salida);
			}catch(FileNotFoundException e){
			}
			tostada("Foto Obtenida y Guardada");
		}
		if(resultCode == Activity.RESULT_CANCELED){
			tostada("Operacion Cancelada");
		}
	}

	private void escribirAjustes(){
		try {
			if(datosAjustes.isEmpty()){
				FileOutputStream fosxml = new FileOutputStream(new File(getFilesDir(),"ajustes.xml"));
				XmlSerializer docxml = Xml.newSerializer();
				docxml.setOutput(fosxml, "UTF-8");
				docxml.startDocument(null, Boolean.valueOf(true));
				docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
				docxml.startTag(null, "DatosAjustes");
				docxml.startTag(null, "Ajustes");
				docxml.attribute(null, "Nombre", "");
				docxml.attribute(null, "Ruta", "Publica");
				docxml.endTag(null, "Ajustes");
				docxml.endDocument();
				docxml.flush();
				fosxml.close();
				datosAjustes.add("");
				datosAjustes.add("Publica");
			}else{
				FileOutputStream fosxml = new FileOutputStream(new File(getFilesDir(),"ajustes.xml"));
				XmlSerializer docxml = Xml.newSerializer();
				docxml.setOutput(fosxml, "UTF-8");
				docxml.startDocument(null, Boolean.valueOf(true));
				docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
				docxml.startTag(null, "DatosAjustes");
				docxml.startTag(null, "Ajustes");
				docxml.attribute(null, "Nombre", datosAjustes.get(0));
				docxml.attribute(null, "Ruta", datosAjustes.get(1));
				docxml.endTag(null, "Ajustes");
				docxml.endDocument();
				docxml.flush();
				fosxml.close();
			}
		} catch (Exception e) {
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
		nombreGenerado="/IMG_"+agno+mes+dia+"_"+hora+minuto+segundo+".jpg";
		return nombreGenerado;
	}

	private void leerAjustes(){
		XmlPullParser lectorxml = Xml.newPullParser();
		try {
			lectorxml.setInput(new FileInputStream(new File(getExternalFilesDir(null),"ajustes.xml")),"utf-8");
			int evento = lectorxml.getEventType();
			while (evento != XmlPullParser.END_DOCUMENT){
				if(evento == XmlPullParser.START_TAG){
					String etiqueta = lectorxml.getName();
					if(etiqueta.compareTo("Ajustes")==0){
							String nombre=lectorxml.getAttributeValue(null, "Nombre");
							String ruta=lectorxml.getAttributeValue(null, "Ruta");
							datosAjustes.add(nombre);
							datosAjustes.add(ruta);
					}
				}
				evento = lectorxml.next();
			}
		} catch (Exception e) {
			Log.v("Lectura", "Fallo de Lectura. No Existe o no Funciona");
		}
	}


	public void tostada(String s){
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	}

}