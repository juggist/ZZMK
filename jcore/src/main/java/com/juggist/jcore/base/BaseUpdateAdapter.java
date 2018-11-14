package com.juggist.jcore.base;

import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author juggist
 * @date 2018/11/14 1:01 PM
 */
public abstract class BaseUpdateAdapter<T> extends BaseAdapter {
    public abstract void update(List<T> t);
}
