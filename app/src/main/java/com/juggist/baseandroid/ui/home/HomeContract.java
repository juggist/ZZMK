package com.juggist.baseandroid.ui.home;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author juggist
 * @date 2018/11/8 3:14 PM
 */
public class HomeContract {
    public interface View<T> extends BaseView<Present> {
        void getBannerSucceed(ArrayList<String> banners);

        void getBannerFail(String extMsg);


        void getListEmpty();
        void getListSucceed(List<T> dataBeans, boolean refresh /*true:下拉刷新，false:上拉加载*/);
        void getListSucceedEnd(List<T> dataBeans,boolean refresh /*true:下拉刷新，false:上拉加载*/);

        void getListEmptyFail(String extMsg);
        void getListFail(String extMsg,boolean refresh /*true:下拉刷新，false:上拉加载*/);

        void toSession(String group_name,String group_id);
        void toShare(String group_id);
    }

    public interface Present extends BasePresent {
        void getBanner();

        void refreshSessionList();

        void loadMoreSessionList();

        void toShare(int position);

        void toSession(int position);
    }
}
