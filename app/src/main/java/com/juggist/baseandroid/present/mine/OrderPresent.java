package com.juggist.baseandroid.present.mine;

import com.juggist.baseandroid.ui.mine.fragment.OrderContract;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.OrderBean;
import com.juggist.jcore.service.ISessionService;
import com.juggist.jcore.service.SessionService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author juggist
 * @date 2018/11/13 4:33 PM
 */
public class OrderPresent implements OrderContract.Present {
    private OrderContract.View view;
    private String condition;
    private ISessionService sessionService;

    private int page = 0;
    private static final int page_size = 10;
    private List<OrderBean> totalOrderBeans = new ArrayList<>();

    public OrderPresent(OrderContract.View view,String condition) {
        this.view = view;
        this.condition = condition;
        sessionService = new SessionService();
        view.setPresent(this);
    }

    @Override
    public void refreshOrderList() {
        page = 0;
        getOrderList();
    }

    @Override
    public void loadMoreOrderList() {
        getOrderList();
    }

    private void getOrderList(){
        if(view != null)
            view.showLoading();
        sessionService.getOrderList(String.valueOf(page), String.valueOf(page_size), condition, new ResponseCallback<List<OrderBean>>() {
            @Override
            public void onSucceed(List<OrderBean> orderBeans) {
                /**
                 * 数据
                 */
                //添加数据
                if(page == 0){//刷新
                    totalOrderBeans.clear();
                }
                totalOrderBeans.addAll(orderBeans);
                
                
                /**
                 * 视图
                 */
                if(view == null)
                    return;
                view.dismissLoading();
                if(page == 0){//刷新
                    if(orderBeans.size() == 0){ //页面数据为空
                        view.getOrderListEmpty();
                    }else if(orderBeans.size() < page_size){
                        view.getOrderListSucceedEnd(totalOrderBeans,true);
                    }else{
                        view.getOrderListSucceed(totalOrderBeans,true);
                    }
                }else{//加载更多
                    if(orderBeans.size() >= 0 && orderBeans.size() < page_size){
                        view.getOrderListSucceedEnd(totalOrderBeans,false);
                    }else{
                        view.getOrderListSucceed(totalOrderBeans,false);
                    }
                }
                
                /**
                 * 设置page 
                 */
                if(orderBeans.size() == page_size){
                    page++;
                }
            }

            @Override
            public void onError(String message) {
                getOrderListFail(message);
            }

            @Override
            public void onApiError(String state, String message) {
                getOrderListFail(message + " : " + state);
            }
        });
    }
    private void getOrderListFail(String extMsg){
        if(view == null)
            return;
        view.dismissLoading();
        if(page == 0){//刷新
            if(totalOrderBeans.size() == 0){//无数据
                  view.getOrderListEmptyFail(extMsg);
            }else{//有数据
                  view.getOrderListFail(extMsg,true);
            }
        }else{//加载更多
            view.getOrderListFail(extMsg,false);
        }

    }
    @Override
    public void start() {
        getOrderList();
    }

    @Override
    public void detach() {
        view = null;

    }
}
