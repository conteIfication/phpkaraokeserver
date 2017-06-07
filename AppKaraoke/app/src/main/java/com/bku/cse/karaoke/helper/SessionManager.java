package com.bku.cse.karaoke.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.bku.cse.karaoke.model.User;

/**
 * Created by thonghuynh on 5/28/2017.
 */

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "KaraokeLogin";
    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn, User userInfo) {

        if ( isLoggedIn ) {
            editor.putInt( "uid", userInfo.getUid() );
            editor.putString( "name", userInfo.getName() );
            editor.putString( "email", userInfo.getEmail() );
            editor.putString( "phone_no", userInfo.getPhone_no() );
            editor.putBoolean( "gender", userInfo.isGender() );
            editor.putString( "birthday", userInfo.getBirthday() );
            editor.putString( "avatar", userInfo.getAvatar() );
        }
        else {
            //Log out
            editor.clear();
        }

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
    public User getUserInfo() {
        User user = null;
        if (isLoggedIn()) {
            user = new User();
            user.setUid(pref.getInt("uid", 0));
            user.setName( pref.getString("name", "") );
            user.setEmail( pref.getString("email", "") );
            user.setPhone_no( pref.getString("phone_no", "") );
            user.setGender( pref.getBoolean("gender", false) );
            user.setBirthday( pref.getString("birthday", "") );
            user.setAvatar( pref.getString("avatar", "") );
        }
        return user;
    }
}
