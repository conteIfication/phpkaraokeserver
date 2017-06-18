package com.bku.cse.karaoke.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.helper.BaseURLManager;
import com.bku.cse.karaoke.helper.DatabaseHelper;
import com.bku.cse.karaoke.libffmpeg.ExecuteBinaryResponseHandler;
import com.bku.cse.karaoke.libffmpeg.FFmpeg;
import com.bku.cse.karaoke.libffmpeg.LoadBinaryResponseHandler;
import com.bku.cse.karaoke.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.bku.cse.karaoke.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.bku.cse.karaoke.model.RecordedSong;
import com.bku.cse.karaoke.util.KaraokeStorage;
import com.bku.cse.karaoke.util.SongLyric;
import com.bku.cse.karaoke.util.Word;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by thonghuynh on 6/13/2017.
 */

public class JustPlayActivity  extends AppCompatActivity{
    private final String TAG = JustPlayActivity.class.getSimpleName();

    String beat_path_downloaded = "";
    String subtitle_path_downloaded = "";

    ImageView imgBackground;
    CircleImageView imgSong;
    ScrollView scrollView_lyric;
    LinearLayout ll_lyric;
    ProgressBar progressBar;

    Button btn_record_or_stop;
    ImageButton btn_play;
    TextView tv_current_time, tv_label_download, tv_progress_status, tv_score;
    LinearLayout ll_wrap_download;
    Bundle mBundle;
    DatabaseHelper db;

    //Lyric
    SongLyric lyric;
    int startTimeFirstWord = 0;

    //Player
    MediaPlayer mediaPlayer;

    //Flag Playing
    boolean playingFlag = false;

