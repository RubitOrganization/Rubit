package com.coderschool.android2.rubit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vinay on 06/11/16.
 */

public class UserModel implements Parcelable {
    private String mUid;
    private String mEmail;
    private String mDisplayName;
    private String mPhotoUrl;


    public UserModel() {

    }

    public UserModel(Parcel in) {
        mUid = in.readString();
        mEmail = in.readString();
        mDisplayName = in.readString();
        mPhotoUrl = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public UserModel(String uid, String email, String displayName, String photoUrl) {
        this.mUid = uid;
        this.mEmail = email;
        this.mDisplayName = displayName;
        this.mPhotoUrl = photoUrl;
    }

    public String getUid() {
        return mUid;
    }

    public void setUid(String uid) {
        mUid = uid;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    public String getPhotoUrl() {
        return mPhotoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        mPhotoUrl = photoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUid);
        parcel.writeString(mEmail);
        parcel.writeString(mDisplayName);
        parcel.writeString(mPhotoUrl);
    }
}
