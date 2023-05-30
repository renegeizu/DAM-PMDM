package com.izv.usoorientaciones;

	import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Principal extends Activity {

	protected FragmentoDcha fd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		fd = (FragmentoDcha)this.getFragmentManager().findFragmentById(R.id.fragDcha);
		ListView lvContactos=(ListView)this.findViewById(R.id.lvAgenda);
		Adaptador ac=new Adaptador(this, listaContactos());
		lvContactos.setAdapter(ac);
		lvContactos.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View vista, int pos, long id) {
				//Si fd es distinto de null --> Esta en LandScape
				//Si fd es null --> Esta en portrait y se lanza la segunda activity
				Contacto contacto1=(Contacto)av.getItemAtPosition(pos);
				String texto=contacto1.getOtros();
				if (fd != null && fd.isInLayout()) {
					fd.setTexto(texto);
				}else{
					Intent i = new Intent(Principal.this, Secundaria.class);
					i.putExtra("contacto", contacto1);
					startActivity(i);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	public List<Contacto> listaContactos(){
		List<Contacto> listaContacto=new ArrayList<Contacto>();
		Contacto c=new Contacto("Paco", "666", "Malo");
		listaContacto.add(c);
		c=new Contacto("JC", "555", "Empanao");
		listaContacto.add(c);
		c=new Contacto("Antonio", "444", "Bueno");
		listaContacto.add(c);
		return listaContacto;
	}

}
