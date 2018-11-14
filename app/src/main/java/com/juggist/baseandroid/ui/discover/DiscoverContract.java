package com.juggist.baseandroid.ui.discover;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.ArticleBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/11/9 9:58 AM
 */
public class DiscoverContract {
    public interface View extends BaseView<Present> {
        
        void getListEmpty();
        void getListSucceed(List<ArticleBean> articleBeans, boolean refresh /*true:下拉刷新，false:上拉加载*/);
        void getListSucceedEnd(List<ArticleBean> articleBeans, boolean refresh /*true:下拉刷新，false:上拉加载*/);

        void getListEmptyFail(String extMsg);
        void getListFail(String extMsg,boolean refresh /*true:下拉刷新，false:上拉加载*/);
    }

    public interface Present extends BasePresent {

        void refreshArticleList();

        void loadMoreArticleList();
    }
}
