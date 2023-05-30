package com.izv.fragmentos;

	import android.app.Fragment;
	import android.os.Bundle;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.widget.Button;
	import android.widget.TextView;

public class FragmentoInferior extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragmento_inferior, container, false);
		Button bt = (Button) view.findViewById(R.id.btInferior);
		bt.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				TextView tv=(TextView) view.findViewById(R.id.tvInferior);
				tv.setText("Estamos en el inferior");
			}
		});
		return view;
	}

}