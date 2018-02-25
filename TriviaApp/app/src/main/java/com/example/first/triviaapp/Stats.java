package com.example.first.triviaapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;

public class Stats extends AppCompatActivity {

    HashMap<String,FinalAnswers> hashMap;
    LinearLayout linearLayout;
    int count;
    ProgressBar progressBar;
    TextView percentageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        hashMap = new HashMap<>();

        hashMap = (HashMap<String,FinalAnswers>) getIntent().getSerializableExtra(Trivia.FINAL_ANS);
        count = getIntent().getIntExtra(Trivia.COUNT,0);

        int wrongCount=0;


        count  = count+1;
        Log.d("COunt-total size: ",""+count);

        linearLayout = (LinearLayout) findViewById(R.id.slayout);

        Iterator iterator = hashMap.keySet().iterator();
        while(iterator.hasNext()) {
            String key = (String) iterator.next();
            FinalAnswers value = (FinalAnswers) hashMap.get(key);

            if(!value.getGivenAnswer().equals(value.getCorrectAnswer())) {

                wrongCount++;
                TextView textView = new TextView(this);
                textView.setTextColor(ContextCompat.getColor(this,R.color.colorBlack));
                textView.setText(value.getQuestion());
                TextView yourAns = new TextView(this);
                if(value.getGivenAnswer().trim().equals("-1")) {
                    yourAns.setBackgroundColor(Color.parseColor("#ff0000"));
                    yourAns.setText("Unanswered");
                }
                else{
                    yourAns.setBackgroundColor(Color.parseColor("#ff0000"));
                    yourAns.setText(value.getGivenAnswer());

                }
                TextView correctAns = new TextView(this);
                correctAns.setText(value.getCorrectAnswer());
                linearLayout.addView(textView);
                linearLayout.addView(yourAns);
                linearLayout.addView(correctAns);

            }

            //Log.d("values:",value.getCorrectAnswer()+" "+value.getGivenAnswer());
        }
        setPerformance(wrongCount);


    }

    public void setPerformance(int wrongCount) {

            double val = ((double) wrongCount/(double) count);
           double percentage = val*100;
           // int percentage = (int) val*100;
       Log.d("percentage:"," Wrong:"+wrongCount+"Complete"+count+"percentage"+val);

        percentageView = (TextView) findViewById(R.id.percentageText);
        progressBar  = (ProgressBar) findViewById(R.id.progressBar2);

            percentageView.setText((100-(int)percentage)+"%");
            progressBar.setProgress(100 - (int)percentage);

    }

    public void onClickFinish(View view) {
        finish();
        Intent intent = new Intent(Stats.this, MainActivity.class);
        startActivity(intent);
    }

}
