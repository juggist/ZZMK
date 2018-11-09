package com.juggist.baseandroid.present.discover;

import com.juggist.baseandroid.ui.discover.DiscoverContract;
import com.juggist.jcore.Constants;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.ArticleBean;
import com.juggist.jcore.service.ArticleService;
import com.juggist.jcore.service.IArticleService;

import java.util.ArrayList;

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
        articleService.getArticleList(String.valueOf(page), String.valueOf(page_size), new ResponseCallback<ArrayList<ArticleBean>>() {
            @Override
            public void onSucceed(ArrayList<ArticleBean> articleBeans) {
                if(articleBeans != null){
                    /**
                     * 数据
                     */
                    //添加数据
                    if(page == 0){//刷新
                        totalArticleBeans.clear();
                    }
                    totalArticleBeans.addAll(articleBeans);
                    
                    
                    /**
                     * 视图
                     */
                    if(view == null)
                        return;
                    view.dismissLoading();
                    if(page == 0){//刷新
                        if(articleBeans.size() == 0){ //页面数据为空
                            view.getArticleListEmpty();
                        }else if(articleBeans.size() < page_size){
                            view.getArticleListSucceedEnd(totalArticleBeans,true);
                        }else{
                            view.getArticleListSucceed(totalArticleBeans,true);
                        }
                    }else{//加载更多
                        if(articleBeans.size() >= 0 && articleBeans.size() < page_size){
                            view.getArticleListSucceedEnd(totalArticleBeans,false);
                        }else{
                            view.getArticleListSucceed(totalArticleBeans,false);
                        }
                    }
                    
                    /**
                     * 设置page 
                     */
                    if(articleBeans.size() == page_size){
                        page++;
                    }
                }else{
                    getArticleListError(Constants.ERROR.DATA_IS_NULL);
                }
            }

            @Override
            public void onError(String message) {
                getArticleListError(message);
            }

            @Override
            public void onApiError(String state, String message) {
                getArticleListError(message + " : " + state);
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
    private void getArticleListError(String extMsg){
        if(view == null)
            return; 
        view.dismissLoading();
        if(page == 0){//刷新
            if(totalArticleBeans.size() == 0){//无数据
                  view.getArticleListEmptyFail(extMsg);
            }else{//有数据
                  view.getArticleListFail(extMsg,true);
            }
        }else{//加载更多
            view.getArticleListFail(extMsg,false);
        }        
        
    }
}
