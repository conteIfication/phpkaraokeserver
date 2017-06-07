package com.bku.cse.karaoke.model;

/**
 * Created by thonghuynh on 6/7/2017.
 */

public class RecordedSong {

    int rsid;
    int score;
    String path;
    String record_type;
    Boolean is_shared;
    String up_time;
    int kid;
    int srid;

    public RecordedSong() {
        this.rsid = 0;
        this.score = 0;
        this.path = "";
        this.record_type = "";
        this.is_shared = false;
        this.up_time = "";
        this.kid = 0;
        this.srid = 0;
    }

    public RecordedSong(int rsid, int score, String path, String record_type, Boolean is_shared, String up_time, int kid, int srid) {
        this.rsid = rsid;
        this.score = score;
        this.path = path;
        this.record_type = record_type;
        this.is_shared = is_shared;
        this.up_time = up_time;
        this.kid = kid;
        this.srid = srid;
    }

    public int getRsid() {
        return rsid;
    }

    public void setRsid(int rsid) {
        this.rsid = rsid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRecord_type() {
        return record_type;
    }

    public void setRecord_type(String record_type) {
        this.record_type = record_type;
    }

    public Boolean getIs_shared() {
        return is_shared;
    }

    public void setIs_shared(Boolean is_shared) {
        this.is_shared = is_shared;
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

    public int getSrid() {
        return srid;
    }

    public void setSrid(int srid) {
        this.srid = srid;
    }
}
