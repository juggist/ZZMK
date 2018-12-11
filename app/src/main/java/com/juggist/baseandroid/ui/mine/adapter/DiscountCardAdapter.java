package com.juggist.baseandroid.ui.mine.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.baseandroid.GlideApp;
import com.juggist.baseandroid.R;
import com.juggist.jcore.bean.DiscountCardBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author juggist
 * @date 2018/11/15 4:39 PM
 */
public class DiscountCardAdapter extends BaseQuickAdapter<DiscountCardBean,BaseViewHolder> {
    private Context context;
    private List<DiscountCardBean> data;
    public DiscountCardAdapter(int layoutResId, @Nullable List<DiscountCardBean> data,Context context) {
        super(layoutResId, data);
        this.context = context;
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, DiscountCardBean item) {
        GlideApp.with(context).load(item.getImage()).into((ImageView) helper.getView(R.id.img));
    }

}
