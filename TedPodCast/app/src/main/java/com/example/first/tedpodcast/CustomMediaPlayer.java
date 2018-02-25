package com.example.first.tedpodcast;

/**
 * Created by Chaithanya on 3/11/2017.
 */
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class CustomMediaPlayer extends Fragment implements MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener{
    private ImageView buttonPlayPause;
    private SeekBar seekBarProgress;
    public String editTextSongURL;

    int flag =0;
    private MediaPlayer mediaPlayer;
    private int mediaFileLengthInMilliseconds; // this value contains the song duration in milliseconds. Look at getDuration() method in MediaPlayer class

    private final Handler handler = new Handler();
    private int fromWhere = 0;

    public CustomMediaPlayer() {
        // Required empty public constructor
    }
    Podcast podcast;

    public void setSongUrlObject(Podcast podcast, MediaPlayer mediaPlayer, int fromWhere) {
        this.podcast = podcast;
        this.mediaPlayer = mediaPlayer;
        this.fromWhere = fromWhere;
        playMusic();

    }

    @Override
    public String toString() {
        return super.toString();
    }

    private void playMusic() {


        editTextSongURL = podcast.getLink();
        Log.d("asdsada"," "+editTextSongURL);

       // mediaPlayer = new MediaPlayer();

        if(fromWhere == 1) {
            PlayActivity.pause_play.setImageResource(R.drawable.pausebutton);
        }
        else
            MainActivity.play_pause.setImageResource(R.drawable.pausebutton);

        try {
            mediaPlayer.setDataSource(podcast.getLink());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
                mediaPlayer.setLooping(false);
                        flag =1;
                mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL
                primarySeekBarProgressUpdater();

            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                if(fromWhere == 1) {
                    PlayActivity.bar.setProgress(0);
                    primarySeekBarProgressUpdater();
                }
                else {
                    MainActivity.progressBar.setProgress(0);
                    primarySeekBarProgressUpdater();
                    MainActivity.play_pause.setVisibility(View.GONE);
                    MainActivity.progressBar.setVisibility(View.GONE);
                }
            }
        });




/*        if(mediaPlayer == null)
        mediaPlayer = new MediaPlayer();
        else {
            mediaPlayer.stop();
        mediaPlayer = new MediaPlayer();
        }*/

        if(fromWhere == 1) {
            buttonPlayPause = PlayActivity.pause_play;
        }
        else
            buttonPlayPause = MainActivity.play_pause;

/*

        final Timer t = new Timer();

        t.schedule(new TimerTask() {

            @Override
            public void run() {
                buttonPlayPause.performClick();

            }

        },500);

*/

        buttonPlayPause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("I am clicked","- playpause");
         //       if(v.getId() == buttonPlayPause.getId()){
                /** ImageButton onClick event handler. Method which start/pause mediaplayer playing */
              if(!mediaPlayer.isPlaying() && flag ==0) {
                  try {
                      mediaPlayer.setDataSource(editTextSongURL); // setup song from http://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                      mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }
                mediaFileLengthInMilliseconds = mediaPlayer.getDuration(); // gets the song length in milliseconds from URL

                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    flag =1;
                    buttonPlayPause.setImageResource(R.drawable.pausebutton);
                } else {
                    mediaPlayer.pause();
                    buttonPlayPause.setImageResource(R.drawable.play);
                }
        primarySeekBarProgressUpdater();
       //     }
            }
        });

        if(fromWhere ==1) {
            seekBarProgress = PlayActivity.bar;
        }
        else
        seekBarProgress = MainActivity.progressBar;
        seekBarProgress.setMax(99); // It means 100% .0-99
        seekBarProgress.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
           //     if(v.getId() == R.id.SeekBarTestPlay){
                    /** Seekbar onTouch event handler. Method which seeks MediaPlayer to seekBar primary progress position*/
                    if(mediaPlayer.isPlaying()){
                        SeekBar sb = (SeekBar)v;
                        int playPositionInMillisecconds = (mediaFileLengthInMilliseconds / 100) * sb.getProgress();
                        mediaPlayer.seekTo(playPositionInMillisecconds);
                    }
         //       }
                return false;
            }
        });



        //mediaPlayer.setOnBufferingUpdateListener(this);
        //mediaPlayer.setOnCompletionListener(this);
    }

    /** Method which updates the SeekBar primary progress by current song playing position*/
    private void primarySeekBarProgressUpdater() {
        seekBarProgress.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaFileLengthInMilliseconds)*100)); // This math construction give a percentage of "was playing"/"song length"
        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    primarySeekBarProgressUpdater();
                }
            };
            handler.postDelayed(notification,1000);
        }
    }




    @Override
    public void onCompletion(MediaPlayer mp) {
        /** MediaPlayer onCompletion event handler. Method which calls then song playing is complete*/

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        /** Method which updates the SeekBar secondary progress by current song loading from URL position*/
        seekBarProgress.setSecondaryProgress(percent);
        Log.d("seconds is: ", percent+" ");
    }


}