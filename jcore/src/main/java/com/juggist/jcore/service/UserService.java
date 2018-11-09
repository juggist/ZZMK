package com.juggist.jcore.service;

import com.juggist.jcore.base.BaseService;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.UserBean;
import com.juggist.jcore.http.ApiServiceGenerator;
import com.juggist.jcore.http.ErrorCode;
import com.juggist.jcore.http.exception.ApiCodeErrorException;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * @author juggist
 * @date 2018/10/30 4:39 PM
 */
public class UserService extends BaseService implements IUserService{
    UserServiceApi userAPI;
    public UserService() {
        userAPI = ApiServiceGenerator.createService(UserServiceApi.class);
    }
    public void login(String cellphone, String cellphone_code, final ResponseCallback<UserBean> callback){
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","cellphoneLogin");
        params.put("cellphone",cellphone);
        params.put("cellphone_code",cellphone_code);
        this.getFilterResponse(userAPI.loginAndRegister(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserBean[]>() {
                    @Override
                    public void accept(UserBean[] userBeans) throws Exception {
                        if(userBeans.length == 1){
                            callback.onSucceed(userBeans[0]);
                        }else{
                            throw new ApiCodeErrorException(ErrorCode.USER_INFO_OUT_OF_LENGTH);
                        }
                    }
                }, new ConsumerThrowable(callback));
    }

    @Override
    public void register(String cellphone, String cellphone_code, String active_code,final ResponseCallback<UserBean> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","cellphoneRegister");
        params.put("cellphone",cellphone);
        params.put("cellphone_code",cellphone_code);
        params.put("active_code",active_code);
        this.getFilterResponse(userAPI.loginAndRegister(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserBean[]>() {
                    @Override
                    public void accept(UserBean[] userBeans) {
                        if(userBeans.length == 1){
                            callback.onSucceed(userBeans[0]);
                        }else{
                            throw new ApiCodeErrorException(ErrorCode.USER_INFO_OUT_OF_LENGTH);
                        }
                    }
                }, new ConsumerThrowable(callback));
    }


}
