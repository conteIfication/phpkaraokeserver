package com.bku.cse.karaoke.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.helper.BaseURLManager;
import com.bku.cse.karaoke.helper.DatabaseHelper;
import com.bku.cse.karaoke.helper.SessionManager;
import com.bku.cse.karaoke.model.FavoriteSong;
import com.bku.cse.karaoke.rest.ApiClient;
import com.bku.cse.karaoke.rest.ApiInterface;
import com.bku.cse.karaoke.util.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.IOException;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by thonghuynh on 5/29/2017.
 */

public class SelectModeKaraokeActivity extends AppCompatActivity {
    private static final int REQUEST_LOGIN = 120;
    Toolbar toolbar;
    LinearLayout linearLayout;
    ImageView imageView;
    TextView tv_songname, tv_singer;
    Button btn_sing_record;
    TextView btn_just_play;
    RadioGroup rg_record_type;
    Bundle mBundle;
    SessionManager session;
    Menu menu;
    DatabaseHelper db;
    MenuItem menu_add_favorite;
    BaseURLManager baseURL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utils.checkTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mode_karaoke);

        imageView = (ImageView)findViewById(R.id.img_song_mode_kar);
        tv_songname = (TextView) findViewById(R.id.song_name_mode_kar);
        tv_singer = (TextView) findViewById(R.id.singer_mode_kar);
        btn_just_play = (TextView) findViewById(R.id.btn_sing_play);
        btn_sing_record = (Button) findViewById(R.id.btn_sing_record);
        rg_record_type = (RadioGroup) findViewById(R.id.record_type);
        mBundle = new Bundle();
        session = new SessionManager(getApplicationContext());
        db = new DatabaseHelper(getApplicationContext());

        linearLayout = (LinearLayout) findViewById(R.id.linearLayout_mode_kar);
        baseURL = new BaseURLManager(this);
        toolbar = (Toolbar)findViewById(R.id.toolbar_mode_karaoke);
        toolbar.setTitle("Select karaoke mode");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get data of song
        Intent gI = getIntent();
        prepareSong(gI);


        //Create Event
        View.OnClickListener btn_sing_record_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iSing = null;
                String record_type = "";
                //check Audio or Video
                if ( rg_record_type.getCheckedRadioButtonId() == R.id.rb_audio ) {
                    //Audio Type
                    iSing = new Intent(getApplicationContext(), SingRecordAudioActivity.class);
                    record_type = "audio";
                }
                else {
                    //Video Type
                    iSing = new Intent(getApplicationContext(), SingRecordVideoActivity.class);
                    record_type = "video";
                }
                //Put Info Of song
                mBundle.putString("record_type", record_type);
                iSing.putExtras(mBundle);

                //check login
                if ( session.isLoggedIn() ) {
                    //add view_no
                    ApiInterface apiService = ApiClient.getClient(getBaseContext()).create(ApiInterface.class);
                    apiService.increaseView( mBundle.getInt("kid") ).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });


                    //start
                    startActivity(iSing);
                    finish();
                }
                else  {
                    startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), REQUEST_LOGIN);
                }
            }
        };

        View.OnClickListener btn_just_play_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iSing = new Intent( getApplicationContext(), JustPlayActivity.class );
                iSing.putExtras( mBundle );
                //check login
                if ( session.isLoggedIn() ) {
                    startActivity(iSing);
                    finish();
                }
                else  {
                    startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class), REQUEST_LOGIN);
                }
            }
        };

        //Set Event
        btn_sing_record.setOnClickListener( btn_sing_record_listener );
        btn_just_play.setOnClickListener( btn_just_play_listener );
    }

    public void prepareSong(Intent i) {
        String name = i.getStringExtra("name");
        String image = i.getStringExtra("image");
        String subtitle_path = i.getStringExtra("subtitle_path");
        String beat_path = i.getStringExtra("beat_path");
        int kid = i.getIntExtra("kid", 0);
        String singer = i.getStringExtra("singer");

        //set to Bundle
        mBundle.putString( "name", name );
        mBundle.putString( "image", image );
        mBundle.putString( "subtitle_path", subtitle_path );
        mBundle.putString( "beat_path", beat_path );
        mBundle.putInt( "kid", kid );
        mBundle.putString( "singer", singer );

        tv_singer.setText(singer);
        tv_songname.setText(name);
        Glide.with(getApplicationContext()).load(baseURL.getBaseURL() + image)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_mode_karaoke, menu);

        menu_add_favorite = menu.findItem(R.id.add_favorite);
        //setFavorite Icon
        if ( db.get_FavoriteKS( mBundle.getInt("kid") ) != null ) {
            menu_add_favorite.setIcon( R.drawable.ic_star_yellow );
        }
        else {
            menu_add_favorite.setIcon( R.drawable.ic_star_white );
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_favorite:
                if ( db.get_FavoriteKS(mBundle.getInt("kid")) == null) {
                    //not yet Favorite
                    db.add_FavoriteKS( new FavoriteSong(0, new Date().toString(), mBundle.getInt("kid") ) );
                    menu_add_favorite.setIcon( R.drawable.ic_star_yellow );
                    Toast.makeText(getApplicationContext(), "Added favorite song", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.remove_FavoriteKS( mBundle.getInt("kid") );
                    menu_add_favorite.setIcon( R.drawable.ic_star_white );
                    Toast.makeText(getApplicationContext(), "Removed favorite song", Toast.LENGTH_SHORT).show();
                }
                break;
            case android.R.id.home:
                finish();
                break;

            default:break;
        }

        return true;
    }
}
