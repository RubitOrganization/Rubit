package com.coderschool.android2.rubit.utils;


import com.coderschool.android2.rubit.models.ImageResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by vinay on 26/11/16.
 */

public interface ImgurApi {
    @Multipart
    @POST("image")
    Call<ImageResponse> create(
            @Part MultipartBody.Part image,
            @Part("name") RequestBody name
    );
}
