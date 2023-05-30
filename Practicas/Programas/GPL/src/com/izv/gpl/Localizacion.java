package com.izv.gpl;

	import java.util.Date;
import java.util.List;

import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Localizacion extends Activity implements 
	com.google.android.gms.location.LocationListener,
	com.google.android.gms.location.LocationClient.ConnectionCallbacks,
	com.google.android.gms.location.LocationClient.OnConnectionFailedListener{
	
	private TextView tv;
	private LocationRequest peticionLocalizacion;
	private LocationClient clienteLocalizacion;
	private Location ultima=null;
	private Geocoder geocoder;
	private ObjectContainer bd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_localizacion);
		tv=(TextView) findViewById(R.id.tv);
		bd=Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), getExternalFilesDir(null)
				+"/bd.db4o");
		if(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)==ConnectionResult.SUCCESS){
			tv.append("Google Play: Disponible");
			peticionLocalizacion = LocationRequest.create();
			peticionLocalizacion.setInterval(5000);
			peticionLocalizacion.setFastestInterval(1000);
			peticionLocalizacion.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
			clienteLocalizacion = new LocationClient(this, this, this);
			clienteLocalizacion.connect();
		}else{
			tv.append("Google Play: No Disponible");
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		clienteLocalizacion.requestLocationUpdates(peticionLocalizacion, this);
		ultima=clienteLocalizacion.getLastLocation();
	}

	@Override
	public void onDisconnected() {
		
	}

	@Override
	public void onLocationChanged(Location loc) {
		float precision=loc.getAccuracy();
		double altitud=loc.getAltitude();
		double latitud=loc.getLatitude();
		double longitud=loc.getLongitude();
		String proveedor=loc.getProvider();
		tv.append("\nPrecision: "+precision+", Altitud: "+altitud+", Latitud: "+latitud
				+", Longitud: "+longitud+" Provedor: "+proveedor);
		ultima=loc;
		inserta2(loc);
	}
	
	public void actualizar(View v){
		if(ultima!=null){
			new Tarea().execute();
		}
	}
	
	public void tostada(String s){
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
	
	private class Tarea extends AsyncTask<Void, Void, Void>{

		List<Address> direcciones;
		
		@Override
		protected Void doInBackground(Void... params) {
			try{
				direcciones=geocoder.getFromLocation(ultima.getLatitude(), 
						ultima.getLongitude(), 1);
			}catch(Exception e){}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result){
			if (direcciones.size()>0) {
				Address direccion=direcciones.get(0);
				tv.append("\n"+direccion.getAddressLine(0));
				tv.append("\n"+direccion.getLocality());
				tv.append("\n"+direccion.getCountryName());
			}
			super.onPostExecute(result);
		}
		
	}
	
	private void inserta(Location loc){
		bd.store(loc);
	}
	
	private List<Location> list(){
		return bd.query(Location.class);
	}
	
	public void listar(View v){
		List<Location> lista=list();
		for(Location location : lista){
			tv.append("\nLatitud: "+location.getLatitude()+", Longitud: "+location.getLongitude());
		}
	}
	
	private Location qbe(){
		List<Location> lista=bd.queryByExample(ultima);
		return lista.get(0);
	}
	
	private List<Location> soda(){
		Query consulta=bd.query();
		consulta.constrain(Coordenada.class);
		consulta.descend("precision").constrain("32").smaller();
		return consulta.execute();
	}
	
	private void inserta2(Location loc){
		Paseos paseo=new Paseos(new Date(), loc);
		bd.store(paseo);
	}
	
	private List<Paseos> qbe2(Date fecha){
		return bd.queryByExample(new Paseos(fecha, null));
	}

}