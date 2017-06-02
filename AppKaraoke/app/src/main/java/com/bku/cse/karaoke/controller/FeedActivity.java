package com.bku.cse.karaoke.controller;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.cse.karaoke.R;

import com.bku.cse.karaoke.libffmpeg.*;
import com.bku.cse.karaoke.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.bku.cse.karaoke.libffmpeg.exceptions.FFmpegNotSupportedException;


public class FeedActivity extends AppCompatActivity {
    private static final String TAG = FeedActivity.class.getSimpleName();
    public final int REQUEST_INTERNET = 123;
    public final int REQUEST_W_EXTERNAL = 124;
    public final int REQUEST_RECORD_AUDIO = 125;

    FFmpeg ffmpeg;
    EditText commandEditText;
    LinearLayout outputLayout;
    Button runButton;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        ffmpeg = FFmpeg.getInstance(this);

        loadFFMpegBinary();
        initUI();

    }
    private void initUI() {

        commandEditText = (EditText) findViewById(R.id.command);

        outputLayout = (LinearLayout)findViewById(R.id.command_output);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(null);

        runButton = (Button) findViewById(R.id.run_command);
        runButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cmd = commandEditText.getText().toString();
                String[] command = cmd.split(" ");
                if (command.length != 0) {
                    execFFmpegBinary(command);
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.empty_command_toast), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void loadFFMpegBinary() {
        try {
            ffmpeg.loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    showUnsupportedExceptionDialog();
                }
            });
        } catch (FFmpegNotSupportedException e) {
            showUnsupportedExceptionDialog();
        }
    }
    private void execFFmpegBinary(final String[] command) {
        try {
            ffmpeg.execute(command, new ExecuteBinaryResponseHandler() {
                @Override
                public void onFailure(String s) {
                    addTextViewToLayout("FAILED with output : "+s);
                }

                @Override
                public void onSuccess(String s) {
                    addTextViewToLayout("SUCCESS with output : "+s);
                }

                @Override
                public void onProgress(String s) {
                    Log.d(TAG, "Started command : ffmpeg "+command);
                    addTextViewToLayout("progress : "+s);
                    progressDialog.setMessage("Processing\n"+s);
                }

                @Override
                public void onStart() {
                    outputLayout.removeAllViews();

                    Log.d(TAG, "Started command : ffmpeg " + command);
                    progressDialog.setMessage("Processing...");
                    progressDialog.show();
                }

                @Override
                public void onFinish() {
                    Log.d(TAG, "Finished command : ffmpeg "+command);
                    progressDialog.dismiss();
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // do nothing for now
        }
    }
    private void addTextViewToLayout(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        outputLayout.addView(textView);
    }
    private void showUnsupportedExceptionDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getString(R.string.device_not_supported))
                .setMessage(getString(R.string.device_not_supported_message))
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("This Device", "Not support for ffmpeg");
                    }
                })
                .create()
                .show();

    }

    @Override
    public void onBackPressed() {
        Intent home_intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(home_intent);
        finish();
    }
}
