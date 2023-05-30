package com.izv.agendatts;

	import java.util.ArrayList;
	import android.content.Context;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.widget.ArrayAdapter;
	import android.widget.TextView;

public class Adaptador extends ArrayAdapter<String>{
	
	ArrayList<String> agenda;
	Context contexto;

	public Adaptador(Context contexto, ArrayList<String> agenda){
		super(contexto, R.layout.detalle, agenda);
		this.contexto=contexto;
		this.agenda=agenda;
	}
	
	@Override
	public View getView(int posicion, View vista, ViewGroup padre){
		if(vista==null){
			LayoutInflater i=(LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			vista=i.inflate(R.layout.detalle, null);
		}
		TextView tv=(TextView) vista.findViewById(R.id.tvTexto);
		tv.setText(agenda.get(posicion).toString());
		return vista;
	}

}