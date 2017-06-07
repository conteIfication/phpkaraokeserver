package com.bku.cse.karaoke.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bku.cse.karaoke.R;

import java.text.DecimalFormat;

import static com.bku.cse.karaoke.util.Utils.checkTheme;
import static com.bku.cse.karaoke.util.Utils.getScreenOrientation;

public class DetailActivity extends AppCompatActivity {
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
    private boolean isPlaying = true;

    private static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkTheme(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        // Disable app bar shadow
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String title = getIntent().getStringExtra("TITLE");
        getSupportActionBar().setTitle(title);

        time = (TextView) findViewById(R.id.media_time);

        seekBar = (SeekBar) findViewById(R.id.mediacontroller_progress);
        seekBar.setPadding(15, 0, 15, 0);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });

        play_btn = (ImageButton) findViewById(R.id.media_play_pause);

        TextView tv = (TextView) findViewById(R.id.text_detail_title);
        tv.setText(title);

        TextView tva = (TextView) findViewById(R.id.text_detail_author);
        tva.setText("2017-05-20");

        videoView = (VideoView) findViewById(R.id.videoView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /* Show Controller */
        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if (controllerShow) {
                    controllerShow = false;
                    LinearLayout controller_layout = (LinearLayout) findViewById(R.id.media_custom_controller);
                    controller_layout.setVisibility(View.INVISIBLE);
                    return false;
                } else {
                    controllerShow = true;
                    LinearLayout controller_layout = (LinearLayout) findViewById(R.id.media_custom_controller);
                    controller_layout.setVisibility(View.VISIBLE);
                    return false;
                }
            }
        });

        /* Scale screen */

        try {
            // ID của file video.
            int id = this.getResources().getIdentifier("big_buck_bunny", "raw", this.getPackageName());

            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(this, Uri.parse("android.resource://" + getPackageName() + "/" + id));
            video_height = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            video_width = metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            device_height = displayMetrics.heightPixels;
            device_width = displayMetrics.widthPixels;


            Rect rectangle = new Rect();
            Window window = getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            int statusBarHeight = rectangle.top;
            int actionBarHeight = 144;
            TypedValue tvl = new TypedValue();
            if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tvl, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tvl.data, getResources().getDisplayMetrics());
            }
            int real_height = device_height - statusBarHeight - actionBarHeight;

            int check = getScreenOrientation(this);
            if (check == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE || check == ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE) {
                int temp_height = real_height;
                int temp_width = temp_height * Integer.parseInt(video_width) / Integer.parseInt(video_height);

                if (temp_width < device_width) {
                    FrameLayout fr_layout = (FrameLayout) findViewById(R.id.videoSurfaceContainer);
                    ViewGroup.LayoutParams fr_params = fr_layout.getLayoutParams();

                    fr_params.height = temp_height;
                    fr_params.width = device_width;

                    fr_layout.setLayoutParams(fr_params);
                } else {
                    //TODO:
                }
            } else {
                int temp_width = device_width;
                int temp_height = temp_width * Integer.parseInt(video_height) / Integer.parseInt(video_width);

                if (temp_height < real_height) {
                    FrameLayout fr_layout = (FrameLayout) findViewById(R.id.videoSurfaceContainer);
                    ViewGroup.LayoutParams fr_params = fr_layout.getLayoutParams();

                    fr_params.height = temp_height;
                    fr_params.width = temp_width;

                    fr_layout.setLayoutParams(fr_params);
                } else {
                    //TODO: Height out of screen when max width;
                }
            }
            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));

        } catch (Exception e) {
            e.printStackTrace();
        }

        videoView.requestFocus();

        // Sự kiện khi file video sẵn sàng để chơi.
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {
                mp = mediaPlayer;

                mp.seekTo(position);
                updateSeekerBar();
                mp.start();

                play_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isPlaying) {
                            pause();
                        } else {
                            resume();
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("themeSetting", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Media Controller
     */

    private void pause() {
        Log.d(TAG, "On Pause");

        isPlaying = false;
        this.mp.pause();
        play_btn.setImageResource(R.drawable.ic_media_play_dark);
    }

    private void resume() {
        Log.d(TAG, "On Resume");

        isPlaying = true;
        play_btn.setImageResource(R.drawable.ic_media_pause_dark);
        this.mp.start();
    }

    // Khi bạn xoay điện thoại, phương thức này sẽ được gọi
    // nó lưu trữ lại ví trí file video đang chơi.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Lưu lại vị trí file video đang chơi.
        this.mp.pause();
        savedInstanceState.putInt("CurrentPosition", this.mp.getCurrentPosition());
    }


    // Sau khi điện thoại xoay chiều xong. Phương thức này được gọi,
    // bạn cần tái tạo lại ví trí file nhạc đang chơi.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("CurrentPosition");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * Seeker Bar
     */
    private void updateSeekerBar() {
        mHandler.postDelayed(updateTimeTask, 100);
    }

    private Runnable updateTimeTask = new Runnable() {
        public void run() {
            seekBar.setProgress(videoView.getCurrentPosition());
            seekBar.setMax(videoView.getDuration());

            time.setText(convertTime(videoView.getCurrentPosition()));
            mHandler.postDelayed(this, 100);
        }
    };

    private String convertTime(int time) {
        DecimalFormat formatter = new DecimalFormat("00");

        int curr_time = videoView.getCurrentPosition() / 1000;
        String min = formatter.format(curr_time / 60);
        String sec = formatter.format(curr_time % 60);
        return min + ":" + sec;
    }
}
