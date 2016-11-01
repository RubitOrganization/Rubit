/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.utils.ActivityUtils;

import butterknife.ButterKnife;

/**
 * {@link SplashActivity} displays splash screen.
 *
 * @author TienVNguyen
 */
public class SplashActivity extends AppCompatActivity {

    private SplashPresenter mSplashPresenter;
    private SplashFragment mSplashFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpLayout();
        setUpFragment();
        setUpPresenter();
    }

    /**
     * set up layout
     */
    private void setUpLayout() {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    /**
     * set up fragment
     */
    private void setUpFragment() {
        mSplashFragment = (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == mSplashFragment) {
            mSplashFragment = SplashFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mSplashFragment, R.id.contentFrame);
        }
    }

    /**
     * set up presenter
     */
    private void setUpPresenter() {
        mSplashPresenter = new SplashPresenter(mSplashFragment);
    }
}
