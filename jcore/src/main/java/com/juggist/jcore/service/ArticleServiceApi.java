package com.juggist.jcore.service;

import com.juggist.jcore.bean.ArticleBean;
import com.juggist.jcore.bean.ResponseBean;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author juggist
 * @date 2018/11/9 9:53 AM
 */
public interface ArticleServiceApi {
    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<ArrayList<ArticleBean>>> getArticleList(@FieldMap HashMap<String,String> params);
}
