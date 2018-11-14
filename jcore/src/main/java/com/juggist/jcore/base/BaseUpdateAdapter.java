package com.juggist.jcore.base;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author juggist
 * @date 2018/11/14 1:01 PM
 */
public abstract class BaseUpdateAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder> {

    public BaseUpdateAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public abstract void update(List<T> t);
}
