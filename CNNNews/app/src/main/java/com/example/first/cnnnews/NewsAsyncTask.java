package com.example.first.cnnnews;

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
 * Created by Chaithanya on 2/13/2017.
 */

public class NewsAsyncTask extends AsyncTask<String, Void, ArrayList<News>> {

    MainActivity mainActivity;
    ProgressDialog progressDialog;

    public NewsAsyncTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    protected ArrayList<News> doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection con =  (HttpURLConnection) url.openConnection();
            con.connect();
            int status = con.getResponseCode();
            if(status == HttpURLConnection.HTTP_OK) {
                InputStream in = con.getInputStream();
                Log.d("going to parser:"," ");

                return NewsUtil.NewsUtilParser.parseNews(in);
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
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading");
        progressDialog.show();

    }

    @Override
    protected void onPostExecute(ArrayList<News> result) {
        super.onPostExecute(result);


        if(result != null) {
            progressDialog.dismiss();
            mainActivity.setNews(result);
            Log.d("asdsdasdsdadas"," "+result.toString());
        }

    }

}
