package com.ivz.pestanas;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragmento_Dos extends Fragment{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup contenedor, Bundle estado) { 
		View v = inflater.inflate(R.layout.fragmento_dos, contenedor, false);
		return v;
	}
}