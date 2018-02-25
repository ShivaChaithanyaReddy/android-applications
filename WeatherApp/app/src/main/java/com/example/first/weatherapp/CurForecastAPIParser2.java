package com.example.first.weatherapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;

import static com.example.first.weatherapp.MainActivity.sharedpreferences;

/**
 * Created by Chaithanya on 4/7/2017.
 */

public class CurForecastAPIParser2 implements Serializable {


    static public class parser {
        static final String DEGREE  = "\u00b0";

        static ForecastAPI articlesParser(String in) throws JSONException {

            //    ArrayList<Articles> list = new ArrayList<Articles>();

            JSONArray root = new JSONArray(in);

            if(root.length() > 0) {
                JSONObject object = root.getJSONObject(0);


                JSONObject temp_obj = object.getJSONObject("Temperature");
                JSONObject Metric_obj = temp_obj.getJSONObject("Metric");
                String value = Metric_obj.getString("Value");
                String units = Metric_obj.getString("Unit");


                String Tempunits = sharedpreferences.getString(MainActivity.tempp, null);
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(1);


                String minTemp = null;
                if(units.equals("F"))
                    minTemp = "Fahrenheit";
                else
                    minTemp = "Celcius";

                Log.d("llllllllllllllll",""+Tempunits+" minvalue is: "+ minTemp);



                ForecastAPI obj  = new ForecastAPI(object.getString("LocalObservationDateTime"),
                        object.getString("WeatherIcon"),
                        object.getString("WeatherText"),
                        value+DEGREE+units);


                if(Tempunits.equals("Fahrenheit")) {
                    if(minTemp.equals("Fahrenheit")) {
                        obj.setMetric(value+ DEGREE + "F");
                    }else
                        obj.setMetric(String.valueOf(df.format(( ((1.8)*(Float.parseFloat(value))) + 32)  )) + DEGREE + "F");

                }else {
                    if(minTemp.equals("Fahrenheit")) {
                        obj.setMetric(String.valueOf(df.format(((Float.parseFloat(value) - 32) * 5) / 9 )) + DEGREE + "C");
                    }else
                        obj.setMetric(value+ DEGREE + "C");
                }


                return obj;
            }

            return null;
        }
    }

}
