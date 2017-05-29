package com.bku.cse.karaoke.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thonghuynh on 5/29/2017.
 */

public class KaraokeSong {
    @SerializedName("kid")
    private int kid;
    @SerializedName("name")
    private String name;
    @SerializedName("subtitle_path")
    private String subtitle_path;
    @SerializedName("beat_path")
    private String beat_path;
    @SerializedName("view_no")
    private int view_no;
    @SerializedName("up_time")
    private String up_time;
    @SerializedName("image")
    private String image;
    @SerializedName("uid")
    private int uid;
    @SerializedName("singer")
    private String singer;

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle_path() {
        return subtitle_path;
    }

    public void setSubtitle_path(String subtitle_path) {
        this.subtitle_path = subtitle_path;
    }

    public String getBeat_path() {
        return beat_path;
    }

    public void setBeat_path(String beat_path) {
        this.beat_path = beat_path;
    }

    public int getView_no() {
        return view_no;
    }

    public void setView_no(int view_no) {
        this.view_no = view_no;
    }

    public String getUp_time() {
        return up_time;
    }

    public void setUp_time(String up_time) {
        this.up_time = up_time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
