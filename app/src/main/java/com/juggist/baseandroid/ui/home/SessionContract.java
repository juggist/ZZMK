package com.juggist.baseandroid.ui.home;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.ProductBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/11/9 5:09 PM
 */
public class SessionContract {
    public interface View extends BaseView<Present> {
        
        void getListEmpty();
        void getListSucceed(List<ProductBean.DataBean.GoodsListBean> dataBeans, boolean refresh /*true:下拉刷新，false:上拉加载*/);
        void getListSucceedEnd(List<ProductBean.DataBean.GoodsListBean> dataBeans, boolean refresh /*true:下拉刷新，false:上拉加载*/);

        void getListEmptyFail(String extMsg);
        void getListFail(String extMsg,boolean refresh /*true:下拉刷新，false:上拉加载*/);

        void downloadShareSucceed();
        void downloadShareFail(String msg);
    }

    public interface Present extends BasePresent {

        void refreshOnSellProductsList();

        void loadMoreOnSellProductsList();

        void preparDownload(int position);
        void startDownload();
    }
}
