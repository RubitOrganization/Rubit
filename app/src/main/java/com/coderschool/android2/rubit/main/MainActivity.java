/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.coderschool.android2.rubit.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.connectionDialog.ConnectionDialogListener;
import com.coderschool.android2.rubit.utils.ConnectionUtils;

/**
 * {@link MainActivity}
 */
public class MainActivity extends AppCompatActivity implements ConnectionDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!ConnectionUtils.verifyConnectionDialog(this, getSupportFragmentManager())) {
            Log.e("Test!", "Test");
        }
    }

    @Override
    public void onFinishConnectionDialog() {

    }
}
