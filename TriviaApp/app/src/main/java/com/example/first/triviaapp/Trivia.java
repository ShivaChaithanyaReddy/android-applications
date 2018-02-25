package com.example.first.triviaapp;

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Trivia extends AppCompatActivity {

    ArrayList < Questions > listQuestions = new ArrayList < Questions > ();
    int Qcount = 0;
    RelativeLayout questionsLayout;
    RelativeLayout loadingLayout;
    ImageView image;
    Button previous;
    Button next;
    LinearLayout scroll;
    ArrayList < FinalAnswers > answers;
    RadioGroup rg;
    int answered;
    int x, z, count = 0;
    HashMap < String, FinalAnswers > hashMap;
    Intent intent;
    static final String FINAL_ANS = "ans";
    static final String COUNT = "count  ";
    TextView time;
    TextView Qnumber;
    CountDownTimer countDownTimer;
    int Numberv =0;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        questionsLayout = (RelativeLayout) findViewById(R.id.RelQuestion);
        loadingLayout = (RelativeLayout) findViewById(R.id.loadingPanel);
        image = (ImageView) findViewById(R.id.imageView2);
        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);

        answers = new ArrayList < FinalAnswers > ();
        hashMap = new HashMap < > ();

        listQuestions = (ArrayList < Questions > ) getIntent().getSerializableExtra(MainActivity.QUESTIONS);

        relLayout(listQuestions.get(Qcount));
        Qcount = 0;


        previous.setEnabled(false);
        previous.setBackground(ContextCompat.getDrawable(Trivia.this, R.drawable.button_disabled));

        time = (TextView) findViewById(R.id.time);


        //Start Timer
        time.setText("Time Left: 120 seconds");

        countDownTimer = new CountDownTimer(10 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText("Time Left: " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {

                for (Questions q: listQuestions) {
                    if (!hashMap.containsKey(q.getId())) {
                        FinalAnswers finalAnswers = new FinalAnswers();
                        finalAnswers.setQuestion(q.getText());
                        finalAnswers.setGivenAnswer(String.valueOf(-1));
                        finalAnswers.setCorrectAnswer(q.getAnswer());
                        hashMap.put(q.getId(), finalAnswers);


                    }
                }
                intent = new Intent(Trivia.this, Stats.class);
                intent.putExtra(FINAL_ANS, hashMap);
                intent.putExtra(COUNT, listQuestions.size() - 1);
                startActivity(intent);
                finish();

            }
        };

        countDownTimer.start();




        findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Qcount == 0) {

                    previous.setEnabled(false);
                    previous.setBackground(ContextCompat.getDrawable(Trivia.this, R.drawable.button_disabled));

                }
                else {
                    Log.d("hashmap ContainsKey","");
                    int x = rg.getCheckedRadioButtonId();
                    /////////////// for setting current question
                    Questions current = listQuestions.get(Qcount);
                    if (hashMap.containsKey(current.getId())) {
                        //Log.d("hashmap ContainsKey", " " + hashMap.get(current.getId()).getQuestion() + " with ans" + hashMap.get(current.getId()).getGivenAnswer());
                        hashMap.get(current.getId()).setGivenAnswer(String.valueOf(x));
                    } else {
                    //    Log.d("added Current:", " ");
                        FinalAnswers obj = new FinalAnswers();
                        obj.setQuestion(current.getText());
                        obj.setCorrectAnswer(current.getAnswer());
                        obj.setGivenAnswer(String.valueOf(x));
                        hashMap.put(current.getId(), obj);

                    }



                    ////////////// for setting current question....

                    Questions temp = listQuestions.get(Qcount - 1);
                    if (hashMap.containsKey(temp.getId())) {
                    //    Log.d("hashmap ContainsKey", " " + hashMap.get(temp.getId()).getQuestion() + " with ans" + hashMap.get(temp.getId()).getGivenAnswer());
                        // hashMap.get(temp.getId()).setGivenAnswer(String.valueOf(x));
                    } else {

                        FinalAnswers obj = new FinalAnswers();
                        obj.setQuestion(temp.getText());
                        obj.setCorrectAnswer(temp.getAnswer());
                        obj.setGivenAnswer(String.valueOf(x));
                        hashMap.put(temp.getId(), obj);

                    }

                    Qcount--;
                    Log.d("previous is",""+Qcount);
                    Numberv = 1;
                    relLayout(listQuestions.get(Qcount));
                }
            }
        });


        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Qcount == listQuestions.size()-2){
                    next.setText("Finish");
                }

                if (Qcount == listQuestions.size() - 1) {

                  //  next.setText("Finish");


                            int x = rg.getCheckedRadioButtonId();

                            Questions temp = listQuestions.get(Qcount);

                            if (hashMap.containsKey(temp.getId())) {
                                hashMap.get(temp.getId()).setGivenAnswer(String.valueOf(x));
                                //                      }
                            } else {

                                FinalAnswers obj = new FinalAnswers();
                                obj.setQuestion(temp.getText());
                                obj.setCorrectAnswer(temp.getAnswer());
                                obj.setGivenAnswer(String.valueOf(x));

                                hashMap.put(temp.getId(), obj);

                            }


                            intent = new Intent(Trivia.this, Stats.class);
                            intent.putExtra(FINAL_ANS, hashMap);
                            intent.putExtra(COUNT, listQuestions.size() - 1);
                    finish();
                            startActivity(intent);


                } else {

                    Questions temp = listQuestions.get(Qcount);
                    // Log.d("questionIfcontains"," "+(Qcount+1)+"abc"+temp.getText());
                    int x = rg.getCheckedRadioButtonId();
//                    Log.d("present checked is :", " " + " " + x);

                    if (hashMap.containsKey(temp.getId())) {
  //                      Log.d("This is present:", " " + " " + hashMap.get(temp.getId()).getQuestion());

                        hashMap.get(temp.getId()).setGivenAnswer(String.valueOf(x));

    //                    Log.d("containsnext:", "");
                    } else {

                        FinalAnswers obj = new FinalAnswers();
                        obj.setQuestion(temp.getText());
                        obj.setCorrectAnswer(temp.getAnswer());
                        obj.setGivenAnswer(String.valueOf(x));
                    //    Log.d("added questiont:", " " + temp.getText() + " ans: " + obj.getGivenAnswer());

                        hashMap.put(temp.getId(), obj);

                    }
                    Qcount++;
Log.d("Qcount sending: "," "+Qcount);
                    relLayout((listQuestions.get(Qcount)));
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void relLayout(Questions questions) {

        TextView Question = new TextView(this);
        if ((Qcount > 0) || (Qcount==0 && Numberv==1))
            scroll.removeAllViews();

        scroll = (LinearLayout) findViewById(R.id.ScrollLayout);

        Question.setText(questions.getText());
        Qnumber = (TextView) findViewById(R.id.Qnumber);
        Qnumber.setText("Q"+(Qcount+1));

        Log.d("hashmap ContainsKey", " "+questions.getText());

        scroll.addView(Question);
        new DownloadImageAsync(this).execute(questions.getImage(), String.valueOf(Qcount));

        setRadioButtons(questions.getChoices(), questions);
    }

    public void setRadioButtons(List < String > list, Questions questions) {
        ScrollView sv = new ScrollView(this);
        int aa = 0;
        int ada = 0;

        if (hashMap.containsKey(questions.getId())) {

            aa = Integer.parseInt(hashMap.get(questions.getId()).getGivenAnswer());
            ada = 12;

        }

        rg = new RadioGroup(this);
        count++;

        int length = list.size();
        z = length;
        for (int i = 0; i < length; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setText(list.get(i));
            rb.setId(i + 1);
            if ((aa == rb.getId()) && ada == 12) {
                rb.toggle();
                ada = aa;
            }
            rg.addView(rb);
        }
        scroll.addView(rg);
    }

}