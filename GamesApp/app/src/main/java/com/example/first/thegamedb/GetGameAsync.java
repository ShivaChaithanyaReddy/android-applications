package com.example.first.thegamedb;

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
 * Created by Chaithanya on 2/17/2017.
 */

public class GetGameAsync extends AsyncTask<String, Void, ArrayList<GetGame>> {

    IGame iGame;
    String selected =null;

    public GetGameAsync(IGame iGame, String selected) {
        this.iGame = iGame;
        this.selected = selected;
    }

    @Override
    protected ArrayList<GetGame> doInBackground(String... params) {

        URL url = null;
        try {
            url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            Log.d("assdads"," Game found");

            int status = connection.getResponseCode();

            if(status == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
                return GetGameUtil.getGameParser.Gparser(in,selected);
                //
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<GetGame> arrayList) {
        super.onPostExecute(arrayList);

        if(arrayList != null) {
           iGame.sendGame(arrayList);
        }
    }

    public interface IGame {
        void sendGame(ArrayList<GetGame> games);
    }
}
