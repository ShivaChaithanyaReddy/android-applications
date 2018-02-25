package com.example.first.weatherapp;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;



import org.ocpsoft.prettytime.PrettyTime;

import java.util.Locale;
import java.util.Map;

import static com.example.first.weatherapp.FiveDayParser2.parser.DEGREE;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener, JSONParsing.IActivity{


    Button setCity;
    EditText cityName;
    EditText countryName;
    Button searchCity;
    AlertDialog.Builder alertDialogue;

    String alert_city_name;
    String alert_country_name;

    ProgressBar progressBar;


    FirebaseDatabase database;
    DatabaseReference myRef;

    //for Recview
    private RecyclerView recyclerView;
    //   private RecViewAdapter recViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    // for Recview
//for Recview
    private RecViewMain recViewAdapter;
    // for Recview

    public static final String CityName = "cityname";
    public static final String CountryName = "countryname";
    static  TextView displayText;

    //For Shared Preferences
    static SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Key = "key";
    public static final String city = "city";
    public static final String country = "country";
    public static final String tempp = "temperature_preference";



    static  ArrayList<SaveInFirebase> FirebaseList;


    String citynamefromalert;
    String countrynamefromalert;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Weather App");

        alertDialogue = new AlertDialog.Builder(MainActivity.this);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        displayText = (TextView) findViewById(R.id.cities_display_text);

        FirebaseList = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
         myRef = database.getReference("SavedCities");


        //RecyclerView Code starts:
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_main);


        LinearLayoutManager mgridLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mgridLayoutManager);


        recViewAdapter = new RecViewMain(FirebaseList, MainActivity.this,MainActivity.this);

        recyclerView.setAdapter(recViewAdapter);
        //RecyclerView Code ends:




        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.hasChildren()) {
                    displayText.setText("There are no cities to display. \n " +
                            "search the city from the search box and save");
                }else {
                    displayText.setText("Saved Cities");
                    Log.d("Testing",""+dataSnapshot.child("Cities").getChildrenCount());

                    for (DataSnapshot postSnapshot: dataSnapshot.child("Cities").getChildren()) {
                        Log.d("TAG", "Value is: " + postSnapshot);
                        if(!FirebaseList.contains(postSnapshot.getKey()))
                            FirebaseList.add(postSnapshot.getValue(SaveInFirebase.class));

                    }

                    recViewAdapter.notifyDataSetChanged();
                }
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        findViewById(R.id.set_city_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialogue();
            }
        });

