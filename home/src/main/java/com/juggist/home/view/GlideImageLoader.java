package com.juggist.home.view;

import android.content.Context;
import android.widget.ImageView;

import com.juggist.jcore.utils.MyImageLoader;
import com.youth.banner.loader.ImageLoader;

/**
 * @author juggist
 * @date 2018/11/6 1:56 PM
 *
 * banner自定义的图片加载器
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        MyImageLoader.getInstance().loadImage((String)path,imageView);
    }
}
