package com.juggist.baseandroid;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.juggist.jcore.Constants;

import androidx.annotation.NonNull;

/**
 * @author juggist
 * @date 2018/11/13 5:53 PM
 */
@GlideModule
public class MyGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        /**
         * /storage/emulated/0/ZZMK/GLIDE/
         */
        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context,Constants.PATH.PATH_GLIDE,250 * 1024 * 1024));
    }
}