//****************************For seraching the cities::::::::::::::::::::

        findViewById(R.id.search_city_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write a message to the database

               cityName = (EditText) findViewById(R.id.city_name_text);
                countryName = (EditText) findViewById(R.id.editText2);

                Intent intent = new Intent(MainActivity.this, CityWeather.class);
                intent.putExtra(CityName, cityName.getText().toString());
                intent.putExtra(CountryName, countryName.getText().toString());
                startActivity(intent);

            }
        });


    }



    @Override
    protected void onPostResume() {
        super.onPostResume();


        String saveTemp = sharedpreferences.getString(tempp, null);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(1);


        if(FirebaseList != null && FirebaseList.size() != 0) {

            Log.d("Firebase is:::",""+FirebaseList.size());
            displayText.setText("Saved Cities");

            for(int i=0;i<FirebaseList.size();i++) {
                SaveInFirebase s = FirebaseList.get(i);
                String f = null;
                Log.d("qqqqqqqqqqqqqqqq",""+s.getTempearture());

                if(saveTemp.equals("Celcius")) {
                    if(s.getTempearture().contains("C"))
                        f = s.getTempearture();
                    else {
                        String te = s.getTempearture().replace(DEGREE+"F", "");
                        Log.d("aaaa",""+te);
                        f =String.valueOf(df.format(((Float.parseFloat(te) - 32) * 5) / 9 )) + DEGREE + "C";
                    }
                }else {
                    if(s.getTempearture().contains("C")) {
                        String te = s.getTempearture().replace(DEGREE+"C", "");
                        f = String.valueOf(df.format(( (1.8)*(Float.parseFloat(te)) + 32)  )) + DEGREE + "F";
                    }else {
                        f = s.getTempearture();
                    }
                }
                s.setTempearture(f);
                FirebaseList.remove(i);FirebaseList.add(i,s);
            }

            Collections.sort(FirebaseList);
        }else {
            displayText.setText("There are no cities to display. \n " +
                    "search the city from the search box and save");
        }
recViewAdapter.notifyDataSetChanged();

        String getkey = sharedpreferences.getString(Key, null);
        citynamefromalert = sharedpreferences.getString(city, null);
        countrynamefromalert = sharedpreferences.getString(country, null);

        if(getkey != null && citynamefromalert != null && countrynamefromalert != null) {
            //   findViewById(R.id.set_current_city_text).setVisibility(View.GONE);
         //   progressBar.setVisibility(View.VISIBLE);
            new CurForecastAPIParser(MainActivity.this).execute("http://dataservice.accuweather.com/currentconditions/v1/"+getkey+"?apikey=MdLqXVqGYJrQ9ENW4ZHJeqEWGMnnfoZV");

        }

    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which){
            case R.id.set_city_btn:
                Log.d("asdsad","asdsa");
                break;
            case R.id.search_city_btn:

                break;
        }
    }

    public void showAlertDialogue() {

        LinearLayout layout = new LinearLayout(this);
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);

        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 2);

        TextView tv = new TextView(this);
        tv.setText("Enter City Details:");
        tv.setPadding(40, 40, 40, 40);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);


        final EditText et = new EditText(this);
        et.setHint("Enter your city");
        alert_city_name = et.getText().toString();

        final EditText et1 = new EditText(this);
        et1.setHint("Enter your country");
        alert_country_name = et1.getText().toString();


        /// for buttons
        LinearLayout layoutForButtons = new LinearLayout(this);
        LinearLayout.LayoutParams parms1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutForButtons.setOrientation(LinearLayout.HORIZONTAL);
        layoutForButtons.setLayoutParams(parms1);
        layoutForButtons.setPadding(2, 2, 2, 2);

        Button cancel = new Button(this);
        cancel.setText("Cancel");

        Button set = new Button(this);
        set.setText("Set");


        layoutForButtons.addView(cancel,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));
        layoutForButtons.addView(set, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 0.5f));

        //for buttons
        LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv1Params.bottomMargin = 5;
        layout.addView(et, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.addView(et1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));


        //
        layout.addView(layoutForButtons);


        //
        alertDialogue.setView(layout);
        alertDialogue.setCancelable(false);
        alertDialogue.setTitle(R.string.alertTitle);
        // alertDialogBuilder.setMessage("Input Student ID");
        alertDialogue.setCustomTitle(tv);

        final AlertDialog alertDialogg = alertDialogue.create();

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogg.dismiss();
                citynamefromalert = et.getText().toString();
                countrynamefromalert = et1.getText().toString();
                new JSONParsing(MainActivity.this,0).execute("http://dataservice.accuweather.com/locations/v1/"+countrynamefromalert+"/search?apikey=MdLqXVqGYJrQ9ENW4ZHJeqEWGMnnfoZV&q="+citynamefromalert);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogg.dismiss();
            }
        });

        try {
            alertDialogg.show();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    @Override
    public void senddata(LocationAPI value) {
        Log.d("adads",""+value);
        if(value != null) {
            Toast.makeText(MainActivity.this, R.string.cityDetailsSaved , Toast.LENGTH_SHORT).show();
            //For Shared Preferences
            editor = sharedpreferences.edit();
            editor.putString(Key, String.valueOf(value.getKey()));
            editor.putString(city, citynamefromalert);
            editor.putString(country, countrynamefromalert);
            editor.putString(tempp,"Celcius");
            editor.commit();
            progressBar.setVisibility(View.VISIBLE);
            new CurForecastAPIParser(MainActivity.this).execute("http://dataservice.accuweather.com/currentconditions/v1/"+String.valueOf(value.getKey())+"?apikey=MdLqXVqGYJrQ9ENW4ZHJeqEWGMnnfoZV");

        }

        else {
            Toast.makeText(MainActivity.this, R.string.cityNotFound , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendCurForecast(ForecastAPI forecastAPI) {
        progressBar.setVisibility(View.GONE);
        Button b = (Button) findViewById(R.id.set_city_btn);
        b.setVisibility(View.INVISIBLE);

        ImageView image = (ImageView) findViewById(R.id.image_view);
        image.setVisibility(View.VISIBLE);
        if(Integer.parseInt(forecastAPI.getWeatherIcon())>= 10)
            Picasso.with(MainActivity.this).load("http://developer.accuweather.com/sites/default/files/"+Integer.parseInt(forecastAPI.getWeatherIcon())+"-s.png").into(image);
        else
            Picasso.with(MainActivity.this).load("http://developer.accuweather.com/sites/default/files/0"+Integer.parseInt(forecastAPI.getWeatherIcon())+"-s.png").into(image);

        TextView first = (TextView)findViewById(R.id.set_current_city_text);
        first.setText(citynamefromalert+", "+countrynamefromalert);
        first.setVisibility(View.VISIBLE);

        TextView second = (TextView) findViewById(R.id.weather_details_text);
        second.setText(forecastAPI.getWeatherText());
        second.setVisibility(View.VISIBLE);

        TextView fourth = (TextView) findViewById(R.id.temparature_details_text);
        fourth.setText("Temperature "+forecastAPI.getMetric());
        fourth.setVisibility(View.VISIBLE);

///

        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ssX", Locale.US);
        Date date = null;
        try {
            date = format.parse(forecastAPI.getDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PrettyTime p=new PrettyTime();

        //
        TextView fifth = (TextView) findViewById(R.id.time_details_text);
        fifth.setText("Updated "+p.format(date).toString());
        fifth.setVisibility(View.VISIBLE);


    }

    @Override
    public void forsearch(LocationAPI locationAPI) {

    }

    @Override
    public void sendFiveDayCast(FiveDay fiveDay, long keyValue) {

    }

    @Override
    public void showDayAndNight(int position) {

    }

    @Override
    public void onClickStar(int position) {

        final SaveInFirebase apps = FirebaseList.get(position);

        if(apps.isFavorite()) {
            apps.setFavorite(false);
            myRef.child("Cities").child(apps.getKey()).child("favorite").setValue(false);

            for(int i=0;i<FirebaseList.size();i++) {
                SaveInFirebase s = FirebaseList.get(i);
                if(s.getKey().equals(apps.getKey()) ) {
                    s = new SaveInFirebase(apps.getCountry(),
                            apps.getTime(),
                            apps.getKey(),
                            apps.isFavorite(),
                            apps.getTempearture(),apps.getCity());
                    FirebaseList.remove(i);FirebaseList.add(i,s);
                }
            }
            recViewAdapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Successfully removed from Favourite list :)", Toast.LENGTH_SHORT).show();

        }
        else {
            apps.setFavorite(true);
            myRef.child("Cities").child(apps.getKey()).child("favorite").setValue(true);


            for(int i=0;i<FirebaseList.size();i++) {
                SaveInFirebase s = FirebaseList.get(i);
                if(s.getKey().equals(apps.getKey()) ) {
                    s = new SaveInFirebase(apps.getCountry(),
                            apps.getTime(),
                            apps.getKey(),
                            apps.isFavorite(),
                            apps.getTempearture(),apps.getCity());
                    FirebaseList.remove(i);FirebaseList.add(i,s);
                }
            }
            recViewAdapter.notifyDataSetChanged();

            Toast.makeText(MainActivity.this, "Successfully saved to Favourite list :)", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void OnLongClickDelete(int position) {

        final SaveInFirebase apps = FirebaseList.get(position);

        apps.setFavorite(false);
        myRef.child("Cities").child(apps.getKey()).removeValue();

        for(int i=0;i<FirebaseList.size();i++) {
            SaveInFirebase s = FirebaseList.get(i);
            if(s.getKey().equals(apps.getKey()) ) {
                s = new SaveInFirebase(apps.getCountry(),
                        apps.getTime(),
                        apps.getKey(),
                        apps.isFavorite(),
                        apps.getTempearture(),apps.getCity());
                FirebaseList.remove(i);
            }
        }
        recViewAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();

        if(FirebaseList.size() == 0) {
            displayText.setText("There are no cities to display. \n " +
                    "search the city from the search box and save");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                settings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void settings() {

            Intent intent  = new Intent("com.example.first.weatherapp.intent.action.PREFES");
        intent.addCategory(Intent.CATEGORY_DEFAULT);

        startActivity(intent);
           }
}
