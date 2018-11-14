package com.juggist.baseandroid.present.home;

import com.juggist.baseandroid.ui.home.SessionContract;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.base.SmartRefreshResponseCallback;
import com.juggist.jcore.bean.ProductBean;
import com.juggist.jcore.bean.UserInfo;
import com.juggist.jcore.service.ISessionService;
import com.juggist.jcore.service.SessionService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author juggist
 * @date 2018/11/9 5:22 PM
 */
public class SessionPresent implements SessionContract.Present {
    private SessionContract.View view;
    private ISessionService sessionService;

    private int page = 1;
    private static final int page_size = 10;
    private String group_id;
    private ArrayList<ProductBean.DataBean.GoodsListBean> totalProducts = new ArrayList<>();


    public SessionPresent(SessionContract.View view,String group_id) {
        this.view = view;
        this.group_id = group_id;
        view.setPresent(this);
        sessionService = new SessionService();
    }

    @Override
    public void refreshOnSellProductsList() {
        page = 1;
        getProductList();
    }

    @Override
    public void loadMoreOnSellProductsList() {
        getProductList();
    }

    private void getProductList(){
        if(view != null)
            view.showLoading();
        sessionService.getProductList(UserInfo.userId(), UserInfo.token(), String.valueOf(page), String.valueOf(page_size), group_id, new SmartRefreshResponseCallback<ProductBean.DataBean.GoodsListBean>() {
            @Override
            public int getPage() {
                return page;
            }

            @Override
            public int getPageSize() {
                return page_size;
            }

            @Override
            public void setPage(int page) {
                SessionPresent.this.page = page;
            }

            @Override
            public void clearTotalList() {
                totalProducts.clear();
            }

            @Override
            public List<ProductBean.DataBean.GoodsListBean> getTotalList() {
                return totalProducts;
            }

            @Override
            public void setTotalList(List<ProductBean.DataBean.GoodsListBean> t) {
                totalProducts.addAll(t);
            }

            @Override
            public BaseView getView() {
                return view;
            }

            @Override
            public void onSucceed(List<ProductBean.DataBean.GoodsListBean> t) {
                super.onSucceed(t);
                dismissLoading();
            }

            @Override
            public void onError(String message) {
                super.onError(message);
                dismissLoading();
            }

            @Override
            public void onApiError(String state, String message) {
                super.onApiError(state, message);
                dismissLoading();
            }
        });
    }
    @Override
    public void start() {
        getProductList();
    }

    @Override
    public void detach() {
        view = null;
    }

    private void dismissLoading(){
        if(view != null)
            view.dismissLoading();
    }
}
