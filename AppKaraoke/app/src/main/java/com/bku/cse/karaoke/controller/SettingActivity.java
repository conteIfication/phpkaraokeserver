package com.bku.cse.karaoke.controller;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.helper.SessionManager;
import com.bku.cse.karaoke.util.Utils;

public class SettingActivity extends AppCompatActivity {
    Toolbar toolbar;
    LinearLayout linearLayout;
    Boolean isChangedTheme = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Utils.checkTheme(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default: break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent resIntent = new Intent();
        resIntent.putExtra("isChangedTheme", true);
        resIntent.putExtra("text", "12345");
        setResult( RESULT_OK, resIntent );
        finish();
    }

}
