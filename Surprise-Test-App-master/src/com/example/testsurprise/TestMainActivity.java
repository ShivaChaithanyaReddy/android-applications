package com.example.testsurprise;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class TestMainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_main);
		Button test=(Button)findViewById(R.id.Test);
		test.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				Intent myintent= new Intent(view.getContext(),TestCode.class);
				startActivityForResult(myintent,0);
			
			}
		});
		Button evarsity=(Button)findViewById(R.id.Evarsity);
		 
        
		evarsity.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				Intent myintent= new Intent(view.getContext(),EvarsityFinal.class);
				startActivityForResult(myintent,0);
			
			}
		});
	        
	        Button rest=(Button)findViewById(R.id.Result);
			rest.setOnClickListener(new View.OnClickListener(){
				public void onClick(View view){
					Intent myintent= new Intent(view.getContext(),Result.class);
					startActivityForResult(myintent,0);
				
				}
			});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test_main, menu);
		return true;
	}

}
