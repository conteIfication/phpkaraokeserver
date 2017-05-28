package com.bku.cse.karaoke.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thonghuynh on 5/28/2017.
 */

public class MSGAuth {
    @SerializedName("tag")
    private String tag;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
