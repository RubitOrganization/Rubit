/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.models;

import android.os.Parcelable;

/**
 * {@link RequestModel}
 *
 * @author TienVNguyen
 */
public class RequestModel implements Parcelable {
    public static final Creator<RequestModel> CREATOR = new Creator<RequestModel>() {
        @Override
        public RequestModel createFromParcel(android.os.Parcel in) {
            return new RequestModel(in);
        }

        @Override
        public RequestModel[] newArray(int size) {
            return new RequestModel[size];
        }
    };
    private String uid;
    private String requestId;
    private String subject;
    private boolean isConnected;
    private boolean isCompleted;

    public RequestModel() {
    }

    public RequestModel(String uid, String requestId, String subject, boolean isConnected, boolean isCompleted) {
        this.uid = uid;
        this.requestId = requestId;
        this.subject = subject;
        this.isConnected = isConnected;
        this.isCompleted = isCompleted;
    }

    protected RequestModel(android.os.Parcel in) {
        uid = in.readString();
        requestId = in.readString();
        subject = in.readString();
        isConnected = in.readByte() != 0;
        isCompleted = in.readByte() != 0;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(requestId);
        parcel.writeString(subject);
        parcel.writeByte((byte) (isConnected ? 1 : 0));
        parcel.writeByte((byte) (isCompleted ? 1 : 0));
    }
}
