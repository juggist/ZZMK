package com.juggist.jcore.service;

import com.juggist.jcore.bean.DiscountCardBean;
import com.juggist.jcore.bean.OrderBean;
import com.juggist.jcore.bean.ProductBean;
import com.juggist.jcore.bean.ResponseBean;
import com.juggist.jcore.bean.SessionBean;
import com.juggist.jcore.bean.ShopCarBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<ProductBean>> getProductList(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<ArrayList<ShopCarBean>>> getShopCar(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<List<OrderBean>>> getOrderList(@FieldMap HashMap<String,String> params);


    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<List<DiscountCardBean>>> getDiscountCardList(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<ProductBean>> getBatchForwardList(@FieldMap HashMap<String,String> params);
}

