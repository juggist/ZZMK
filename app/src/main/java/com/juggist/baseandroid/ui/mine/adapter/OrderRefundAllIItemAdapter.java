package com.juggist.baseandroid.ui.mine.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.baseandroid.GlideApp;
import com.juggist.baseandroid.MyApplication;
import com.juggist.baseandroid.R;
import com.juggist.jcore.base.BaseUpdateAdapter;
import com.juggist.jcore.bean.OrderBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author juggist
 * @date 2018/12/4 9:48 AM
 */
public class OrderRefundAllIItemAdapter extends BaseUpdateAdapter<OrderBean.GoodsListBean> {
    List<OrderBean.GoodsListBean> data;
    public OrderRefundAllIItemAdapter(int layoutResId, @Nullable List<OrderBean.GoodsListBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    public void update(List<OrderBean.GoodsListBean> t) {
        this.data.clear();
        this.data.addAll(t);
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean.GoodsListBean item) {
        GlideApp.with(MyApplication.getInstance()).load(item.getGoods_pic()).into((ImageView) helper.getView(R.id.iv_icon));
        helper.setText(R.id.tv_content, item.getGoods_name())
                .setText(R.id.tv_real_money, "￥" + item.getGoods_price())
                .setText(R.id.tv_num, "x" + item.getGoods_number());
        List<OrderBean.GoodsListBean.AttrBean> spes = item.getAttr();
        if (spes != null && spes.size() > 0) {
            OrderBean.GoodsListBean.AttrBean valus = spes.get(0);
            helper.setText(R.id.tv_specs, valus.getAttr() + ":" + valus.getAttrvalue());
        }
    }
}
