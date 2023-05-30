package com.izv.videocamara;

import java.io.File;
import java.util.List;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

public class Principal extends Activity {

	public static int ACCION_VIDEO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		final EditText etNombre = (EditText) findViewById(R.id.etNombre);
		final EditText etDuracion = (EditText) findViewById(R.id.etDuracion);
		ImageButton bt = (ImageButton) findViewById(R.id.btGrabar);
		
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				if (intentDisponible(intent)==true)
					{String nombre= etNombre.getText().toString();
					int duracion = Integer.parseInt(etDuracion.getText().toString());
					grabar (nombre,duracion, intent);}
				else
					{tostada();}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

	public boolean intentDisponible(Intent intent) {
		List<ResolveInfo> lista = getPackageManager().queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
		return lista.size() > 0;
	}

	public void grabar(String nombre, int duracion, Intent intent) {
		File archivo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)+ "/" + nombre + ".mp4");
		Uri videoUri = Uri.fromFile(archivo);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
		intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, duracion);
		startActivityForResult(intent, ACCION_VIDEO);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == ACCION_VIDEO) {
			VideoView vv = (VideoView) findViewById(R.id.vvReproductor);
			vv.setVideoURI(data.getData());
			vv.start();
		}
	}
	
	public void tostada (){
		Toast.makeText(this,"Cámara no disponible", Toast.LENGTH_SHORT).show();
	}
}
