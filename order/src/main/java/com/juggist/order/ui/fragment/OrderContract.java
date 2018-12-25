package com.juggist.order.ui.fragment;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.OrderBean;

/**
 * @author juggist
 * @date 2018/11/13 4:33 PM
 */
public class OrderContract {
    public interface View extends BaseView<Present> {

    }

    public interface Present extends BasePresent {

        void refreshOrderList();

        void loadMoreOrderList();

        String getRefundId(int position);

        String getOrderId(int position);

        OrderBean getOrder(int position);
    }
}
