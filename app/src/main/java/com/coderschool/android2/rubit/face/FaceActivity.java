/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.face;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.constants.IntentConstants;
import com.coderschool.android2.rubit.editProfile.EditProfileFragment;
import com.coderschool.android2.rubit.login.LoginActivity;
import com.coderschool.android2.rubit.models.TagItems;
import com.coderschool.android2.rubit.models.UserModel;
import com.coderschool.android2.rubit.settings.SettingsFragment;
import com.coderschool.android2.rubit.utils.ActivityUtils;
import com.coderschool.android2.rubit.utils.FirebaseUtils;
import com.coderschool.android2.rubit.utils.GoogleApiClientUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link FaceActivity} displays main screen.
 *
 * @author TienVNguyen
 */
public class FaceActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = FaceActivity.class.getSimpleName();

    //    @BindView(R.id.toolbar_actionbar)
//    Toolbar mToolbar;
    Bundle bundle;
    @BindView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigationView)
    NavigationView mNavigationView;

    private FaceFragment mFaceFragment;
    private FacePresenter mFacePresenter;
    boolean is_skip;
    List<TagItems> tagItemsList;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase mFirebaseDatabase;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromTagScreenBundle();
        setUpLayout();
//        setUpGoogleApiClient();
        setUpFragment();
        setDrawerContent(mNavigationView);
        setUpPresenter();
        mFirebaseAuth = FirebaseUtils.getFirebaseNewInstance();
    }

    private void setUpGoogleApiClient() {
        mGoogleApiClient = GoogleApiClientUtils.authGoogleApiClient(this, this, this, null);
    }

    private void setDrawerContent(NavigationView navigationView) {
        UserModel userModel = FirebaseUtils.getUserDetails();
        View hView = navigationView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.name);
        nav_user.setText(userModel.getDisplayName());
        TextView nav_email = (TextView) hView.findViewById(R.id.email);
        nav_email.setText(userModel.getEmail());
        RoundedImageView nav_circle_img = (RoundedImageView) hView.findViewById(R.id.circleView);
        Glide.with(this)
                .load(userModel.getPhotoUrl())
                .into(nav_circle_img);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (item.getItemId()) {
            case R.id.home_screen:
                Toast.makeText(this, "home_screen", Toast.LENGTH_SHORT).show();
                fragmentClass = FaceFragment.class;
                break;
            case R.id.edit_profile:
                Toast.makeText(this, "edit_profile", Toast.LENGTH_SHORT).show();
                fragmentClass = EditProfileFragment.class;
                break;
            case R.id.settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.logout:
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                //here we just implment firebaseUser and FirebaseAuth to logout
//                FirebaseUtils.setCurrentUserToNull(null);
//                mFirebaseAuth.signOut();
//                mFirebaseDatabase = null;
//                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                moveBackToLoginActivity();
                break;
            default:
                Toast.makeText(this, "home_screen", Toast.LENGTH_SHORT).show();
                fragmentClass = FaceFragment.class;
                break;
        }

        if (fragmentClass != null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (fragment.getClass().getSimpleName().equals(FaceFragment.class.getSimpleName())) {
                ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                        fragment,
                        R.id.contentFrame);
                setUpPresenter();
            } else {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.contentFrame, fragment)
                        .commit();
            }
        }

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();

    }

    private void moveBackToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigationView:
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(new BundleData(is_skip, tagItemsList));
    }

    private void getDataFromTagScreenBundle() {
        bundle = getIntent().getExtras();
        if (bundle != null) {
            is_skip = bundle.getBoolean(IntentConstants.IS_SKIP, false);
            tagItemsList = bundle.getParcelableArrayList(IntentConstants.LIST_OF_CHOSEN_TAGS);
            if (tagItemsList != null) {
                for (TagItems listOfTags : tagItemsList) {
                    String tagName = listOfTags.getTagName();
                    Log.d("tagName", tagName + "");
                }
            }
//            Log.d("is_skip", is_skip + "");
//            Log.d("is_skip", tagItemsList.size() + "");
        }
    }

    /**
     * set up layout
     */
    private void setUpLayout() {
        setContentView(R.layout.activity_face);
        ButterKnife.bind(this);
//        setSupportActionBar(mToolbar);
//        setupNavHeader();
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("FaceActivity");
    }

    /**
     * set up presenter
     */
    private void setUpPresenter() {
        mFacePresenter = new FacePresenter(mFaceFragment);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "[FACE_ACTIVITY] onConnectionFailed:-" + connectionResult);
    }

    public class BundleData {
        public final boolean isSkip;
        public List<TagItems> tagItemsList = new ArrayList<>();


        public BundleData(boolean isSkip, List<TagItems> tagItemsList) {
            this.isSkip = isSkip;
            this.tagItemsList = tagItemsList;
        }
    }
}
