package com.izv.practicafragmentos;

	import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.izv.practicafragmentos.db.GestionAnime;
import com.izv.practicafragmentos.pojo.Anime;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.app.Dialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class Principal extends Activity {
	
	protected FragDer fDer;
	protected GestionAnime ga;
	protected Adaptador ac;
	protected ArrayList<Anime> lista=new ArrayList<Anime>();
	protected ListView lvAnimes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		ga=new GestionAnime(this);
		ga.open();
		Cursor c=ga.getCursor();
		c.moveToFirst();
		while(c.isAfterLast()==false){
			lista.add(new Anime(c.getString(1), c.getString(2),c.getString(3)));
			c.moveToNext();
		}
		fDer= (FragDer)this.getFragmentManager().findFragmentById(R.id.fragDer);
		lvAnimes=(ListView)this.findViewById(R.id.lista);
		ac=new Adaptador(this, lista);
		lvAnimes.setAdapter(ac);
		lvAnimes.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> av, View vista, int pos, long id) {
				Anime serie=(Anime) av.getItemAtPosition(pos);
				String nombre=serie.getNombre();
				String genero=serie.getGenero();
				String descripcion=serie.getDescripcion();
				if (fDer != null && fDer.isInLayout()) {
					fDer.setNombre(nombre);
					fDer.setGenero(genero);
					fDer.setDescripcion(descripcion);
				}else{
					Intent i = new Intent(Principal.this, Secundaria.class);
					i.putExtra("Anime", serie);
					startActivity(i);
				}
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
			case R.id.action_agregar:
				final Dialog d=new Dialog(this);
				d.setContentView(R.layout.detalle_agregar);
				d.setTitle("Insertar Nuevo Anime");
				final EditText etNombre=(EditText) d.findViewById(R.id.etAgregarNombre);
				final EditText etGenero=(EditText) d.findViewById(R.id.etAgregarGenero);
				final EditText etDescripcion=(EditText) d.findViewById(R.id.etAgregarDescripcion);
				Button bt=(Button)d.findViewById(R.id.btAgregar);
				bt.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Anime serie=new Anime(etNombre.getText().toString(),
								etGenero.getText().toString(), etDescripcion.getText().toString());
						ga.insert(serie);
						Cursor c=ga.getCursor();
						c.moveToLast();
						while(c.isAfterLast()==false){
							lista.add(new Anime(c.getString(1), c.getString(2),c.getString(3)));
							c.moveToNext();
						}
						ac.notifyDataSetChanged();
						d.dismiss();
					}
				});
				d.show();
				return true;
			case R.id.action_borrar:
				final Dialog df=new Dialog(this);
				df.setContentView(R.layout.detalle_dialogo);
				df.setTitle("Borrar Anime");
				final EditText etNom=(EditText) df.findViewById(R.id.etNombre);
				Button btBorrar=(Button)df.findViewById(R.id.btAceptar);
				btBorrar.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						ga.delete(etNom.getText().toString());
						lista.clear();
						Cursor c=ga.getCursor();
						c.moveToLast();
						while(c.isAfterLast()==false){
							lista.add(new Anime(c.getString(1), c.getString(2),c.getString(3)));
							c.moveToNext();
						}
						ac.notifyDataSetChanged();
						df.dismiss();
					}
				});
				df.show();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
