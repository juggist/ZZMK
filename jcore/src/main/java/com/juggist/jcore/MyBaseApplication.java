package com.juggist.jcore;

import android.app.Application;
import android.os.StrictMode;
import androidx.annotation.Nullable;

import com.juggist.jcore.utils.Utils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

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
}
