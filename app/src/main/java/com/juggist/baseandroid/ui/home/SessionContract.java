package com.juggist.baseandroid.ui.home;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.ProductBean;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/9 5:09 PM
 */
public class SessionContract {
    public interface View extends BaseView<Present> {
        
        void getOnSellProductsListEmpty();
        void getOnSellProductsListSucceed(ArrayList<ProductBean.DataBean.GoodsListBean> dataBeans, boolean refresh /*true:下拉刷新，false:上拉加载*/);
        void getOnSellProductsListSucceedEnd(ArrayList<ProductBean.DataBean.GoodsListBean> dataBeans,boolean refresh /*true:下拉刷新，false:上拉加载*/);

        void getOnSellProductsListEmptyFail(String extMsg);
        void getOnSellProductsListFail(String extMsg,boolean refresh /*true:下拉刷新，false:上拉加载*/);
    }

    public interface Present extends BasePresent {

        void refreshOnSellProductsList();

        void loadMoreOnSellProductsList();
    }
}
