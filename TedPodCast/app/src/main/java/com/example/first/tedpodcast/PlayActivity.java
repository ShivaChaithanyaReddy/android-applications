package com.example.first.tedpodcast;

import android.icu.text.DecimalFormat;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PlayActivity extends AppCompatActivity {

    static ImageView pause_play;
    static SeekBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        Podcast podcast = (Podcast) getIntent().getSerializableExtra(RecViewAdapter.OBJECT);

        String title = podcast.getTitle();
        String image = podcast.getImage();
        String desctiption = podcast.getDescription();
        String date = podcast.getReleaseDate().split("&&")[1];
        String duration = podcast.getDuration();


        TextView titleView = (TextView) findViewById(R.id.lasttitle);
        ImageView imageView = (ImageView) findViewById(R.id.imageView5);
        TextView descView = (TextView) findViewById(R.id.lastdescription);
        TextView dateView = (TextView) findViewById(R.id.lastdate);
        TextView durationView = (TextView) findViewById(R.id.lastduration);
        pause_play = (ImageView) findViewById(R.id.pause_play1);
        bar = (SeekBar) findViewById(R.id.progressBar1);

        titleView.setText(title);
        descView.setText("Description: "+desctiption);
        dateView.setText("Publication Date: "+date);
        double dur = Double.parseDouble(duration)/60;

        dur = dur*100;
        dur = Math.round(dur);
        dur = dur/100;
        //dur = (double)Math.round(dur*100)/100;
        durationView.setText("Duration: "+dur+" mins");
        Picasso.with(this).load(image).into(imageView);


        if((RecViewAdapter.flag ==0)) {
            RecViewAdapter.flag =1;
            RecViewAdapter.mediaPlayer = new MediaPlayer();
            CustomMediaPlayer player = new CustomMediaPlayer();
            player.setSongUrlObject(podcast, RecViewAdapter.mediaPlayer,1);
        }
        else {
            RecViewAdapter.mediaPlayer.reset();
            CustomMediaPlayer player = new CustomMediaPlayer();
            player.setSongUrlObject(podcast, RecViewAdapter.mediaPlayer,1);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
        if(RecViewAdapter.flag ==1)
            RecViewAdapter.mediaPlayer.reset();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(RecViewAdapter.flag ==1)
            RecViewAdapter.mediaPlayer.reset();

    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
        if(RecViewAdapter.flag ==1)
            RecViewAdapter.mediaPlayer.reset();
    }
}
