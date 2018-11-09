package com.juggist.jcore;

import com.juggist.jcore.utils.PathUtils;

/**
 * @author juggist
 * @date 2018/10/30 3:15 PM
 */
public class Constants {
    /**
     * 网络请求状态码；
     */
    public static final String REQUEST_SUCCESS = "200";//网络请求成功;
    public static final String REQUEST_FAIL = "-1";//网络请求失败;


    /**
     * 服务器状态
     */
    public static final String SERVICE_BUSY = "服务器繁忙，请稍后重试。";

    public static class ERROR{
        public static final String DATA_IS_NULL = "数据获取失败";
    }
    public static class URL{
//        public static final String BASE = "http://47.106.109.194/";
        public static final String BASE = "http://47.106.64.20//";
    }

    public static class CountDownTime{
        //步长
        public static final long COUNT_DOWN_INTERVAL = 1000;

        //登录倒计时总长
        public static final long LOGIN_AUTH_CODE = 60000;
        //注册倒计时总长
        public static final long REGISTER_AUTH_CODE = 60000;
    }
    public static class SP{
        public static final String APP_ALREADY_OPEN = "app_already_open";
        public static final String USER_INFO = "user_info";

        public static final String COUNT_DOWN_TIME_LOGIN = "count_down_time_login";
        public static final String COUNT_DOWN_TIME_REGISTER = "count_down_time_register";
    }

    public static class PATH{
        private static final String BASE_PATH = PathUtils.getExternalStoragePath() + "/ZZMK/";
        public static final String PATH_SAVE_SESSION_PIC = BASE_PATH +  "SESSION_PIC/";//商品图片地址
    }
}
