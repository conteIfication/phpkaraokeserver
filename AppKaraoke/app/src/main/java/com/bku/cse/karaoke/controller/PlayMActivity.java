package com.bku.cse.karaoke.controller;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;


import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.helper.SessionManager;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by thonghuynh on 6/13/2017.
 */

public class PlayMActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    TextView tv_songname, tv_uptime, tv_onwer, tv_timer, tv_score;
    ImageButton btn_play;
    SeekBar sb_progress;
    Toolbar toolbar;
    SessionManager session;

    boolean playingFlag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_music);

        toolbar = (Toolbar) findViewById(R.id.pm_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Init
        tv_songname = (TextView) findViewById(R.id.tv_songname);
        tv_uptime = (TextView) findViewById(R.id.tv_uptime);
        tv_onwer = (TextView) findViewById(R.id.tv_owner);
        tv_timer = (TextView) findViewById(R.id.media_time);
        btn_play = (ImageButton) findViewById(R.id.media_play_pause);
        sb_progress = (SeekBar)findViewById(R.id.mediacontroller_progress);
        tv_score = (TextView) findViewById(R.id.pm_score);
        Bundle mBun = getIntent().getExtras();
        session = new SessionManager(this);

        //Set UI
        tv_songname.setText( mBun.getString("songname") );
        tv_onwer.setText( session.getUserInfo().getName() );
        tv_uptime.setText( mBun.getString("up_time") );
        tv_score.setText( "" + mBun.getInt("score") );

        //media init
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource( mBun.getString("path") );
            mediaPlayer.prepare();
            sb_progress.setMax( mediaPlayer.getDuration() );

        } catch (IOException e) {
            e.printStackTrace();
        }

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (playingFlag) {
                    pausePlaying();
                }
                else {
                    startPlaying();
                }
            }
        });

        //set on complete media
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying();
            }
        });

    }
    public void pausePlaying() {
        playingFlag = false;
        mediaPlayer.pause();
        btn_play.setImageResource( R.drawable.ic_media_play_dark );
        handler.removeCallbacks( UpdateSongTime );

    }
    public void startPlaying() {
        playingFlag = true;
        mediaPlayer.start();
        btn_play.setImageResource( R.drawable.ic_media_pause_dark );
        UpdateSongTime.run();
    }
    public void stopPlaying() {
        playingFlag = false;
        btn_play.setImageResource( R.drawable.ic_media_play_dark );
        handler.removeCallbacks( UpdateSongTime );
    }

    Handler handler = new Handler();
    PlayingRunable UpdateSongTime = new PlayingRunable();
    public  class  PlayingRunable implements   Runnable {
        @Override
        public void run() {
            int currentTime = mediaPlayer.getCurrentPosition();
            long minutes = TimeUnit.MILLISECONDS.toMinutes((long) currentTime);
            final long seconds =
                    TimeUnit.MILLISECONDS.toSeconds((long) currentTime) - TimeUnit.MINUTES.toSeconds(minutes);

            //Set Current Time -> TextView, ProgressBar
            tv_timer.setText(String.format("%s:%s", minutes < 10 ? "0" + minutes : "" + minutes, (seconds < 10) ? "0" + seconds : "" + seconds));
            sb_progress.setProgress(currentTime);

            handler.postDelayed(this, 100);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:break;
        }

        return true;
    }
}
