/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.utils.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link MainActivity} displays main screen.
 *
 * @author TienVNguyen
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.toolbar_actionbar)
    Toolbar mToolbar;
    private MainFragment mMainFragment;
    private MainPresenter mMainPresenter;

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
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("MainActivity");
    }

    /**
     * set up presenter
     */
    private void setUpPresenter() {
        mMainPresenter = new MainPresenter(mMainFragment);
    }


    /**
     * set up fragment
     */
    private void setUpFragment() {
        mMainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (null == mMainFragment) {
            mMainFragment = MainFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mMainFragment,
                    R.id.contentFrame);
        }
    }
}
