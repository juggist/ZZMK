package com.juggist.jcore.service;

import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.UserBean;

/**
 * @author juggist
 * @date 2018/10/31 11:41 AM
 */
public interface IUserService {
    void login(String cellphone, String cellphone_code, ResponseCallback<UserBean> callback);
    void register(String cellphone, String cellphone_code,String active_code, ResponseCallback<UserBean> callback);
}
