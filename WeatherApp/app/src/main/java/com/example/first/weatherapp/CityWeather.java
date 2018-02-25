package com.example.first.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.example.first.weatherapp.FiveDayParser2.parser.DEGREE;
import static com.example.first.weatherapp.MainActivity.FirebaseList;
import static com.example.first.weatherapp.MainActivity.displayText;
import static com.example.first.weatherapp.MainActivity.tempp;

public class CityWeather extends AppCompatActivity implements JSONParsing.IActivity {

    ProgressDialog progressDialog;
    String cityName;
    String countryName;
    TextView heading;
    TextView heading_ans;

    int Status = 1;


    //for Recview
    private RecyclerView recyclerView;
    private RecViewAdapter recViewAdapter;
    private GridLayoutManager mLayoutManager;
    // for Recview

    ArrayList<String> FirebaseListKey;

    ArrayList<FiveDay.DailyCast> mainList;
    ImageView dayImage;
    ImageView nightImage;
    TextView daymsg;
    TextView nightmsg;

    TextView headlineText;
    TextView headlineAns;
    String Mainurl;

    String keyvalue;


    //For Shared Preferences
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        mainList = new ArrayList<>();
         dayImage = (ImageView) findViewById(R.id.day_imageview);
         nightImage = (ImageView) findViewById(R.id.night_imageview);
         daymsg  = (TextView) findViewById(R.id.day_condition);
         nightmsg = (TextView) findViewById(R.id.night_condition);

        headlineText = (TextView) findViewById(R.id.headline_forecat_text);
        headlineAns = (TextView) findViewById(R.id.heading_forecast_ans_text);

        heading = (TextView) findViewById(R.id.dailyforcast_text);
        heading_ans = (TextView) findViewById(R.id.heading_ans_text);

        heading_ans.setMovementMethod(new ScrollingMovementMethod());


