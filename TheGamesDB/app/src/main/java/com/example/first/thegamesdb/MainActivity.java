package com.example.first.thegamesdb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;

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




    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        search = (EditText) findViewById(R.id.SearchET);
        searchBtn = (Button) findViewById(R.id.searchBtn);
        Go = (Button) findViewById(R.id.goBtn);
        scrollView = (ScrollView) findViewById(R.id.ScrollView);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        Go.setEnabled(false);





/*


        SimilarGames ss = new SimilarGames();
        ss.setId("15246");

        SimilarGames ssa = new SimilarGames();
        ssa.setId("15225");

        ArrayList<SimilarGames> arrayList = new ArrayList<>();
        arrayList.add(ss);
        arrayList.add(ssa);

        Intent intent = new Intent(MainActivity.this,SimilarGamesA.class);
        intent.putExtra(SG,arrayList);
        startActivity(intent);
*/


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
    public void sendData(ArrayList<Games> arrayList) {
        progressDialog.dismiss();
     //   Log.d("asdada",arrayList.toString());

        if(arrayList == null || arrayList.isEmpty())
            Toast.makeText(this,"Sorry no game found!",Toast.LENGTH_SHORT).show();

        CompleteList = new ArrayList<>();
        CompleteList = arrayList;

        for(Games g: arrayList) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(g.getTitle()+". "+g.getReleaseDate()+". "+g.getPlatform());
            radioButton.setId(Integer.parseInt(g.getId()));
            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Go.setEnabled(true);
                Go.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.button_enable));
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
