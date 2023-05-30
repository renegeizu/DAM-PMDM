package com.izv.radiobutton;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class Principal extends Activity {

	int clave=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		RadioGroup rg=(RadioGroup) findViewById(R.id.radioGroup1);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				TextView tv=(TextView) findViewById(R.id.textView2);
				switch (checkedId) {
					case R.id.radio0:
						tv.setText("Si");
						clave=1;
						break;
					case R.id.radio1:
						tv.setText("No");
						clave=2;
						break;
					case R.id.radio2:
						tv.setText("Quizas");
						clave=3;
						break;
				}
			}
		});
		Button bt1=(Button) findViewById(R.id.button1);
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				TextView tv=(TextView) findViewById(R.id.textView1);
				switch (clave) {
				case 0:
					tv.setText("No hay ningun radiobutton marcado");
					break;
				case 1:
					tv.setText("Has marcado 'Si'");
					break;
				case 2:
					tv.setText("Has marcado 'No'");
					break;
				case 3:
					tv.setText("Has marcado 'Quizas'");
					break;
			}
			}
		});
		Button bt2=(Button) findViewById(R.id.button2);
		bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				RadioGroup rg=(RadioGroup) findViewById(R.id.radioGroup1);
				rg.clearCheck();
				TextView tv=(TextView) findViewById(R.id.textView1);
				tv.setText("");
				TextView tv2=(TextView) findViewById(R.id.textView2);
				tv2.setText("");
				clave=0;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
}
