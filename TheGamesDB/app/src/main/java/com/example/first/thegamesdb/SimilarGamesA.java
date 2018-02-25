package com.example.first.thegamesdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SimilarGamesA extends AppCompatActivity  implements GetGameAsync.IGame{


    LinearLayout linearLayout;
    int count=0;
    int temp =0;
    String titleGame;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_games);


        ArrayList<SimilarGames> arrayList = new ArrayList<>();

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgress(100);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Extracting Similar Games");
        progressDialog.show();


        arrayList = (ArrayList<SimilarGames>) getIntent().getSerializableExtra(GameDetails.SG);
        titleGame = getIntent().getExtras().getString(GameDetails.TITLE);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText("Similar Games to "+titleGame);
        count = arrayList.size()-1;

        Log.d("assad", "asasdasa");

        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);



            for (SimilarGames s : arrayList) {
                new GetGameAsync(this).execute("http://thegamesdb.net/api/GetGame.php?id=" + s.getId());
            }


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void sendGame(ArrayList<GetGame> games) {

        temp++;
        if(temp >= count-1)
        progressDialog.dismiss();

        TableRow tableRow = new TableRow(this);
        TextView tv = new TextView(this);
        tv.setText(games.get(0).getTitle()+". Released in "+games.get(0).getReleaseDate()+". Platform:\n"+games.get(0).getPlatform());
tv.setMaxWidth(900);
tv.setPadding(10,10,10,10);
        tv.setBackground(getBaseContext().getDrawable(R.drawable.border));
        TextView gap = new TextView(this);
        gap.setText("\n");
        tableRow.addView(tv);
        tableRow.addView(gap);
        linearLayout.addView(tableRow);
    }


    public void onClick(View view) {
        finish();
    }
}
