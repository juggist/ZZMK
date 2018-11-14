package com.juggist.baseandroid.ui.home;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.SessionBean;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/8 3:14 PM
 */
public class HomeContract {
    public interface View extends BaseView<Present> {
        void getBannerSucceed(ArrayList<String> banners);

        void getBannerFail(String extMsg);


        void getSessionListEmpty();
        void getSessionListSucceed(ArrayList<SessionBean.DataBean> dataBeans,boolean refresh /*true:下拉刷新，false:上拉加载*/);
        void getSessionListSucceedEnd(ArrayList<SessionBean.DataBean> dataBeans,boolean refresh /*true:下拉刷新，false:上拉加载*/);

        void getSessionListEmptyFail(String extMsg);
        void getSessionListFail(String extMsg,boolean refresh /*true:下拉刷新，false:上拉加载*/);

        void toSession(String group_name,String group_id);
    }

    public interface Present extends BasePresent {
        void getBanner();

        void refreshSessionList();

        void loadMoreSessionList();

        void toSession(int position);
    }
}
