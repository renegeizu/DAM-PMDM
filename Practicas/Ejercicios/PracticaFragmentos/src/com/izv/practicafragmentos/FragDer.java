package com.izv.practicafragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragDer extends Fragment{
	@Override
	public View onCreateView(LayoutInflater i, ViewGroup vg, Bundle b){
		View v=i.inflate(R.layout.fragmento_derecha, vg, false);
		return v;
	}
	
	public void setNombre(String s){
		TextView tv=(TextView) getView().findViewById(R.id.tvNombre);
		tv.setText(s);
	}
	
	public void setGenero(String s){
		TextView tv=(TextView) getView().findViewById(R.id.tvGenero);
		tv.setText(s);
	}
	
	public void setDescripcion(String s){
		TextView tv=(TextView) getView().findViewById(R.id.tvDescripcion);
		tv.setText(s);
	}
}