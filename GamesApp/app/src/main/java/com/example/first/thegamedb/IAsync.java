package com.example.first.thegamedb;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Chaithanya on 2/17/2017.
 */

public class IAsync extends AsyncTask<String , Void, Bitmap> {

    All gameDetails;

    public IAsync(All gameDetails) {
        this.gameDetails = gameDetails;
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            return  bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        gameDetails.imageView.setImageBitmap(bitmap);
        gameDetails.progressBar = (ProgressBar) gameDetails.findViewById(R.id.progressBar);
        gameDetails.progressBar.setVisibility(View.GONE);
    }
}
