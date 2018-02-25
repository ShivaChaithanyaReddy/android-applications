package com.example.first.weatherapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import static com.example.first.weatherapp.MainActivity.sharedpreferences;
import static com.example.first.weatherapp.MainActivity.tempp;

/**
 * Created by Chaithanya on 4/10/2017.
 */

public class Temperature_preference extends ListPreference {

    static final String DEGREE  = "\u00b0";

    //For Shared Preferences
    SharedPreferences.Editor editor;
    public static final String MyTemp = "MyPrefTemp" ;

    private int mClickedDialogEntryIndex;

    public Temperature_preference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int getValueIndex() {
        return findIndexOfValue(this.getValue() +"");
    }


    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        String temperat = sharedpreferences.getString(MainActivity.tempp, null);


        Log.d("botoototoototoott",""+positiveResult);
        editor = sharedpreferences.edit();
        editor.putString(tempp, getEntry().toString());
        editor.commit();

    if (getEntry().equals("Celcius")) {
        if(temperat.equals("Celcius")) {
            Toast.makeText(getContext(), "No changes made", Toast.LENGTH_SHORT).show();
        }else
        Toast.makeText(getContext(), "Temperature Unit has been Updated to" + DEGREE + "C from" + DEGREE + "F", Toast.LENGTH_SHORT).show();
    } else if(getEntry().equals("Fahrenheit")){
        if(temperat.equals("Fahrenheit")) {
            Toast.makeText(getContext(), "No changes made", Toast.LENGTH_SHORT).show();
        }else
        Toast.makeText(getContext(), "Temperature Unit has been changed to" + DEGREE + "F from " + DEGREE + "C", Toast.LENGTH_SHORT).show();
    }else {
        Toast.makeText(getContext(), "No changes made", Toast.LENGTH_SHORT).show();

    }

    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);


        String temperature = sharedpreferences.getString(MainActivity.tempp, null);


        CharSequence[] cc = getEntryValues();
        for(int i=0;i<cc.length;i++) {
            Log.d("asdfghjgfdsa",""+cc[i]);
            if(temperature.equals(cc[i])) {
                setValueIndex(i);
            }
        }


        mClickedDialogEntryIndex = getValueIndex();
        builder.setSingleChoiceItems(this.getEntries(), mClickedDialogEntryIndex, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mClickedDialogEntryIndex = which;

            }
        });

        builder.setTitle("Choose temperature unit");
        Log.d("",getEntry() + " " + this.getEntries()[0]);
        builder.setPositiveButton("Done", this);
        builder.setCancelable(false);
        builder.setNegativeButton("",this);
    }

    public  void onClick (DialogInterface dialog, int which)
    {
        this.setValue(this.getEntryValues()[mClickedDialogEntryIndex]+"");
    }
}
