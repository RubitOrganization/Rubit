/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.utils;

import android.view.View;
import android.widget.TextView;

/**
 * TextViewUtils
 *
 * @author TienVNguyen
 */
public class TextViewUtils {

    /**
     * Set text to a TextView<br>
     * - If contentString is not empty, TextView will have that<br>
     * - If contentString is empty, TextView will be gone
     *
     * @param textView      {@link TextView}
     * @param contentString {@link Object}
     */
    public static void setTextView(final TextView textView,
                                   final Object contentString) {

        final String value = String.valueOf(contentString);
        if (null != value && 0 < value.length() && !"NULL".equalsIgnoreCase(value)) {
            textView.setText(value);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }
}
