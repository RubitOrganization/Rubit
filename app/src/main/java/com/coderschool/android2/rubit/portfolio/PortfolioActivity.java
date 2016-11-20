/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.portfolio;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.connectionDialog.ConnectionDialogListener;
import com.coderschool.android2.rubit.constants.DatabaseConstants;
import com.coderschool.android2.rubit.constants.IntentConstants;
import com.coderschool.android2.rubit.login.LoginActivity;
import com.coderschool.android2.rubit.utils.ConnectionUtils;
import com.coderschool.android2.rubit.utils.FirebaseUtils;
import com.coderschool.android2.rubit.utils.FlowLayout;
import com.coderschool.android2.rubit.utils.GoogleApiClientUtils;
import com.coderschool.android2.rubit.utils.ImageUtils;
import com.coderschool.android2.rubit.utils.LoadFakeImages;
import com.coderschool.android2.rubit.utils.PostFakeDataForReviewComments;
import com.coderschool.android2.rubit.utils.TextViewUtils;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PortfolioActivity extends AppCompatActivity
        implements ConnectionDialogListener, GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = PortfolioActivity.class.getSimpleName();

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rvRoot)
    RelativeLayout rvRoot;

    @BindView(R.id.includePortfolio)
    View includePortfolio;

    /* PROFILE */
    @BindView(R.id.rlPortfolio)
    RelativeLayout rlPortfolio;
    @BindView(R.id.imgProfilePicture)
    ImageView imgProfilePicture;
    @BindView(R.id.txtUserName)
    TextView txtUserName;
    @BindView(R.id.txtJobTitle)
    TextView txtJobTitle;

    /* QUOTE */
    @BindView(R.id.separateLine0)
    View separateLine0;
    @BindView(R.id.rlQuote)
    LinearLayout rlQuote;
    @BindView(R.id.txtAbout)
    TextView txtAbout;
    @BindView(R.id.txtQuote)
    TextView txtQuote;

    /* BADGES */
    @BindView(R.id.rlBadges)
    RelativeLayout rlBadges;
    @BindView(R.id.txtBadgeTitle)
    TextView txtBadgeTitle;
