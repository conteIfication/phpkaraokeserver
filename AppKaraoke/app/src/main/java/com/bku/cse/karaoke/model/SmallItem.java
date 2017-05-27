package com.bku.cse.karaoke.model;

/**
 * Created by quangthanh on 5/9/2017.
 */

public class SmallItem {
    private String songName;

    public SmallItem(String songName){
        this.songName = songName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }
}
