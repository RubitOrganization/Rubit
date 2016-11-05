package com.coderschool.android2.rubit.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.utils.ActivityUtils;

import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    private LoginFragment mLoginFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpLayout();
        setUpFragment();
        setUpPresenter();

    }

    private void setUpLayout() {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    private void setUpFragment() {
        mLoginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentLoginFrame);
        if (mLoginFragment == null) {
            mLoginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mLoginFragment, R.id.contentLoginFrame);
        }
    }

    private void setUpPresenter() {
        LoginPresenter loginPresenter = new LoginPresenter(mLoginFragment);
    }

}
