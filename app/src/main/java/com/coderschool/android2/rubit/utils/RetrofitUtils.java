/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by <tien.workinfo@gmail.com - rubit1359@gmail.com - manetivinay@gmail.com>, October 2016
 */

package com.coderschool.android2.rubit.utils;

import com.coderschool.android2.rubit.constants.DatabaseConstants;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vinay on 26/11/16.
 */
public class RetrofitUtils {
    public static Retrofit get(String apiKey) {
        return new Retrofit.Builder()
                .baseUrl(DatabaseConstants.IMGUR_BASE_URL)
                .client(client(apiKey))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient client(String clientId) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(clientIdInterceptor(clientId))
                .addInterceptor(interceptor)
                .build();
    }

    private static Interceptor clientIdInterceptor(final String clientId) {
        return chain -> {
            Request request = chain.request();
            request = request.newBuilder()
                    .addHeader("Authorization", "Client-ID " + clientId)
                    .build();
            return chain.proceed(request);
        };
    }
}
