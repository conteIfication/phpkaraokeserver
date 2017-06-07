package com.bku.cse.karaoke.model;

import java.util.Date;

/**
 * Created by thonghuynh on 6/7/2017.
 */

public class FavoriteSong {
    int fid;
    String up_time;
    int kid;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getUp_time() {
        return up_time;
    }

    public void setUp_time(String up_time) {
        this.up_time = up_time;
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public FavoriteSong() {
        this.fid = 0;
        this.up_time = new Date().toString();
        this.kid = 0;
    }

    public FavoriteSong(int fid, String up_time, int kid) {

        this.fid = fid;
        this.up_time = up_time;
        this.kid = kid;
    }
}
