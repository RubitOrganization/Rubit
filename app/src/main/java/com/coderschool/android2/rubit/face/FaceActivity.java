/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.face;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.constants.IntentConstants;
import com.coderschool.android2.rubit.models.TagItems;
import com.coderschool.android2.rubit.utils.ActivityUtils;

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
public class FaceActivity extends AppCompatActivity {

    private static final String TAG = FaceActivity.class.getSimpleName();

    @BindView(R.id.toolbar_actionbar)
    Toolbar mToolbar;
    Bundle bundle;

    private FaceFragment mFaceFragment;
    private FacePresenter mFacePresenter;
    boolean is_skip;
    List<TagItems> tagItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromTagScreenBundle();
        setUpLayout();
        setUpFragment();
        setUpPresenter();
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
        setSupportActionBar(mToolbar);

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

    public class BundleData {
        public final boolean isSkip;
        public List<TagItems> tagItemsList = new ArrayList<>();


        public BundleData(boolean isSkip, List<TagItems> tagItemsList) {
            this.isSkip = isSkip;
            this.tagItemsList = tagItemsList;
        }
    }
}
