package com.juggist.jcore.service;

import com.juggist.jcore.Constants;
import com.juggist.jcore.base.BaseService;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.DiscountCardBean;
import com.juggist.jcore.bean.OrderBean;
import com.juggist.jcore.bean.ProductBean;
import com.juggist.jcore.bean.SessionBean;
import com.juggist.jcore.bean.ShopCarBean;
import com.juggist.jcore.bean.UserInfo;
import com.juggist.jcore.http.ApiServiceGenerator;
import com.juggist.jcore.http.ErrorCode;
import com.juggist.jcore.http.exception.ApiCodeErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author juggist
 * @date 2018/11/8 4:10 PM
 */
public class SessionService extends BaseService implements ISessionService {
    SessionServiceApi sessionServiceApi;

    public SessionService() {
        sessionServiceApi = ApiServiceGenerator.createService(SessionServiceApi.class);
    }

    @Override
    public void getSessionList(String user_id, String token, String page, String page_size, final ResponseCallback<List<SessionBean.DataBean>> callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("controller", "GoodsPublic");
        params.put("action", "indexPage");
        params.put("user_id", user_id);
        params.put("token", token);
        params.put("page", page);
        params.put("page_size", page_size);
        this.getFilterResponse(sessionServiceApi.getSessionList(params), AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SessionBean>() {
                    @Override
                    public void accept(SessionBean sessionBean) throws Exception {
                        if (sessionBean != null && sessionBean.getData() != null) {
                            callback.onSucceed((ArrayList<SessionBean.DataBean>) sessionBean.getData());
                        } else {
                            callback.onError(Constants.ERROR.DATA_IS_NULL);
                        }
                    }
                }, new ConsumerThrowable<>(callback));
    }

    @Override
    public void getProductList(String user_id, String token, String page, String page_size, String group_id, final ResponseCallback<List<ProductBean.DataBean.GoodsListBean>> callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("controller", "GoodsPublic");
        params.put("action", "getOnSellGoodsListByGroupId");
        params.put("user_id", user_id);
        params.put("token", token);
        params.put("page", page);
        params.put("page_size", page_size);
        params.put("group_id", group_id);
        this.getFilterResponse(sessionServiceApi.getProductList(params), Schedulers.io())
                .map(new Function<ProductBean, ArrayList<ProductBean.DataBean.GoodsListBean>>() {
                    @Override
                    public ArrayList<ProductBean.DataBean.GoodsListBean> apply(ProductBean productBean) throws Exception {
                        if (productBean != null && productBean.getData() != null && productBean.getData().getGoods_list() != null) {
                            return (ArrayList<ProductBean.DataBean.GoodsListBean>) productBean.getData().getGoods_list();
                        }
                        throw new ApiCodeErrorException(ErrorCode.DATA_NULL);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<ProductBean.DataBean.GoodsListBean>>() {
                    @Override
                    public void accept(ArrayList<ProductBean.DataBean.GoodsListBean> dataBeans) throws Exception {
                        callback.onSucceed(dataBeans);
                    }
                }, new ConsumerThrowable<>(callback));
    }

    /**
     * 购物车 增删改查
     *
     * @param callback
     */
    @Override
    public void queryShopCar(final ResponseCallback<ArrayList<ShopCarBean>> callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("controller", "Member");
        params.put("action", "getShoppingCardGoodsList");
        params.put("user_id", UserInfo.userId());
        params.put("token", UserInfo.token());
        this.getFilterResponse(sessionServiceApi.getShopCar(params), AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<ShopCarBean>>() {
                    @Override
                    public void accept(ArrayList<ShopCarBean> shopCarBeans) throws Exception {
                        callback.onSucceed(shopCarBeans);
                    }
                }, new ConsumerThrowable<>(callback));
    }

    @Override
    public void addShopCar(String coupon_id, ResponseCallback<String> callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("controller", "Member");
        params.put("action", "getShoppingCardGoodsList");
        params.put("user_id", UserInfo.userId());
        params.put("token", UserInfo.token());
    }

    @Override
    public void deleteShopCar(String goods_id, ResponseCallback<String> callback) {

    }

    @Override
    public void updateShopCar(String goods_id, String goods_num, ResponseCallback<String> callback) {

    }

    @Override
    public void getOrderList(String page, String page_size, String condition, final ResponseCallback<List<OrderBean>> callback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("controller", "Member");
        params.put("action", "getOrdersList");
        params.put("user_id", UserInfo.userId());
        params.put("token", UserInfo.token());
        params.put("page", page);
        params.put("page_size", page_size);
        params.put("condition", condition);
        this.getFilterResponse(sessionServiceApi.getOrderList(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<OrderBean>>() {
                    @Override
                    public void accept(List<OrderBean> orderBeans) throws Exception {
                        callback.onSucceed(orderBeans);
                    }
                },new ConsumerThrowable<>(callback));
    }

    @Override
    public void getDiscountCardList(int tag, String page, String page_size, final ResponseCallback<List<DiscountCardBean>> callback) {
        String action = "";
        switch (tag){
            case 0:
                action = "getCouponListByUserId";
                break;
            case 1:
                action = "getCouponListByUserIdUsed";
                break;
            case 2:
                action = "getCouponListByUserIdExpire";
                break;
        }
        HashMap<String, String> params = new HashMap<>();
        params.put("controller", "Member");
        params.put("action", action);
        params.put("user_id", UserInfo.userId());
        params.put("token", UserInfo.token());
        params.put("page", page);
        params.put("page_size", page_size);
        this.getFilterResponse(sessionServiceApi.getDiscountCardList(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<DiscountCardBean>>() {
                    @Override
                    public void accept(List<DiscountCardBean> discountCardBeans) throws Exception {
                        callback.onSucceed(discountCardBeans);
                    }
                },new ConsumerThrowable<>(callback));
    }

//    @Override
//    public void updateShopCar(String goods_id, String goods_num) {
//        HashMap<String, String> params = new HashMap<>();
//        params.put("controller", "Member");
//        params.put("action", "getShoppingCardGoodsList");
//        params.put("user_id", UserInfo.userId());
//        params.put("token", UserInfo.token());
//        params.put("goods_id", goods_id);
//        params.put("goods_num", goods_num);
//    }
}
