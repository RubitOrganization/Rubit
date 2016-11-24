/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.utils.ActivityUtils;

import butterknife.ButterKnife;

/**
 * LoginActivity
 */
public class LoginActivity extends AppCompatActivity {

    private LoginFragment mLoginFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpLayout();
        setUpFragment();
        setUpPresenter();

    }

    private void setUpLayout() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void setUpFragment() {
        mLoginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentLoginFrame);
        if (mLoginFragment == null) {
            mLoginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mLoginFragment, R.id.contentLoginFrame);
        }
    }

    private void setUpPresenter() {
        LoginPresenter loginPresenter = new LoginPresenter(mLoginFragment);
    }

}