        progressDialog = new ProgressDialog(CityWeather.this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

         cityName = getIntent().getStringExtra(MainActivity.CityName);
         countryName = getIntent().getStringExtra(MainActivity.CountryName);

        progressDialog.show();

        //For getting the key from city and country
        new JSONParsing(CityWeather.this,1).execute("http://dataservice.accuweather.com/locations/v1/"+countryName+"/search?apikey=MdLqXVqGYJrQ9ENW4ZHJeqEWGMnnfoZV&q="+cityName);


        FirebaseListKey = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("SavedCities");



        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for (DataSnapshot postSnapshot: dataSnapshot.child("Cities").getChildren()) {
                    Log.d("TAG", "Value is: " + postSnapshot);
                    if(!FirebaseListKey.contains(postSnapshot.getKey()))
                    FirebaseListKey.add(postSnapshot.getKey());
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


    }

    @Override
    protected void onResume() {
        Log.d("I am eecuted",": onresume");
        super.onResume();

        if(mainList.size() !=0 && mainList != null) {
            ///////Temp conversion:
            String saveTemp = sharedpreferences.getString(tempp, null);
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(1);

            for(int i=0;i<mainList.size();i++) {

                 FiveDay.DailyCast s = mainList.get(i);

                String low = s.getMintemerature();
                String high = s.getMaxtemperature();
                String lt = null;
                String ht = null;
                if(saveTemp.equals("Celcius")) {
                    if(low.contains("C")) {
                     ht = high; lt = low;
                    }else {
                        lt = low.replace(DEGREE+"F", "");
                        ht = high.replace(DEGREE+"F", "");
                        ht = String.valueOf(df.format(((Float.parseFloat(ht) - 32) * 5) / 9 )) + DEGREE + "C";
                        lt = String.valueOf(df.format(((Float.parseFloat(lt) - 32) * 5) / 9 )) + DEGREE + "C" ;
                    }
                }else {
                    if(low.contains("C")) {
                        lt = low.replace(DEGREE+"C", "");
                        ht = high.replace(DEGREE+"C", "");

                        ht = String.valueOf(df.format(( (1.8)*(Float.parseFloat(ht)) + 32)  )) + DEGREE + "F";
                        lt = String.valueOf(df.format(( (1.8)*(Float.parseFloat(lt)) + 32)  )) + DEGREE + "F";
                    }else {
                        ht=high;lt=low;
                    }
                }
                s.setMintemerature(lt);
                s.setMaxtemperature(ht);
                mainList.remove(i);mainList.add(i,s);
            }
            Log.d("posiiotashjdas",""+Status);
            headlineAns.setText("Temperature: "+mainList.get(Status).getMaxtemperature()+" / "+ mainList.get(Status).getMintemerature());

        }

    }

    @Override
    public void senddata(LocationAPI value) {

    }

    //Method to get the key
    @Override
    public void forsearch(LocationAPI value) {

        if(value != null) {
            keyvalue = String.valueOf(value.getKey());
            new FiveDayParser(CityWeather.this, value.getKey()).execute("http://dataservice.accuweather.com/forecasts/v1/daily/5day/"+String.valueOf(value.getKey())+"?apikey=MdLqXVqGYJrQ9ENW4ZHJeqEWGMnnfoZV");
        }

        else {
            progressDialog.dismiss();
            Toast.makeText(CityWeather.this, R.string.cityNotFound , Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    //Mehod to get details of all the Five days.
    @Override
    public void sendFiveDayCast(final FiveDay fiveDay, long keyValue) {
        progressDialog.dismiss();

        heading.setText("Daily forecast for "+cityName+", "+countryName);
        heading_ans.setText(fiveDay.getHeadline());


        Mainurl = fiveDay.getHeadline_link();


        mainList = fiveDay.getArrayList();
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ssX", Locale.US);
        Date date = null;
        try {
            date = format.parse(mainList.get(0).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");

        headlineText.setText("Forecast on "+formatter.format(date));
        headlineAns.setText("Temperature: "+ mainList.get(0).getMaxtemperature()+" / "+mainList.get(0).getMintemerature());


        if(Integer.parseInt(mainList.get(0).getDay_icon()) < 10) {


            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/0"+Integer.parseInt(mainList.get(0).getDay_icon())+"-s.png").into(dayImage);
            daymsg.setText(mainList.get(0).getDay_phrase());
  }
        else {

            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+Integer.parseInt(mainList.get(0).getDay_icon())+"-s.png").into(dayImage);
            daymsg.setText(mainList.get(0).getDay_phrase());

        }

        if(Integer.parseInt(mainList.get(0).getNight_icon()) < 10) {

            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/0"+Integer.parseInt(mainList.get(0).getNight_icon())+"-s.png").into(nightImage);
            nightmsg.setText(mainList.get(0).getNight_phrase());
        }
        else {
            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+Integer.parseInt(mainList.get(0).getNight_icon())+"-s.png").into(nightImage);
            nightmsg.setText(mainList.get(0).getNight_phrase());

        }


        findViewById(R.id.textView8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = mainList.get(0).getInner_mobilelink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        findViewById(R.id.textView9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Mainurl));
                startActivity(i);

            }
        });


        //RecyclerView Code starts:
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        GridLayoutManager mgridLayoutManager = new GridLayoutManager(CityWeather.this,1, GridLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mgridLayoutManager);


        recViewAdapter = new RecViewAdapter(mainList, this,this);
        recyclerView.setAdapter(recViewAdapter);
        //RecyclerView Code ends:



    }

    @Override
    public void showDayAndNight(int position) {

        Status = position;
        Log.d("Position is is is:",""+Status);
        final FiveDay.DailyCast dailyCast = mainList.get(position);

        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ssX", Locale.US);
        Date date = null;
        try {
            date = format.parse(dailyCast.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy");

        headlineText.setText("Forecast on "+formatter.format(date));
        headlineAns.setText("Temperature: "+ dailyCast.getMaxtemperature()+" / "+dailyCast.getMintemerature());


        findViewById(R.id.textView8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = dailyCast.getInner_mobilelink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        findViewById(R.id.textView9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(Mainurl));
                startActivity(i);
            }
        });




        if(Integer.parseInt(dailyCast.getDay_icon()) < 10) {
            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/0"+Integer.parseInt(dailyCast.getDay_icon())+"-s.png").into(dayImage);
            daymsg.setText(dailyCast.getDay_phrase());
        }else {
            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+Integer.parseInt(dailyCast.getDay_icon())+"-s.png").into(dayImage);
            daymsg.setText(dailyCast.getDay_phrase());
        }

        if(Integer.parseInt(dailyCast.getNight_icon()) <10) {
            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/0"+Integer.parseInt(dailyCast.getNight_icon())+"-s.png").into(nightImage);
            nightmsg.setText(dailyCast.getNight_phrase());

        }else {
            Picasso.with(CityWeather.this).load("http://developer.accuweather.com/sites/default/files/"+Integer.parseInt(dailyCast.getNight_icon())+"-s.png").into(nightImage);
            nightmsg.setText(dailyCast.getNight_phrase());

        }

    }

