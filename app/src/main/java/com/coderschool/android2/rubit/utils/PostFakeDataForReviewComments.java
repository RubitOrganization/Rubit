package com.coderschool.android2.rubit.utils;

import com.coderschool.android2.rubit.models.ReviewCommentsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinay on 20/11/16.
 */

public class PostFakeDataForReviewComments {
    public static List<ReviewCommentsModel> reviewComments() {
        String headingText = "Take me a photo of Qatar";
        String commentText = "\"Great landscape photographer. hope to more chance to work with Lo Nguyen\"";
        float ratingBarCount = 3.5f;

        List<ReviewCommentsModel> reviewComments = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            reviewComments.add(new ReviewCommentsModel("https://unsplash.it/128/128/?image=" + i, "User Name " + i, ratingBarCount, headingText, commentText, "https://unsplash.it/480/320/?image=" + (10 - i)));
        }
        return reviewComments;
    }
}
