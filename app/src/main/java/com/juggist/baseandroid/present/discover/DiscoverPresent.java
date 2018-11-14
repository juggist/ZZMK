package com.juggist.baseandroid.present.discover;

import com.juggist.baseandroid.ui.discover.DiscoverContract;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.base.SmartRefreshResponseCallback;
import com.juggist.jcore.bean.ArticleBean;
import com.juggist.jcore.service.ArticleService;
import com.juggist.jcore.service.IArticleService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author juggist
 * @date 2018/11/9 10:00 AM
 */
public class DiscoverPresent implements DiscoverContract.Present {
    private DiscoverContract.View view;
    private IArticleService articleService;

    private int page = 0;
    private static final int page_size = 10;
    
    private ArrayList<ArticleBean> totalArticleBeans = new ArrayList<>();


    public DiscoverPresent(DiscoverContract.View view) {
        this.view = view;
        view.setPresent(this);
        articleService = new ArticleService();
    }

    @Override
    public void refreshArticleList() {
        page = 0;
        getArticleList();
    }

    @Override
    public void loadMoreArticleList() {
        getArticleList();
    }
    
    private void getArticleList(){
        showLoading();
        articleService.getArticleList(String.valueOf(page), String.valueOf(page_size), new SmartRefreshResponseCallback<ArticleBean>() {
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
                DiscoverPresent.this.page = page;
            }

            @Override
            public void clearTotalList() {
                totalArticleBeans.clear();
            }

            @Override
            public List<ArticleBean> getTotalList() {
                return totalArticleBeans;
            }

            @Override
            public void setTotalList(List<ArticleBean> t) {
                totalArticleBeans.addAll(t);
            }

            @Override
            public BaseView getView() {
                return view;
            }

            @Override
            public void onSucceed(List<ArticleBean> t) {
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
        getArticleList();
    }

    @Override
    public void detach() {
        view = null;
    }
    private void showLoading(){
        if(view != null)
            view.showLoading();
    }
    private void dismissLoading(){
        if(view != null)
            view.dismissLoading();
    }
}
