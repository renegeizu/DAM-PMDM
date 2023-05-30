package com.izv.photocamera;

	import java.io.File;
	import java.io.FileInputStream;
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
	import android.util.Xml;
	import android.view.Menu;
	import android.view.MenuItem;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.Toast;

public class Principal extends Activity {
	
	public static ArrayList<String> datosAjustes=new ArrayList<String>();
	private static int AJUSTES=1, CAMERA=2;
	String direccion="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		leerDeAjustes();
		escribirEnAjustes();
		Button bt=(Button) findViewById(R.id.btFoto);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				if(datosAjustes.get(0).toString().equals(""))
					{if(datosAjustes.get(1).toString().equals("Publica"))
						{File archivo = new File(Environment.getExternalStorageDirectory()
								+generarNombre());
						direccion=archivo.toString();
					}else
						{File archivo = new File(getExternalFilesDir(null)
								+generarNombre());
						direccion=archivo.toString();}
				}else
					{if(datosAjustes.get(1).toString().equals("Publica")){
						File archivo = new File(Environment.getExternalStorageDirectory()
								+"/"+datosAjustes.get(0)+".jpg");
						nombreValido(archivo, "Publica");
					}else
						{File archivo = new File(getExternalFilesDir(null)
								+"/"+datosAjustes.get(0)+".jpg");
						nombreValido(archivo,"Privada");}
				}
				File archivoFinal=new File(direccion);
				Uri fileUri = Uri.fromFile(archivoFinal);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
				startActivityForResult(intent, CAMERA);}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.MenuAjustes:
				Intent i = new Intent(this, Ajustes.class);
				startActivityForResult(i, AJUSTES);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void leerDeAjustes(){
		XmlPullParser lectorxml = Xml.newPullParser();
		try {
			lectorxml.setInput(new FileInputStream(new File(getExternalFilesDir(null),"ajustes.xml")),"utf-8");
			int evento = lectorxml.getEventType();
			while (evento != XmlPullParser.END_DOCUMENT)
				{if(evento == XmlPullParser.START_TAG)
					{String etiqueta = lectorxml.getName();
					if(etiqueta.compareTo("Ajustes")==0)
						{String nombre=lectorxml.getAttributeValue(null, "Nombre");
						datosAjustes.add(nombre);
						String ruta=lectorxml.getAttributeValue(null, "Ruta");
						datosAjustes.add(ruta);}
				}
				evento = lectorxml.next();}
		} catch (Exception e) {
		}
	}
	
	private void escribirEnAjustes(){
		try {
			if(datosAjustes.isEmpty())
				{FileOutputStream fosxml = new FileOutputStream(new File(getFilesDir(),"ajustes.xml"));
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
			}else
				{FileOutputStream fosxml = new FileOutputStream(new File(getFilesDir(),"ajustes.xml"));
				XmlSerializer docxml = Xml.newSerializer();
				docxml.setOutput(fosxml, "UTF-8");
				docxml.startDocument(null, Boolean.valueOf(true));
				docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
				docxml.startTag(null, "Propiedades");
				docxml.startTag(null, "Ajustes");
				docxml.attribute(null, "Nombre", datosAjustes.get(0));
				docxml.attribute(null, "Ruta", datosAjustes.get(1));
				docxml.endTag(null, "Ajustes");
				docxml.endDocument();
				docxml.flush();
				fosxml.close();}
		} catch (Exception e) {
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode == Activity.RESULT_OK && requestCode == AJUSTES)
			{String nombre=data.getStringExtra("Nombre");
			String ruta=data.getStringExtra("Ruta");
			datosAjustes.clear();
			datosAjustes.add(nombre);
			datosAjustes.add(ruta);
			escribirEnAjustes();}
		if (resultCode == Activity.RESULT_OK && requestCode == CAMERA) 
			{tostada("Foto Guardada con Exito");}
		if(resultCode == Activity.RESULT_CANCELED)
			{tostada("Cambios en Ajustes Cancelados");}
	}
	
	public String generarNombre(){
		String nombreGenerado="";
		Calendar c = Calendar.getInstance();
		String agno = c.get(Calendar.YEAR)+"";
		String mes = c.get(Calendar.MONTH)+1+"";
		String dia = c.get(Calendar.DAY_OF_MONTH)+"";
		String hora = c.get(Calendar.HOUR_OF_DAY)+"";
		String minuto = c.get(Calendar.MINUTE)+"";
		String segundo = c.get(Calendar.SECOND)+"";
		if(Integer.parseInt(mes)<10)
			{mes="0"+mes;}
		if(Integer.parseInt(dia)<10)
			{dia="0"+dia;}
		if(Integer.parseInt(hora)<10)
			{hora="0"+hora;}
		if(Integer.parseInt(minuto)<10)
			{minuto="0"+minuto;}
		if(Integer.parseInt(segundo)<10)
			{segundo="0"+segundo;}
		nombreGenerado="/IMG_"+agno+mes+dia+"_"+hora+minuto+segundo+".jpg";
		return nombreGenerado;
	}
	
	public void tostada(String texto){
		Toast.makeText(this, texto, Toast.LENGTH_LONG).show();
	}
	
	public void nombreValido(File archivo, String memoria){
		int cont=1;
		if(memoria.equals("Publica"))
			{if(archivo.exists())
				{while(archivo.exists())
					{archivo=new File(Environment.getExternalStorageDirectory()
							+"/"+datosAjustes.get(0)+"_"+cont+".jpg");
					cont=cont+1;}
			}
		}else
			{if(memoria.equals("Privada"))
				{if(archivo.exists())
					{while(archivo.exists())
						{archivo=new File(getExternalFilesDir(null)
								+"/"+datosAjustes.get(0)+"_"+cont+".jpg");
						cont=cont+1;}
				}
			}
		}
		direccion=archivo.toString();
	}
}