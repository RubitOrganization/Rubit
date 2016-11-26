package com.coderschool.android2.rubit.tag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.utils.ActivityUtils;

import butterknife.ButterKnife;

public class ChooseTagActivity extends AppCompatActivity {

    public static final String TAG = ChooseTagActivity.class.getSimpleName();

    private ChooseTagFragment mChooseTagFragment;
    private ChooseTagPresenter mChooseTagPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpLayout();
        setUpFragment();
        setUpPresenter();
    }

    private void setUpPresenter() {
        mChooseTagPresenter = new ChooseTagPresenter(mChooseTagFragment);
    }

    private void setUpFragment() {
        mChooseTagFragment = (ChooseTagFragment) getSupportFragmentManager().findFragmentById(R.id.chooseTagContentFrame);
        if (mChooseTagFragment == null) {
            mChooseTagFragment = ChooseTagFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mChooseTagFragment, R.id.chooseTagContentFrame);
        }
    }

    private void setUpLayout() {
        setContentView(R.layout.activity_choose_tag);
        ButterKnife.bind(this);
    }
}
