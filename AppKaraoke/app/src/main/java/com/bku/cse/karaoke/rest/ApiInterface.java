package com.bku.cse.karaoke.rest;

import com.bku.cse.karaoke.model.KSongList;
import com.bku.cse.karaoke.model.KaraokeSong;
import com.bku.cse.karaoke.model.MSGAuth;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface ApiInterface {
    @FormUrlEncoded
    @POST("login")
    Call<MSGAuth> postLogin(@Field("email") String email,
                         @Field("password") String password);

    @FormUrlEncoded
    @POST("register")
    Call<MSGAuth> postRegister(@Field("name") String name,
                               @Field("email") String email,
                               @Field("password") String password);

    @GET("get-all-song")
    Call<KSongList> getAllSong();

    @GET("get-hot-song")
    Call<KSongList> getHotSong();

    @GET("get-new-song")
    Call<KSongList> getNewSong();

    @GET("karaoke-song/{kid}")
    Call<KaraokeSong> getKaraokeSong( @Path("kid") int kid  );

    @GET("view-ks/{kid}")
    Call<String> increaseView( @Path("kid") int kid );
}
