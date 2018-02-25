package com.example.testsurprise;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;

import android.os.Build;

public class TestCode extends Activity {
	static final String URL2="&testid=";
	static final String URL1="http://connecting.hostingsiteforfree.com/aakash/students/?reg_no=";
	
EditText testcode , reg ;
Button next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.drawable.anim10, R.drawable.anim9);
		setContentView(R.layout.activity_test_code);
		// Show the Up button in the action bar.
		setupActionBar();
		testcode=(EditText)findViewById(R.id.Code);
		reg=(EditText)findViewById(R.id.Registration);
		next= (Button)findViewById(R.id.Proceed);
			next.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
			Intent myintent= new Intent(view.getContext(),Test.class);
			startActivityForResult(myintent,0);
		String a=testcode.getText().toString();
		String b=reg.getText().toString();
		String url=URL1+b+URL2+a;
		Intent intent = new Intent(TestCode.this,Test.class);
		intent.putExtra("text", url);
		startActivity(intent);
		}
		});
		
		Animation animZoomin = AnimationUtils.loadAnimation(getApplicationContext(), R.drawable.animzoom);
		next.startAnimation(animZoomin);
		 
	}

	
	

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_code, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
