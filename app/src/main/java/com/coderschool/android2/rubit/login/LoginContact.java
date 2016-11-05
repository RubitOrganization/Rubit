package com.coderschool.android2.rubit.login;

import com.coderschool.android2.rubit.base.BasePresenter;
import com.coderschool.android2.rubit.base.BaseView;

/**
 * Created by vinay on 03/11/16.
 */

public class LoginContact {

    interface View extends BaseView<LoginContact.Presenter> {

        void showViews();

//        void startAnotherActivity();
    }

    interface Presenter extends BasePresenter {
        void addViews();
    }
}
