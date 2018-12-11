package com.juggist.jcore.service;

import com.juggist.jcore.bean.OrderPreBean;
import com.juggist.jcore.bean.OrderRefundBean;
import com.juggist.jcore.bean.OrderTransportBean;
import com.juggist.jcore.bean.ResponseBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author juggist
 * @date 2018/12/3 2:22 PM
 */
public interface AccountServiceApi {
    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<OrderRefundBean>> getOrderRefundDetail(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<OrderTransportBean>> getOrderTransport(@FieldMap HashMap<String,String> params);
    /**
     * 退款(未发货)
     */
    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<String>> refundNoDispatch(@FieldMap HashMap<String,String> params);
    /**
     * 退款(已发货)
     */
    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<String>> refundDispatched(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<String>> updateShopNum(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<String>> deleteShop(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<String>> addShop(@FieldMap HashMap<String,String> params);

    @FormUrlEncoded
    @POST("api.php")
    Observable<ResponseBean<OrderPreBean>> createTmpOrder(@FieldMap HashMap<String,String> params);
}
