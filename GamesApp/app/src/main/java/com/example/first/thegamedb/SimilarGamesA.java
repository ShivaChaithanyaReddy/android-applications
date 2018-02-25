package com.example.first.thegamedb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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
    public static String GAME = "a";

    ArrayList<GetGame> arrayList1;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar_games);


        arrayList1 = new ArrayList<>();
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

        for (SimilarGames s : arrayList) {
            new GetGameAsync(this,s.getId()).execute("http://thegamesdb.net/api/GetGame.php?id=" + s.getId());
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void sendGame(ArrayList<GetGame> games) {

        temp++;




arrayList1.add(games.get(0));

        if(temp >= count-1) {
            progressDialog.dismiss();
            disp(arrayList1);
        }
    }

    private void disp(final ArrayList<GetGame> arrayList1) {

        ListView list = (ListView) findViewById(R.id.Lview);
        ArrayAdapter<GetGame> adapter = new ArrayAdapter<GetGame>(this, android.R.layout.simple_list_item_1,arrayList1);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SimilarGamesA.this,All.class);
                Log.d("ashjbashdbsahbdhabdcash",arrayList1.get(position).getGid());
                intent.putExtra(GAME, arrayList1.get(position).getGid());
                startActivity(intent);
            }
        });
    }


    public void onClick(View view) {
        finish();
    }
}
