package com.coderschool.android2.rubit.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by vinay on 04/11/16.
 */

public class ProgressDialogHelper {

    static ProgressDialog mProgressDialog;
    private static Activity mcontext;

    public ProgressDialogHelper() {

    }

    public ProgressDialogHelper(Activity context) {
        this.mcontext = context;
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle("Please wait");
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setCancelable(true);
        mProgressDialog.setIndeterminate(true);
    }

    public ProgressDialogHelper(Context context, String title, String message) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle(title);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public ProgressDialog getProgressDialog() {
        return mProgressDialog;
    }

    public void setProgressDialog(ProgressDialog mProgressDialog) {
        //make sure the previous dialog is hidden
        hide();
        this.mProgressDialog = mProgressDialog;
    }

    public static void show() {
        if (mProgressDialog != null && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void create(Context context, String title, String message) {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        mProgressDialog = ProgressDialog.show(context, title, message);
    }

    public void hide() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    public static void dismiss() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mcontext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressDialog.dismiss();
                }
            });
            mProgressDialog = null;
        }
    }

    public void onDestroy() {
        hide();
    }

}
