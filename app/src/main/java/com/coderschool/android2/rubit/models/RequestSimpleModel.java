/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.models;

/**
 * {@link RequestSimpleModel}
 *
 * @author TienVNguyen
 */
public class RequestSimpleModel {
    private RequestModel requestModel;
    private UserModel userModel;

    public RequestSimpleModel() {
    }

    public RequestSimpleModel(RequestModel requestModel, UserModel userModel) {
        this.requestModel = requestModel;
        this.userModel = userModel;
    }

    public RequestModel getRequestModel() {
        return requestModel;
    }

    public void setRequestModel(RequestModel requestModel) {
        this.requestModel = requestModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
