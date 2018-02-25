package com.example.first.thegamedb;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity implements ApiAsyncTask.IGames{


    EditText search;
    Button searchBtn;
    Button Go;
    ScrollView scrollView;
    ProgressDialog progressDialog;
    RadioGroup radioGroup;
    Intent intent;
    String Name = null;
    public static String GAME = "gameId";
    public static String GAMES = "allGames";
    ArrayList<Games> CompleteList;

    public static String SG = "sg";


    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        search = (EditText) findViewById(R.id.SearchET);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        Go = (Button) findViewById(R.id.goBtn);


        Go.setEnabled(false);





    }


    public void onClickSearch(View view) {

        Name = search.getText().toString();

        if(Name == null || Name.isEmpty())
            Toast.makeText(this,"Name cannot be empty!",Toast.LENGTH_SHORT).show();
        else {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setCancelable(false);
            //  progressDialog.getWindow().setLayout(800, 800);


            new ApiAsyncTask(this).execute("http://thegamesdb.net/api/GetGamesList.php?name="+Name);
        }
    }

    @Override
    public void sendData(final ArrayList<Games> arrayList) {




        progressDialog.dismiss();
        //   Log.d("asdada",arrayList.toString());

        if(arrayList == null || arrayList.isEmpty())
            Toast.makeText(this,"Sorry no game found!",Toast.LENGTH_SHORT).show();

        CompleteList = new ArrayList<>();
        CompleteList = arrayList;


        listView   = (ListView) findViewById(R.id.listView);
        DataAdapter adapter = new DataAdapter(this, R.layout.xmllayout, arrayList);

        listView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,GameDetails.class);
                Log.d("In main game is:",""+CompleteList.get(position).getId());
                intent.putExtra(GAME, CompleteList.get(position).getId());
                startActivity(intent);
            }
        });
    }


    public void onClickGo(View view) {

        int radioChecked = radioGroup.getCheckedRadioButtonId();

        Log.d("Game checked is :"," "+radioChecked);

        intent = new Intent(MainActivity.this, GameDetails.class);
        intent.putExtra(GAME, radioChecked);
        startActivity(intent);
    }



}
