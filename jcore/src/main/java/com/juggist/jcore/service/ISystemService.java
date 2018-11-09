package com.juggist.jcore.service;

import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.BannerBean;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/7 4:12 PM
 */
public interface ISystemService {
    void getBanner(String user_id,String token,ResponseCallback<ArrayList<BannerBean>> callback);

    void getLoginAuthCode(String phone,ResponseCallback<String> callback);

    void getRegisterAuthCode(String phone,ResponseCallback<String> callback);
}
