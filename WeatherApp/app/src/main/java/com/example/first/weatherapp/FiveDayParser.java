package com.example.first.weatherapp;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Chaithanya on 4/8/2017.
 */


public class FiveDayParser extends AsyncTask<String, Void, FiveDay> {

    JSONParsing.IActivity iActivity;
    long keyValue;

    public FiveDayParser(JSONParsing.IActivity iActivity, long keyValue) {
        this.iActivity = iActivity;
        this.keyValue = keyValue;
    }

    @Override
    protected FiveDay doInBackground(String... params) {

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

                return FiveDayParser2.parser.articlesParser(sb.toString());
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
    protected void onPostExecute(FiveDay fiveDay) {
//        if(locationAPI != null) {
        iActivity.sendFiveDayCast(fiveDay, keyValue);
        //      }
    }
}
