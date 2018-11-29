package com.juggist.jcore.http;

import com.juggist.jcore.http.interceptor.EncryptionInterceptor;
import com.juggist.jcore.http.interceptor.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


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
    public static  OkHttpClient.Builder okHttpClientWithOutInterceptor(){
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false);

        return okHttpClientBuilder;
    }
    /**
     * 包装OkHttpClient，用于下载文件的回调
     *
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient
     */
    public static OkHttpClient addProgressResponseListener(final ProgressResponseListener progressListener) {

        return okHttpClient()
                //增加拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //拦截
                        Response originalResponse = chain.proceed(chain.request());

                        //包装响应体并返回
                        return originalResponse.newBuilder()
                                .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                                .build();
                    }
                })
                .build();
    }


    /**
     * 包装OkHttpClient，用于上传文件的回调
     *
     * @param progressListener 进度回调接口
     * @return 包装后的OkHttpClient
     */
    public static OkHttpClient addProgressRequestListener(final ProgressRequestListener progressListener) {
        return okHttpClientWithOutInterceptor()
                //增加拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .method(original.method(), new ProgressRequestBody(original.body(), progressListener))
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();


    }
}
