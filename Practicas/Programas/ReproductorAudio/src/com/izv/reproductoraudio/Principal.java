package com.izv.reproductoraudio;

	import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		ImageButton btRep=(ImageButton) findViewById(R.id.imageButton1);
		btRep.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String url = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC)
						+"/cancion.mp3";
				MediaPlayer mp=new MediaPlayer();
				mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
				try{
					mp.setDataSource(url);
					mp.prepare();
					mp.start();
				}catch(Exception e){
					Log.v("Reproductor-Start", "Error");
				}			
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

}
