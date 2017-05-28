package com.bku.cse.karaoke.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bku.cse.karaoke.R;

/**
 * Created by thonghuynh on 5/29/2017.
 */

public class FeedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
    }
    @Override
    public void onBackPressed() {
        Intent home_intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(home_intent);
        finish();
    }
}
