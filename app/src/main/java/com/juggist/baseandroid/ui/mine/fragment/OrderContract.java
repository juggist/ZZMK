package com.juggist.baseandroid.ui.mine.fragment;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.OrderBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/11/13 4:33 PM
 */
public class OrderContract {
    public interface View extends BaseView<Present> {


        void getOrderListEmpty();
        void getOrderListSucceed(List<OrderBean> orderBeans, boolean refresh /*true:下拉刷新，false:上拉加载*/);
        void getOrderListSucceedEnd(List<OrderBean> orderBeans,boolean refresh /*true:下拉刷新，false:上拉加载*/);

        void getOrderListEmptyFail(String extMsg);
        void getOrderListFail(String extMsg,boolean refresh /*true:下拉刷新，false:上拉加载*/);

    }

    public interface Present extends BasePresent {

        void refreshOrderList();

        void loadMoreOrderList();

    }
}
