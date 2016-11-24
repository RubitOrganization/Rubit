/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by vinay on 06/11/16.
 */
public class GoogleApiClientUtils {
    private static GoogleApiClient mGoogleApiClient;

    public static GoogleApiClient authGoogleApiClient(Context context,
                                                      FragmentActivity fragmentActivity,
                                                      GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener,
                                                      GoogleSignInOptions googleSignInOptions) {
        if (googleSignInOptions == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .enableAutoManage(fragmentActivity /*FragmentActivity*/, onConnectionFailedListener /*OnConnectionFailedListener*/)
                    .addApi(Auth.GOOGLE_SIGN_IN_API)
                    .build();
        } else {
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .enableAutoManage(fragmentActivity /*FragmentActivity*/, onConnectionFailedListener /*OnConnectionFailedListener*/)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                    .build();
        }
        return mGoogleApiClient;
    }
}
