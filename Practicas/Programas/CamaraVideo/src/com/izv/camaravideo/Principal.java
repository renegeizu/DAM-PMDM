package com.izv.camaravideo;

import java.io.File;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
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
	
	public static int ACCION_VIDEO=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		final EditText nombre=(EditText)findViewById(R.id.editText1);
		final EditText duracion=(EditText)findViewById(R.id.editText2);
		ImageButton bt=(ImageButton)findViewById(R.id.imageButton1);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int d=1;
				String n="nombre";
				try{
					n=nombre.getText().toString();
					d=Integer.parseInt(duracion.getText().toString());
				}catch(Exception e){
					tostada("Valores No Válidos Sustituidos Por Valores Por Defecto");
				}
				tomarVideo(n, d);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == ACCION_VIDEO) {
			VideoView vv = (VideoView) findViewById(R.id.videoView1);
			vv.setVisibility(View.VISIBLE);
			vv.setVideoURI(data.getData());
			vv.start();
		}
	}
	
	public boolean intentDisponible(Intent intent){
		List<ResolveInfo> lista=getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return lista.size()>0;
	}
	
	public void tomarVideo(String nombre, int duracion){
		File archivo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
				+"/"+nombre+".mp4");
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		if(intentDisponible(intent)){
			Uri videoUri = Uri.fromFile(archivo);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
			intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, duracion);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			startActivityForResult(intent, ACCION_VIDEO);
		}else{
			tostada("La Cámara No Está Disponible");
		}
	}
	
	public void tostada(String s){
		Toast.makeText(this, s, Toast.LENGTH_LONG).show();
	}
	
}
