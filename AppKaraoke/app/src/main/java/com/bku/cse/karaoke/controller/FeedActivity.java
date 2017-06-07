package com.bku.cse.karaoke.controller;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.cse.karaoke.R;

import com.bku.cse.karaoke.helper.DatabaseHelper;
import com.bku.cse.karaoke.helper.SessionManager;
import com.bku.cse.karaoke.model.KaraokeSong;
import com.bku.cse.karaoke.model.RecordedSong;
import com.bku.cse.karaoke.util.Utils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Date;
import java.util.List;


public class FeedActivity extends AppCompatActivity {
    private static final String TAG = FeedActivity.class.getSimpleName();
    private static final int REQUEST_CODE_LOGIN = 11;
    private static final int REQUEST_CODE_SETTING = 12;
    public final int REQUEST_INTERNET = 123;
    public final int REQUEST_W_EXTERNAL = 124;
    public final int REQUEST_RECORD_AUDIO = 125;

    BottomBar bottomBar;
    SessionManager session;
    private BottomBarTab bottomBarTab_me;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utils.checkTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //Init UI
        bottomBar = (BottomBar) findViewById(R.id.bottombar);
        session = new SessionManager(getApplicationContext());
        bottomBarTab_me = (BottomBarTab) findViewById(R.id.tab_me);

        //Set Feed Tab Selected
        bottomBar.selectTabAtPosition(1);

        //check Login Status
        if ( !session.isLoggedIn() ) {
            bottomBarTab_me.setTitle("Guest");
        }else {
            bottomBarTab_me.setTitle("Me");
        }

        //Test database
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        RecordedSong recordedSong = new RecordedSong(
                0, 0, "path__", "audio", true, new Date().toString(), 1, 2

        );

//        db.addRecordedSong(recordedSong);
        List<RecordedSong> listKar = null;
        listKar = db.get_AllRecordedSong();
        for (RecordedSong recordedSong1: listKar) {

            Log.d("Data Karaoke", "" + recordedSong1.getPath());

        }
        db.closeDB();

        //bottom bar listener
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_song_book:  //SONGBOOK tab
                        finish();
                        Intent sbIntent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(sbIntent);
                        break;
                    case R.id.tab_feed:    //FEED tab
                        Toast.makeText(getApplicationContext(), "Feed", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tab_me:       //ME tab
                        if ( session.isLoggedIn() ) {
                            //login
                            finish();
                            Intent meIntent = new Intent(getApplicationContext(), MeActivity.class);
                            startActivity(meIntent);
                        }
                        else {
                            //not Login
                            Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivityForResult(loginIntent, REQUEST_CODE_LOGIN);
                        }
                        break;
                    default: break;

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_LOGIN:
                if ( session.isLoggedIn() ) {
                    finish();
                    Intent meIntent = new Intent(getApplicationContext(), MeActivity.class);
                    startActivity(meIntent);
                }
                bottomBar.selectTabAtPosition(1);
                break;
            default:break;
        }
    }

}
