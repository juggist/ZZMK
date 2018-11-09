package com.juggist.baseandroid.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juggist.baseandroid.R;

/**
 * @author juggist
 * @date 2018/11/6 2:49 PM
 */
public class SessionItemAdapter extends BaseAdapter {
    private Context context;

    private Listener listener;
    public SessionItemAdapter(Context context,Listener listener) {
        this.context = context;
        this.listener = listener;
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            vh.gv = convertView.findViewById(R.id.gv);
            vh.ibtn_sale = convertView.findViewById(R.id.ibtn_sale);
            vh.ibtn_buy = convertView.findViewById(R.id.ibtn_buy);
            vh.ibtn_download = convertView.findViewById(R.id.ibtn_download);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.gv.setAdapter(new Adapter());
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
                    listener.buy();
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        private ImageView iv_session_icon;
        private TextView tv_session_name,tv_session_old_price,tv_session_weight,tv_session_new_price;
        private GridView gv;
        private ImageButton ibtn_sale,ibtn_buy,ibtn_download;
    }

    private class Adapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
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
            Glide.with(context).load("http://files.maidoupo.com/ccfile//png/20170915_1505439210013.png").into(vh.iv);
            return convertView;
        }

    }
    private static class ViewHolder2{
        private ImageView iv;
    }
    public interface Listener{
        void download();
        void buy();
    }
}
