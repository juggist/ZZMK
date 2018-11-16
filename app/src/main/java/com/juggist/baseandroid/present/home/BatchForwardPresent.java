package com.juggist.baseandroid.present.home;

import com.juggist.baseandroid.ui.home.BatchForwardContract;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.ProductBean;
import com.juggist.jcore.bean.UserInfo;
import com.juggist.jcore.service.ISessionService;
import com.juggist.jcore.service.SessionService;

import java.util.List;

/**
 * @author juggist
 * @date 2018/11/16 3:00 PM
 */
public class BatchForwardPresent implements BatchForwardContract.Present {
    private BatchForwardContract.View view;
    private ISessionService sessionService;

    private String group_id;

    public BatchForwardPresent(BatchForwardContract.View view, String group_id) {
        this.view = view;
        this.group_id = group_id;
        sessionService = new SessionService();
        view.setPresent(this);
    }

    @Override
    public void getBatchForwardList() {
        if (view != null)
            view.showLoading();
        sessionService.getBatchForwardList(UserInfo.userId(), UserInfo.token(), "1", "-1", group_id, new ResponseCallback<List<ProductBean.DataBean.GoodsListBean>>() {
            @Override
            public void onSucceed(List<ProductBean.DataBean.GoodsListBean> goodsListBeans) {
                if (view == null)
                    return;
                view.dismissLoading();
                if (goodsListBeans.size() == 0)
                    view.getListEmpty();
                else
                    view.getListSucceed(goodsListBeans);

            }

            @Override
            public void onError(String message) {
                if (view != null) {
                    view.dismissLoading();
                    view.getListFail(message);
                }
            }

            @Override
            public void onApiError(String state, String message) {
                if (view != null) {
                    view.dismissLoading();
                    view.getListFail(message + " : " + state);
                }
            }
        });
    }

    @Override
    public void start() {
        getBatchForwardList();
    }

    @Override
    public void detach() {
        view = null;
    }
}
