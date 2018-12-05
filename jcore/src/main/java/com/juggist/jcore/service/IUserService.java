package com.juggist.jcore.service;

import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.AddressBean;
import com.juggist.jcore.bean.UserBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/10/31 11:41 AM
 */
public interface IUserService {
    void login(String cellphone, String cellphone_code, ResponseCallback<UserBean> callback);
    void register(String cellphone, String cellphone_code,String active_code, ResponseCallback<UserBean> callback);

    void getAddressList(ResponseCallback<List<AddressBean>> callback);

    void setDefaultAddress(String address_id,ResponseCallback<String> callback);
    void deleteAddress(String address_id,ResponseCallback<String> callback);
    void addAddress(String detail,String province_id,String city_id,String area_id,String cellphone,String consignee,String is_default ,ResponseCallback<String> callback);
    void changeAddress(String address_id,String detail,String province_id,String city_id,String area_id, String cellphone, String consignee, String is_default,ResponseCallback<String> callback);
}
