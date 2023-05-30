package com.izv.camarafotos;

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
		//Se lee el archivo XML de los Ajustes
		leerAjustes();
		//Se escribe el archivo XML si no existia con opciones predeterminadas
		escribirAjustes();
		ImageButton bt=(ImageButton) findViewById(R.id.imageButton1);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Se inicia el Intent para lanzar la camara
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//Si en los ajustes no tiene un nombre escrito (en blanco es el predeterminado)
				//Se genera un nombre con la fecha y la hora en que se toma la fotografia
				if(datosAjustes.get(0).toString().equals("")){
					if(datosAjustes.get(1).toString().equals("Publica")){
						//Se guarda en la memoria externa publica
						File archivo = new File(Environment.getExternalStorageDirectory()
								+generarNombre());
						direccion=archivo.toString();
					}else{
						//Se guarda en la memoria externa privada
						File archivo = new File(getExternalFilesDir(null)+generarNombre());
						direccion=archivo.toString();
					}
				}else{
					//Si en los ajustes tiene un nombre escrito
					//La fotografia se genera  con dicho nombre
					if(datosAjustes.get(1).toString().equals("Publica")){
						//Se guarda en la memoria externa publica
						File archivo = new File(Environment.getExternalStorageDirectory()
								+"/"+datosAjustes.get(0)+".jpg");
						nombreValidoPublica(archivo);
					}else{
						//Se guarda en la memoria externa privada
						File archivo = new File(getExternalFilesDir(null)
								+"/"+datosAjustes.get(0)+".jpg");
						nombreValidoPrivada(archivo);
					}
				}
				//Se le pasa la intent la ruta y el nombre del archivo
				//Se lanza la camara
				File archivoFinal=new File(direccion);
				Uri fileUri = Uri.fromFile(archivoFinal);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
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
				//Al pulsar en el menu ajustes, se lanza la activity 
				//Que permite modificarlos
				Intent iAjustes = new Intent(this, Ajustes.class);
				startActivityForResult(iAjustes, AJUSTES);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		//Si se ha lanzado la activity de ajustes
		//Se recogen los valores y se guardan en el archivo XML de ajustes
		//Y en el array que contiene dichos datos
		if (resultCode == Activity.RESULT_OK && requestCode == AJUSTES) {
			String nombre=data.getStringExtra("Nombre");
			String ruta=data.getStringExtra("Ruta");
			datosAjustes.clear();
			datosAjustes.add(nombre);
			datosAjustes.add(ruta);
			escribirAjustes();
		}
		//Si se ha lanzado la activity para capturar la imagen
		//Devuelve un mensaje si se ha tomado correctamente
		if (resultCode == Activity.RESULT_OK && requestCode == CAMARA) {			
			tostada("Foto Obtenida y Guardada");
		}
		//Si se pulsa el boton de retroceso o cancelar
		//Devuelve un mensaje que informa de lo ocurrido
		if(resultCode == Activity.RESULT_CANCELED){
			tostada("Operacion Cancelada");
		}
	}

	private void escribirAjustes(){
		//Con este metodo se escriben los valores en el XML
		try {
			if(datosAjustes.isEmpty()){
				//Si tras leer del XML, el Array esta vacio
				//Se rellena con valores por defecto y se cargan en el Arrray
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
				//Si tras leer, el Array, tiene contenido
				//Se guardan los datos del Array en el XML
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
		//Genera un nombre para la imagen
		//A partir de la fecha y hora actuales
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
		//Lee desde el archivo XML los ajustes y los escribe
		//En el Array para que esten disponibles
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
			//Si no hay archivo XML, saca el mensaje
			Log.v("Lectura", "Fallo de Lectura. No Existe o no Funciona");
		}
	}

	public void nombreValidoPrivada(File archivo){
		//Comprueba que el nombre que se le da a la imagen
		//No existe. Si existe le añade "_1" o variaciones
		//El nombre se concatena con la ruta de la memoria externa privada
		int cont=1;
		if(archivo.exists()){
			while(archivo.exists()){
				archivo=new File(getExternalFilesDir(null)+"/"+datosAjustes.get(0)+"_"+cont+".jpg");
				cont++;
			}
		}
		direccion=archivo.toString();
	}

	public void nombreValidoPublica(File archivo){
		//Comprueba que el nombre que se le da a la imagen
		//No existe. Si existe le añade "_1" o variaciones
		//El nombre se concatena con la ruta de la memoria externa publica
		int cont=1;
		if(archivo.exists()){
			while(archivo.exists()){
				archivo=new File(Environment.getExternalStorageDirectory()
						+"/"+datosAjustes.get(0)+"_"+cont+".jpg");
				
				cont++;
			}
		}
		direccion=archivo.toString();
	}

	public void tostada(String s){
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	}

}