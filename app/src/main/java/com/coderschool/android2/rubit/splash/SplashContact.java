/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.splash;

import com.coderschool.android2.rubit.base.BasePresenter;
import com.coderschool.android2.rubit.base.BaseView;

/**
 * {@link SplashContact}
 *
 * @author TienVNguyen
 */
interface SplashContact {

    interface View extends BaseView<Presenter> {

        void showImage();

        void startAnotherActivity();
    }

    interface Presenter extends BasePresenter {

        void addImage();
    }
}
