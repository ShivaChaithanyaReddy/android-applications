package com.example.first.weatherapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static com.example.first.weatherapp.MainActivity.tempp;

/**
 * Created by Chaithanya on 4/9/2017.
 */

public class Prefes extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settigs_preferences);
        

        Preference pref = findPreference(getString(R.string.pref_sync));
        pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {



                Log.d("cvbnbvbnmbvc",""+newValue.toString());
                return true;
            }
        });
    }

}


