/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.face;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.chat.ChatActivity;
import com.coderschool.android2.rubit.connectionDialog.ConnectionDialogListener;
import com.coderschool.android2.rubit.constants.DatabaseConstants;
import com.coderschool.android2.rubit.constants.IntentConstants;
import com.coderschool.android2.rubit.detailsTask.DetailsTaskActivity;
import com.coderschool.android2.rubit.login.LoginActivity;
import com.coderschool.android2.rubit.models.RequestModel;
import com.coderschool.android2.rubit.models.UserModel;
import com.coderschool.android2.rubit.requestList.RequestListActivity;
import com.coderschool.android2.rubit.utils.ConnectionUtils;
import com.coderschool.android2.rubit.utils.GoogleApiClientUtils;
import com.coderschool.android2.rubit.utils.ImageUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link FaceFragment}
 *
 * @author TienNguyen
 */
public class FaceFragment extends Fragment
        implements FaceContact.View, ConnectionDialogListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = FaceFragment.class.getSimpleName();

    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.imgProfilePicture)
    ImageView imgProfilePicture;
    @BindView(R.id.imgGo)
    ImageView imgGo;
    @BindView(R.id.txtPop)
    TextView txtPop;
    @BindView(R.id.txtDetails)
    TextView mTxtDetails;
    @BindView(R.id.edtQuestBar)
    EditText edtQuestBar;

    private FaceContact.Presenter mPresenter;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private GoogleApiClient mGoogleApiClient;
    private Handler handler = new Handler();
    private RequestModel mRequestModel = new RequestModel();
    private UserModel mUserMode = new UserModel();

    private String uid, mDisplayName, mEmail, mPhotoUrl;
    /**
     * runnable
     */
    private Runnable runnable = this::startRunnableForPop;

    /**
     * Constructor
     */
    public FaceFragment() {
    }

    /**
     * newInstance
     *
     * @return {@link FaceFragment}
     */
    public static FaceFragment newInstance() {
        final Bundle args = new Bundle();

        final FaceFragment fragment = new FaceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ConnectionUtils.verifyConnectionDialogForFragment(getActivity(), this, getActivity().getSupportFragmentManager())) {
            setUpGoogleApiClient();
            setUpFirebase();
            getCurrentUser();
            verifyCurrentUser();
            //createUserInFirebaseIfNotExist();
        }
    }

//    private void createUserInFirebaseIfNotExist() {
//        if (!ConnectionUtils.verifyConnectionDialogForFragment(getActivity(), this, getActivity().getSupportFragmentManager())) {
//            Log.e("Test!", "Test");
//        } else {
//            //initialise Firebase Auth
//            final DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
//            if (uid != null) {
//                UserModel user = new UserModel(uid, mEmail, mDisplayName, mPhotoUrl);
//                mDatabaseReference.child(DatabaseConstants.RUBIT_USERS)
//                        .child(uid)
//                        .addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                if (!dataSnapshot.exists()) {
//                                    userDoesNotExistsPushToFirebase(user, mDatabaseReference);
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//                                System.out.println("failed to create new User in rubit_users: " + databaseError.getCode());
//                            }
//                        });
//            } else {
//                Log.e(TAG, "UID is null");
//            }
//        }
//    }

