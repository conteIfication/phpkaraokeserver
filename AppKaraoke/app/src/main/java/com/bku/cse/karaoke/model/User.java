package com.bku.cse.karaoke.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thonghuynh on 6/7/2017.
 */

public class User {
    final public static String AVATAR_DEFAULT = "storage/avatar.png";

    @SerializedName("uid")
    private int uid;


    @SerializedName("email")
    private String email;

    @SerializedName("name")
    private String name;

    @SerializedName("phone_no")
    private String phone_no;

    @SerializedName("birthday")
    private String birthday;

    @SerializedName("gender")
    private boolean gender;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @SerializedName("avatar")
    private String avatar;


}
