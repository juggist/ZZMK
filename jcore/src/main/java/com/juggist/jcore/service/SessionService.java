package com.juggist.jcore.service;

import com.juggist.jcore.Constants;
import com.juggist.jcore.base.BaseService;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.ProductBean;
import com.juggist.jcore.bean.SessionBean;
import com.juggist.jcore.http.ApiServiceGenerator;
import com.juggist.jcore.http.ErrorCode;
import com.juggist.jcore.http.exception.ApiCodeErrorException;

import java.util.ArrayList;
import java.util.HashMap;

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
    public void getSessionList(String user_id, String token, String page, String page_size, final ResponseCallback<ArrayList<SessionBean.DataBean>> callback) {
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
    public void getProductList(String user_id, String token, String page, String page_size, String group_id, final ResponseCallback<ArrayList<ProductBean.DataBean.GoodsListBean>> callback) {
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
}
