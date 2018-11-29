package com.juggist.jcore;

import android.app.Application;
import android.os.StrictMode;

import com.juggist.jcore.utils.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import androidx.annotation.Nullable;
import zlc.season.rxdownload3.core.DownloadConfig;
import zlc.season.rxdownload3.extension.ApkInstallExtension;

/**
 * @author juggist
 * @date 2018/10/31 10:54 AM
 */
public class MyBaseApplication extends Application {
    private static MyBaseApplication instance;

    public static MyBaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        setupLeakCanary();
        setLogger();
        //注册工具类
        Utils.init(this);

        setRxDownload();

    }

    /**
     * leakCanary 内存检测
     */
    protected void setupLeakCanary(){
        enabledStrictMode();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }
        LeakCanary.install(this);
    }
    private static void enabledStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                .detectAll() //
                .penaltyLog() //
                .penaltyDeath() //
                .build());
    }
    /**
     * logger开启
     */
    protected void setLogger(){
        Logger.addLogAdapter(new AndroidLogAdapter(){
            @Override
            public boolean isLoggable(int priority, @Nullable String tag) {
                return true;
            }
        });
    }

    /**
     * 设置rxdownload配置
     */
    protected void setRxDownload(){
        DownloadConfig.Builder builder = DownloadConfig.Builder.Companion.create(this)
                .enableAutoStart(true)              //自动开始下载
                .setDefaultPath(Constants.PATH.PATH_SAVE_SHARE_PIC)     //设置默认的下载地址
                .useHeadMethod(true)    //使用http HEAD方法进行检查
                .setMaxRange(2)       // 每个任务并发的线程数量
                .setRangeDownloadSize(4 * 1000 * 1000) //每个线程下载的大小，单位字节
                .setMaxMission(3)      // 同时下载的任务数量
                .enableDb(true)                             //启用数据库
//                .setDbActor(CustomSqliteActor(this))        //自定义数据库
                .enableService(true)                        //启用Service
                .enableNotification(true)                  //启用Notification
//                .setNotificationFactory(NotificationFactoryImpl()) 	    //自定义通知
//                .setOkHttpClientFacotry(OkHttpClientFactoryImpl()) 	    //自定义OKHTTP
                .addExtension(ApkInstallExtension.class);          //添加扩展

        DownloadConfig.INSTANCE.init(builder);

    }
}
