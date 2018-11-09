package com.juggist.jcore.service;

import com.juggist.jcore.base.BaseService;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.BannerBean;
import com.juggist.jcore.http.ApiServiceGenerator;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * @author juggist
 * @date 2018/11/7 4:13 PM
 */
public class SystemService extends BaseService implements ISystemService {
    SystemServiceApi systemService;
    public SystemService() {
        systemService = ApiServiceGenerator.createService(SystemServiceApi.class);
    }

    @Override
    public void getBanner(String user_id, String token, final ResponseCallback<ArrayList<BannerBean>> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","GoodsPublic");
        params.put("action","indexAd");
        params.put("user_id",user_id);
        params.put("token",token);
        this.getFilterResponse(systemService.getBanner(params),AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<BannerBean>>() {
                    @Override
                    public void accept(ArrayList<BannerBean> bannerBeans) throws Exception {
                        callback.onSucceed(bannerBeans);
                    }
                }, new ConsumerThrowable<>(callback));
    }

    /**
     * 获取用户登录验证码
     * @param cellphone
     * @param callback
     */
    @Override
    public void getLoginAuthCode(String cellphone, final ResponseCallback<String> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","sendCellphoneCode");
        params.put("cellphone",cellphone);
        this.getFilterResponse(systemService.sendAuthCode(params), AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object s){
                        callback.onSucceed("");
                    }
                },new ConsumerThrowable<>(callback));
    }

    @Override
    public void getRegisterAuthCode(String cellphone,final ResponseCallback<String> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Member");
        params.put("action","sendRegisterCellphoneCode");
        params.put("cellphone",cellphone);
        this.getFilterResponse(systemService.sendAuthCode(params), AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object s){
                        callback.onSucceed("");
                    }
                },new ConsumerThrowable<>(callback));
    }


}
