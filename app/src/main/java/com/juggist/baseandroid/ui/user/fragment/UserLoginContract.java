package com.juggist.baseandroid.ui.user.fragment;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;

/**
 * @author juggist
 * @date 2018/11/7 4:29 PM
 */
public class UserLoginContract {
    public interface View extends BaseView<Present>{
        void getAuthCodeSucceed(String authCode);
        void getAuthCodeFail(String extMsg);

        void loginSucceed();
        void loginFail(String extMsg);


    }
    public interface Present extends BasePresent{
        void getAuthCode(String phone);
        void login(String phone,String code);
    }
}
