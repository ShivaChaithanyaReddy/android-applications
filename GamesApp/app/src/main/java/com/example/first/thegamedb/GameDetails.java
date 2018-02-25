package com.example.first.thegamedb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameDetails extends AppCompatActivity implements GetGameAsync.IGame{

    ProgressDialog progressDialog;
    ImageView imageView;
    ScrollView scrollView;
    LinearLayout linearLayout;
    TextView genre;
    TextView publisher;
    Button trailer;
    Button similar;
    Button finishbtn;
    ProgressBar progressBar;
    ExecutorService threadpool;

    ArrayList<GetGame> receivedGameDetails;
    public static String TRAILER = "trailer";
    public static String SG = "sg";
    public static String TITLE = "title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_details);



        ArrayList<Games> arrayList = new ArrayList<>();
        String gameSelected = null;

        gameSelected = getIntent().getExtras().getString(MainActivity.GAME);
        Log.d("game selected id is:"," "+gameSelected);

        int gameis = Integer.parseInt(gameSelected);

        imageView = (ImageView) findViewById(R.id.imageView);
        scrollView = (ScrollView) findViewById(R.id.scroll);
        linearLayout = (LinearLayout) findViewById(R.id.linear);
        genre = (TextView) findViewById(R.id.genreTV);
        publisher = (TextView) findViewById(R.id.publisherTV);
        similar = (Button) findViewById(R.id.similagamesBtn);
        finishbtn = (Button) findViewById(R.id.finishBtn);

        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setCancelable(false);
        new GetGameAsync(this,gameSelected).execute("http://thegamesdb.net/api/GetGame.php?id="+gameSelected);



    }

    @Override
    public void sendGame(ArrayList<GetGame> games) {

        receivedGameDetails = new ArrayList<>();
        receivedGameDetails = games;


        progressDialog.dismiss();
        if(games != null)
        {
            GetGame game = games.get(0);
            Log.d("Image is ::::::::::::"," "+game.getImage());
            if(game.getImage() != null) {
                new ImageAsync(this).execute(games.get(0).getImage());
            }
            else {
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.GONE);
            }
            TextView tv = new TextView(this);

            if(games.get(0).getOverview() == null)
                tv.setText("Data not available!!");
            else
                tv.setText(games.get(0).getOverview());

            linearLayout.addView(tv);


            if(game.getGenre() == null)
                genre.setText("Genre: Data Not available!!");
            else
                genre.setText("Genre: "+game.getGenre());

            if(game.getPublisher() == null)
                publisher.setText("Publisher: Data Not available!!");
            else
                publisher.setText("Publisher: "+game.getPublisher());
        }
    }


    public void onClickS(View view) {

        ArrayList<SimilarGames> arrayList = receivedGameDetails.get(0).getArrayList();
        if(arrayList !=null) {

            Intent intent = new Intent(GameDetails.this, SimilarGamesA.class);
            intent.putExtra(SG, arrayList);
            intent.putExtra(TITLE,receivedGameDetails.get(0).getTitle());
            startActivity(intent);
        }

        else{
            Toast.makeText(this,"No Similar Game Found!!",Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickF(View view) {
/*        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(i);*/

        deleteCache(this);
        finish();
    }


    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}
