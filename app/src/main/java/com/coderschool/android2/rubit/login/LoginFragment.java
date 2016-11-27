/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.connectionDialog.ConnectionDialogListener;
import com.coderschool.android2.rubit.models.UserModel;
import com.coderschool.android2.rubit.register.RegisterActivity;
import com.coderschool.android2.rubit.tag.ChooseTagActivity;
import com.coderschool.android2.rubit.utils.ConnectionUtils;
import com.coderschool.android2.rubit.utils.FirebaseUtils;
import com.coderschool.android2.rubit.utils.GoogleApiClientUtils;
import com.coderschool.android2.rubit.utils.ProgressDialogHelper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vinay on 03/11/16.
 */
public class LoginFragment extends Fragment
        implements ConnectionDialogListener, LoginContact.View, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    public static final String TAG = LoginFragment.class.getSimpleName();
    private static final int RC_SIGN_IN = 9001;

    @BindView(R.id.sign_in_button)
    AppCompatImageView mSignInButton;
    @BindView(R.id.emailIv)
    RelativeLayout emailEdt;
    @BindView(R.id.passwordIv)
    RelativeLayout passwordEdt;
    @BindView(R.id.edtTxtEmail)
    AppCompatEditText edtTxtEmail;
    @BindView(R.id.edtTxtPassword)
    AppCompatEditText edtTxtPassword;
    @BindView(R.id.createAccountTv)
    RelativeLayout createAccountTv;

    private LoginContact.Presenter mPresenter;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mFirebaseAuth;

    /**
     * Constructor
     */
    public LoginFragment() {

    }

    /**
     * newInstance
     *
     * @return LoginFragment
     */
    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = GoogleApiClientUtils.authGoogleApiClient(getActivity(), getActivity(), this, googleSignInOptions);
        //Initialise FirebaseAuth
        mFirebaseAuth = FirebaseUtils.getFirebaseNewInstance();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = null;
        if (container != null) {
            root = LayoutInflater
                    .from(container.getContext())
                    .inflate(R.layout.fragment_login, container, false);
            ButterKnife.bind(this, root);

        }
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSignInButton.setOnClickListener(this);
        createAccountTv.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            Log.d(TAG, "statusCode:" + statusCode + "");
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                if (account != null)
                    firebaseAuthWithGoogle(account);
                else
                    Log.e(TAG, "[LOGIN_ACTIVITY] Account value is null");
            } else {
                // Google Sign In failed
                Log.e(TAG, "[LOGIN_ACTIVITY] Google Sign In failed.");
            }
        } else {
            // Google Sign In failed
            Log.e(TAG, "[LOGIN_ACTIVITY] Google Sign In failed.");
        }
    }

    /**
     * Firebase Auth With Google
     *
     * @param account GoogleSignInAccount
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "FirebaseAuthWithGoogle:" + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    ProgressDialogHelper.dismiss();

                    Log.d(TAG, "[LOGIN_ACTIVITY] SignInWithCredential:onComplete:" + task.isSuccessful());
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w(TAG, String.format("[LOGIN_ACTIVITY] SignInWithCredential:%s", task.getException()));
                        Toast.makeText(getActivity(), "Authentication Failed", Toast.LENGTH_LONG).show();
                    } else {
                        createUserIfNotExists();
                    }
                });
    }

    /**
     * create User If Not Exists
     */
    private void createUserIfNotExists() {
        if (null != FirebaseUtils.getCurrentUserRef()) {
            final String currentUserId = FirebaseUtils.getCurrentUserId();
            if (currentUserId != null) {
                final DatabaseReference rubitUser = FirebaseUtils.getRubitUser();
                rubitUser.child(currentUserId)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    UserModel user = FirebaseUtils.getUserDetails();
                                    rubitUser.child(currentUserId).setValue(user);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                System.out.println("[LOGIN_ACTIVITY] Failed to create new User in rubit_users: " + databaseError.getCode());
                            }
                        });

                Toast.makeText(getActivity(), "Login Success, Welcome " + FirebaseUtils.getCurrentUserName() + "!", Toast.LENGTH_LONG).show();
                startMainActivity();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showViews() {

    }

//    @Override
//    public void startAnotherActivity() {
//        new Handler().postDelayed(this::runnableAction, IntentConstants.SPLASH_TIME_OUT);
//    }

    /**
     * startMainActivity
     */
    private void startMainActivity() {
        Intent intent = new Intent(getActivity(), ChooseTagActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void setPresenter(LoginContact.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "[LOGIN_ACTIVITY] onConnectionFailed:" + connectionResult);
        Toast.makeText(getActivity(), R.string.google_play_services_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.createAccountTv:
                Intent intent = new Intent(getContext(), RegisterActivity.class);
                getActivity().startActivity(intent);
        }
    }

    /**
     * sign In
     */
    private void signIn() {
        if (ConnectionUtils.verifyConnectionDialogForFragment(getActivity(), this, getActivity().getSupportFragmentManager())) {
            new ProgressDialogHelper(getActivity());
            ProgressDialogHelper.show();
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            Log.e(TAG, getString(R.string.text_connection_error));
        }
    }

    @Override
    public void onFinishConnectionDialog() {
        signIn();
    }

}

