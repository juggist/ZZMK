package com.juggist.baseandroid.ui.home;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.ProductBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/11/16 3:00 PM
 */
public class BatchForwardContract {

    public interface View extends BaseView<Present> {

        void getListEmpty();

        void getListSucceed(List<ProductBean.DataBean.GoodsListBean> dataBeans);

        void getListFail(String extMsg);
    }

    public interface Present extends BasePresent {

        void getBatchForwardList();
    }
}
