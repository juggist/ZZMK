package com.juggist.baseandroid.ui.buy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.juggist.baseandroid.R;

/**
 * @author juggist
 * @date 2018/11/7 2:01 PM
 * 购物车适配器
 */
public class BuyAdapter extends BaseAdapter {
    private Context context;

    public BuyAdapter(Context context) {
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_buy_item,null);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private static class ViewHolder{
        private ImageView iv_select,iv,iv_add,iv_minus;
        private TextView tv_content,tv_price,tv_spes,tv_num;
    }
}
