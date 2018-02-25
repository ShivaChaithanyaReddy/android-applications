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
 * Created by Chaithanya on 4/7/2017.
 */



public class CurForecastAPIParser extends AsyncTask<String, Void, ForecastAPI> {

    JSONParsing.IActivity iActivity;

    public CurForecastAPIParser(JSONParsing.IActivity iActivity) {
        this.iActivity = iActivity;
    }

    @Override
    protected ForecastAPI doInBackground(String... params) {

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

                return CurForecastAPIParser2.parser.articlesParser(sb.toString());
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
    protected void onPostExecute(ForecastAPI forecastAPI) {
//        if(locationAPI != null) {
        iActivity.sendCurForecast(forecastAPI);
        //      }
    }
}
