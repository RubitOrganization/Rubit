/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.coderschool.android2.rubit.constants.IntentConstants;

/**
 * Created by vinay on 12/11/16.
 */
public class RubitChoooseTagPreference {
    public static final String RUBIT_TAG_SCREEN_PREFERENCE = "rubit_tag_screen_preference";

    public static boolean isFirst(Context context) {
        final SharedPreferences reader = context.getSharedPreferences(RUBIT_TAG_SCREEN_PREFERENCE, Context.MODE_PRIVATE);
        final boolean first = reader.getBoolean(IntentConstants.IS_FIRST, true);
        if (first) {
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean(IntentConstants.IS_FIRST, false);
            editor.commit();
        }
        return first;
    }
}
