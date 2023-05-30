package com.izv.practicafragmentos;

	import java.util.List;
	import com.izv.practicafragmentos.pojo.Anime;
	import android.content.Context;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.widget.ArrayAdapter;
	import android.widget.TextView;

public class Adaptador extends ArrayAdapter<Anime>{
	
	private List<Anime> lista;
	private static LayoutInflater i;

	public Adaptador(Context context, List<Anime> objects) {
		super(context, R.layout.detalle_lista, objects);
		lista=objects;
		i=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getView(int pos, View v, ViewGroup g){
		if(v==null){
			v=i.inflate(R.layout.detalle_lista, null);
		}
		Anime serie=(Anime)lista.get(pos);
		TextView tv=(TextView) v.findViewById(R.id.tv);
		tv.setText(serie.getNombre());
		return v;
	}

}