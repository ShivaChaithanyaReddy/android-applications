package com.example.first.weatherapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.example.first.weatherapp.FiveDay.*;
import static com.example.first.weatherapp.MainActivity.sharedpreferences;

/**
 * Created by Chaithanya on 4/8/2017.
 */


public class FiveDayParser2 implements Serializable {


       static public class parser {
        static final String DEGREE  = "\u00b0";

        static FiveDay articlesParser(String in) throws JSONException {

            //    ArrayList<Articles> list = new ArrayList<Articles>();

            FiveDay fiveDay = new FiveDay();

            JSONObject root = new JSONObject(in);

            JSONObject headline_obj = root.getJSONObject("Headline");
        String headline = headline_obj.getString("Text");
        fiveDay.setHeadline(headline);

            String headlink = headline_obj.getString("MobileLink");
            fiveDay.setHeadline_link(headlink);

            JSONArray dailyforecasts_array = root.getJSONArray("DailyForecasts");


            ArrayList<DailyCast> arrayList = new ArrayList<>();
            for(int i=0;i<dailyforecasts_array.length();i++) {

                FiveDay.DailyCast dailyCast = new FiveDay.DailyCast();

                JSONObject each = dailyforecasts_array.getJSONObject(i);
            String date  = each.getString("Date");
            dailyCast.setDate(date);
                JSONObject temperature_obj = each.getJSONObject("Temperature");
                JSONObject mintemp_obj = temperature_obj.getJSONObject("Minimum");

                String minvalue = mintemp_obj.getString("Value");
                String minunits = mintemp_obj.getString("Unit");

                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(1);


                String Tempunits = sharedpreferences.getString(MainActivity.tempp, null);

                String minTemp = null;
                if(minunits.equals("F"))
                    minTemp = "Fahrenheit";
                else
                    minTemp = "Celcius";

                Log.d("llllllllllllllll",""+Tempunits+" minvalue is: "+ minTemp);

                if(Tempunits.equals("Fahrenheit")) {
                    if(minTemp.equals("Fahrenheit")) {
                        dailyCast.setMintemerature(minvalue+ DEGREE + "F");
                    }else
                        dailyCast.setMintemerature(String.valueOf(df.format(( (1.8)*(Float.parseFloat(minvalue)) + 32)  )) + DEGREE + "F");

                }else {
                    if(minTemp.equals("Fahrenheit")) {
                        dailyCast.setMintemerature(String.valueOf(df.format(((Float.parseFloat(minvalue) - 32) * 5) / 9 )) + DEGREE + "C");
                    }else
                        dailyCast.setMintemerature(minvalue+ DEGREE + "C");
                }




                JSONObject maxtemp_obj = temperature_obj.getJSONObject("Maximum");

                String maxvalue = maxtemp_obj.getString("Value");
                String maxunits = maxtemp_obj.getString("Unit");


                String maxTemp = null;
                if(maxunits.equals("F"))
                    maxTemp = "Fahrenheit";
                else
                    maxTemp = "Celcius";

                if(Tempunits.equals("Fahrenheit")) {
                    if(maxTemp.equals("Fahrenheit")) {
                        dailyCast.setMaxtemperature(maxvalue+ DEGREE + "F");
                    }else
                        dailyCast.setMaxtemperature(String.valueOf(df.format(( (1.8)*(Float.parseFloat(maxvalue)) + 32)  )) + DEGREE + "F");
                }else {
                    if(maxTemp.equals("Fahrenheit")) {
                        dailyCast.setMaxtemperature(String.valueOf(df.format(((Float.parseFloat(maxvalue) - 32) * 5) / 9)) + DEGREE + "C");
                    }else
                        dailyCast.setMaxtemperature(maxvalue+ DEGREE + "C");
                }




                JSONObject day_obj = each.getJSONObject("Day");

                dailyCast.setDay_icon(day_obj.getString("Icon"));
                dailyCast.setDay_phrase(day_obj.getString("IconPhrase"));


                JSONObject night_obj = each.getJSONObject("Night");

                dailyCast.setNight_icon(night_obj.getString("Icon"));
                dailyCast.setNight_phrase(night_obj.getString("IconPhrase"));


                dailyCast.setInner_mobilelink(each.getString("MobileLink"));

                arrayList.add(dailyCast);
            }

            fiveDay.setArrayList(arrayList);

            return fiveDay;
        }
    }

}
