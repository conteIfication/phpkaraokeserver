package com.bku.cse.karaoke.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thonghuynh on 5/29/2017.
 */

public class KSongList {
    @SerializedName("listKSongs")
    private List<KaraokeSong> listKSongs = new ArrayList<>();

    public List<KaraokeSong> getListKSongs() {
        return listKSongs;
    }

    public void setListKSongs(List<KaraokeSong> listKSongs) {
        this.listKSongs = listKSongs;
    }
}
