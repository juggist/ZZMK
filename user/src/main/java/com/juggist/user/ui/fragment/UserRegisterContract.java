package com.juggist.user.ui.fragment;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;

/**
 * @author juggist
 * @date 2018/11/8 1:55 PM
 */
public class UserRegisterContract {
    public interface View extends BaseView<Present> {
        void getAuthCodeSucceed(String authCode);
        void getAuthCodeFail(String extMsg);

        void registerSucceed();
        void registerFail(String extMsg);


    }
    public interface Present extends BasePresent {
        void getAuthCode(String phone);
        void register(String phone,String code);
    }
}
