package com.juggist.baseandroid.ui.buy.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.baseandroid.R;
import com.juggist.jcore.bean.OrderCreateTmpBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author juggist
 * @date 2018/11/7 3:30 PM
 * 提交订单适配器
 */
public class OrderSubmitAdapter extends BaseQuickAdapter<OrderCreateTmpBean.GoodsListBean,BaseViewHolder> {
    private Context context;
    public OrderSubmitAdapter(int layoutResId, @Nullable List<OrderCreateTmpBean.GoodsListBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderCreateTmpBean.GoodsListBean item) {
        Glide.with(context).load(item.getMain_pic()).into((ImageView) helper.getView(R.id.iv));
        helper.setText(R.id.tv_content,item.getGoods_name())
                .setText(R.id.tv_price,"￥" + item.getUser_price())
                .setText(R.id.tv_num,"X" + item.getGoods_number());
        if (item.getAttr() != null && item.getAttr().size() > 0) {
            helper.setText(R.id.tv_specs, item.getAttr().get(0).getAttr_name() + ":" + item.getAttr().get(0).getValue());
        }
    }
}
