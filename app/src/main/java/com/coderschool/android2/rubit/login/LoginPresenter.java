/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.login;

import android.support.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vinay on 03/11/16.
 */
public class LoginPresenter implements LoginContact.Presenter {

    private final LoginContact.View mView;

    public LoginPresenter(@Nullable LoginContact.View view) {
        mView = checkNotNull(view, "View cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void addViews() {
        mView.showViews();
    }

    @Override
    public void start() {
        addViews();
    }
}
