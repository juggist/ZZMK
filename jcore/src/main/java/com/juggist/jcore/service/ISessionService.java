package com.juggist.jcore.service;

import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.SessionBean;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/8 4:08 PM
 */
public interface ISessionService {
     void getSessionList(String user_id, String token, String page, String page_size, ResponseCallback<ArrayList<SessionBean.DataBean>> callback);
}
