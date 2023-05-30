package com.izv.geolocalizacion;

	import java.util.List;
	import java.util.Locale;
	import android.location.Address;
	import android.location.Geocoder;
	import android.location.Location;
	import android.location.LocationListener;
	import android.location.LocationManager;
	import android.os.AsyncTask;
	import android.os.Bundle;
	import android.provider.Settings;
	import android.view.View;
	import android.widget.TextView;
	import android.app.Activity;
	import android.content.Context;
	import android.content.Intent;

public class Principal extends Activity {
	
	private LocationManager locationManager;
	private TextView tv;
	private Geocoder geocoder;
	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.principal);
		locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		geocoder = new Geocoder(this, Locale.getDefault());
		tv=(TextView)findViewById(R.id.tvTexto);
		if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)!=true){
			startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		}
		Location ultimaRed = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		if(ultimaRed!=null)
			tv.append("Ultima Red: "+ultimaRed);
		Location ultimaGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if(ultimaGPS!=null)
			tv.append("\nUltima GPS: "+ultimaGPS);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, 
				locationListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, 
				locationListener);
	}
	
	LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onLocationChanged(Location location) {
			float precision=location.getAccuracy();
			double altitud=location.getAltitude();
			double latitud=location.getLatitude();
			double longitud=location.getLongitude();
			String proveedor=location.getProvider();
			tv.append("\nPrecision: "+precision+" Altitud: "+altitud+" Longitud: "
					+longitud+" Provedor: "+proveedor);
			Principal.this.location=location;
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
	};
	
	@Override
	protected void onDestroy(){
		locationManager.removeUpdates(locationListener);
		super.onDestroy();
	}
	
	private class Tarea extends AsyncTask<Void, Void, Void>{

		List<Address> direcciones;
		
		@Override
		protected Void doInBackground(Void... params) {
			try{
				direcciones=geocoder.getFromLocation(location.getLatitude(), 
						location.getLongitude(), 1);
			}catch(Exception e){
			}
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
	
	public void geocoder(View v){
		new Tarea().execute();
	}
	
}