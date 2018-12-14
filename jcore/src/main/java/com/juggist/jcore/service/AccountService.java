package com.juggist.jcore.service;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.juggist.jcore.base.BaseService;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.OrderCreateBean;
import com.juggist.jcore.bean.OrderCreateTmpBean;
import com.juggist.jcore.bean.OrderRefundBean;
import com.juggist.jcore.bean.OrderTransportBean;
import com.juggist.jcore.bean.UserInfo;
import com.juggist.jcore.http.ApiServiceGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * @author juggist
 * @date 2018/12/3 2:24 PM
 */
public class AccountService extends BaseService implements IAccountService {
    AccountServiceApi accountServiceApi;
    public AccountService() {
        accountServiceApi = ApiServiceGenerator.createService(AccountServiceApi.class);
    }

    /**
     * 获取退单详情
     * @param refund_id
     * @param callback
     */
    @Override
    public void getOrderRefundDetail(String refund_id, final ResponseCallback<OrderRefundBean> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","getRefundDetail");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("refund_id",refund_id);
        this.getFilterResponse(accountServiceApi.getOrderRefundDetail(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderRefundBean>() {
                    @Override
                    public void accept(OrderRefundBean orderRefundBean) throws Exception {
                        callback.onSucceed(orderRefundBean);
                    }
                },new ConsumerThrowable<>(callback));
    }

    @Override
    public void getOrderTransport(String orders_id, final ResponseCallback<OrderTransportBean> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","queryLogistics");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("orders_id",orders_id);
        this.getFilterResponse(accountServiceApi.getOrderTransport(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderTransportBean>() {
                    @Override
                    public void accept(OrderTransportBean orderRefundBean) throws Exception {
                        callback.onSucceed(orderRefundBean);
                    }
                },new ConsumerThrowable<>(callback));
    }

    @Override
    public void refundNoDispatch(String orders_id, String describes, ResponseCallback<String> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","addReFundRecordForOrders");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("orders_id",orders_id);
        params.put("describes",describes);
        this.getFilterResponse(accountServiceApi.refundDispatched(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                },new ConsumerThrowable<>(callback));
    }

    @Override
    public void refundDispatched(String orders_id, String describes, String reason, List<String> images, final ResponseCallback<String> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","addReFundRecordForOrders");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("orders_id",orders_id);
        params.put("describes",describes);
        params.put("reason",reason);
        JSONArray jsonArray = new JSONArray();
        for (String img:images) {
            jsonArray.put(img);
        }
        params.put("image",jsonArray.toString());
        this.getFilterResponse(accountServiceApi.refundNoDispatch(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                            callback.onSucceed(s);
                    }
                },new ConsumerThrowable<>(callback));
    }

    /**
     * 更新订单商品数量
     * @param goods_id
     * @param number
     * @param callback
     */
    @Override
    public void updateShopNum(String goods_id, String number, final ResponseCallback<String> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","updateShoppingCardGoodsNumber");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("goods_id",goods_id);
        params.put("number",number);
        this.getFilterResponse(accountServiceApi.updateShopNum(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        callback.onSucceed(s);
                    }
                },new ConsumerThrowable<>(callback));

    }

    @Override
    public void deleteShop(String goods_id, final ResponseCallback<String> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","deleteGoodsFromShoppingCard");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("goods_id",goods_id);
        this.getFilterResponse(accountServiceApi.deleteShop(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        callback.onSucceed(s);
                    }
                },new ConsumerThrowable<>(callback));

    }

    @Override
    public void addShop(String goods_id, String number, final ResponseCallback<String> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","addGoodsToShoppingCard");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("goods_id",goods_id);
        params.put("goods_number",number);
        this.getFilterResponse(accountServiceApi.addShop(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        callback.onSucceed(s);
                    }
                },new ConsumerThrowable<>(callback));

    }

    /**
     * 创建零时订单
     * @param goods_json
     * @param callback
     */
    @Override
    public void createTmpOrder(String goods_json, final ResponseCallback<OrderCreateTmpBean> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","getPreOrderGoodsListFromDetail");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("goods_json",goods_json);
        params.put("coupon_id","-1");
        this.getFilterResponse(accountServiceApi.createTmpOrder(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderCreateTmpBean>() {
                    @Override
                    public void accept(OrderCreateTmpBean orderPreBean) throws Exception {
                        callback.onSucceed(orderPreBean);
                    }
                },new ConsumerThrowable<>(callback));

    }

    @Override
    public void createOrder(List<OrderCreateTmpBean.GoodsListBean> goodsListBeans, String address_id, String coupon_id, String cn_id, String cn_id_bg, String cd_id_front, final ResponseCallback<OrderCreateBean> callback) {
        HashMap<String,String> params = new HashMap<>();
        if(!TextUtils.isEmpty(cn_id)){
            params.put("cn_id",cn_id);
        }
        if(!TextUtils.isEmpty(cn_id)){
            params.put("cn_id_bg",cn_id_bg);
        }
        if(!TextUtils.isEmpty(cn_id)){
            params.put("cd_id_front",cd_id_front);
        }
        params.put("controller","Member");
        params.put("action","createOrder");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("address_id",address_id);
        params.put("coupon_id",coupon_id);
        params.put("note","来自Android下单");
        JSONArray array = new JSONArray();
        for (OrderCreateTmpBean.GoodsListBean item:goodsListBeans) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("goods_id",item.getAuto_id());
                obj.put("goods_number",item.getGoods_number());
                obj.put("attr",new Gson().toJson(item.getAttr()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
        }
        params.put("goods_json",array.toString());
        this.getFilterResponse(accountServiceApi.createOrder(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderCreateBean>() {
                    @Override
                    public void accept(OrderCreateBean orderCreateBean) throws Exception {
                        callback.onSucceed(orderCreateBean);
                    }
                },new ConsumerThrowable<>(callback));
    }
}
