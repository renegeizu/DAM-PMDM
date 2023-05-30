package com.izv.texttospeech;

	import java.util.Locale;
	import android.os.Bundle;
	import android.app.Activity;
	import android.speech.tts.TextToSpeech;
	import android.util.Log;
	import android.view.Menu;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.EditText;
	import android.widget.RadioButton;
	import android.widget.Toast;

public class Principal extends Activity implements TextToSpeech.OnInitListener{
	
	private TextToSpeech tts;
	private String texto="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		Button bt=(Button)findViewById(R.id.button1);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				tts = new TextToSpeech(Principal.this, Principal.this);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

	@Override
	public void onInit(int estado) {
		RadioButton rb1=(RadioButton)findViewById(R.id.radio0);
		RadioButton rb2=(RadioButton)findViewById(R.id.radio1);
		RadioButton rb3=(RadioButton)findViewById(R.id.radio2);
		int resultado=0;
		if (estado==TextToSpeech.SUCCESS){
			if(rb1.isChecked()){
				resultado=tts.setLanguage(new Locale("es", "ES"));
			}else if(rb2.isChecked()){
				resultado=tts.setLanguage(new Locale("en", "EN"));
			}else if(rb3.isChecked()){
				resultado=tts.setLanguage(new Locale("ja", "JA"));
			}
	        if(resultado==TextToSpeech.LANG_MISSING_DATA || resultado==TextToSpeech.LANG_NOT_SUPPORTED) {
	        	Toast.makeText(this, "Fallo Lenguaje", Toast.LENGTH_LONG).show();
	        }else{
	            lecturaTexto();
	        }
	    } else {
	        Toast.makeText(this, "Fallo Reproduccion", Toast.LENGTH_LONG).show();
	    }
	}
	
	public void lecturaTexto(){
		EditText et=(EditText)findViewById(R.id.editText1);
		texto=et.getText().toString();
		tts.speak(texto, TextToSpeech.QUEUE_FLUSH,null);
		if(tts.isSpeaking()){
			Log.v("Estado", "Funcionando");
		}else{
			Log.v("Estado", "No Funciona");
		}
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