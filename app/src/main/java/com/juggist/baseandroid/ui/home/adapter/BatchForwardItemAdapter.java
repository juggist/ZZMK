package com.juggist.baseandroid.ui.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.baseandroid.GlideApp;
import com.juggist.baseandroid.R;
import com.juggist.jcore.base.BaseUpdateAdapter;
import com.juggist.jcore.bean.ProductBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author juggist
 * @date 2018/11/16 2:40 PM
 */
public class BatchForwardItemAdapter extends BaseUpdateAdapter<ProductBean.DataBean.GoodsListBean> {
    private Context context;
    private List<ProductBean.DataBean.GoodsListBean> data;
    private List<Boolean> select;
    public BatchForwardItemAdapter(int layoutResId, @Nullable List<ProductBean.DataBean.GoodsListBean> data,Context context) {
        super(layoutResId, data);
        this.context = context;
        this.data = data;
        select = new ArrayList<>();
    }

    @Override
    public void update(List<ProductBean.DataBean.GoodsListBean> t) {
        data.clear();
        select.clear();
        data.addAll(t);
        for (ProductBean.DataBean.GoodsListBean item:data) {
            select.add(false);
        }
        notifyDataSetChanged();
    }

    public int updateSelect(int position){
        int count = 0;
        for(Boolean b : select){
            if(b)
                count++;
        }
        if(position >= select.size()){
            return count;
        }

        select.set(position,!select.get(position));
        notifyItemChanged(position);
        return select.get(position) ? ++count : --count;
    }
    @Override
    protected void convert(final BaseViewHolder helper, ProductBean.DataBean.GoodsListBean item) {
        helper.getView(R.id.ibtn_select).setSelected(select.get(helper.getLayoutPosition()));

        final ArrayList<String> mainPics = (ArrayList<String>) item.getMain_pic();
        if (mainPics != null && mainPics.size() > 0) {
            GlideApp.with(context).load(mainPics.get(0)).into((ImageView) helper.getView(R.id.iv_session_icon));
        }
        helper.setText(R.id.tv_session_name, item.getGoods_name())
                .setText(R.id.tv_session_old_price, "代购价:￥" + item.getPrice())
                .setText(R.id.tv_session_new_price, "￥" + item.getWholesale_price())
                .setText(R.id.tv_session_location, item.getMail_type_name())
                .setText(R.id.tv_session_counter_price, item.getShoppe() == null ? "" : "专柜价:￥" + item.getShoppe())
                .setText(R.id.tv_session_no, "货号:" + item.getSn())
                .addOnClickListener(R.id.ibtn_select);
        ((TextView) helper.getView(R.id.tv_session_counter_price)).setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean> spes = (ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean>) item.getAttr();
        if (spes != null && spes.size() > 0) {
            ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean.ValueBean> valus = (ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean.ValueBean>) spes.get(0).getValue();
            if (valus != null && valus.size() > 0) {
                helper.setText(R.id.tv_session_weight, spes.get(0).getAttrname() + ":" + valus.get(0).getContent());
            }
        }
    }
}
