/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.main;

import com.coderschool.android2.rubit.base.BasePresenter;
import com.coderschool.android2.rubit.base.BaseView;

/**
 * {@link MainContact}
 *
 * @author TienVNguyen
 */
interface MainContact {

    interface View extends BaseView<Presenter> {

        void showRequestPop();

        void showProfilePicture();

        void showImagePositive();

        void showImageNegative();

        void showDetails();

        void txtPopOnClick();

        void imgProfilePictureOnClick();

        void btnGoOnclick();

        void edtQuestBarOnKeyListener();

        void txtDetailsOnClick();
    }

    interface Presenter extends BasePresenter {

        void selectRequestPopUp();

        void selectGo();

        void updateEdtQuestBar();

        void selectDetails();
    }
}
