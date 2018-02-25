package com.example.first.weatherapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.DialogPreference;
import android.support.annotation.RequiresApi;
import android.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.first.weatherapp.MainActivity.country;
import static com.example.first.weatherapp.MainActivity.sharedpreferences;

/**
 * Created by Chaithanya on 4/10/2017.
 */

public class current_city_preferences extends DialogPreference implements JSONParsing.IActivity{

    Context context;
    EditText city;
    EditText country;

    String kk;
    String gkk;
    //For Shared Preferences
    SharedPreferences.Editor editor;
    public static final String MyPREFERENCES = "MyPrefs" ;


    public current_city_preferences(Context context, AttributeSet attrs) {
        super(context, attrs);


        aa();
    }

    private void aa() {

        gkk = sharedpreferences.getString(MainActivity.Key, null);
        String citynamefromalert = sharedpreferences.getString(MainActivity.city, null);
        String countrynamefromalert = sharedpreferences.getString(MainActivity.country, null);


        if(gkk != null) {
            setDialogTitle("Update Current City");
            setPositiveButtonText("Update");

        }else {
            setDialogTitle("Enter City Details");
        }

    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        //For Shared Preferences

        if(positiveResult) {
            new JSONParsing(current_city_preferences.this,0).execute("http://dataservice.accuweather.com/locations/v1/"+country.getText().toString()+"/search?apikey=MdLqXVqGYJrQ9ENW4ZHJeqEWGMnnfoZV&q="+city.getText().toString());
        }
    }
    @Override
    protected View onCreateDialogView() {

        LayoutInflater factory = LayoutInflater.from(getContext());
        final View textEntryView = factory.inflate(R.layout.current_city_pref, null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(false);

        String getkey = sharedpreferences.getString(MainActivity.Key, null);
        String citynamefromalert = sharedpreferences.getString(MainActivity.city, null);
        String countrynamefromalert = sharedpreferences.getString(MainActivity.country, null);

        kk = getkey;


        city = (EditText) textEntryView.findViewById(R.id.city_pref_edit_text);
        country = (EditText) textEntryView.findViewById(R.id.editText5);


        if(getkey != null && citynamefromalert != null && countrynamefromalert != null) {

            setDialogTitle("Update city Details"); // <- this should work


            alert.setTitle(R.string.update);
            alert.setView(textEntryView);

            city.setText(citynamefromalert);
            country.setText(countrynamefromalert);

            setPositiveButtonText(R.string.update);

            alert.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("value is ::::::",""+which);
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });


        }


        else {

            setDialogTitle("Enter City Details"); // <- this should work

            alert.setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.d("value is ::::::",""+which);
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

        }

/*        //syntax error in View
        View namebar = View.findViewById(R.id.namebar);
        ((ViewGroup) namebar.getParent()).removeView(namebar);
        */

        //AlertDialog alertDialog = alert.create();
       // alertDialog.show();


        return textEntryView;
        }

    @Override
    public void senddata(LocationAPI value) {

        if(value != null && gkk == null) {
            Toast.makeText(getContext(), R.string.cityDetailsSaved , Toast.LENGTH_SHORT).show();
            //For Shared Preferences
            editor = sharedpreferences.edit();
            editor.putString(MainActivity.Key, String.valueOf(value.getKey()));
            editor.putString(MainActivity.city, city.getText().toString());
            editor.putString(MainActivity.country, country.getText().toString());
            editor.putString(MainActivity.tempp,"Celcius");
            editor.commit();
        }

        else if(value != null && gkk != null) {
            Toast.makeText(getContext(), "Current City Updated" , Toast.LENGTH_SHORT).show();
            //For Shared Preferences
            editor = sharedpreferences.edit();
            editor.putString(MainActivity.Key, String.valueOf(value.getKey()));
            editor.putString(MainActivity.city, city.getText().toString());
            editor.putString(MainActivity.country, country.getText().toString());
            editor.putString(MainActivity.tempp,"Celcius");
            editor.commit();
        }
        else {
            Toast.makeText(getContext(), R.string.cityNotFound , Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendCurForecast(ForecastAPI forecastAPI) {

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

    }

    @Override
    public void OnLongClickDelete(int position) {

    }
}
