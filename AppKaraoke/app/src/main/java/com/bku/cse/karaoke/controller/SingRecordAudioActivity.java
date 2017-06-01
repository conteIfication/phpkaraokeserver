package com.bku.cse.karaoke.controller;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bku.cse.karaoke.R;
import com.bku.cse.karaoke.rest.ApiClient;
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
import jp.wasabeef.blurry.Blurry;

/**
 * Created by thonghuynh on 5/29/2017.
 */

public class SingRecordAudioActivity extends AppCompatActivity {
    public final int REQUEST_INTERNET = 123;
    public final int REQUEST_W_EXTERNAL = 124;
    public final int REQUEST_RECORD_AUDIO = 125;

    int startTimeFirstWord = 0;

    String beat_path_downloaded = "";
    String subtitle_path_downloaded = "";
    String record_path = "";

    ImageView imgBackground;
    CircleImageView imageView;
    ScrollView scrollView;
    LinearLayout ll_lyric;
    ProgressBar progressBar;

    Button btn_record_or_stop;
    ImageButton btn_play;
    TextView tv_current_time, tv_label_download;

    LinearLayout ll_wrap_download;

    Bundle mBundle;

    //Lyric
    SongLyric lyric;

    //Play music
    MediaPlayer mediaPlayer;

    //Recorder
    MediaRecorder mediaAudioRecorder;

    //Flag Playing
    boolean playingFlag = false;

