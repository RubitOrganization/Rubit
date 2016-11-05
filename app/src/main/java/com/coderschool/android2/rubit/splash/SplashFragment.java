/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.constants.IntentConstants;
import com.coderschool.android2.rubit.login.LoginActivity;
import com.coderschool.android2.rubit.main.MainActivity;
import com.coderschool.android2.rubit.utils.ConnectionUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * {@link SplashFragment} display an Logo image.
 *
 * @author TienVNguyen
 */
public class SplashFragment extends Fragment implements SplashContact.View {

    private static final String TAG = SplashFragment.class.getSimpleName();
    private ImageView mImage;
    private SplashContact.Presenter mPresenter;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    public SplashFragment() {
    }

    /**
     * newInstance
     *
     * @return {@link SplashFragment}
     */
    public static SplashFragment newInstance() {
        Bundle args = new Bundle();

        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpFirebase();
    }

    private void setUpFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.splash_fragment, container, false);
        mImage = (ImageView) root.findViewById(R.id.imgLogo);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    @Override
    public void showImage() {
        mImage.setVisibility(View.VISIBLE);
        startAnotherActivity();
    }

    @Override
    public void startAnotherActivity() {
        new Handler().postDelayed(this::runnableAction, IntentConstants.SPLASH_TIME_OUT);
    }

    /**
     * Get the action after the time out.
     */
    private void runnableAction() {
        if (ConnectionUtils.verifyConnectionDialog(getActivity(), getActivity().getSupportFragmentManager())) {
            if (mFirebaseUser == null) {
                final Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                final Intent intent = new Intent(getContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        } else {
            Log.e(TAG, getString(R.string.text_connection_error));
        }
    }

    @Override
    public void setPresenter(@NonNull SplashContact.Presenter presenter) {
        mPresenter = presenter;
    }
}