//    @BindView(R.id.rvBadges)
//    RecyclerView rvBadges;

    /* OVERVIEW */
    @BindView(R.id.separateLine1)
    View separateLine1;
    @BindView(R.id.rlOverview)
    RelativeLayout rlOverview;
    @BindView(R.id.txtOverviewTitle)
    TextView txtOverviewTitle;
    @BindView(R.id.imgEditTitle)
    ImageView imgEditTitle;
    @BindView(R.id.txtOverviewText)
    TextView txtOverviewText;

    /* TAGS */
    @BindView(R.id.separateLine2)
    View separateLine2;
    @BindView(R.id.rlTags)
    RelativeLayout rlTags;
    @BindView(R.id.txtTags)
    TextView txtTags;
    @BindView(R.id.imgEditTags)
    ImageView imgEditTags;
    @BindView(R.id.fl_content)
    FlowLayout mFlowLayout;

    /* REVIEWS */
    @BindView(R.id.separateLine3)
    View separateLine3;
    @BindView(R.id.rlReviews)
    RelativeLayout rlReviews;
    @BindView(R.id.txtReviewTitle)
    TextView txtReviewTitle;
    @BindView(R.id.txtReviewCount)
    TextView txtReviewCount;
    @BindView(R.id.imgReview)
    ImageView imgReview;
    @BindView(R.id.rvReview)
    RecyclerView rvReview;

    /* IMAGES */
    @BindView(R.id.separateLine4)
    View separateLine4;
    @BindView(R.id.rlImages)
    RelativeLayout rlImages;
    @BindView(R.id.txtImage)
    TextView txtImage;
    @BindView(R.id.imgEditImage)
    ImageView imgEditImage;
    @BindView(R.id.rvPortfolios)
    RecyclerView rvPortfolios;

    private FirebaseAuth mFirebaseAuth;
    private GoogleApiClient mGoogleApiClient;
    private String mQuest;
    private String mUserId;


    // once after we get the real tags from user
    private String[] strs = new String[]{
            "#Android", "#iOS", "#Nodejs", "#Ruby"
    };
    private int value = 0; // boolean value for click event

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        ButterKnife.bind(this);

        if (ConnectionUtils.verifyConnectionDialogForActivity(this, this.getSupportFragmentManager())) {
            setUpGoogleApiClient();
            setUpFirebase();
            verifyDoesUserExists();
            fetchIntentData();
            fetchingData();

        }
    }

    /**
     * fetchIntentData
     */
    public void fetchIntentData() {
        final Intent intent = getIntent();
        if (null == intent) return;
        mQuest = intent.getStringExtra(IntentConstants.QUEST);
        mUserId = intent.getStringExtra(IntentConstants.USER_ID);
    }

    /**
     * fetching Data
     */
    private void fetchingData() {
        FirebaseUtils.getCurrentUserRef()//TODO: Un-know for TAG
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        updateDataForProfile(dataSnapshot);
                        updateDataForQuote(dataSnapshot);
                        updateDataForBadges();
                        updateDataForOverviews();
                        updateDataForTags();
                        updateReviewComments();
                        updateSupporterImages();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed in user (Portfolio): " + databaseError.getCode());
                    }
                });
    }

    private void updateSupporterImages() {
        //allows for optimizations  // in future we need to remove this below line for nwo i'm placing
        rvPortfolios.setHasFixedSize(true);
        // Define Grid Layout Manager layout
        final LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //pass the layout to recyclerView layout manager
        rvPortfolios.setLayoutManager(layout);
        //create adapter
        DisplaySupporterImagesAdapter supporterImagesAdapter = new DisplaySupporterImagesAdapter(this, LoadFakeImages.getImages());
        //assign adapter to recyclerView
        rvPortfolios.setAdapter(supporterImagesAdapter);

    }

    private void updateReviewComments() {
        //allows for optimizations  // in future we need to remove this below line for nwo i'm placing
        rvReview.setHasFixedSize(true);
        // Define Linear Layout Manager layout
        final LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //pass the layout to recyclerView layout manager
        rvReview.setLayoutManager(layout);
        //create a adapter
        ReviewCommentsAdapter adapter = new ReviewCommentsAdapter(this, PostFakeDataForReviewComments.reviewComments());
        //assign adapter to recyclerView
        rvReview.setAdapter(adapter);
    }

    /**
     * updateDataForProfile
     *
     * @param dataSnapshot {@link DataSnapshot}
     */
    private void updateDataForProfile(DataSnapshot dataSnapshot) {
        ImageUtils.loadingImageWithRoundTransform(getApplicationContext(), imgProfilePicture, String.valueOf(dataSnapshot.child(DatabaseConstants.PHOTO_URL).getValue()), true);
        TextViewUtils.setTextView(txtUserName, dataSnapshot.child(DatabaseConstants.DISPLAY_NAME).getValue());
        TextViewUtils.setTextView(txtJobTitle, dataSnapshot.child(DatabaseConstants.JOB_TITLE).getValue());
    }

    /**
     * updateDataForOverviews
     *
     * @param dataSnapshot {@link DataSnapshot}
     */
    private void updateDataForQuote(DataSnapshot dataSnapshot) {
        TextViewUtils.setTextView(txtQuote, dataSnapshot.child(DatabaseConstants.QUOTE).getValue());
    }

    /**
     * updateDataForTags
     */
    private void updateDataForTags() {
        for (int i = 0; i < strs.length; i++) {
            final TextView tv = new TextView(this);
            tv.setPadding(50, 8, 50, 8);
            tv.setGravity(Gravity.CENTER);

            FlowLayout.LayoutParams lp = new FlowLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(25, 25, 25, 25);
            tv.setLayoutParams(lp);
            tv.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            tv.setText(strs[i]);
//            tv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (value == 0) {
//                        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_highlight_off_black_24dp, 0);
//                        value = 1;
//                    } else {
//                        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//                        value = 0;
//                    }
//                }
//            });
            mFlowLayout.addView(tv);
        }
    }

    /**
     * updateDataForOverviews
     */
    private void updateDataForOverviews() {

    }

    /**
     * updateDataForBadges
     */
    private void updateDataForBadges() {

    }

    /**
     * verify current user
     */
    private void verifyDoesUserExists() {
        if (FirebaseUtils.getCurrentUserRef() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }
    }

    /**
     * set up google Api client
     */
    private void setUpGoogleApiClient() {
        mGoogleApiClient = GoogleApiClientUtils.authGoogleApiClient(this, this, this, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:-" + connectionResult);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onFinishConnectionDialog() {
        onResume();
    }

    /**
     * set up Firebase
     */
    private void setUpFirebase() {
        mFirebaseAuth = FirebaseUtils.getFirebaseNewInstance();
    }
}
