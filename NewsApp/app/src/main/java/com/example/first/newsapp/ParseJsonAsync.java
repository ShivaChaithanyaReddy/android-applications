package com.example.first.newsapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Chaithanya on 2/6/17.
 */

public class ParseJsonAsync extends AsyncTask<String, Void, ArrayList<Articles>>{
    MainActivity activity;

    public ParseJsonAsync(MainActivity activity) {
        this.activity = activity;
    }


    @Override
    protected ArrayList<Articles> doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int status = con.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();
                while(line!=null) {
                    sb.append(line);
                    line = reader.readLine();
                }

                Log.d("adsfghsfdghsdgfhawdfj",sb.toString());
                return JSONParser.parser.articlesParser(sb.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Articles> articles) {
        if(articles != null) {
            activity.getCompletedData(articles);


          /*  for(Articles a: articles)
            Log.d("demo:", a.getAuthor());*/
        }
    }

}
