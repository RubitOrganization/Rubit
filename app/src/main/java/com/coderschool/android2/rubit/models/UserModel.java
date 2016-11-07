package com.coderschool.android2.rubit.models;

import android.os.Parcelable;

import java.util.Map;
/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */


/**
 * {@link UserModel}
 *
 * @author TienVNguyen
 */
public class UserModel implements Parcelable {
    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(android.os.Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    private String uid;
    private String email;
    private String displayName;
    private String photoUrl;
    private double credit;
    private boolean isConnected;
    private Map<String, Boolean> tags;
    private Map<String, Boolean> requests;

    public UserModel() {
    }

    public UserModel(String uid, String email, String displayName, String photoUrl, double credit, boolean isConnected, Map<String, Boolean> tags, Map<String, Boolean> requests) {
        this.uid = uid;
        this.email = email;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
        this.credit = credit;
        this.isConnected = isConnected;
        this.tags = tags;
        this.requests = requests;
    }

    private UserModel(android.os.Parcel in) {
        uid = in.readString();
        email = in.readString();
        displayName = in.readString();
        photoUrl = in.readString();
        credit = in.readDouble();
        isConnected = in.readByte() != 0;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public Map<String, Boolean> getTags() {
        return tags;
    }

    public void setTags(Map<String, Boolean> tags) {
        this.tags = tags;
    }

    public Map<String, Boolean> getRequests() {
        return requests;
    }

    public void setRequests(Map<String, Boolean> requests) {
        this.requests = requests;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(email);
        parcel.writeString(displayName);
        parcel.writeString(photoUrl);
        parcel.writeDouble(credit);
        parcel.writeByte((byte) (isConnected ? 1 : 0));
    }
}
