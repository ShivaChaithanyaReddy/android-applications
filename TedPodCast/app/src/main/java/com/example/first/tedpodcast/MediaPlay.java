package com.example.first.tedpodcast;

import android.media.MediaPlayer;
import android.util.Log;
import android.widget.SeekBar;

/**
 * Created by Chaithanya on 3/11/2017.
 */

public class MediaPlay {

    private MediaPlayer mPlayer;
    Podcast podcast;

    public MediaPlay(Podcast podcast) {
        this.podcast = podcast;
        playMusic();
    }

    private void playMusic() {
        try {
            mPlayer = new MediaPlayer();

            mPlayer.setDataSource(podcast.getLink());
            mPlayer.prepareAsync();
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mp) {
                    mPlayer.start();

                }
            });
            mPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    // Media player will required Track's information to give
                    // you buffering percentage. So once check after starting
                    // player are you getting track duration from
                    // MediaPlayer.getDuration()

                    Log.e("onBufferingUpdate", "" + percent);
                    MainActivity.progressBar.setSecondaryProgress(percent);

                    // *****
                    // Buffering update will be correct if mp.getDuration() > 0
                    // double ratio = percent / 100.0;
                    // int bufferingpercent = (int) (mp.getDuration() * ratio);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SeekBar.OnSeekBarChangeListener seekbarListner = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            if (fromUser) {
                if (mPlayer != null) {
                    if (progress <= seekBar.getSecondaryProgress()) {
                    } else {
                        seekBar.setProgress(progress);
                    }

                }
            }
        }
    };
}
