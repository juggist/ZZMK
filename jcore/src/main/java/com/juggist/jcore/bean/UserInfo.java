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
    public static String headPic(){
        UserBean userBean = SPUtil.getObjectFromShare(Constants.SP.USER_INFO);
        if(userBean != null){
            return userBean.getPhoto();
        }else{
            return "";
        }
    }
    public static String nickName(){
        UserBean userBean = SPUtil.getObjectFromShare(Constants.SP.USER_INFO);
        if(userBean != null){
            return userBean.getNickname();
        }else{
            return "";
        }
    }
    public static int todayOrdersNumber(){
        UserBean userBean = SPUtil.getObjectFromShare(Constants.SP.USER_INFO);
        if(userBean != null){
            return userBean.getToday_orders_number();
        }else{
            return 0;
        }
    }
    public static double totalPrice(){
        UserBean userBean = SPUtil.getObjectFromShare(Constants.SP.USER_INFO);
        if(userBean != null){
            return userBean.getTotal_price();
        }else{
            return 0.0;
        }
    }

}
