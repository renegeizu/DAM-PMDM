package com.ivz.pestanas;

	import android.app.Fragment;
	import android.os.Bundle;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;

public class Fragmento_Uno extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle estado) { 
		View v = inflater.inflate(R.layout.fragmento_uno, contenedor, false);
		return v;
	}
}