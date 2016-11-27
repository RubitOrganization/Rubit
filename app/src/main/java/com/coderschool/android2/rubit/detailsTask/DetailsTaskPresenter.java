/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.detailsTask;

import android.support.annotation.Nullable;

/**
 * Created by vinay on 22/11/16.
 */
public class DetailsTaskPresenter implements DetailsTaskContract.Presenter {

    private DetailsTaskContract.View mView;

    DetailsTaskPresenter(final @Nullable DetailsTaskContract.View view) {
        mView = view;
        if (mView != null) {
            mView.setPresenter(this);
        }
    }


    @Override
    public void selectCurrentLocationOnMap() {
        mView.currentLocationOnClick();
    }

    @Override
    public void updateFirstTextView() {
        mView.editFirstTextOnKeyListener();

    }

    @Override
    public void updateSecondTextView() {
        mView.editSecondTextOnKeyListener();
    }

    @Override
    public void updateThirdTextView() {
        mView.editThirdTextOnKeyListener();
    }

    @Override
    public void selectPicturePlusButton() {
        mView.plusButtonOnClick();
    }

    @Override
    public void start() {
        selectCurrentLocationOnMap();
        updateFirstTextView();
        updateSecondTextView();
        updateThirdTextView();
        selectPicturePlusButton();

        mView.showFirstTextView();
        mView.showSecondTextView();
        mView.showThirdTextView();
        mView.showPictureImageView();
        mView.showPlusButton();
        mView.showAddressDetails();
        mView.showGoogleMap();
    }
}
