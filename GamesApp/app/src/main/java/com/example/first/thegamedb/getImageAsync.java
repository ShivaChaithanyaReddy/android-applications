package com.example.first.thegamedb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vandita on 2/20/17.
 */

public class getImageAsync extends AsyncTask<String, Void, Bitmap> {
    IData activity;
    DataClass obj;
    ImageView imageView;

    public getImageAsync(IData activity, ImageView imageView) {
        this.activity = activity;
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            Bitmap image = BitmapFactory.decodeStream(con.getInputStream());
            return image;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
       // super.onPostExecute(bitmap);
        imageView.setImageBitmap(bitmap);


    }
    interface IData
    {
        public void setUpData(Bitmap bitmap);
        public Context getContext();
    }
}
