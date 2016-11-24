/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.utils;

import com.coderschool.android2.rubit.models.SupporterImagesModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay on 20/11/16.
 */
public class LoadFakeImages {
    public static List<SupporterImagesModel> getImages() {
        List<SupporterImagesModel> supporterImagesModels = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            supporterImagesModels.add(new SupporterImagesModel("https://unsplash.it/200/150/?image=" + i));
        }
        return supporterImagesModels;
    }
}
