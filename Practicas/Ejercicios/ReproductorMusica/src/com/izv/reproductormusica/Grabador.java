package com.izv.reproductormusica;

	import java.io.File;
	import java.util.Calendar;
	import android.media.MediaRecorder;
	import android.os.Bundle;
	import android.os.Environment;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.ImageView;
	import android.widget.TextView;
	import android.widget.Toast;
	import android.app.Activity;

public class Grabador extends Activity {
	
	private MediaRecorder grabador;
	private TextView tv;
	private Button btGrabar;
	private Button btDetener;
	private ImageView btImagen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grabador);
		try{
			File archivo = Environment.getExternalStorageDirectory();
			String exstPath = archivo.getPath();
			File carpeta = new File(exstPath+"/Grabaciones");
			carpeta.mkdir();
		}catch(Exception e){
		}
		tv=(TextView) findViewById(R.id.tvGrabacion);
		btImagen=(ImageView) findViewById(R.id.imageView1);
		btGrabar=(Button) findViewById(R.id.btGrabar);
		btGrabar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					tv.setText("Grabacion en Curso");
					btImagen.setImageResource(R.drawable.microfono_activo);
					grabador = new MediaRecorder();
					grabador.setAudioSource(MediaRecorder.AudioSource.MIC);
					grabador.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
					grabador.setOutputFile(Environment.getExternalStorageDirectory().getPath()
							+"/Grabaciones/"+generarNombre());
					grabador.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					grabador.prepare();
					grabador.start();
				}catch(Exception e){
					Toast.makeText(v.getContext(), "No se ha podido iniciar la grabacion", 
							Toast.LENGTH_LONG).show();
				}
			}
		});
		btDetener=(Button)findViewById(R.id.btDetener);
		btDetener.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try{
					grabador.stop();
					grabador.release();
					grabador = null;
					Toast.makeText(v.getContext(), "Grabacion finalizada y almacenada", 
							Toast.LENGTH_LONG).show();
					finish();
				}catch(Exception e){
					Toast.makeText(v.getContext(), "Error en la grabacion", 
							Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	public String generarNombre(){
		Calendar cal = Calendar.getInstance();	
		String hora, minutos, agno, mes, dia;
		if(cal.get(Calendar.HOUR_OF_DAY)<10)
			hora="0"+cal.get(Calendar.HOUR_OF_DAY);
		else
			hora=""+cal.get(Calendar.HOUR_OF_DAY);
		if(cal.get(Calendar.MINUTE)<10)
			minutos="0"+cal.get(Calendar.MINUTE);
		else
			minutos=""+cal.get(Calendar.MINUTE);
		agno=""+cal.get(Calendar.YEAR);
		if(cal.get(Calendar.MONTH)+1<10)
			mes="0"+cal.get(Calendar.MONTH)+1;
		else
			mes=""+cal.get(Calendar.MONTH)+1;
		if(cal.get(Calendar.DAY_OF_MONTH)<10)
			dia="0"+cal.get(Calendar.DAY_OF_MONTH);
		else
			dia=""+cal.get(Calendar.DAY_OF_MONTH);
		return "MIC_"+agno+mes+dia+hora+minutos+".mp4";
	}

}
