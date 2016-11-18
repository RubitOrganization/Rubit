/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.face;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link FaceActivity} displays main screen.
 *
 * @author TienVNguyen
 */
public class FaceActivity extends AppCompatActivity {

    private static final String TAG = FaceActivity.class.getSimpleName();

    @BindView(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    private FaceFragment mFaceFragment;
    private FacePresenter mMainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpLayout();
        setUpFragment();
        setUpPresenter();
    }

    /**
     * set up layout
     */
    private void setUpLayout() {
        setContentView(R.layout.activity_face);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("FaceActivity");
    }

    /**
     * set up presenter
     */
    private void setUpPresenter() {
        mMainPresenter = new FacePresenter(mFaceFragment);
    }


    /**
     * set up fragment
     */
    private void setUpFragment() {
        mFaceFragment = (FaceFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == mFaceFragment) {
            mFaceFragment = FaceFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mFaceFragment,
                    R.id.contentFrame);
        }
    }
}
