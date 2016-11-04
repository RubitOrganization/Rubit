/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * {@link ActivityUtils} provides methods to help Activities load their UI.
 *
 * @author TienVNguyen
 */
public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}.<br>
     * The operation is performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(final @NonNull FragmentManager fragmentManager,
                                             final @NonNull Fragment fragment, final int frameId) {
        // Action
        fragmentManager.beginTransaction()
                .add(frameId, fragment)
                .commit();
    }
}
