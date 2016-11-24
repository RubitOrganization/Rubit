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
 * Created by vinay on 20/11/16.
 */
public class SupporterImagesModel implements Parcelable {
    public static final Creator<SupporterImagesModel> CREATOR = new Creator<SupporterImagesModel>() {
        @Override
        public SupporterImagesModel createFromParcel(Parcel in) {
            return new SupporterImagesModel(in);
        }

        @Override
        public SupporterImagesModel[] newArray(int size) {
            return new SupporterImagesModel[size];
        }
    };
    private String supporterImageUrl;


    public SupporterImagesModel(String imageUrl) {
        this.supporterImageUrl = imageUrl;
    }

    private SupporterImagesModel(Parcel in) {
        supporterImageUrl = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(supporterImageUrl);
    }

    public String getSupporterImageUrl() {
        return supporterImageUrl;
    }

    public void setSupporterImageUrl(String supporterImageUrl) {
        this.supporterImageUrl = supporterImageUrl;
    }
}
