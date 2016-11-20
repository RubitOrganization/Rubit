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
