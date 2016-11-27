/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vinay on 12/11/16.
 */
public class TagItems implements Parcelable {
    public static final Parcelable.Creator<TagItems> CREATOR = new Creator<TagItems>() {
        @Override
        public TagItems createFromParcel(Parcel in) {
            return new TagItems(in);
        }

        @Override
        public TagItems[] newArray(int size) {
            return new TagItems[size];
        }
    };
    private String tagName;
    private int tagImages;

    public TagItems(String tagName, int tagImages) {
        this.tagName = tagName;
        this.tagImages = tagImages;
    }

    protected TagItems(Parcel in) {
        tagName = in.readString();
        tagImages = in.readInt();
    }

    public String getTagName() {
        return tagName;
    }

    public int getTagImages() {
        return tagImages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tagName);
        dest.writeInt(tagImages);
    }
}
