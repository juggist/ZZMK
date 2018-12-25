package com.juggist.jcore.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestListener;
import com.juggist.jcore.GlideApp;
import com.juggist.jcore.MyBaseApplication;

/**
 * @author juggist
 * @date 2018/12/18 10:24 AM
 */
public class MyImageLoader {
    private static MyImageLoader instance;

    public synchronized static MyImageLoader getInstance() {
        if(instance == null){
            synchronized (MyImageLoader.class){
                if(instance == null){
                    instance = new MyImageLoader();
                }
            }
        }
        return instance;
    }

    public void loadImage(String url, ImageView imageView){
        GlideApp.with(MyBaseApplication.getInstance()).load(url).into(imageView);
    }
    public void loadImage(int resId, ImageView imageView){
        GlideApp.with(MyBaseApplication.getInstance()).load(resId).into(imageView);
    }

    public void loadImageListener(String url,ImageView imageView, RequestListener<Drawable> callback){
        GlideApp.with(MyBaseApplication.getInstance()).load(url).addListener(callback).into(imageView);
    }
}
