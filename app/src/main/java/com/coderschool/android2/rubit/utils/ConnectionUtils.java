/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.coderschool.android2.rubit.R;
import com.coderschool.android2.rubit.connectionDialog.ConnectionDialogFragment;
import com.coderschool.android2.rubit.constants.IntentConstants;

/**
 * {@link ConnectionUtils} holds the Configuration functions
 *
 * @author TienVNguyen
 */
public class ConnectionUtils {

    /**
     * verify connection dialog for activity
     *
     * @param activity        {@link Activity}
     * @param fragmentManager {@link FragmentManager}
     * @return {@link Boolean}
     */
    public static boolean verifyConnectionDialogForActivity(final Activity activity,
                                                            final FragmentManager fragmentManager) {
        return verifyConnectionDialogForFragment(activity, null, fragmentManager);
    }

    /**
     * verify connection dialog for fragment
     *
     * @param activity        {@link Activity}
     * @param fragment        {@link Fragment}
     * @param fragmentManager {@link FragmentManager}
     * @return {@link Boolean}
     */
    public static boolean verifyConnectionDialogForFragment(final Activity activity,
                                                            final Fragment fragment,
                                                            final FragmentManager fragmentManager) {
        if (isConnected(activity)) {
            return true;
        } else {
            final ConnectionDialogFragment connectionDialogFragment = ConnectionDialogFragment.newInstance(
                    activity, fragment, fragmentManager, activity.getString(R.string.text_connection_error_title));
            connectionDialogFragment.show(fragmentManager, IntentConstants.DIALOG_CONNECTION_TAG);
            return false;
        }
    }

    /**
     * is connected
     *
     * @param activity {@link Activity}
     * @return {@link Boolean}
     */
    private static boolean isConnected(final Activity activity) {
        final ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return null != networkInfo && networkInfo.isConnected();
    }
}
