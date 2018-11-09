package com.juggist.jcore.service;

import com.juggist.jcore.bean.BannerBean;
import com.juggist.jcore.bean.ResponseBean;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author juggist
 * @date 2018/11/7 4:10 PM
 */
public interface SystemServiceApi {

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<ArrayList<BannerBean>>> getBanner(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<Object>> sendAuthCode(@FieldMap HashMap<String,String> params);
}
