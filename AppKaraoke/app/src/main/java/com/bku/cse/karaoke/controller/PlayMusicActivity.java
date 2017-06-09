package com.bku.cse.karaoke.controller;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bku.cse.karaoke.R;

import java.io.IOException;

/**
 * Created by thonghuynh on 6/9/2017.
 */

public class PlayMusicActivity extends AppCompatActivity {

    private VideoView videoView;
    private MediaPlayer mp;
    private SeekBar seekBar;
    private Handler mHandler = new Handler();
    private ImageButton play_btn;
    private TextView time;
    private int position = 0;

    private int device_height;
    private int device_width;
    private String video_height;
    private String video_width;

    private boolean controllerShow = false;
    boolean isPlaying = false;

    MediaPlayer mediaPlayer;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);


        setContentView(R.layout.activity_detail);

        mediaPlayer = new MediaPlayer();
        time = (TextView) findViewById(R.id.media_time);

        //set SOurce
        Intent gI = getIntent();
        try {
            mediaPlayer.setDataSource( gI.getStringExtra("path") );
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }

        seekBar = (SeekBar) findViewById(R.id.mediacontroller_progress);
        seekBar.setPadding(15, 0, 15, 0);

        play_btn = (ImageButton) findViewById(R.id.media_play_pause);

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! isPlaying) {
                    try {
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isPlaying = !isPlaying;
                }
                else {
                    mediaPlayer.pause();
                }

            }
        });


    }
}
