/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.models;

import android.os.Parcelable;

/**
 * {@link TagModel}
 *
 * @author TienVNguyen
 */
public class TagModel implements Parcelable {
    public static final Creator<TagModel> CREATOR = new Creator<TagModel>() {
        @Override
        public TagModel createFromParcel(android.os.Parcel in) {
            return new TagModel(in);
        }

        @Override
        public TagModel[] newArray(int size) {
            return new TagModel[size];
        }
    };
    private String uid;
    private String tagId;
    private String text;

    public TagModel() {
    }

    public TagModel(String uid, String tagId, String text) {
        this.uid = uid;
        this.tagId = tagId;
        this.text = text;
    }

    private TagModel(android.os.Parcel in) {
        uid = in.readString();
        tagId = in.readString();
        text = in.readString();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(tagId);
        parcel.writeString(text);
    }
}
