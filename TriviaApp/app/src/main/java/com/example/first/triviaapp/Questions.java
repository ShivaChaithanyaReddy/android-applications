package com.example.first.triviaapp;

import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Chaithanya on 2/8/2017.
 */

public class Questions  implements Serializable{

    String id;
    String text;
    String image;
    List<String> choices = new ArrayList<>();
    String answer;
    Bitmap bitmap;

    static public Questions createQuestion(JSONObject eachQuestionObject) throws JSONException {
        Questions questions = new Questions();
        questions.setId(eachQuestionObject.getString("id"));
        questions.setText(eachQuestionObject.getString("text"));

        if(!eachQuestionObject.has("image"))
            questions.setImage("notAvailable");
        else {
            //new GetImageAsync().execute(eachQuestionObject.getString("image"));

            questions.setImage(eachQuestionObject.getString("image"));
        }



        JSONObject choicesobject = eachQuestionObject.getJSONObject("choices");
        JSONArray inarray = choicesobject.getJSONArray("choice");
      //  Log.d("First",inarray.getString(0));
        questions.setChoices(inarray);
        questions.setAnswer(choicesobject.getString("answer"));
        return questions;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", image='" + image + '\'' +
                ", choices=" + choices +
                ", answer='" + answer + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(JSONArray arrayjson) {

      // Log.d("asdasdsdsa",as);

        for(int i=0;i<arrayjson.length();i++) {
            try {
                choices.add(arrayjson.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
