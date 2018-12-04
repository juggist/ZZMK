package com.juggist.jcore.service;

import com.juggist.jcore.bean.AddressBean;
import com.juggist.jcore.bean.ResponseBean;
import com.juggist.jcore.bean.UserBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author juggist
 * @date 2018/10/30 4:31 PM
 */
public interface UserServiceApi {
    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<UserBean[]>> loginAndRegister(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<List<AddressBean>>> getAddressList(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<String>> setDefaultAddress(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<String>> deleteAddress(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<String>> addAddress(@FieldMap HashMap<String,String> params);
}
