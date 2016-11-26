/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

/**
 * {@link RequestModel}
 *
 * @author TienVNguyen
 */
public class RequestModel implements Parcelable {

    private String uid;
    private String requestId;
    private String subject;
    private boolean isConnected;
    private boolean isCompleted;
    private Map<String, Boolean> tags;
    private String detailEdtFirstQstTxt;
    private String detailEdtSecondQstTxt;
    private String detailEdtThirdQsttxt;
    private List<String> detailImageQst;


    public RequestModel() {
    }


    protected RequestModel(Parcel in) {
        uid = in.readString();
        requestId = in.readString();
        subject = in.readString();
        isConnected = in.readByte() != 0;
        isCompleted = in.readByte() != 0;
        detailEdtFirstQstTxt = in.readString();
        detailEdtSecondQstTxt = in.readString();
        detailEdtThirdQsttxt = in.readString();
        detailImageQst = in.createStringArrayList();
    }

    public static final Creator<RequestModel> CREATOR = new Creator<RequestModel>() {
        @Override
        public RequestModel createFromParcel(Parcel in) {
            return new RequestModel(in);
        }

        @Override
        public RequestModel[] newArray(int size) {
            return new RequestModel[size];
        }
    };

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

    public Map<String, Boolean> getTags() {
        return tags;
    }

    public void setTags(Map<String, Boolean> tags) {
        this.tags = tags;
    }

    public String getDetailEdtFirstQstTxt() {
        return detailEdtFirstQstTxt;
    }

    public void setDetailEdtFirstQstTxt(String detailEdtFirstQstTxt) {
        this.detailEdtFirstQstTxt = detailEdtFirstQstTxt;
    }

    public String getDetailEdtSecondQstTxt() {
        return detailEdtSecondQstTxt;
    }

    public void setDetailEdtSecondQstTxt(String detailEdtSecondQstTxt) {
        this.detailEdtSecondQstTxt = detailEdtSecondQstTxt;
    }

    public String getDetailEdtThirdQsttxt() {
        return detailEdtThirdQsttxt;
    }

    public void setDetailEdtThirdQsttxt(String detailEdtThirdQsttxt) {
        this.detailEdtThirdQsttxt = detailEdtThirdQsttxt;
    }

    public List<String> getDetailImageQst() {
        return detailImageQst;
    }

    public void setDetailImageQst(List<String> detailImageQst) {
        this.detailImageQst = detailImageQst;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(requestId);
        dest.writeString(subject);
        dest.writeByte((byte) (isConnected ? 1 : 0));
        dest.writeByte((byte) (isCompleted ? 1 : 0));
        dest.writeString(detailEdtFirstQstTxt);
        dest.writeString(detailEdtSecondQstTxt);
        dest.writeString(detailEdtThirdQsttxt);
        dest.writeStringList(detailImageQst);
    }
}
