package com.juggist.baseandroid.eventbus;

import com.juggist.jcore.bean.AddressBean;

/**
 * @author juggist
 * @date 2018/12/5 2:58 PM
 */
public class AddressEvent {
    /**
     * 刷新地址列表
     */
    public static class AddressListUpdate{

    }
    public static class AddressChoose{
        AddressBean addressBean;

        public AddressChoose(AddressBean addressBean) {
            this.addressBean = addressBean;
        }

        public AddressBean getAddressBean() {
            return addressBean;
        }
    }
}
