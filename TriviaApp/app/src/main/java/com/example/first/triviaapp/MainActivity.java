package com.example.first.triviaapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
    String URLForJSON = "http://dev.theappsdr.com/apis/trivia_json/index.php";
    Intent intent;
    final static String QUESTIONS = "Ques";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Checking internet connection
        if(!isConnected())
            Toast.makeText(this,"Please connect to internet!",Toast.LENGTH_SHORT).show();
        else {
//Code goes here
            new JSONParsingAsync_main(this).execute(URLForJSON);
        }

        findViewById(R.id.MainExit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
            return true;
        return false;
    }

    public void getAllQuestions(final ArrayList<Questions> result) {
      //  Log.d("parsed data:",result.toString());
        ///////////////////////////////////////////
            findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        intent = new Intent(MainActivity.this,Trivia.class);
                        intent.putExtra(QUESTIONS,result);
                        startActivity(intent);
                    finish();

                }
            });
    }

}
