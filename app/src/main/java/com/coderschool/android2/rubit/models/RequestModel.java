/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * {@link RequestModel}
 *
 * @author TienVNguyen
 */
@Parcel(analyze = {RequestModel.class})
public class RequestModel {
    @SerializedName("userId")
    private String mUserId;
    @SerializedName("displayName")
    private String mUserName;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("text")
    private String mRequest;
    @SerializedName("photoUrl")
    private String mProfilePicture;

    public RequestModel() {
    }

    public RequestModel(String mUserId, String mUserName, String mEmail, String mRequest, String mProfilePicture) {
        this.mUserId = mUserId;
        this.mUserName = mUserName;
        this.mEmail = mEmail;
        this.mRequest = mRequest;
        this.mProfilePicture = mProfilePicture;
    }

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmRequest() {
        return mRequest;
    }

    public void setmRequest(String mRequest) {
        this.mRequest = mRequest;
    }

    public String getmProfilePicture() {
        return mProfilePicture;
    }

    public void setmProfilePicture(String mProfilePicture) {
        this.mProfilePicture = mProfilePicture;
    }
}
