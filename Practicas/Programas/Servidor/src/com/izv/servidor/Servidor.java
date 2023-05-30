package com.izv.servidor;

	import java.io.DataInputStream;
	import java.io.DataOutputStream;
	import java.io.IOException;
	import java.net.ServerSocket;
	import java.net.Socket;
	import android.os.Bundle;
	import android.app.Activity;
	import android.view.Menu;
	import android.view.View;
	import android.view.View.OnClickListener;
	import android.widget.Button;
	import android.widget.TextView;

public class Servidor extends Activity {
	
	private TextView tv;
	private Button bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servidor);
		tv=(TextView) findViewById(R.id.textView1);
		bt=(Button) findViewById(R.id.button1);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try{
					ServerSocket servicio = new ServerSocket(6666);
					Socket servidor = servicio.accept();
					Hebra h=new Hebra(servidor);
					h.start();
				}catch(Exception e){}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.servidor, menu);
		return true;
	}
	
	public class Hebra extends Thread {
		
		private Socket s;
		DataInputStream flujoE;
		DataOutputStream flujoS;
		
		public Hebra(Socket s) throws IOException {
			this.s = s;
			flujoE = new DataInputStream(s.getInputStream());
			flujoS = new DataOutputStream(s.getOutputStream());
			flujoS.writeUTF("Hola cliente");
		}
		
		@Override
		public void run() {
			try{
				String valor = "";
				while (valor.compareTo("Fin") != 0) {
					valor = flujoE.readUTF();
					tv.append("Servidor Recibe: " + valor);
				}
				flujoE.close();
				flujoS.close();
				s.close();
			}catch(Exception e){}
		}
	}
	
}