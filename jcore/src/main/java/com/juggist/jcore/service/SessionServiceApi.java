package com.juggist.jcore.service;

import com.juggist.jcore.bean.ResponseBean;
import com.juggist.jcore.bean.SessionBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author juggist
 * @date 2018/11/8 4:11 PM
 */
public interface SessionServiceApi {
    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<SessionBean>> getSessionList(@FieldMap HashMap<String,String> params);
}
