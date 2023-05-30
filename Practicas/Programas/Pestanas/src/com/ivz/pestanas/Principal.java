package com.ivz.pestanas;

	import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.Menu;

public class Principal extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		ActionBar ab = getActionBar();
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ab.show();
		Tab pes1 = ab.newTab().setText("Fragmento 1");
		Fragment f1 = new Fragmento_Uno ();
		pes1.setTabListener(new Oyente(f1));
		ab.addTab(pes1);
		Tab pes2 = ab.newTab().setText("Fragmento 2");
		Fragment f2 = new Fragmento_Dos ();
		pes2.setTabListener(new Oyente(f2));
		ab.addTab(pes2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	private class Oyente implements TabListener {
		
		private Fragment f;
		
		public Oyente(Fragment f) {
			super();
			this.f = f;
		}
		
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
		
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
		ft.replace(R.id.loContenedor, f);
		}
		
		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		ft.remove(f);
		}
		
	}

}