    @Override
    public void onClickStar(int position) {

    }

    @Override
    public void OnLongClickDelete(int position) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.city_weather_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save:
                String Tempkey = keyvalue;
                //gets the temperature of the city => to store in Firebase
                new CurForecastAPIParser(CityWeather.this).execute("http://dataservice.accuweather.com/currentconditions/v1/"+Tempkey+"?apikey=MdLqXVqGYJrQ9ENW4ZHJeqEWGMnnfoZV");
                return true;
            case R.id.current:
                setAsCurretnCity();
                return true;
            case R.id.city_settings:
                settings();
                Log.d("asdasd","menu clicked");
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


    private void setAsCurretnCity() {


        String getkey = sharedpreferences.getString(MainActivity.Key, null);
        String citynamefromalert = sharedpreferences.getString(MainActivity.city, null);
        String countrynamefromalert = sharedpreferences.getString(MainActivity.country, null);

        if(keyvalue.equals(getkey)) {
            Toast.makeText(this, "Current city Updated", Toast.LENGTH_SHORT).show();
        }
        else {
            //For Shared Preferenc
            editor = sharedpreferences.edit();
            editor.putString(MainActivity.Key, keyvalue);
            editor.putString(MainActivity.city, cityName);
            editor.putString(MainActivity.country, countryName);
            editor.commit();
            Toast.makeText(this, "Current city Saved", Toast.LENGTH_SHORT).show();
        }


    }

    //To save the city in firebase
    @Override
    public void sendCurForecast(ForecastAPI forecastAPI) {

        String Tempkey = keyvalue;
        String TempCityName = cityName;
        String TempCountryName = countryName;
        String Temperature = forecastAPI.getMetric();
        boolean favorite = false;

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        SaveInFirebase save_obj = new SaveInFirebase(TempCountryName,
                                                    dateFormat.format(date),
                                                    Tempkey,
                                                    favorite,
                                                    Temperature, TempCityName);


        myRef.child("Cities").child(Tempkey).setValue(save_obj);



        myRef.child("Cities").child(Tempkey).child("time").setValue(dateFormat.format(date));

        if(FirebaseListKey.contains(Tempkey)) {
            for(int i=0;i<FirebaseList.size();i++) {
                SaveInFirebase s = FirebaseList.get(i);
                if(s.getKey().equals(Tempkey) ) {
                    favorite=s.isFavorite();
                    myRef.child("Cities").child(Tempkey).child("favorite").setValue(true);
                    s = new SaveInFirebase(TempCountryName,
                            dateFormat.format(date),
                            Tempkey,
                            favorite,
                            Temperature, TempCityName);
                FirebaseList.remove(i);FirebaseList.add(i,s);
                }
            }
            Toast.makeText(this, "City Updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "City Saved", Toast.LENGTH_SHORT).show();
            FirebaseList.add(save_obj);
        }
    }
}
