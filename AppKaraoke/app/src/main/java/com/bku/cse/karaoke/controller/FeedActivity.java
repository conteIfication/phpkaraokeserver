package com.bku.cse.karaoke.controller;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.cse.karaoke.R;

import com.bku.cse.karaoke.helper.SessionManager;
import com.bku.cse.karaoke.util.Utils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;


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

        //Test SQL Lite
        SQLiteDatabase myDatabase = openOrCreateDatabase("kar100", MODE_PRIVATE, null );
        //myDatabase.execSQL("DROP TABLE TutorialsPoint;");
        myDatabase.execSQL("CREATE TABLE IF NOT EXISTS TutorialsPoint(ID INTEGER PRIMARY KEY  AUTOINCREMENT, Username VARCHAR UNIQUE,Password VARCHAR);");
        //myDatabase.execSQL("INSERT INTO TutorialsPoint('Username', 'Password') VALUES('admin','adm2222in');");
//        myDatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin1','12345');");
//        myDatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin2','123');");
//        myDatabase.execSQL("INSERT INTO TutorialsPoint VALUES('admin3','12344');");

        Cursor cursor = myDatabase.rawQuery("SELECT * FROM TutorialsPoint;", null);
        TextView tv = (TextView) findViewById(R.id.textView4);

        while ( cursor.moveToNext() ) {
            tv.setText( tv.getText() + "-" + cursor.getString(0) );
        }
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
