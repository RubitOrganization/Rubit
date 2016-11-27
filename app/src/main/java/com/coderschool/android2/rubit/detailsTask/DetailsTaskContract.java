/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.detailsTask;

import com.coderschool.android2.rubit.base.BasePresenter;
import com.coderschool.android2.rubit.base.BaseView;

/**
 * Created by vinay on 22/11/16.
 */
public class DetailsTaskContract {

    interface View extends BaseView<Presenter> {

        void showGoogleMap();

        void currentLocationOnClick();

        void showAddressDetails();

        void showFirstTextView();

        void showSecondTextView();

        void showThirdTextView();

        void showPictureImageView();

        void editFirstTextOnKeyListener();

        void editSecondTextOnKeyListener();

        void editThirdTextOnKeyListener();

        void plusButtonOnClick();

        void showPlusButton();

    }

    interface Presenter extends BasePresenter {

        void selectCurrentLocationOnMap();

        void updateFirstTextView();

        void updateSecondTextView();

        void updateThirdTextView();

        void selectPicturePlusButton();
    }
}
