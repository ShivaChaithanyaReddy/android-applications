package com.example.first.newsapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vandita on 2/6/17.
 */

public class AppendSpinnerAsync extends AsyncTask<String, Void, String> {
    MainActivity activity;

    public AppendSpinnerAsync(MainActivity activity) {
        this.activity = activity;
    }

    String baseurl = "https://newsapi.org/v1/articles?apiKey=7b04912ad9a948488c626f648e7694d4&source=";


    @Override
    protected String doInBackground(String... params) {
        Log.d("url is ", baseurl+params[0]);
        return baseurl+params[0];
    }

    @Override
    protected void onPostExecute(String result) {

        activity.getAppenededData(result);

    }
}