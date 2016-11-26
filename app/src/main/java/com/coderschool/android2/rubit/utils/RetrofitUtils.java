package com.coderschool.android2.rubit.utils;

import com.coderschool.android2.rubit.constants.DatabaseConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                request = request.newBuilder()
                        .addHeader("Authorization", "Client-ID " + clientId)
                        .build();
                return chain.proceed(request);
            }
        };
    }
}
