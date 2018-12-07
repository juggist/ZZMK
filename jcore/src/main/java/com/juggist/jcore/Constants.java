package com.juggist.jcore;

import android.os.Environment;

import com.juggist.jcore.utils.PathUtils;

import java.io.File;

/**
 * @author juggist
 * @date 2018/10/30 3:15 PM
 */
public class Constants {
    public static final boolean DEBUG = true;
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
        public static final String TOKEN_ERROR_CODE = "302";
        public static final String TOKEN_ERROR_MSG = "token错误!";

        public static final String DATA_IS_NULL = "数据获取失败";
        public static final String DATA_OUT_OF_LENGTH = "数据不存在";
        public static final String DATA_GOSN_ERROR = "服务器数据异常";

        public static final String SHOPCAR_UPDATE_NUM_OUT_OF_LENGTH = "修改商品数量异常";

        public static final String DOWNLOAD_SHARE_IMG_FAIL="下载分享图片失败";

    }
    public static class URL{
//        public static final String BASE = "http://47.106.109.194//";//UAT
        public static final String BASE = "http://47.106.64.20//";//线上
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
        public static final String PATH_GALLERY_PATH =  Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM +File.separator+"Camera"+File.separator;//系统相册目录

        private static final String BASE_PATH = PathUtils.getExternalStoragePath() + "/ZZMK/";
        public static final String PATH_GLIDE = BASE_PATH +  "GLIDE/";//glide外部磁盘缓存


        public static final String PATH_SAVE_SESSION_PIC = BASE_PATH +  "SESSION_PIC/";//商品图片地址
        public static final String PATH_SAVE_SHARE_PIC = BASE_PATH +  "SHARE_PIC/";//商品图片分享地址
        public static final String PATH_SAVE_SHARE_MERGER_PIC = BASE_PATH +  "SHARE_MERGER_PIC/";//商品图片合成分享地址
        public static final String PATH_REFUND_PIC = BASE_PATH +  "SHARE_REFUND_PIC/";//退货退款图片地址


    }
}
