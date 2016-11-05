/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.connectionDialog.ConnectionDialogListener;
import com.coderschool.android2.rubit.login.LoginActivity;
import com.coderschool.android2.rubit.utils.ConnectionUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link MainActivity}
 */
public class MainActivity extends AppCompatActivity implements ConnectionDialogListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.toolbar_actionbar)
    Toolbar mToolbar;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private String mDisplayName, mEmail, mPhotoUrl;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("MainActivity");
        if (!ConnectionUtils.verifyConnectionDialog(this, getSupportFragmentManager())) {
            Log.e("Test!", "Test");
        } else {
            //initialise Firebase Auth
            setUpFirebase();
            getCurrentUser();

            if (mFirebaseUser == null) {
                // Not signed in. launch the sign in activity
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return;
            } else {
                mDisplayName = mFirebaseUser.getDisplayName();
                mEmail = mFirebaseUser.getEmail();
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
                Toast.makeText(this, "name :-" + mDisplayName + " mEmail:-" + mEmail, Toast.LENGTH_LONG).show();
            }

            setUpGoogleApiClient();
        }
    }

    private void getCurrentUser() {
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    private void setUpFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void setUpGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /*FragmentActivity*/, this /*onConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
    }

    @Override
    public void onFinishConnectionDialog() {


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:-" + connectionResult);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the actionbar if it is parent
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mFirebaseUser = null;
                mPhotoUrl = null;
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }
}
