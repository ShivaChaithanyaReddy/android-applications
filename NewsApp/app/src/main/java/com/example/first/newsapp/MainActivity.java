package com.example.first.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner newsSpinner;
    String spinnerValue;
    TextView title;
    TextView author;
    TextView published;
    TextView description;
    int x=0;
    int length;
    ArrayList<Articles> finalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsSpinner = (Spinner) findViewById(R.id.spinner2);

    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }


    public void onGetNews(View view){

        Log.d("mainactivitylodddd","safasfsad");
        if(isConnectedOnline()){
            spinnerValue = newsSpinner.getSelectedItem().toString().trim();
            /*if(spinnerValue == "BBC"){
                spinnerValue = "bbc-news";
            }
            else if(spinnerValue == "CNN"){
                spinnerValue = "cnn";
            }
            else if(spinnerValue == "BUZZFEED"){
                spinnerValue = "buzzfeed";
            }
            else if(spinnerValue == "ESPN"){
                spinnerValue = "espn";
            }
            else if(spinnerValue == "Sky News"){
                spinnerValue = "sky-news";
            }*/

            new AppendSpinnerAsync(this).execute(spinnerValue);


        }
        else{
            Toast.makeText(MainActivity.this, "No network Connection", Toast.LENGTH_SHORT).show();
        }

    }

    public void getCompletedData(ArrayList<Articles> data){


       title = (TextView) findViewById(R.id.title);
       author = (TextView) findViewById(R.id.author);
      published = (TextView) findViewById(R.id.published);
       description = (TextView) findViewById(R.id.description);

        title.setText(data.get(0).getTitle());
        author.setText(data.get(0).getAuthor());
        published.setText(data.get(0).getPublishedAt());
        description.setText(data.get(0).getDescription());


        finalList = data;
        length = finalList.size();


    }
    public void onClickRightArrow(View view){
        if(x<length-1){

            x++;
Articles articles = finalList.get(x);
            title.setText(articles.getTitle());
            author.setText(articles.getAuthor());
            published.setText(articles.getPublishedAt());
            description.setText(articles.getDescription());
        }
        else
        {
            Toast.makeText(MainActivity.this, "No right elements to show", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickLeftArrow(View view){
        if(x>0){

            x--;
            Articles articles = finalList.get(x);
            title.setText(articles.getTitle());
            author.setText(articles.getAuthor());
            published.setText(articles.getPublishedAt());
            description.setText(articles.getDescription());
        }
        else
        {
            Toast.makeText(MainActivity.this, "No left elements to show", Toast.LENGTH_SHORT).show();
        }

    }

    public void onClickLeftMost(View view){
        if(x==0) {
            Toast.makeText(MainActivity.this, "first article", Toast.LENGTH_SHORT).show();

        }else{

            x=0;
            Articles articles = finalList.get(x);
            title.setText(articles.getTitle());
            author.setText(articles.getAuthor());
            published.setText(articles.getPublishedAt());
            description.setText(articles.getDescription());
        }

    }

    public void onClickRightMost(View view){
        if(x==length-1) {
            Toast.makeText(MainActivity.this, "Last article", Toast.LENGTH_SHORT).show();

        }else{

            x=length-1;
            Articles articles = finalList.get(x);
            title.setText(articles.getTitle());
            author.setText(articles.getAuthor());
            published.setText(articles.getPublishedAt());
            description.setText(articles.getDescription());
        }

    }
    public void getAppenededData(String data1){


        new ParseJsonAsync(this).execute(data1);



    }

}

