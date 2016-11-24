/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.detailsTask;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.utils.ActivityUtils;

public class DetailsTaskActivity extends AppCompatActivity {

    private DetailsTaskFragment mDetailsTaskFragment;
    private DetailsTaskPresenter mDetailsTaskPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpLayout();
        setUpFragment();
        setUpPresenter();

    }

    private void setUpPresenter() {
        mDetailsTaskPresenter = new DetailsTaskPresenter(mDetailsTaskFragment);
    }

    private void setUpFragment() {
        mDetailsTaskFragment = (DetailsTaskFragment) getSupportFragmentManager().findFragmentById(R.id.contentDetailTaskFrame);
        if (mDetailsTaskFragment == null) {
            mDetailsTaskFragment = DetailsTaskFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mDetailsTaskFragment, R.id.contentDetailTaskFrame);
        }
    }

    private void setUpLayout() {
        setContentView(R.layout.activity_details_task);
    }
}
