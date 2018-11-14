package com.juggist.baseandroid.ui.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juggist.baseandroid.R;
import com.juggist.jcore.bean.ProductBean;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/6 2:49 PM
 */
public class SessionItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ProductBean.DataBean.GoodsListBean> productList;

    private Listener listener;
    public SessionItemAdapter(Context context,Listener listener) {
        this.context = context;
        this.listener = listener;
        productList = new ArrayList<>();
    }
    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_session_item,null);
            vh.iv_session_icon = convertView.findViewById(R.id.iv_session_icon);
            vh.tv_session_name = convertView.findViewById(R.id.tv_session_name);
            vh.tv_session_old_price = convertView.findViewById(R.id.tv_session_old_price);
            vh.tv_session_weight = convertView.findViewById(R.id.tv_session_weight);
            vh.tv_session_new_price = convertView.findViewById(R.id.tv_session_new_price);
            vh.tv_session_location = convertView.findViewById(R.id.tv_session_location);
            vh.tv_session_counter_price = convertView.findViewById(R.id.tv_session_counter_price);
            vh.tv_session_no = convertView.findViewById(R.id.tv_session_no);
            vh.tv_session_vip_price = convertView.findViewById(R.id.tv_session_vip_price);
            vh.gv = convertView.findViewById(R.id.gv);
            vh.ibtn_sale = convertView.findViewById(R.id.ibtn_sale);
            vh.ibtn_buy = convertView.findViewById(R.id.ibtn_buy);
            vh.ibtn_download = convertView.findViewById(R.id.ibtn_download);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }



        final ProductBean.DataBean.GoodsListBean item = productList.get(position);
        ArrayList<String> mainPics = (ArrayList<String>) item.getMain_pic();
        if(mainPics != null && mainPics.size() > 0){
            Glide.with(context).load(mainPics.get(0)).into(vh.iv_session_icon);
            vh.gv.setAdapter(new Adapter(mainPics));
        }
        vh.tv_session_name.setText(item.getGoods_name());
        vh.tv_session_old_price.setText("代购价:￥" + item.getPrice());
        ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean> spes = (ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean>) item.getAttr();
        if(spes != null && spes.size() > 0){
            ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean.ValueBean> valus = (ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean.ValueBean>) spes.get(0).getValue();
            if(valus != null && valus.size() > 0){
                vh.tv_session_weight.setText(spes.get(0).getAttrname() + ":" + valus.get(0).getContent());
            }
        }
        vh.tv_session_new_price.setText("￥" + item.getWholesale_price());
        vh.tv_session_location.setText(item.getMail_type_name());
        vh.tv_session_counter_price.setText(item.getShoppe() == null ? "" : "专柜价:￥" + item.getShoppe());
        vh.tv_session_counter_price.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        vh.tv_session_no.setText("货号:" + item.getSn());
        vh.tv_session_vip_price.setText("升级享受会员价格:￥" + item.getWholesale_price());

        vh.ibtn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( listener != null)
                    listener.download();
            }
        });
        vh.ibtn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( listener != null)
                    listener.buy(item);
            }
        });
        vh.gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener != null)
                    listener.toBigPic((ArrayList<String>) item.getMain_pic(),position);
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        private ImageView iv_session_icon;
        private TextView tv_session_name,tv_session_old_price,tv_session_weight,tv_session_new_price,tv_session_location,tv_session_counter_price,tv_session_no,tv_session_vip_price;
        private GridView gv;
        private ImageButton ibtn_sale,ibtn_buy,ibtn_download;
    }

    private class Adapter extends BaseAdapter{
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
            if(convertView == null){
                vh = new ViewHolder2();
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_session_item_iv,null);
                vh.iv = convertView.findViewById(R.id.iv);
                convertView.setTag(vh);
            }else{
                vh = (ViewHolder2) convertView.getTag();
            }
            Glide.with(context).load(mainPics.get(position)).into(vh.iv);
            return convertView;
        }

    }
    private static class ViewHolder2{
        private ImageView iv;
    }
    public interface Listener{
        void download();
        void buy(ProductBean.DataBean.GoodsListBean goodsListBean);
        void toBigPic(ArrayList<String> picUrls,int position);
    }

    public void update(ArrayList<ProductBean.DataBean.GoodsListBean> productList){
        this.productList.clear();
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }
}
