package com.juggist.jcore.http;

import com.juggist.jcore.Constants;
import com.juggist.jcore.http.convert.GsonConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * @author juggist
 * @date 2018/10/30 2:42 PM
 */
public class ApiServiceGenerator {
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .client(HttpClient.okHttpClient().build())
            .baseUrl(Constants.URL.BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    public static <T> T createService(Class<T> tClass){
        return builder.build().create(tClass);
    }

    /**
     * 创建带响应进度(下载进度)回调的service
     */
    public static <T> T createResponseService(Class<T> tClass, ProgressResponseListener listener) {
        return builder
                .client(HttpClient.addProgressResponseListener(listener))
                .build()
                .create(tClass);
    }
}
