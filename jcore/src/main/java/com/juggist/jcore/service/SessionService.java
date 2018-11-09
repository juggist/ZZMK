package com.juggist.jcore.service;

import com.juggist.jcore.Constants;
import com.juggist.jcore.base.BaseService;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.SessionBean;
import com.juggist.jcore.http.ApiServiceGenerator;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * @author juggist
 * @date 2018/11/8 4:10 PM
 */
public class SessionService extends BaseService implements ISessionService {
    SessionServiceApi sessionServiceApi;
    public SessionService() {
        sessionServiceApi = ApiServiceGenerator.createService(SessionServiceApi.class);
    }

    @Override
    public void getSessionList(String user_id, String token, String page, String page_size, final ResponseCallback<ArrayList<SessionBean.DataBean>> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","GoodsPublic");
        params.put("action","indexPage");
        params.put("user_id",user_id);
        params.put("token",token);
        params.put("page",page);
        params.put("page_size",page_size);
        this.getFilterResponse(sessionServiceApi.getSessionList(params), AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SessionBean>() {
                    @Override
                    public void accept(SessionBean sessionBean) throws Exception {
                        if(sessionBean != null && sessionBean.getData() != null ){
                            callback.onSucceed((ArrayList<SessionBean.DataBean>) sessionBean.getData());
                        }else{
                            callback.onError(Constants.ERROR.DATA_IS_NULL);
                        }
                    }
                }, new ConsumerThrowable<>(callback));
    }
}
