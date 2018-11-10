package com.juggist.baseandroid.present.home;

import com.juggist.baseandroid.ui.home.SessionContract;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.ProductBean;
import com.juggist.jcore.bean.UserInfo;
import com.juggist.jcore.service.ISessionService;
import com.juggist.jcore.service.SessionService;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/9 5:22 PM
 */
public class SessionPresent implements SessionContract.Present {
    private SessionContract.View view;
    private ISessionService sessionService;

    private int page = 0;
    private static final int page_size = 10;
    private String group_id;
    private ArrayList<ProductBean.DataBean.GoodsListBean> totalProducts = new ArrayList<>();


    public SessionPresent(SessionContract.View view,String group_id) {
        this.view = view;
        this.group_id = group_id;
        view.setPresent(this);
        sessionService = new SessionService();
    }

    @Override
    public void refreshOnSellProductsList() {
        page = 0;
        getProductList();
    }

    @Override
    public void loadMoreOnSellProductsList() {
        getProductList();
    }

    private void getProductList(){
        if(view != null)
            view.showLoading();
        sessionService.getProductList(UserInfo.userId(), UserInfo.token(), String.valueOf(page), String.valueOf(page_size), group_id, new ResponseCallback<ArrayList<ProductBean.DataBean.GoodsListBean>>() {
            @Override
            public void onSucceed(ArrayList<ProductBean.DataBean.GoodsListBean> goodsListBeans) {
                /**
                 * 数据
                 */
                //添加数据
                if(page == 0){//刷新
                    totalProducts.clear();
                }
                totalProducts.addAll(goodsListBeans);


                /**
                 * 视图
                 */
                if(view == null)
                    return;
                view.dismissLoading();
                if(page == 0){//刷新
                    if(goodsListBeans.size() == 0){ //页面数据为空
                        view.getOnSellProductsListEmpty();
                    }else if(goodsListBeans.size() < page_size){
                        view.getOnSellProductsListSucceedEnd(totalProducts,true);
                    }else{
                        view.getOnSellProductsListSucceed(totalProducts,true);
                    }
                }else{//加载更多
                    if(goodsListBeans.size() >= 0 && goodsListBeans.size() < page_size){
                        view.getOnSellProductsListSucceedEnd(totalProducts,false);
                    }else{
                        view.getOnSellProductsListSucceed(totalProducts,false);
                    }
                }
                
                /**
                 * 设置page 
                 */
                if(goodsListBeans.size() == page_size){
                    page++;
                }
            }

            @Override
            public void onError(String message) {
                getProductListError(message);
            }

            @Override
            public void onApiError(String state, String message) {
                getProductListError(message + " : " + state);
            }
        });
    }
    @Override
    public void start() {
        getProductList();
    }

    @Override
    public void detach() {
        view = null;
    }
    private void getProductListError(String extMsg){
        if(view == null)
            return;
        view.dismissLoading();
        if(page == 0){//刷新
            if(totalProducts.size() == 0){//无数据
                  view.getOnSellProductsListEmptyFail(extMsg);
            }else{//有数据
                  view.getOnSellProductsListFail(extMsg,true);
            }
        }else{//加载更多
            view.getOnSellProductsListFail(extMsg,false);
        }
    }
}
