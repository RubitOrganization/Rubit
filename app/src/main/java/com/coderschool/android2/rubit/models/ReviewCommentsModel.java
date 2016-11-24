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
public class ReviewCommentsModel implements Parcelable {
    public static final Creator<ReviewCommentsModel> CREATOR = new Creator<ReviewCommentsModel>() {
        @Override
        public ReviewCommentsModel createFromParcel(Parcel in) {
            return new ReviewCommentsModel(in);
        }

        @Override
        public ReviewCommentsModel[] newArray(int size) {
            return new ReviewCommentsModel[size];
        }
    };
    private String reviewerAvatar;
    private String reviewerName;
    private float ratingCount;
    private String headingRequestName;
    private String commentText;
    private String imageUrl;

    private ReviewCommentsModel(Parcel in) {
        reviewerAvatar = in.readString();
        reviewerName = in.readString();
        ratingCount = in.readFloat();
        headingRequestName = in.readString();
        commentText = in.readString();
        imageUrl = in.readString();
    }

    public ReviewCommentsModel(String reviewerAvatar, String reviewerName, float ratingBarCount, String headingText, String commentText, String imageUrl) {
        this.reviewerAvatar = reviewerAvatar;
        this.reviewerName = reviewerName;
        this.ratingCount = ratingBarCount;
        this.headingRequestName = headingText;
        this.commentText = commentText;
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(reviewerAvatar);
        dest.writeString(reviewerName);
        dest.writeFloat(ratingCount);
        dest.writeString(headingRequestName);
        dest.writeString(commentText);
        dest.writeString(imageUrl);
    }

    public String getReviewerAvatar() {
        return reviewerAvatar;
    }

    public void setReviewerAvatar(String reviewerAvatar) {
        this.reviewerAvatar = reviewerAvatar;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public float getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(float ratingCount) {
        this.ratingCount = ratingCount;
    }

    public String getHeadingRequestName() {
        return headingRequestName;
    }

    public void setHeadingRequestName(String headingRequestName) {
        this.headingRequestName = headingRequestName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
