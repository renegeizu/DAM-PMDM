package com.izv.usoorientaciones;

	import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Adaptador extends ArrayAdapter<Contacto>{
	
	private List lista;
	private static LayoutInflater i;

	public Adaptador(Context context, List<Contacto> objects) {
		super(context, R.layout.cntfragdcha, objects);
		lista=objects;
		i=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(int pos, View v, ViewGroup g){
		if(v==null){
			v=i.inflate(R.layout.cntfragdcha, null);
		}
		Contacto contacto=(Contacto)lista.get(pos);
		TextView tvDcha=(TextView) v.findViewById(R.id.tvContenido);
		tvDcha.setText(contacto.getNombre()+"\n"+contacto.getTelefono());
		return v;
	}
	
}
