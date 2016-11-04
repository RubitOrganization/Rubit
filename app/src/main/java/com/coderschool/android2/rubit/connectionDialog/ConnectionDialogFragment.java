/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.connectionDialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.constants.IntentConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link ConnectionDialogFragment}
 *
 * @author TienVNguyen
 */
public class ConnectionDialogFragment extends DialogFragment {

    @BindView(R.id.btnRetry)
    protected Button mBtnRetry;

    /**
     * Empty constructor is required for {@link DialogFragment}
     */
    public ConnectionDialogFragment() {
    }

    /**
     * newInstance
     *
     * @param title {@link String}
     * @return {@link ConnectionDialogFragment}
     */
    public static ConnectionDialogFragment newInstance(final String title) {
        final Bundle args = new Bundle();
        args.putString(IntentConstants.DIALOG_CONNECTION_TITLE, title);

        final ConnectionDialogFragment frag = new ConnectionDialogFragment();
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_connection, container);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpDialogConfig();
        setUpRetryConnection();
    }

    /**
     * setUpDialogConfig
     */
    private void setUpDialogConfig() {
        getDialog().setTitle(
                getArguments().getString(
                        IntentConstants.DIALOG_CONNECTION_TITLE,
                        getString(R.string.text_connection_error_title)));
        getDialog().setCancelable(false);

        final Window window = getDialog().getWindow();
        if (null != window)
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    /**
     * set up re-try connection
     */
    private void setUpRetryConnection() {
        mBtnRetry.setOnClickListener(view1 -> onConnectionDialogDismiss());
    }

    /**
     * on connection dialog dismiss
     */
    private void onConnectionDialogDismiss() {
        final ConnectionDialogListener listener = (ConnectionDialogListener) getActivity();
        listener.onFinishConnectionDialog();
        this.dismiss();
    }
}
