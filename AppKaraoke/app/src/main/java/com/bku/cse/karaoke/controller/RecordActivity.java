package com.bku.cse.karaoke.controller;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.bku.cse.karaoke.R;

import java.io.IOException;

public class RecordActivity extends AppCompatActivity {
    private boolean permissionAccepted = false;
    private String[] permissions = {Manifest.permission.CAMERA};
    private static final int REQUEST_PERMISSION = 200;

    private SurfaceView surface_view;
    private Camera mCamera;
    SurfaceHolder.Callback sh_ob = null;
    SurfaceHolder surface_holder = null;
    SurfaceHolder.Callback sh_callback = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION); // Request permision

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        surface_view = (SurfaceView) findViewById(R.id.surf);

        if (surface_holder == null) {
            surface_holder = surface_view.getHolder();
        }

        sh_callback = my_callback();
        surface_holder.addCallback(sh_callback);
    }

    SurfaceHolder.Callback my_callback() {
        SurfaceHolder.Callback ob1 = new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                mCamera = Camera.open(1);
                mCamera.setDisplayOrientation(90);

                try {
                    mCamera.setPreviewDisplay(holder);
                } catch (IOException exception) {
                    mCamera.release();
                    mCamera = null;
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {
                mCamera.startPreview();
            }
        };
        return ob1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION:
                permissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionAccepted) finish();

    }
}