    //BASE_URL
    BaseURLManager baseURL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //get Intent
        Intent gIntent = getIntent();
        mBundle = gIntent.getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_record_audio);

        //hide phone bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set Background Image
        imgBackground = (ImageView) findViewById(R.id.sra_background);
        Bitmap bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.nepal));
        imgBackground.setImageBitmap( bitmap );
        imgBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //init View
        scrollView_lyric = (ScrollView) findViewById(R.id.sra_scroll_view);
        ll_lyric = (LinearLayout) findViewById(R.id.sra_ll_lyrics);
        btn_play = (ImageButton) findViewById(R.id.sra_btn_play);
        tv_current_time = (TextView) findViewById(R.id.sra_current_time);
        progressBar = (ProgressBar) findViewById(R.id.sra_progressBar);
        imgSong = (CircleImageView) findViewById(R.id.sra_avatar);
        lyric = new SongLyric();
        ll_wrap_download = (LinearLayout) findViewById(R.id.sra_wrap_download);
        tv_label_download = (TextView) findViewById(R.id.sra_label_download);
        baseURL  = new BaseURLManager(this);
        tv_progress_status = (TextView) findViewById(R.id.sra_progress_status);
        db = new DatabaseHelper(getApplicationContext());
        tv_score = (TextView) findViewById(R.id.sra_score);
        btn_record_or_stop = (Button) findViewById(R.id.sra_record_or_stop);


        //download
        new DownloadBeatSubTask().execute(
                baseURL.getBaseURL() + mBundle.getString("beat_path"),
                baseURL.getBaseURL() + mBundle.getString("subtitle_path")
        );

        //init Player
        mediaPlayer = new MediaPlayer();

        //FUNCTION
        //hide Scroll Bar
        scrollView_lyric.setVerticalScrollBarEnabled(false);

        //clear ll_lyric
        ll_lyric.removeAllViews();

        //Load avatar
        Glide.with(getApplicationContext()).load(baseURL.getBaseURL() + mBundle.getString("image"))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgSong);

        //Make Listener
        View.OnClickListener btn_play_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( playingFlag ) {
                    //is playing
                    pausePlaying();
                }
                else {
                    //not playing
                    startPlaying();
                }
            }
        };
        MediaPlayer.OnCompletionListener media_player_complete = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying();
            }
        };


        //SET LISTENER
        btn_play.setOnClickListener( btn_play_listener );
        mediaPlayer.setOnCompletionListener( media_player_complete );

    }
    public void startPlaying() {
        mediaPlayer.start();
        UpdateSongTime.run();
        //setIcon Pause
        btn_play.setImageResource(R.drawable.ic_media_pause_dark);
        playingFlag = true;
    }
    public void pausePlaying() {
        mediaPlayer.pause();
        handler.removeCallbacks(UpdateSongTime);
        //setIcon Play
        btn_play.setImageResource(R.drawable.ic_media_play_dark);
        playingFlag = false;
    }
    public void stopPlaying() {
        handler.removeCallbacks(UpdateSongTime);
        tv_current_time.setText("00:00");
        mediaPlayer.stop();
        //setIcon Play
        btn_play.setImageResource(R.drawable.ic_media_play_dark);
        progressBar.setProgress(0);
        playingFlag = false;
    }

    public boolean deleteFile_(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }


    //Init Parameter
    int playingLine = -1;
    int addedLine = -1;

    //handler when playing
    Handler handler = new Handler();

    //Playing Runnable
    private PlayingLyricRunnable UpdateSongTime = new PlayingLyricRunnable();
    public class PlayingLyricRunnable implements Runnable {
        @Override
        public void run() {
            int currentTime = mediaPlayer.getCurrentPosition();
            long minutes = TimeUnit.MILLISECONDS.toMinutes((long) currentTime);
            final long seconds =
                    TimeUnit.MILLISECONDS.toSeconds((long) currentTime) - TimeUnit.MINUTES.toSeconds(minutes);

            //Set Current Time -> TextView, ProgressBar
            tv_current_time.setText(String.format("%s:%s", minutes < 10 ? "0" + minutes : "" + minutes, (seconds < 10) ? "0" + seconds : "" + seconds));
            progressBar.setProgress(currentTime);

            //Show Lyric
            if (lyric.getWord(lyric.getSize() - 1).getLine() < 4 )
                return;

            int currentLine = lyric.getCurrentLine(currentTime);
            //if the current line changed -> show Lyric
            if ( Math.abs(currentLine - playingLine) >= 1 ) {

                if (playingLine == -1) {
                    ll_lyric.addView( getLyricOneLine(0) );
                    ll_lyric.addView( getLyricOneLine(1) );
                    ll_lyric.addView( getLyricOneLine(2) );
                    ll_lyric.addView( getLyricOneLine(3) );
                    addedLine = 3;
                }
                else {
                    ll_lyric.addView( getLyricOneLine(++addedLine) );
                }

                //ScrollView Slide Down
                scrollView_lyric.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView_lyric.smoothScrollTo(0, ll_lyric.getBottom());
                    }
                });
                //set Playing Line
                playingLine = currentLine;
            }

            //highlight WORD
            if (currentTime >= startTimeFirstWord) {
                //First Word change Color
                Word mWord = lyric.getWordAtTime(currentTime);

                if ( mWord != null ) {
                    TextView tv = (TextView) ll_lyric.findViewById( mWord.getTimeStart() );

                    if (tv != null){
                        tv.setTextColor(Color.CYAN);
                    }
                }
            }
            //post delay 10 mms
            handler.postDelayed(this, 10);
        }
    }

    public LinearLayout getLyricOneLine(int line ) {
        LinearLayout newLL = new LinearLayout(getApplicationContext());
        newLL.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        newLL.setOrientation(LinearLayout.HORIZONTAL);
        newLL.setGravity(Gravity.CENTER);

        boolean found = false;

        for (int i = 0; i < lyric.getSize(); i++) {
            if (lyric.getWord(i).getLine() == line) {
                Word w = lyric.getWord(i);
                found = true;

                TextView newTV = new TextView(getBaseContext());
                newTV.setId(w.getTimeStart());
                newTV.setLayoutParams(new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT ));
                newTV.setText(w.getContent());
                newTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                newTV.setPadding(0, 6, 0 ,6);
                newTV.setTextColor(Color.WHITE);
                newTV.setTypeface(Typeface.DEFAULT_BOLD);

                //add to Linear Layout
                newLL.addView(newTV);
            }
            else {
                if (found)
                    break;
            }
        }
        return newLL;
    }

    @Override
    public void onBackPressed() {
        if (playingFlag) {
            stopPlaying();
            super.onBackPressed();
            return;
        }

        super.onBackPressed();
    }

    public InputStream OpenHttpConnection(String urlString) throws IOException {
        InputStream in = null;
        int response = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }
    public String downloadFile(String url, String folderPath) {
        InputStream in = null;
        String PATH = Environment.getExternalStorageDirectory() + folderPath;

        int lastSlash = url.lastIndexOf("/");
        String fName = url.substring(lastSlash + 1, url.length());

        try {
            File file = new File(PATH);
            if(!file.exists()) {
                file.mkdirs();
            }

            in = OpenHttpConnection(url);

            File outputFile = new File(file, fName);
            if (outputFile.exists())
                return ( PATH + fName );

            FileOutputStream fos = new FileOutputStream(outputFile);

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.flush();
            fos.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //return
        return ( PATH + fName );
    }
    public class DownloadBeatSubTask extends AsyncTask<String, Void, String> {
        String subtitle_path;

        @Override
        protected String doInBackground(String... params) {
            subtitle_path = params[1];

            return downloadFile(params[0], KaraokeStorage.APP_STORAGE_SONGS);
        }
        @Override
        protected void onPostExecute(String pathOnDevice) {
            beat_path_downloaded = pathOnDevice;
            Log.d("Downloaded_MP3", "path:" + beat_path_downloaded );

            //set MP3 source
            try {
                mediaPlayer.setDataSource(beat_path_downloaded);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //set Progress Bar
            progressBar.setMax(mediaPlayer.getDuration());

            //after download MP3 --> download XML file
            new DownloadXMLTask().execute( subtitle_path );
        }
    }
    public class DownloadXMLTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return downloadFile(params[0], KaraokeStorage.APP_STORAGE_SONGS);
        }
        @Override
        protected void onPostExecute(String pathOnDevice) {
            subtitle_path_downloaded = pathOnDevice;
            Log.d("Downloaded_XML", "path:" + subtitle_path_downloaded);

            //parse Lyric
            lyric.doParse( subtitle_path_downloaded );
            startTimeFirstWord = lyric.getWord(0).getTimeStart();

            //hide download progress
            ll_wrap_download.setVisibility(View.INVISIBLE);
            btn_play.setVisibility(View.VISIBLE);
        }
    }
}
