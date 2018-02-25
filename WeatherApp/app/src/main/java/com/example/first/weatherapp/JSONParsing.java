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


public class JSONParsing extends AsyncTask<String, Void, LocationAPI> {

    IActivity iActivity;
    
    int a;

    public JSONParsing(IActivity iActivity, int a) {
        this.iActivity = iActivity;
        this.a = a;
    }

    @Override
    protected LocationAPI doInBackground(String... params) {

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

                return JSONParser2.parser.articlesParser(sb.toString());
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
    protected void onPostExecute(LocationAPI locationAPI) {
        if(a == 0) {
            iActivity.senddata(locationAPI);
        }else if(a == 1) {
            iActivity.forsearch(locationAPI);
        }
    }

    //Interface

    public interface IActivity {
        void senddata(LocationAPI value);

        void sendCurForecast(ForecastAPI forecastAPI);

        void forsearch(LocationAPI locationAPI);

        void sendFiveDayCast(FiveDay fiveDay, long keyValue);

        void showDayAndNight(int position);

        void onClickStar(int position);

        void OnLongClickDelete(int position);
    }

}
