package com.bku.cse.karaoke.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by thonghuynh on 6/13/2017.
 */

public class BaseURLManager {
    // LogCat tag
    private static String TAG = BaseURLManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "KaraokeBaseURL";
    private static final String KEY_BASE_URL = "BASE_URL";

    public BaseURLManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getBaseURL() {
        return pref.getString( KEY_BASE_URL, "http://192.168.0.105:8000/" );
    }

    public void setBaseURL(String url) {
        editor.putString( KEY_BASE_URL, url );
        editor.apply();
        Log.d( TAG, "Change BASE_URL to: " + url );
    }

}
