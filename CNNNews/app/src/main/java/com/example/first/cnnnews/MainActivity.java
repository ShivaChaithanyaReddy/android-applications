package com.example.first.cnnnews;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RelativeLayout mainLayout;
    ImageView imageView;
    LinearLayout innerLayout;
    ArrayList<News> completeList;
    int x=0;
    int length=0;
    Toast toast;
    DateFormat dateFormat;
    boolean flag = false;
ActionBar actionbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionbar = getSupportActionBar();
        actionbar.setLogo(R.drawable.acnn);
        actionbar.setTitle("CNN News");

        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);

        findViewById(R.id.GetNews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new NewsAsyncTask(MainActivity.this).execute("http://rss.cnn.com/rss/cnn_tech.rss");
            }
        });

    }

    public  void setNews(ArrayList<News> alist) {
        completeList = alist;
        flag = true;
        Log.d("asdsad", alist.toString());
        display();
    }

    public  void display() {


        TextView textView = new TextView(this);
        TextView published = new TextView(this);
        innerLayout = (LinearLayout) findViewById(R.id.ScrollLayout);
        textView.setText(completeList.get(0).getTitle());
        published.setText("Published on: "+completeList.get(0).getPubDate());

        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0,70,0,0);
        published.setLayoutParams(llp);
        innerLayout.addView(textView);innerLayout.addView(published);

        TextView descr = new TextView(this);
        RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        llp1.setMargins(0,80,0,0);
        descr.setLayoutParams(llp1);
        descr.setText("Description:\n "+completeList.get(0).getDesciption() );
        innerLayout.addView(descr);


        imageView = (ImageView)findViewById(R.id.imageView);

        new GetImageAsync(MainActivity.this).execute(completeList.get(0).getImage());

length = completeList.size();

    }



    public  void setall(News news) {

        innerLayout.removeAllViews();


        TextView textView = new TextView(this);
        TextView published = new TextView(this);
        innerLayout = (LinearLayout) findViewById(R.id.ScrollLayout);
        textView.setText(news.getTitle());
        published.setText("Published on: "+ news.getPubDate());


        RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        llp.setMargins(0,70,0,0);
        published.setLayoutParams(llp);
        innerLayout.addView(textView);innerLayout.addView(published);

        TextView descr = new TextView(this);
        RelativeLayout.LayoutParams llp1 = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        llp1.setMargins(0,80,0,0);
        descr.setLayoutParams(llp1);
        descr.setText("Description:\n "+news.getDesciption() );
        innerLayout.addView(descr);


        imageView = (ImageView)findViewById(R.id.imageView);

        new GetImageAsync(MainActivity.this).execute(news.getImage());

    }


    public void onClickRightArrow(View view){

        Log.d("value of X:after right", " " + x);
if(flag != true)
    Toast.makeText(this,"no news found",Toast.LENGTH_SHORT).show();
        else {
    if (x < length - 1) {
        x++;
        News moviex = completeList.get(x);

        setall(moviex);
    } else {
        toast = Toast.makeText(this, "No news found after this year!", Toast.LENGTH_SHORT);
        toast.show();
    }
}

    }
    public void onClickLeftArrow(View view){

        if(flag != true)
            Toast.makeText(this,"no news found",Toast.LENGTH_SHORT).show();
        else {
            if (x > 0) {
                x--;
                News moviex = completeList.get(x);

                setall(moviex);
            } else {
                toast = Toast.makeText(this, "No movies found before this year!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
    public void onClickLeftMost(View view) {

        if (flag != true)
            Toast.makeText(this, "no news found", Toast.LENGTH_SHORT).show();
        else {
            if (x == 0) {
                toast = Toast.makeText(this, "Displaying first movie", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                x = 0;
                News moviex = completeList.get(x);

                setall(moviex);
            }
        }

    }
    public void onClickRightMost(View view){

        if(flag != true)
            Toast.makeText(this,"no news found",Toast.LENGTH_SHORT).show();
        else {
            if (x == length - 1) {
                toast = Toast.makeText(this, "Displaying last available movie", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                x = length - 1;
                News moviex = completeList.get(x);

                setall(moviex);
            }
        }
    }


    public void onClickFinish(View view) {
        finish();
    }



}
