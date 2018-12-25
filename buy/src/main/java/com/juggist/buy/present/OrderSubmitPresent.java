package com.juggist.buy.present;

import com.juggist.buy.ui.OrderSubmitContract;
import com.juggist.jcore.Constants;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.AddressBean;
import com.juggist.jcore.bean.OrderCreateBean;
import com.juggist.jcore.bean.OrderCreateTmpBean;
import com.juggist.jcore.service.AccountService;
import com.juggist.jcore.service.IAccountService;

/**
 * @author juggist
 * @date 2018/12/12 5:56 PM
 */
public class OrderSubmitPresent implements OrderSubmitContract.Present {
    private OrderSubmitContract.View view;
    private IAccountService accountService;

    private OrderCreateTmpBean orderCreateTmpBean;
    private int discountCardPosition = -1;

    public OrderSubmitPresent(OrderSubmitContract.View view, OrderCreateTmpBean orderCreateTmpBean) {
        this.view = view;
        this.orderCreateTmpBean = orderCreateTmpBean;
        accountService = new AccountService();
        view.setPresent(this);
    }


    @Override
    public OrderCreateTmpBean getOrderCreateTmpBean() {
        return orderCreateTmpBean;
    }

    @Override
    public void updateAddress(AddressBean addressBean) {
        orderCreateTmpBean.setAddress(addressBean);
    }

    @Override
    public void updateDiscountCard(int position) {
        discountCardPosition = position;
    }

    @Override
    public void submitOrder() {
        if(orderCreateTmpBean.getAddress().isEmpty()){
            if(view != null)
                view.showErrorDialog(Constants.ERROR.ADDRESS_EMPTY);
            return;
        }
        if (orderCreateTmpBean.getUser_info() == 1) {
            createOrder("", "", "");
        } else {
            if (view != null)
                view.showIDDialog(orderCreateTmpBean.getUser_info());
        }
    }

    @Override
    public void createIDOrder(String id, String cn_id_bg, String cn_id_front) {
        createOrder(id, cn_id_bg, cn_id_front);
    }

    private void createOrder(String id, String cn_id_bg, String cn_id_front) {
        if (view != null)
            view.showLoading();
        accountService.createOrder(orderCreateTmpBean.getGoods_list(), orderCreateTmpBean.getAddress().getAuto_id(), discountCardPosition == -1 ? "-1" : orderCreateTmpBean.getAvailable_coupon().get(discountCardPosition).getId(), id, cn_id_bg, cn_id_front, createBeanResponseCallback);
    }

    ResponseCallback<OrderCreateBean> createBeanResponseCallback = new ResponseCallback<OrderCreateBean>() {
        @Override
        public void onSucceed(OrderCreateBean orderCreateBean) {
            dismissLoading();
            if(view != null)
                view.createOrderSucceed(orderCreateBean);

        }

        @Override
        public void onError(String message) {
            dismissLoading();
            if(view != null)
                view.createOrderFail(message);
        }

        @Override
        public void onApiError(String state, String message) {
            dismissLoading();
            if(view != null)
                view.createOrderFail(message + " ; " + message);
        }
    };

    @Override
    public void start() {

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
