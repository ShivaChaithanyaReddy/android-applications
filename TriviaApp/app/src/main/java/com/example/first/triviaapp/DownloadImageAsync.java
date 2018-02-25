package com.example.first.triviaapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Chaithanya on 2/9/2017.
 */

public class DownloadImageAsync extends AsyncTask<String, Void, Bitmap> {

    Trivia trivia;
    ImageView imageView;
    RelativeLayout imageloading;
    Button next;
    Button previous;

    int count;

    public DownloadImageAsync(Trivia trivia) {
        this.trivia = trivia;
    }

    @Override
    protected Bitmap doInBackground(String... params) {


        count = Integer.parseInt(params[1]);
        try {

            if(params[0].equals("notAvailable"))
            {
                Bitmap icon = BitmapFactory.decodeResource(trivia.getResources(), R.drawable.trivia);
                return  icon;
            }
            else {


                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                Bitmap bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
                return bitmap;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        imageView = (ImageView) trivia.findViewById(R.id.imageView2);
        imageView.setVisibility(View.INVISIBLE);
        imageloading = (RelativeLayout) trivia.findViewById(R.id.loadingPanel);
        imageloading.setVisibility(View.VISIBLE);

        previous = (Button) trivia.findViewById(R.id.previous);
        previous.setEnabled(false);

        next = (Button) trivia.findViewById(R.id.next);

        next.setBackground(ContextCompat.getDrawable(trivia,R.drawable.button_disabled));
        previous.setBackground(ContextCompat.getDrawable(trivia, R.drawable.button_disabled));

        next.setEnabled(false);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(bitmap);

            //set loading layout gone;
            imageloading.setVisibility(View.INVISIBLE);
        previous.setEnabled(true);
        next.setEnabled(true);
        next.setBackground(ContextCompat.getDrawable(trivia,R.drawable.button_enable));
        previous.setBackground(ContextCompat.getDrawable(trivia, R.drawable.button_enable));

        if(count == 0) {
            previous.setBackground(ContextCompat.getDrawable(trivia, R.drawable.button_disabled));
        }

    }

}
