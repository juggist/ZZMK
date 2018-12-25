package com.juggist.order.ui.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.jcore.bean.OrderBean;
import com.juggist.jcore.utils.MyImageLoader;
import com.juggist.order.R;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author juggist
 * @date 2018/12/4 9:48 AM
 */
public class OrderRefundAllIItemAdapter extends BaseQuickAdapter<OrderBean.GoodsListBean,BaseViewHolder> {
    List<OrderBean.GoodsListBean> data;
    public OrderRefundAllIItemAdapter(int layoutResId, @Nullable List<OrderBean.GoodsListBean> data) {
        super(layoutResId, data);
        this.data = data;
    }
    public void update(List<OrderBean.GoodsListBean> data){
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    protected void convert(BaseViewHolder helper, OrderBean.GoodsListBean item) {
        MyImageLoader.getInstance().loadImage(item.getGoods_pic(),(ImageView) helper.getView(R.id.order_iv_icon));
        helper.setText(R.id.order_tv_content, item.getGoods_name())
                .setText(R.id.order_tv_real_money, "￥" + item.getGoods_price())
                .setText(R.id.order_tv_num, "x" + item.getGoods_number());
        List<OrderBean.GoodsListBean.AttrBean> spes = item.getAttr();
        if (spes != null && spes.size() > 0) {
            OrderBean.GoodsListBean.AttrBean valus = spes.get(0);
            helper.setText(R.id.order_tv_specs, valus.getAttr() + ":" + valus.getAttrvalue());
        }
    }
}
