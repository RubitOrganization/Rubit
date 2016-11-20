package com.coderschool.android2.rubit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vinay on 20/11/16.
 */

public class SupporterImagesModel implements Parcelable {
    private String supporterImageUrl;

    public SupporterImagesModel(String imageUrl) {
        this.supporterImageUrl = imageUrl;
    }


    protected SupporterImagesModel(Parcel in) {
        supporterImageUrl = in.readString();
    }

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
