package com.example.first.triviaapp;


import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.System.in;

/**
 * Created by Chaithanya on 2/6/2017.
 */

public class JSONParsing {

    static public class parser {
        static ArrayList<Questions> questionsParser(String in) throws JSONException {

            ArrayList<Questions> arrayList = new ArrayList<Questions>();

            JSONObject root =  new JSONObject(in);
            JSONArray ArticlesArray = root.getJSONArray("questions");

            for(int i=0;i<ArticlesArray.length();i++) {
            JSONObject eachQuestionObject = ArticlesArray.getJSONObject(i);
            Questions questions = Questions.createQuestion(eachQuestionObject);

            arrayList.add(questions);
        }
        return arrayList;
    }
    }
}
