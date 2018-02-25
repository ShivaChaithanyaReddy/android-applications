package com.example.first.thegamedb;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Chaithanya on 2/16/2017.
 */

public class LogoAsync extends AsyncTask<String, Void, ArrayList<Games>> {


    IGames iGames;


    public LogoAsync(IGames iGames) {
        this.iGames = iGames;
    }

    @Override
    protected ArrayList<Games> doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            int status = connection.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
               // return GetGameUtil.getGameParser.Gparser(in);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Games> games) {
        super.onPostExecute(games);

        if(games != null) {
            iGames.sendData(games);
        }
    }

    public interface IGames {
        void sendData(ArrayList<Games> arrayList);
    }
}
