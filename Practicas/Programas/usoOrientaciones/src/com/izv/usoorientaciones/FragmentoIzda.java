package com.izv.usoorientaciones;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentoIzda extends Fragment{
	@Override
	public View onCreateView(LayoutInflater i, ViewGroup vg, Bundle b){
		View v=i.inflate(R.layout.cntfragizda, vg, false);
		return v;
	}
}
