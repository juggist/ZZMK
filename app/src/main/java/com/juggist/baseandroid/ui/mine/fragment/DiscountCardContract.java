package com.juggist.baseandroid.ui.mine.fragment;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.DiscountCardBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/11/15 4:57 PM
 */
public class DiscountCardContract {
    public interface View extends BaseView<Present> {


        void getListEmpty();
        void getListSucceed(List<DiscountCardBean> discountCardBeans, boolean refresh /*true:下拉刷新，false:上拉加载*/);
        void getListSucceedEnd(List<DiscountCardBean> discountCardBeans,boolean refresh /*true:下拉刷新，false:上拉加载*/);

        void getListEmptyFail(String extMsg);
        void getListFail(String extMsg,boolean refresh /*true:下拉刷新，false:上拉加载*/);

    }

    public interface Present extends BasePresent {

        void refreshDiscountCardList();

        void loadMoreDiscountCardList();

    }
}
