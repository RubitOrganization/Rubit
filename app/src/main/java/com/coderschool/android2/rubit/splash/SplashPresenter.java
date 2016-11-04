/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.splash;

import android.support.annotation.NonNull;

/**
 * {@link SplashPresenter}
 *
 * @author TienVNguyen
 */
class SplashPresenter implements SplashContact.Presenter {

    private final SplashContact.View mView;

    /**
     * Constructor
     *
     * @param view {@link SplashContact.View}
     */
    SplashPresenter(@NonNull SplashContact.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void addImage() {
        mView.showImage();
    }

    @Override
    public void start() {
        addImage();
    }
}
