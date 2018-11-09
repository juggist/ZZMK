package com.juggist.baseandroid.ui.discover;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.ArticleBean;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/9 9:58 AM
 */
public class DiscoverContract {
    public interface View extends BaseView<Present> {
        
        void getArticleListEmpty();
        void getArticleListSucceed(ArrayList<ArticleBean> articleBeans, boolean refresh /*true:下拉刷新，false:上拉加载*/);
        void getArticleListSucceedEnd(ArrayList<ArticleBean> articleBeans,boolean refresh /*true:下拉刷新，false:上拉加载*/);

        void getArticleListEmptyFail(String extMsg);
        void getArticleListFail(String extMsg,boolean refresh /*true:下拉刷新，false:上拉加载*/);
    }

    public interface Present extends BasePresent {

        void refreshArticleList();

        void loadMoreArticleList();
    }
}
