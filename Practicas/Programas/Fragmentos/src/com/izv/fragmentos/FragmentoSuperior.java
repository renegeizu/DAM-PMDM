package com.izv.fragmentos;

	import android.app.Fragment;
	import android.os.Bundle;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.widget.TextView;

public class FragmentoSuperior extends Fragment {
	
	View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragmento_superior, container, false);
		return view;
	}
	
	public void superior (View v){
		TextView tv=(TextView) view.findViewById(R.id.tvSuperior);
		tv.setText("Desde el Fragmento");
	}

}