    //Flag Record
    boolean recordingFlag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //Check Permission
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_W_EXTERNAL);
        }
        if (checkSelfPermission(Manifest.permission.INTERNET) !=  PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.INTERNET}, REQUEST_INTERNET);
        }
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) !=  PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO);
        }

        //get Intent
        Intent gIntent = getIntent();
        mBundle = gIntent.getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_record_audio);

        //set Background Image
        imgBackground = (ImageView) findViewById(R.id.sra_background);
        Bitmap bitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.raw.my_image));
        imgBackground.setImageBitmap( bitmap );
        imgBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //init View
        scrollView = (ScrollView) findViewById(R.id.sra_scroll_view);
        ll_lyric = (LinearLayout) findViewById(R.id.sra_ll_lyrics);
        btn_play = (ImageButton) findViewById(R.id.sra_btn_play);
        tv_current_time = (TextView) findViewById(R.id.sra_current_time);
        progressBar = (ProgressBar) findViewById(R.id.sra_progressBar);
        imageView = (CircleImageView) findViewById(R.id.sra_avatar);
        btn_record_or_stop = (Button) findViewById(R.id.sra_record_or_stop);
        lyric = new SongLyric();
        ll_wrap_download = (LinearLayout) findViewById(R.id.sra_wrap_download);
        tv_label_download = (TextView) findViewById(R.id.sra_label_download);

        //init Player
        mediaPlayer = new MediaPlayer();
        mediaAudioRecorder = new MediaRecorder();

        //outputFile
        String PATH_RECORD = Environment.getExternalStorageDirectory() + "/records/";
        File mFileTest = new File(PATH_RECORD);
        if (!mFileTest.exists()) {
            mFileTest.mkdirs();
        }
        String recordName = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());


        //set Media Recorder
        mediaAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaAudioRecorder.setOutputFile( PATH_RECORD + recordName + "_AudioRecord.3gp" );
        record_path = PATH_RECORD + recordName + "_AudioRecord.3gp";

        //FUNCTION
        //hide Scroll Bar
        scrollView.setVerticalScrollBarEnabled(false);
        //clear ll_lyric
        ll_lyric.removeAllViews();

        //Download Mp3 file and xml lyric.
        new DownloadBeatSubTask().execute(ApiClient.BASE_URL + mBundle.getString("beat_path"));

        //Load avatar
        Glide.with(getApplicationContext()).load(ApiClient.BASE_URL + mBundle.getString("image"))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

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
                //set current status for flag
                playingFlag = !playingFlag;
            }
        };
        MediaPlayer.OnCompletionListener media_player_complete = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                handler.removeCallbacks(UpdateSongTime);
                mediaPlayer.reset();
            }
        };

        //btn Record OR Stop
        View.OnClickListener btn_record_stop_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recordingFlag) {
                    //is playing ->Stop Record
                    stopPlaying();
                }
                else {
                    //Set Flag
                    recordingFlag = true;
                    //not playing
                    btn_record_or_stop.setText("STOP RECORDING");

                    //prepare recorder
                    try {
                        mediaAudioRecorder.prepare();
                        mediaAudioRecorder.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //playing
                    startPlaying();
                }

                playingFlag = !playingFlag;
            }
        };


        //SET LISTENER
        btn_play.setOnClickListener(btn_play_listener);
        btn_record_or_stop.setOnClickListener(btn_record_stop_listener);
        mediaPlayer.setOnCompletionListener( media_player_complete );

    }

    public void stopPlaying() {
        mediaPlayer.stop();
        mediaAudioRecorder.stop();

        progressBar.setProgress(0);
        btn_play.setImageResource(R.drawable.ic_media_play_dark);

        Toast.makeText(getApplicationContext(), "Save Record", Toast.LENGTH_LONG).show();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Do you want to save this record?");
        alertDialogBuilder.setMessage("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        //Positive Button
        alertDialogBuilder.setPositiveButton("SAVE",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Toast.makeText(getApplicationContext(),"Saved Record",Toast.LENGTH_LONG).show();
                        btn_record_or_stop.setVisibility(View.INVISIBLE);
                        tv_label_download.setText("Saving record....");
                        ll_wrap_download.setVisibility(View.VISIBLE);
                    }
                });
        //Negative Button
        alertDialogBuilder.setNegativeButton("NO",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }

    public void startPlaying() {
        if ( recordingFlag ) {
            mediaAudioRecorder.resume();
        }
        mediaPlayer.start();
        UpdateSongTime.run();

        //setIcon Pause
        btn_play.setImageResource(R.drawable.ic_media_pause_dark);
    }

    public void pausePlaying() {
        if ( recordingFlag ) {
            mediaAudioRecorder.pause();
        }

        mediaPlayer.pause();
        handler.removeCallbacks(UpdateSongTime);
        btn_play.setImageResource(R.drawable.ic_media_play_dark);
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
                    ll_lyric.addView( getLyricOnLine(0) );
                    ll_lyric.addView( getLyricOnLine(1) );
                    ll_lyric.addView( getLyricOnLine(2) );
                    ll_lyric.addView( getLyricOnLine(3) );
                    addedLine = 3;
                }
                else {
                    ll_lyric.addView( getLyricOnLine(++addedLine) );
                }

                //ScrollView Slide Down
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.smoothScrollTo(0, ll_lyric.getBottom());
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

    public LinearLayout getLyricOnLine( int line ) {
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        //Blur Image Background
        Blurry.with(getApplicationContext())
                .radius(25)
                .sampling(5)
                .color(Color.argb(66, 0, 0, 0))
                .async()
                .capture(imgBackground)
                .into(imgBackground);
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(UpdateSongTime);
        mediaPlayer.release();
        mediaAudioRecorder.release();
    }

    public void downloadMp3(String url) {
        InputStream in = null;
        try {
            String PATH = Environment.getExternalStorageDirectory() + "/download/";

            int lastSlash = url.lastIndexOf("/");
            String fName = url.substring(lastSlash + 1, url.length() - 4);

            beat_path_downloaded = PATH + fName + ".mp3";

            File file = new File(PATH);
            if(!file.exists()) {
                file.mkdirs();
            }

            in = OpenHttpConnection(url);

            File outputFile = new File(file, fName + ".mp3");
            if (outputFile.exists())
                return;

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
    }
    public class DownloadBeatSubTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            //Disable click btn_play
            downloadMp3(params[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("DownMP3--", "success");

            //set MP3 source
            try {
                mediaPlayer.setDataSource(beat_path_downloaded);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //set Progress Bar
            progressBar.setMax(mediaPlayer.getDuration());

            //download XML file
            new DownloadXMLTask().execute(ApiClient.BASE_URL + mBundle.getString("subtitle_path"));
        }
    }

    public void downloadXml(String url) {
        InputStream in = null;
        try {
            String PATH = Environment.getExternalStorageDirectory() + "/download/";
            //String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());

            int lastSlash = url.lastIndexOf("/");
            String fName = url.substring(lastSlash + 1, url.length() - 4);

            subtitle_path_downloaded = PATH + fName + ".xml";

            File file = new File(PATH);
            if(!file.exists()) {
                file.mkdirs();
            }

            in = OpenHttpConnection(url);

            File outputFile = new File(file, fName + ".xml");
            if (outputFile.exists())
                return;

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
    }
    public class DownloadXMLTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            downloadXml(params[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("DownXML--", "success:" + subtitle_path_downloaded);

            //parse Lyric
            lyric.doParse( subtitle_path_downloaded );
            startTimeFirstWord = lyric.getWord(0).getTimeStart();

            //hide download progress
            ll_wrap_download.setVisibility(View.INVISIBLE);

            //show Btn play and btn Start
            btn_record_or_stop.setVisibility(View.VISIBLE);
            btn_play.setVisibility(View.VISIBLE);
        }
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

}
