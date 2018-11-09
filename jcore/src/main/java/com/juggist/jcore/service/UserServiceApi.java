package com.juggist.jcore.service;

import com.juggist.jcore.bean.ResponseBean;
import com.juggist.jcore.bean.UserBean;

import java.util.HashMap;

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
}
