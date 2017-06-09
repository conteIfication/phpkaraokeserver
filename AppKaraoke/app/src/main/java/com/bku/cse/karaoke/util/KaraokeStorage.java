package com.bku.cse.karaoke.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by thonghuynh on 6/8/2017.
 */

public class KaraokeStorage {
    public static final String APP_STORAGE = "/app_karaoke/";
    public static final String APP_STORAGE_SONGS = "/app_karaoke/songs/";
    public static final String APP_STORAGE_AVATARS = "/app_karaoke/avatars/";
    public static final String APP_STORAGE_RECORDS = "/app_karaoke/records/";

    public static void deleteDownloadedFile() {
        File folder = new File(Environment.getExternalStorageDirectory() + APP_STORAGE_SONGS);

        if (folder.isDirectory()) {
            for ( File file : folder.listFiles() ) {
                if ( !file.delete() )
                    Log.d("Delete Error", "file: " + file.getPath() + "is not removed");
            }
        }
    }
}
