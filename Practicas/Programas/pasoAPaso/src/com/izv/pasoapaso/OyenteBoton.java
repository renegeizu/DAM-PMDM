package com.izv.pasoapaso;

	import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class OyenteBoton implements OnClickListener{

	@Override
	public void onClick(View v) {
		//Con el primero, identificas y encuentras el Log
		//Con el segundo, aparece al pulsar el botón
		Log.v("REGISTRO", "Botón Pulsado");
	}

}
