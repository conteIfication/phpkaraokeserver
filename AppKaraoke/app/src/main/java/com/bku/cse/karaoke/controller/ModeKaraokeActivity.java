package com.bku.cse.karaoke.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.rest.ApiClient;
import com.bku.cse.karaoke.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by thonghuynh on 5/29/2017.
 */

public class ModeKaraokeActivity extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout linearLayout;
    ImageView imageView;
    TextView tv_songname, tv_singer;
    Button btn_sing_record;
    TextView btn_sing_play;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utils.checkTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_karaoke);

        imageView = (ImageView)findViewById(R.id.img_song_mode_kar);
        tv_songname = (TextView) findViewById(R.id.song_name_mode_kar);
        tv_singer = (TextView) findViewById(R.id.singer_mode_kar);
        btn_sing_play = (TextView) findViewById(R.id.btn_sing_play);
        btn_sing_record = (Button) findViewById(R.id.btn_sing_record);

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_mode_kar);
        toolbar = (Toolbar)findViewById(R.id.toolbar_mode_karaoke);
        toolbar.setTitle("Select karaoke mode");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent gI = getIntent();
        prepareSong(gI);

    }

    public void prepareSong(Intent i) {
        String name = i.getStringExtra("name");
        String image = i.getStringExtra("image");
        String subtitle_path = i.getStringExtra("subtitle_path");
        String beat_path = i.getStringExtra("beat_path");
        String kid = i.getStringExtra("kid");
        String singer = i.getStringExtra("singer");

        tv_singer.setText(singer);
        tv_songname.setText(name);
        Glide.with(getApplicationContext()).load(ApiClient.BASE_URL + image)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_mode_karaoke, menu);

        return true;
    }
}
