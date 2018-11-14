package com.juggist.jcore.service;

import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.OrderBean;
import com.juggist.jcore.bean.ProductBean;
import com.juggist.jcore.bean.SessionBean;
import com.juggist.jcore.bean.ShopCarBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author juggist
 * @date 2018/11/8 4:08 PM
 */
public interface ISessionService {
    void getSessionList(String user_id, String token, String page, String page_size, ResponseCallback<List<SessionBean.DataBean>> callback);

    void getProductList(String user_id, String token, String page, String page_size, String group_id, ResponseCallback<List<ProductBean.DataBean.GoodsListBean>> callback);

    void queryShopCar(ResponseCallback<ArrayList<ShopCarBean>> callback);

    void addShopCar(String coupon_id,ResponseCallback<String> callback);

    void deleteShopCar(String goods_id,ResponseCallback<String> callback);

    void updateShopCar(String goods_id,String goods_num,ResponseCallback<String> callback);

    void getOrderList(String page, String page_size, String condition, ResponseCallback<List<OrderBean>> callback);

}
