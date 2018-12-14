package com.juggist.baseandroid.ui.buy;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.AddressBean;
import com.juggist.jcore.bean.OrderCreateBean;
import com.juggist.jcore.bean.OrderCreateTmpBean;

/**
 * @author juggist
 * @date 2018/12/12 5:56 PM
 */
public class OrderSubmitContract {
    public interface View extends BaseView<Present> {
        void showIDDialog(int user_info);
        void createOrderSucceed(OrderCreateBean orderCreateBean);
        void createOrderFail(String msg);
    }


    public interface Present extends BasePresent {
        void submitOrder();

        OrderCreateTmpBean getOrderCreateTmpBean();

        void updateAddress(AddressBean addressBean);

        void updateDiscountCard(int position);

        void createIDOrder(String id,String cn_id_bg,String cn_id_front);
    }
}
