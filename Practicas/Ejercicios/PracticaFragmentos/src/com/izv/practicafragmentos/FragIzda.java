package com.izv.practicafragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragIzda extends Fragment{
	@Override
	public View onCreateView(LayoutInflater i, ViewGroup vg, Bundle b){
		View v=i.inflate(R.layout.fragmento_izquierda, vg, false);
		return v;
	}
}