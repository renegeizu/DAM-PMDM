package com.izv.agendatts;

	import java.util.ArrayList;
	import android.os.Bundle;
	import android.speech.RecognizerIntent;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.TextView;
	import android.widget.Toast;
	import android.app.Activity;
	import android.content.Intent;

public class STT extends Activity {
	
	private static int GRABADOR;
	private Button btGrabar;
	private Button btGuardar;
	private TextView tvMensaje;
	private String texto;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tts);
		btGrabar=(Button) findViewById(R.id.btGrabar);
		btGuardar=(Button) findViewById(R.id.btGuardar);
		tvMensaje=(TextView) findViewById(R.id.tvMensaje);
		btGrabar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
					//intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "es-ES");
					intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
					intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Comienza a Grabar");
					intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 3000);
					startActivityForResult(intent, GRABADOR);
				}catch(Exception e){
					tostada("No Posees Grabador");
					finish();
				}
			}
		});
		btGuardar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(tvMensaje.getText().toString().equals("")!=true){
					Intent i = new Intent();
					Bundle bundle = new Bundle();
					bundle.putString("Texto", tvMensaje.getText().toString()); 
					i.putExtras(bundle);
					setResult(Activity.RESULT_OK, i);
					finish();
				}
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		ArrayList<String> textos=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
		texto=textos.get(0);
		tvMensaje.setText(texto);
	}
	
	public void tostada(String cadena){
		Toast.makeText(getApplicationContext(), cadena, Toast.LENGTH_LONG).show();
	}

}