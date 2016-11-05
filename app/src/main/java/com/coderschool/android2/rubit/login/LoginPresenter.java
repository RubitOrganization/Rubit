package com.coderschool.android2.rubit.login;

import android.support.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by vinay on 03/11/16.
 */

public class LoginPresenter implements LoginContact.Presenter {

    private final LoginContact.View mView;

    public LoginPresenter(@Nullable LoginContact.View view) {
        mView = checkNotNull(view, "View cannot be null!");
        mView.setPresenter(this);
    }

    @Override
    public void addViews() {
        mView.showViews();
    }

    @Override
    public void start() {
        addViews();
    }
}
