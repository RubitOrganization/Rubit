/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.main;

import android.support.annotation.NonNull;

/**
 * {@link MainPresenter}
 *
 * @author TienVNguyen
 */
class MainPresenter implements MainContact.Presenter {

    private MainContact.View mvView;

    /**
     * Constructor
     *
     * @param view {@link MainContact.View}
     */
    MainPresenter(final @NonNull MainContact.View view) {
        mvView = view;
        mvView.setPresenter(this);

    }

    @Override
    public void start() {
        selectRequestPopUp();
        selectGo();
        updateEdtQuestBar();
        selectDetails();

        mvView.showRequestPop();
        mvView.showProfilePicture();
        mvView.showImagePositive();
        mvView.showImageNegative();
        mvView.showDetails();
    }

    @Override
    public void selectRequestPopUp() {
        mvView.txtPopOnClick();
        mvView.imgProfilePictureOnClick();
    }

    @Override
    public void selectGo() {
        mvView.btnGoOnclick();
    }

    @Override
    public void updateEdtQuestBar() {
        mvView.edtQuestBarOnKeyListener();
    }

    @Override
    public void selectDetails() {
        mvView.txtDetailsOnClick();
    }
}