//    private void userDoesNotExistsPushToFirebase(UserModel user, DatabaseReference mDatabaseReference) {
//        mDatabaseReference.child(DatabaseConstants.RUBIT_USERS)
//                .child(uid)
//                .setValue(user);
//    }

    @Override
    public void onResume() {
        super.onResume();
        if (ConnectionUtils.verifyConnectionDialogForFragment(getActivity(), this, getActivity().getSupportFragmentManager())) {
            mPresenter.start();
            getCurrentUser();
            runnable.run();
        }
    }

    /**
     * stopRunnableForPop
     */
    public void stopRunnableForPop() {
        handler.removeCallbacks(runnable);
    }

    /**
     * startRunnableForPop
     * TODO: Temperate solution
     */
    public void startRunnableForPop() {
        handler.postDelayed(runnable, IntentConstants.POP_REQUEST_TIME);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabase.getReference(DatabaseConstants.REQUEST)
                .orderByChild(mFirebaseUser.getUid())
                .equalTo(null)
                .limitToFirst(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            mRequestModel.setSubject(String.valueOf(snapshot.child(DatabaseConstants.SUBJECT).getValue()));
                            mFirebaseDatabase.getReference(DatabaseConstants.RUBIT_USERS)
                                    .child(String.valueOf(snapshot.child(DatabaseConstants.UID).getValue()))
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            mUserMode.setPhotoUrl(String.valueOf(dataSnapshot.child(DatabaseConstants.PHOTO_URL).getValue()));
                                            showProfilePicture();
                                            showRequestPop();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            System.out.println("The read failed in user: " + databaseError.getCode());
                                        }
                                    });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed in requests: " + databaseError.getCode());
                    }
                });
    }

    /**
     * verify current user
     */
    private void verifyCurrentUser() {
        if (mFirebaseUser == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        } else {
            uid = mFirebaseUser.getUid();
            mDisplayName = mFirebaseUser.getDisplayName();
            mEmail = mFirebaseUser.getEmail();
            mPhotoUrl = String.valueOf(mFirebaseUser.getPhotoUrl());
            Toast.makeText(getActivity(), "name :-" + mDisplayName + " mEmail:-" + mEmail, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * set up google Api client
     */
    private void setUpGoogleApiClient() {
        mGoogleApiClient = GoogleApiClientUtils.authGoogleApiClient(getActivity(), getActivity(), this, null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_face, container, false);
        if (null != view) {
            ButterKnife.bind(this, view);
        }
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                mFirebaseUser = null;
                mPhotoUrl = null;
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:-" + connectionResult);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }

    @Override
    public void setPresenter(FaceContact.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showRequestPop() {
        if (null != mRequestModel.getSubject() && 0 < mRequestModel.getSubject().length()) {
            txtPop.setVisibility(View.VISIBLE);
            txtPop.setText(mRequestModel.getSubject());
        } else {
            txtPop.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProfilePicture() {
        if (null != mUserMode.getPhotoUrl() && 0 < mUserMode.getPhotoUrl().length()) {
            imgProfilePicture.setVisibility(View.VISIBLE);
            ImageUtils.loadingImageWithRoundTransform(getContext(), imgProfilePicture, mUserMode.getPhotoUrl(), true);
        } else {
            imgProfilePicture.setVisibility(View.GONE);
        }
    }

    @Override
    public void showImagePositive() {
        imgLogo.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.main_screen_logo));
    }

    @Override
    public void showImageNegative() {
        // TODO: Later
    }

    @Override
    public void showDetails() {
        final String detailsText = mTxtDetails.getText().toString();
        final SpannableString content = new SpannableString(detailsText);
        content.setSpan(new UnderlineSpan(), 0, detailsText.length(), 0);
        mTxtDetails.setText(content);
    }

    @Override
    public void txtPopOnClick() {
        txtPop.setOnClickListener(view -> {
            stopRunnableForPop();

            final Intent intent = new Intent(getContext(), RequestListActivity.class);
            intent.putExtra(IntentConstants.REQUEST_MODEL, mRequestModel);
            startActivity(intent);
        });
    }

    @Override
    public void imgProfilePictureOnClick() {
        imgProfilePicture.setOnClickListener(view -> {
            stopRunnableForPop();

            final Intent intent = new Intent(getContext(), RequestListActivity.class);
            intent.putExtra(IntentConstants.REQUEST_MODEL, mRequestModel);
            startActivity(intent);
        });
    }

    @Override
    public void btnGoOnclick() {
        imgGo.setOnClickListener(view -> {
            if (0 >= edtQuestBar.getText().length()) {
                return;
            }

            stopRunnableForPop();

            final RequestModel newRequest = new RequestModel();
            newRequest.setSubject(edtQuestBar.getText().toString());
            newRequest.setUid(mFirebaseUser.getUid());
            newRequest.setConnected(false);
            newRequest.setCompleted(false);

            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mFirebaseDatabase.getReference()
                    .child(DatabaseConstants.REQUEST)
                    .child(edtQuestBar.getText().toString())
                    .setValue(newRequest).addOnCompleteListener(task -> {

                Map<String, Object> requests = new HashMap<>();
                requests.put(newRequest.getSubject(), true);

                mFirebaseDatabase.getReference()
                        .child(DatabaseConstants.RUBIT_USERS)
                        .child(mFirebaseUser.getUid()).child(DatabaseConstants.REQUEST)
                        .updateChildren(requests, (databaseError, databaseReference) -> {

                            final Intent intent = new Intent(FaceFragment.this.getContext(), ChatActivity.class);
                            intent.putExtra(IntentConstants.QUEST, edtQuestBar.getText().toString());
                            intent.putExtra(IntentConstants.USER_ID, mFirebaseUser.getUid());
                            FaceFragment.this.startActivity(intent);
                        });
            });


        });
    }

    @Override
    public void edtQuestBarOnKeyListener() {
        edtQuestBar.setOnKeyListener((view, i, keyEvent) -> false);
    }

    @Override
    public void txtDetailsOnClick() {
        mTxtDetails.setOnClickListener(view -> {
            stopRunnableForPop();

            final Intent intent = new Intent(getContext(), DetailsTaskActivity.class);
            intent.putExtra(IntentConstants.QUEST, edtQuestBar.getText().toString());
            startActivity(intent);
        });
    }

    @Override
    public void onFinishConnectionDialog() {
        onResume();
    }

    /**
     * get current user
     */
    private void getCurrentUser() {
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
    }

    /**
     * set up Firebase
     */
    private void setUpFirebase() {
        mFirebaseAuth = FirebaseAuth.getInstance();
    }
}
