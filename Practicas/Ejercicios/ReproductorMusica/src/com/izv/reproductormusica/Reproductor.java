package com.izv.reproductormusica;

	import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Reproductor extends Activity {
	
	private TextView tvCanciones;
	private ListView lvCanciones;
	private TextView tvNombre;
	private MediaPlayer mp;
	private int cont=0;
	private Cursor listado;
	private Adaptador adaptador;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reproductor);
		mp=new MediaPlayer();
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mp.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mp.setOnCompletionListener(new finCancion());
        new OnAudioFocusChangeListener() {
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                	mp.setVolume(1.0f, 1.0f);
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                	mp.setVolume(0.1f, 0.1f);
                }
            }
        };
        tvCanciones=(TextView) findViewById(R.id.tvCanciones);
        lvCanciones=(ListView) findViewById(R.id.lvCanciones);
        tvNombre=(TextView) findViewById(R.id.tvNombre);
        listado = getCanciones();
        adaptador=new Adaptador(this, listado);
        lvCanciones.setAdapter(adaptador);
        canciones();
        lvCanciones.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
				try{
					mp.reset();
				}catch(Exception e){
				}
				listado.moveToPosition(pos);
				String uri=listado.getString(1);
				tvNombre.setText(tituloCancion(uri));
				reproducir(uri);
			}	
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.reproductor, menu);
		return true;
	}
	
	 public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
 		 	case R.id.action_grabar:
 		 		mp.reset();
 		 		Intent i = new Intent(this, Grabador.class);
 		 		startActivity(i);
 		 		break;
		 }
		 return super.onOptionsItemSelected(item);
	 }
	 
	 @Override
	 public void onResume(){
		 super.onResume();
		 new Intent(Intent.ACTION_MEDIA_MOUNTED, 
	                Uri.parse("file://" + Environment.getExternalStorageDirectory()));   
	     IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_SCANNER_STARTED);
	     intentFilter.addDataScheme("file");     
	     sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, 
	                Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		 listado=getCanciones();
	     adaptador.notifyDataSetChanged();
	     canciones();
	 }
	 
	 @Override
	 public void onDestroy(){
		 super.onDestroy();
		 if(mp.isPlaying()){
			 mp.stop();
		 }
	 }
	    
	 @Override
	 public void onBackPressed() {
		 super.onBackPressed();
		 if(mp.isPlaying()){
			 mp.stop();
		 }
	 }
	 
	 private class finCancion implements OnCompletionListener{
	    	@Override
	    	public void onCompletion(MediaPlayer mp) {
	    		try{
	        		listado.moveToPosition(listado.getPosition()+1);
	        		mp.reset();
	        		String uri=listado.getString(1);
	        		tvNombre.setText(tituloCancion(uri));
	    			reproducir(uri);
	        	}catch(Exception e){
	        		listado.moveToFirst();
	        		mp.reset();
	        		String uri=listado.getString(1);
	        		tvNombre.setText(tituloCancion(uri));
	    			reproducir(uri);
	        	}
	    	}
	    }
	    
	 private class Reproduccion implements OnPreparedListener {
		 @Override
		 public void onPrepared(MediaPlayer mediaplayer) {
			 mp.start();
		 }
	 }
	 
	 public void reproducir(String url){
		 try{
			 mp.setOnPreparedListener(new Reproduccion());
			 mp.setDataSource(url);
			 mp.prepareAsync();
		 }catch(Exception e){
		 }	
	 }
	 
	 public void play(View v){
		 try{
			 if(cont==1){
				 mp.start();
				 cont--;
			 }
		 }catch(Exception e){
		 }
	 }
	 
	 public void pausar(View v){
		 try{
			 if(cont==0){
				 cont++;
				 mp.pause();
			 }
		 }catch(Exception e){
		 }
	 }
	 
	 public void avanzar(View v){
		 try{
			 listado.moveToPosition(listado.getPosition()+1);
			 mp.reset();
			 String uri=listado.getString(1);
			 tvNombre.setText(tituloCancion(uri));
			 reproducir(uri);
		 }catch(Exception e){
			 listado.moveToFirst();
			 mp.reset();
			 String uri=listado.getString(1);
			 tvNombre.setText(tituloCancion(uri));
			 reproducir(uri);
	    }
	 }
	 
	 public void retroceder(View v){
		 try{
			 listado.moveToPosition(listado.getPosition()-1);
			 mp.reset();
			 String uri=listado.getString(1);
			 tvNombre.setText(tituloCancion(uri));
			 reproducir(uri);
		 }catch(Exception e){
			 listado.moveToLast();
			 mp.reset();
			 String uri=listado.getString(1);
			 tvNombre.setText(tituloCancion(uri));
			 reproducir(uri);
	    }
	 }
	 
	 public void parar(View v){
		 try{
			 mp.reset();
		 }catch(Exception e){
		 }
	 }
	 
	 public String tituloCancion(String uri){
		 return uri.substring(uri.lastIndexOf("/")+1, uri.length());
	 }
	 
	 public void canciones(){
		 if(listado.getCount()==0){
			 tvCanciones.setText("No hay canciones en tu dispositivo");
		 }else{
			 tvCanciones.setText("Listado de canciones:");
		 }
	 }
	 
	 public Cursor getCanciones(){
		 Cursor c=getBaseContext().getContentResolver().query(
				 android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, 
				 MediaStore.Audio.Media.IS_MUSIC+"!=0", null, MediaStore.Audio.AudioColumns.TITLE);
		 return c;
	 }

}
