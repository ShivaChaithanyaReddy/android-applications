package com.example.first.tedpodcast;

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
 * Created by vandita on 3/9/17.
 */

public class getPodcastAsync extends AsyncTask<String, Void, ArrayList<Podcast>> {


    IPodcast iPodcast;

    public getPodcastAsync(IPodcast iPodcast) {
        this.iPodcast = iPodcast;
    }

    @Override
    protected ArrayList<Podcast> doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int statusCode = con.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                return PodcastUtil.PodcastPullParser.parsePodcast(in);
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
    protected void onPostExecute(ArrayList<Podcast> podcasts) {
        super.onPostExecute(podcasts);
        if (podcasts != null)
            iPodcast.ParsedData(podcasts);

       // Log.d("demo", podcasts.toString());
    }

    public interface IPodcast {
        void ParsedData(ArrayList<Podcast> podcasts);
    }
}
