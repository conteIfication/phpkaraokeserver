package com.bku.cse.karaoke.rest;

import android.content.Context;
import android.content.SharedPreferences;

import com.bku.cse.karaoke.helper.BaseURLManager;
import com.bku.cse.karaoke.helper.SessionManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thonghuynh on 5/28/2017.
 */

public class ApiClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(Context context) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl( new BaseURLManager(context).getBaseURL()  )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
