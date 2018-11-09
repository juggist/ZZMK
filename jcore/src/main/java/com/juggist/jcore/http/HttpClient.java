package com.juggist.jcore.http;

import com.juggist.jcore.http.interceptor.EncryptionInterceptor;
import com.juggist.jcore.http.interceptor.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


/**
 * @author juggist
 * @date 2018/10/30 3:22 PM
 */
public class HttpClient {
    /**
     * ok_http set
     *
     * @return OkHttpClient
     */
    public static OkHttpClient.Builder okHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new EncryptionInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .retryOnConnectionFailure(false);
        return okHttpClientBuilder;
    }
}
