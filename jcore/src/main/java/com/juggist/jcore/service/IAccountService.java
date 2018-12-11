package com.juggist.jcore.service;

import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.OrderPreBean;
import com.juggist.jcore.bean.OrderRefundBean;
import com.juggist.jcore.bean.OrderTransportBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/12/3 2:23 PM
 */
public interface IAccountService {
    void getOrderRefundDetail(String refundId, ResponseCallback<OrderRefundBean> callback);

    void getOrderTransport(String orders_id, ResponseCallback<OrderTransportBean> callback);

    void refundNoDispatch(String orders_id, String describes, ResponseCallback<String> callback);

    void refundDispatched(String orders_id, String describes, String reason, List<String> images, ResponseCallback<String> callback);

    void updateShopNum(String goods_id, String number, ResponseCallback<String> callback);

    void deleteShop(String goods_id, ResponseCallback<String> callback);

    void addShop(String goods_id, String number, ResponseCallback<String> callback);

    void createTmpOrder(String goods_json, ResponseCallback<OrderPreBean> callback);
}
