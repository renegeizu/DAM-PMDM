package com.izv.usoorientaciones;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentoDcha extends Fragment{
	@Override
	public View onCreateView(LayoutInflater i, ViewGroup vg, Bundle b){
		View v=i.inflate(R.layout.cntfragdcha, vg, false);
		return v;
	}
	
	public void setTexto(String s){
		TextView tv=(TextView) getView().findViewById(R.id.tvContenido);
		tv.setText(s);
	}
}