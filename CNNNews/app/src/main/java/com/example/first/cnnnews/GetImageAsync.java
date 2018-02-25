package com.example.first.cnnnews;

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
 * Created by Chaithanya on 2/13/2017.
 */

public class GetImageAsync extends AsyncTask<String, Void, Bitmap>{


    HttpURLConnection httpURLConnection;

    MainActivity mainActivity;

    public GetImageAsync(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


//    public GetImageAsync(Questions questions) {
//        this.questions = questions;
//    }

    @Override
    protected Bitmap doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            Bitmap bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            return  bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        Log.d("asdfghjgfdsadfghjh","");

        if(bitmap != null) {
            ImageView imageView = (ImageView) mainActivity.findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
        }
    }
}
