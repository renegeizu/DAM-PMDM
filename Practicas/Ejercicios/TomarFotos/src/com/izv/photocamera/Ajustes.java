package com.izv.photocamera;

	import android.os.Bundle;
	import android.app.Activity;
	import android.content.Intent;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.EditText;
	import android.widget.RadioButton;
	import android.widget.RadioGroup;

public class Ajustes extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajustes);
		colocarDatos();
		Button btGuardar=(Button) findViewById(R.id.btGuardar);
		btGuardar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				guardarDatos();
			}
		});
		Button btCancelar=(Button) findViewById(R.id.btCancelar);
		btCancelar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelar();
			}
		});
	}
	
	public void colocarDatos(){
		RadioButton rb1=(RadioButton) findViewById(R.id.rbExterna);
		RadioButton rb2=(RadioButton) findViewById(R.id.rbInterna);
		RadioGroup rg=(RadioGroup) findViewById(R.id.radioGroup1);
		rg.clearCheck();
		if(Principal.datosAjustes.get(1).toString().equals("Publica")){
			rb1.setChecked(true);
		}else{
			rb2.setChecked(true);
		}
		EditText et=(EditText) findViewById(R.id.etNombre);
		et.setText(Principal.datosAjustes.get(0).toString());
	}
	
	public void guardarDatos(){
		RadioButton rb1=(RadioButton) findViewById(R.id.rbExterna);
		String ruta="";
		if(rb1.isChecked()){
			ruta="Publica";
		}else{
			ruta="Privada";
		}
		EditText et=(EditText) findViewById(R.id.etNombre);
		Intent i = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("Nombre", et.getText().toString()); 
		bundle.putString("Ruta", ruta);
		i.putExtras(bundle);
		setResult(Activity.RESULT_OK, i);
		finish();
	}
	
	public void cancelar(){
		Intent i=new Intent();
		setResult(Activity.RESULT_CANCELED, i);
		finish();	
	}

}