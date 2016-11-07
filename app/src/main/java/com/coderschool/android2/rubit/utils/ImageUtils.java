/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.coderschool.android2.rubit.R;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * {@link ImageUtils}
 *
 * @author TienVNguyen
 */
public class ImageUtils {

    /**
     * Using Glide to load image
     *
     * @param context   {@link Context}
     * @param imageView {@link ImageView}
     * @param imagePath {@link String}
     */
    public static void loadingImage(final Context context,
                                    final ImageView imageView,
                                    final String imagePath) {
        loadingImageWithRoundTransform(context, imageView, imagePath, false);
    }


    /**
     * Using Glide to load image with round transform
     *
     * @param context          {@link Context}
     * @param imageView        {@link ImageView}
     * @param imagePath        {@link String}
     * @param isRoundTransform {@link Boolean}
     */
    public static void loadingImageWithRoundTransform(final Context context,
                                                      final ImageView imageView,
                                                      final String imagePath,
                                                      final boolean isRoundTransform) {

        DrawableRequestBuilder<String> request = Glide.with(context)
                .load(imagePath)
                .placeholder(R.drawable.image_placeholder);

        if (isRoundTransform) {
            RoundedCornersTransformation transformation = new RoundedCornersTransformation(context,
                    35, 0, RoundedCornersTransformation.CornerType.ALL);

            request.bitmapTransform(transformation);
        }

        request.into(imageView);
    }
}
