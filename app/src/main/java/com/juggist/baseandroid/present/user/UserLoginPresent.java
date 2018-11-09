package com.juggist.baseandroid.present.user;

import com.juggist.baseandroid.ui.user.fragment.UserLoginContract;
import com.juggist.jcore.Constants;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.UserBean;
import com.juggist.jcore.service.ISystemService;
import com.juggist.jcore.service.IUserService;
import com.juggist.jcore.service.SystemService;
import com.juggist.jcore.service.UserService;
import com.juggist.jcore.utils.SPUtil;

/**
 * @author juggist
 * @date 2018/11/7 4:33 PM
 */
public class UserLoginPresent implements UserLoginContract.Present {
    UserLoginContract.View view;
    IUserService userService;
    ISystemService systemService;

    public UserLoginPresent(UserLoginContract.View view) {
        this.view = view;
        view.setPresent(this);
        userService = new UserService();
        systemService = new SystemService();
    }

    @Override
    public void getAuthCode(String phone) {
        showLoading();
        systemService.getLoginAuthCode(phone, new ResponseCallback<String>() {
            @Override
            public void onSucceed(String s) {
                if(view !=null){
                    view.dismissLoading();
                    view.getAuthCodeSucceed(s);
                }
            }

            @Override
            public void onError(String message) {
                if(view !=null){
                    view.dismissLoading();
                    view.getAuthCodeFail(message);
                }
            }

            @Override
            public void onApiError(String state, String message) {
                if(view !=null){
                    view.dismissLoading();
                    view.getAuthCodeFail(message + ":" + state);
                }
            }
        });
    }

    @Override
    public void login(String phone,String code) {
        showLoading();
        userService.login(phone, code, new ResponseCallback<UserBean>() {
            @Override
            public void onSucceed(UserBean userBean) {
                SPUtil.saveObjectToShare(Constants.SP.USER_INFO,userBean);
                if(view !=null){
                    view.dismissLoading();
                    view.loginSucceed();
                }
            }

            @Override
            public void onError(String message) {
                if(view !=null){
                    view.dismissLoading();
                    view.showErrorDialog(message);
                }
            }

            @Override
            public void onApiError(String state, String message) {
                if(view !=null){
                    view.dismissLoading();
                    view.loginFail(message + ":" + state);
                }
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void detach() {
        view = null;
    }

    private void showLoading(){
        if(view != null)
            view.showLoading();
    }
}
