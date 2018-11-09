package com.juggist.jcore.bean;

import com.juggist.jcore.Constants;
import com.juggist.jcore.base.BaseBean;
import com.juggist.jcore.utils.SPUtil;

/**
 * @author juggist
 * @date 2018/11/7 4:53 PM
 */
public class UserInfo extends BaseBean {
    public static boolean isLogin(){
        if(SPUtil.getObjectFromShare(Constants.SP.USER_INFO) != null){
            return true;
        }else{
            return false;
        }
    }

    public static String userId(){
        UserBean userBean = SPUtil.getObjectFromShare(Constants.SP.USER_INFO);
        if(userBean != null){
            return userBean.getUser_id();
        }else{
            return "";
        }
    }
    public static String token(){
        UserBean userBean = SPUtil.getObjectFromShare(Constants.SP.USER_INFO);
        if(userBean != null){
            return userBean.getToken();
        }else{
            return "";
        }
    }
}
