package com.juggist.jcore.service;

import com.juggist.jcore.base.BaseService;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.AddressBean;
import com.juggist.jcore.bean.UserBean;
import com.juggist.jcore.bean.UserInfo;
import com.juggist.jcore.http.ApiServiceGenerator;
import com.juggist.jcore.http.ErrorCode;
import com.juggist.jcore.http.exception.ApiCodeErrorException;

import java.util.HashMap;
import java.util.List;

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
        params.put("action","passwordLogin");
        params.put("cellphone",cellphone);
        params.put("password",cellphone_code);
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

    /**
     * 获取地址列表
     * @param callback
     */
    @Override
    public void getAddressList(final ResponseCallback<List<AddressBean>> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","getAddressList");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        this.getFilterResponse(userAPI.getAddressList(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AddressBean>>() {
                    @Override
                    public void accept(List<AddressBean> addressBeans) throws Exception {
                        callback.onSucceed(addressBeans);
                    }
                },new ConsumerThrowable<>(callback));
    }

    @Override
    public void setDefaultAddress(String address_id, final ResponseCallback<String> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","setDefaultAddress");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("address_id",address_id);
        this.getFilterResponse(userAPI.setDefaultAddress(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        callback.onSucceed(s);
                    }
                },new ConsumerThrowable<>(callback));
    }

    @Override
    public void deleteAddress(String address_id,final ResponseCallback<String> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","deleteAddress");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("address_id",address_id);
        this.getFilterResponse(userAPI.deleteAddress(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        callback.onSucceed(s);
                    }
                },new ConsumerThrowable<>(callback));
    }

    @Override
    public void addAddress(String detail, String province_id, String city_id, String area_id, String cellphone, String consignee, String is_default, final ResponseCallback<String> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","addAddress");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("detail",detail);
        params.put("province_id",province_id);
        params.put("city_id",city_id);
        params.put("area_id",area_id);
        params.put("cellphone",cellphone);
        params.put("consignee",consignee);
        params.put("is_default",is_default);
        this.getFilterResponse(userAPI.addAddress(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        callback.onSucceed(s);
                    }
                },new ConsumerThrowable<>(callback));
    }

    @Override
    public void changeAddress(String address_id,String detail, String province_id, String city_id, String area_id, String cellphone, String consignee, String is_default, final ResponseCallback<String> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","updateOrdersAddress");
        params.put("user_id",UserInfo.userId());
        params.put("token",UserInfo.token());
        params.put("detail",detail);
        params.put("province_id",province_id);
        params.put("city_id",city_id);
        params.put("area_id",area_id);
        params.put("cellphone",cellphone);
        params.put("consignee",consignee);
        params.put("is_default",is_default);
        params.put("address_id",address_id);
        this.getFilterResponse(userAPI.changeAddress(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        callback.onSucceed(s);
                    }
                },new ConsumerThrowable<>(callback));
    }


}
