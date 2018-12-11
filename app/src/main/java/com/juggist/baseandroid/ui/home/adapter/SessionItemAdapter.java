package com.juggist.baseandroid.ui.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
 * @date 2018/11/6 2:49 PM
 */
public class SessionItemAdapter extends BaseUpdateAdapter<ProductBean.DataBean.GoodsListBean> {
    private Context context;
    private List<ProductBean.DataBean.GoodsListBean> productList;
    private Listener listener;

    public SessionItemAdapter(int layoutResId, @Nullable List<ProductBean.DataBean.GoodsListBean> data, Context context, Listener listener) {
        super(layoutResId, data);
        productList = data;
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ProductBean.DataBean.GoodsListBean item) {

        final ArrayList<String> mainPics = (ArrayList<String>) item.getMain_pic();
        if (mainPics != null && mainPics.size() > 0) {
            GlideApp.with(context).load(mainPics.get(0)).into((ImageView) helper.getView(R.id.iv_session_icon));
            ((GridView) helper.getView(R.id.gv)).setAdapter(new Adapter(mainPics));
        }
        helper.setText(R.id.tv_session_name, item.getGoods_name())
                .setText(R.id.tv_session_old_price, "代购价:￥" + item.getPrice())
                .setText(R.id.tv_session_new_price, "￥" + item.getWholesale_price())
                .setText(R.id.tv_session_location, item.getMail_type_name())
                .setText(R.id.tv_session_counter_price, item.getShoppe() == null ? "" : "专柜价:￥" + item.getShoppe())
                .setText(R.id.tv_session_no, "货号:" + item.getSn())
                .setText(R.id.tv_session_vip_price, "升级享受会员价格:￥" + item.getWholesale_price());


        ((TextView) helper.getView(R.id.tv_session_counter_price)).setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);


        ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean> spes = (ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean>) item.getAttr();
        if (spes != null && spes.size() > 0) {
            ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean.ValueBean> valus = (ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean.ValueBean>) spes.get(0).getValue();
            if (valus != null && valus.size() > 0) {
                helper.setText(R.id.tv_session_weight, spes.get(0).getAttrname() + ":" + valus.get(0).getContent());
            }
        }

        helper.getView(R.id.ibtn_buy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.toBuy(item,helper.getLayoutPosition());
            }
        });
        helper.getView(R.id.ibtn_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.toDownload(helper.getLayoutPosition());
            }
        });
        helper.getView(R.id.ibtn_sale).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.toSale(item);
            }
        });
        ((GridView) helper.getView(R.id.gv)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null)
                    listener.toBigPic(mainPics, position);
            }
        });

    }


    private class Adapter extends BaseAdapter {
        ArrayList<String> mainPics;

        public Adapter(ArrayList<String> mainPics) {
            this.mainPics = mainPics;
        }

        @Override
        public int getCount() {
            return mainPics.size();
        }

        @Override
        public Object getItem(int position) {
            return mainPics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder2 vh = null;
            if (convertView == null) {
                vh = new ViewHolder2();
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_session_item_iv, null);
                vh.iv = convertView.findViewById(R.id.iv);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder2) convertView.getTag();
            }
            Glide.with(context).load(mainPics.get(position)).into(vh.iv);
            return convertView;
        }
    }

    private static class ViewHolder2 {
        private ImageView iv;
    }

    public void update(List<ProductBean.DataBean.GoodsListBean> productList) {
        this.productList.clear();
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    public interface Listener {
        void toBigPic(ArrayList<String> pics, int position);
        void toBuy(ProductBean.DataBean.GoodsListBean goodsListBean,int position);
        void toDownload(int position);
        void toSale(ProductBean.DataBean.GoodsListBean goodsListBean);
    }
}
