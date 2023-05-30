package com.izv.navegador;

	import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Uri data=getIntent().getData();
		String datos="";
		if(data!=null){
			datos=data.toString();
			WebView myWebView = (WebView) this.findViewById(R.id.webView1);
	        myWebView.loadUrl(datos);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
