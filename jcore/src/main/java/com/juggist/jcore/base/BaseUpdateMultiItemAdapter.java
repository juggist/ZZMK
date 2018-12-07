package com.juggist.jcore.base;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * @author juggist
 * @date 2018/12/6 3:55 PM
 */
public abstract class BaseUpdateMultiItemAdapter<T extends MultiItemEntity> extends BaseMultiItemQuickAdapter<T,BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public BaseUpdateMultiItemAdapter(List<T> data) {
        super(data);
    }
    public abstract void update(List<T> t);
}
