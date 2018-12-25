package com.juggist.user.present;

import com.juggist.user.ui.fragment.UserRegisterContract;
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
 * @date 2018/11/8 1:57 PM
 */
public class UserRegisterPresent implements UserRegisterContract.Present {
    UserRegisterContract.View view;
    ISystemService systemService;
    IUserService userService;

    public UserRegisterPresent(UserRegisterContract.View view) {
        this.view = view;
        view.setPresent(this);
        systemService = new SystemService();
        userService = new UserService();
    }

    @Override
    public void getAuthCode(String phone) {
        showLoading();
        systemService.getRegisterAuthCode(phone, new ResponseCallback<String>() {
            @Override
            public void onSucceed(String s) {
                if(view != null){
                    view.dismissLoading();
                    view.getAuthCodeSucceed(s);
                }
            }

            @Override
            public void onError(String message) {
                if(view != null){
                    view.dismissLoading();
                    view.getAuthCodeFail(message);
                }
            }

            @Override
            public void onApiError(String state, String message) {
                if(view != null){
                    view.dismissLoading();
                    view.getAuthCodeFail(message + " : " + state);
                }
            }
        });
    }

    @Override
    public void register(String phone, String code) {
        showLoading();
        userService.register(phone, code, "", new ResponseCallback<UserBean>() {
            @Override
            public void onSucceed(UserBean userBean) {
                SPUtil.saveObjectToShare(Constants.SP.USER_INFO,userBean);
                if(view != null){
                    view.dismissLoading();
                    view.registerSucceed();
                }
            }

            @Override
            public void onError(String message) {
                if(view != null){
                    view.dismissLoading();
                    view.registerFail(message);
                }
            }

            @Override
            public void onApiError(String state, String message) {
                if(view != null){
                    view.dismissLoading();
                    view.registerFail(message + " : " + state);
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
