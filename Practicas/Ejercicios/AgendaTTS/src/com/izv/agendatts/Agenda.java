package com.izv.agendatts;

	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileOutputStream;
	import java.util.ArrayList;
	import java.util.Collections;
	import java.util.Locale;
	import org.xmlpull.v1.XmlPullParser;
	import org.xmlpull.v1.XmlSerializer;
	import android.os.Bundle;
	import android.app.Activity;
	import android.content.Intent;
	import android.speech.tts.TextToSpeech;
	import android.util.Xml;
	import android.view.Menu;
	import android.view.MenuItem;
	import android.view.View;
	import android.widget.AdapterView;
	import android.widget.AdapterView.OnItemClickListener;
	import android.widget.ListView;
	import android.widget.Switch;
	import android.widget.Toast;

public class Agenda extends Activity implements TextToSpeech.OnInitListener{
	
	private static int GRABAR=1;
	private ListView lvAgenda;
	private ArrayList<String> agenda;
	private Adaptador adaptador;
	private TextToSpeech tts;
	private static int SPEECH=2;
	private Switch switch1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.agenda);
		switch1=(Switch)findViewById(R.id.switch1);
		switch1.setActivated(false);
		agenda=new ArrayList<String>();
		leerXML();
		lvAgenda=(ListView) findViewById(R.id.lvAgenda);
		adaptador=new Adaptador(this, agenda);
		lvAgenda.setAdapter(adaptador);
		lvAgenda.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
				if(switch1.isChecked()==false){
					String texto=agenda.get(pos);
					tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null);
				}else{
					agenda.remove(pos);
					adaptador.notifyDataSetChanged();
					escribirXML();
				}
			}	
		});
		Intent intent = new Intent();
		intent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(intent, SPEECH);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.agenda, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.action_grabar:
			Intent iGrabar=new Intent(this, STT.class);
			startActivityForResult(iGrabar, GRABAR);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		if (resultCode==Activity.RESULT_OK && requestCode==GRABAR){
			String texto=(String) data.getStringExtra("Texto");
			agenda.add(texto);
			Collections.sort(agenda);
			adaptador.notifyDataSetChanged();
			escribirXML();
		}
		if (resultCode==TextToSpeech.Engine.CHECK_VOICE_DATA_PASS && requestCode==SPEECH){
				tts = new TextToSpeech(this, this);
		}else{
				//Intent intent = new Intent();
				//intent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				//startActivity(intent);
		}
		if(resultCode==Activity.RESULT_CANCELED){
			tostada("Operación Cancelada");
		}
	}
	
	public void tostada(String cadena){
		Toast.makeText(getApplicationContext(), cadena, Toast.LENGTH_LONG).show();
	}
	
	private void escribirXML(){
		try {
			FileOutputStream fosxml=new FileOutputStream(new File(getExternalFilesDir(null),"agendaTTS.xml"));
			XmlSerializer docxml=Xml.newSerializer();
			docxml.setOutput(fosxml, "UTF-8");
			docxml.startDocument(null, Boolean.valueOf(true));
			docxml.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
			docxml.startTag(null, "Agenda");
			for(String texto: agenda){
				docxml.startTag(null, "Texto");
				docxml.text(texto);
				docxml.endTag(null, "Texto");
			}
			docxml.endDocument();
			docxml.flush();
			fosxml.close();
		} catch (Exception e) {
			tostada("Fallo al Guardar");
		}
	}
	
	private void leerXML(){
		XmlPullParser lectorxml = Xml.newPullParser();
		try{
			lectorxml.setInput(new FileInputStream(new File(getExternalFilesDir(null),"agendaTTS.xml")),"utf-8");
			int evento = lectorxml.getEventType();
			while (evento!=XmlPullParser.END_DOCUMENT){
				if(evento==XmlPullParser.START_TAG){
					String etiqueta=lectorxml.getName();
					if(etiqueta.compareTo("Texto")==0){
							String nombre=lectorxml.nextText();
							agenda.add(nombre);
					}
				}
				evento=lectorxml.next();
			}
		}catch(Exception e) {
			tostada("Fallo al Leer. Si es la primera vez que entras, no tienes Mensajes guardados");
		}
	}

	@Override
	public void onInit(int estado) {
		if (estado==TextToSpeech.SUCCESS) {
			tts.setLanguage(new Locale("es", "ES"));
		}	
	}
	
	@Override
	public void onBackPressed() {
	    if (tts != null) {
	        tts.stop();
	        tts.shutdown();
	    }
	    super.onBackPressed();
	 }
	
	@Override
	public void onDestroy() {
	    if (tts != null) {
	        tts.stop();
	        tts.shutdown();
	    }
	    super.onDestroy();
	 }

}