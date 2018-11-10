package com.juggist.jcore.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;

import com.orhanobut.logger.Logger;

/**
 * @author juggist
 * @date 2018/11/1 3:44 PM
 * 今日头条适配方案
 * 动态计算
 * 给DisplayMetrics设置值
 */
public class DynamicDensityGenerate {
    private static float mNoncompatDensity;
    private static float mNoncompateScaledDensity;
    private static final float CUSTOM_DESIGN_UI_DP = 750;//设计稿的宽，以dp为单位
    public static void setCustomDesign(Fragment fragment,final Application application){
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        listenerComponent(appDisplayMetrics,application);

        final float targetDensity = appDisplayMetrics.widthPixels / CUSTOM_DESIGN_UI_DP;
        final float targetScaleDensity = targetDensity * (mNoncompateScaledDensity / mNoncompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);
        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics fragmentDisplayMetrics = fragment.getResources().getDisplayMetrics();
        fragmentDisplayMetrics.density = targetDensity;
        fragmentDisplayMetrics.scaledDensity = targetScaleDensity;
        fragmentDisplayMetrics.densityDpi = targetDensityDpi;
        Logger.d("Fragment density:%s,scaledDensity:%s,densityDpi:%s",targetDensity,targetScaleDensity,targetDensityDpi);
    }
    public static void setCustomDensity(Activity activity, final Application application){
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        listenerComponent(appDisplayMetrics,application);

        final float targetDensity = appDisplayMetrics.widthPixels / CUSTOM_DESIGN_UI_DP;
        final float targetScaleDensity = targetDensity * (mNoncompateScaledDensity / mNoncompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);
        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    static void listenerComponent(DisplayMetrics appDisplayMetrics,final Application application){
        if(mNoncompatDensity == 0){
            mNoncompatDensity = appDisplayMetrics.density;
            mNoncompateScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if(newConfig != null && newConfig.fontScale > 0){
                        mNoncompateScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }


    }
}
