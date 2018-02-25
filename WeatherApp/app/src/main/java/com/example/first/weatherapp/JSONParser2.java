package com.example.first.weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Chaithanya on 4/7/2017.
 */

public class JSONParser2 {

    static public class parser {
        static LocationAPI articlesParser(String in) throws JSONException {

            //    ArrayList<Articles> list = new ArrayList<Articles>();

            JSONArray root = new JSONArray(in);

            if(root.length() > 0) {
                JSONObject object = root.getJSONObject(0);
                LocationAPI obj  = new LocationAPI(Long.parseLong(object.getString("Key")));
                return obj;
            }

            return null;
        }
    }

}

