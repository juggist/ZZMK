package com.juggist.address.ui;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.AddressBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/12/4 3:17 PM
 */
public class AddressListContract {
    public interface View extends BaseView<Present> {

        void getAddressListEmpty();

        void getAddressListSucceed(List<AddressBean> addressBeans);

        void getAddressListFail(String msg);

        void deleteAddressSucceed(List<AddressBean> addressBeans);
        void deleteAddressEmptySucceed();

        void deleteAddressFail(String msg);

        void showSetDefaultAddressDialog();

        void setDefaultAddressSucceed(List<AddressBean> addressBeans);
        void showDeleteAddressDialog();
        void setDefaultAddressFail(String msg);

    }

    public interface Present extends BasePresent {
        void getAddressList();

        void checkDeleteAddress(int position);
        void deleteAddress();

        void checkDefaultAddress(int position);
        void setDefaultAddress();

        AddressBean getAddress(int position);
    }
}
