package com.example.testsurprise;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class EvarsityFinal extends Activity {
	public WebView webview12;
	ProgressBar progress2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_evarsity_final);
		webview12 = (WebView) findViewById(R.id.webview12);
		webview12.setWebChromeClient(new WebChromeClient());
		 
        progress2 = (ProgressBar) findViewById(R.id.progressBar2);
        progress2.setMax(100);
        
        webview12 = (WebView) findViewById(R.id.webview12);
		WebSettings webSettings = webview12.getSettings();
	    webview12.loadUrl("http://evarsity.srmuniv.ac.in/srmweb/usermanager/youLogin.jsp");
	    webSettings.setJavaScriptEnabled(true);
	    EvarsityFinal.this.progress2.setProgress(0);
 	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.evarsity_final, menu);
		return true;
	}

}
