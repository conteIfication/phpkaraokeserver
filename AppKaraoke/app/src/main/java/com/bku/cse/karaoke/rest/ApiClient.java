package com.bku.cse.karaoke.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by thonghuynh on 5/28/2017.
 */

public class ApiClient {
    public static final String BASE_URL = "http://192.168.0.105:8000/";
//    public static final String BASE_URL = "http://10.0.2.2:8000/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
