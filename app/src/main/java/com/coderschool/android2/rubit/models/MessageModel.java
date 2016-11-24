/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.models;

import android.os.Parcelable;

/**
 * {@link MessageModel}
 *
 * @author TienVNguyen
 */
public class MessageModel implements Parcelable {
    public static final Creator<MessageModel> CREATOR = new Creator<MessageModel>() {
        @Override
        public MessageModel createFromParcel(android.os.Parcel in) {
            return new MessageModel(in);
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };
    private String uid;
    private String requestId;
    private String messageId;
    private String messageText;

    public MessageModel() {
    }

    public MessageModel(String uid, String requestId, String messageId, String messageText) {
        this.uid = uid;
        this.requestId = requestId;
        this.messageId = messageId;
        this.messageText = messageText;
    }

    private MessageModel(android.os.Parcel in) {
        uid = in.readString();
        requestId = in.readString();
        messageId = in.readString();
        messageText = in.readString();
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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(uid);
        parcel.writeString(requestId);
        parcel.writeString(messageId);
        parcel.writeString(messageText);
    }
}
