package com.example.first.triviaapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Chaithanya on 2/8/2017.
 */

public class JSONParsingAsync_main extends AsyncTask<String, Object, ArrayList<Questions>> {

//    String URLForJSON = "http://dev.theappsdr.com/apis/trivia_json/index.php";
    TextView textView;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    TextView ready;
    ImageView trivia;
    Button start;


    MainActivity mainActivity;


    public JSONParsingAsync_main(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    protected ArrayList<Questions> doInBackground(String... params) {


        try {

            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            int statusConnection = con.getResponseCode();
//            Log.d("Connection Code: "," "+statusConnection);
            if(statusConnection == HttpURLConnection.HTTP_OK)
            {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer stringBuffer = new StringBuffer();
                String line=bufferedReader.readLine();
                while(line != null) {
                    stringBuffer.append(line);
                    line = bufferedReader.readLine();
                }
                return JSONParsing.parser.questionsParser(stringBuffer.toString());
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onPostExecute(ArrayList<Questions> result) {

        if(result != null) {
            mainActivity.getAllQuestions(result);
            textView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            ready = (TextView) mainActivity.findViewById(R.id.textView2);
            trivia = (ImageView) mainActivity.findViewById(R.id.imageView);
            trivia.setVisibility(View.VISIBLE);

            start.setEnabled(true);
            start.setBackground(ContextCompat.getDrawable(mainActivity,R.drawable.button_enable));
            ready.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        start = (Button) mainActivity.findViewById(R.id.button2);
        start.setEnabled(false);
        relativeLayout = (RelativeLayout) mainActivity.findViewById(R.id.InsideLayout);

        progressBar = new ProgressBar(mainActivity, null, android.R.attr.progressBarStyle);
        textView = new TextView(mainActivity);
        textView.setText("Loading Trivia");
        textView.setTextColor(ContextCompat.getColor(mainActivity, R.color.colorBlack));

        relativeLayout.addView(textView);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.addRule(relativeLayout.CENTER_IN_PARENT);
        layoutParams1.setMargins(350,360,100,100);
       // progressBar.setLayoutParams(layoutParams);


    }

}
