package com.juggist.baseandroid.present.mine;

import com.juggist.baseandroid.ui.mine.fragment.OrderContract;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.base.SmartRefreshResponseCallback;
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

    private int page = 1;
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
        page = 1;
        getOrderList();
    }

    @Override
    public void loadMoreOrderList() {
        getOrderList();
    }
    public String getRefundId(int position){
        return String.valueOf(totalOrderBeans.get(position).getRefund_id());
    }
    public String getOrderId(int position){
        return String.valueOf(totalOrderBeans.get(position).getOrder_id());
    }
    public OrderBean getOrder(int position){
        return totalOrderBeans.get(position);
    }
    private void getOrderList(){
        if(view != null)
            view.showLoading();
        sessionService.getOrderList(String.valueOf(page), String.valueOf(page_size), condition, new SmartRefreshResponseCallback<OrderBean>() {
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
                OrderPresent.this.page = page;
            }

            @Override
            public void clearTotalList() {
                totalOrderBeans.clear();
            }

            @Override
            public List<OrderBean> getTotalList() {
                return totalOrderBeans;
            }

            @Override
            public void addToTotalList(List<OrderBean> t) {
                totalOrderBeans.addAll(t);
            }

            @Override
            public BaseView getView() {
                return view;
            }

            @Override
            public void onSucceed(List<OrderBean> t) {
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
        getOrderList();
    }

    @Override
    public void detach() {
        view = null;

    }
    private void dismissLoading() {
        if (view != null)
            view.dismissLoading();
    }
}
