package com.juggist.baseandroid.ui.mine.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.juggist.baseandroid.R;

/**
 * @author juggist
 * @date 2018/11/13 11:43 AM
 */
public class OrderColumnAdapter extends BaseAdapter {
    private Context context;

    private int[] imgIds = new int[]{
            R.drawable.store_icon_unpaid,
            R.drawable.store_icon_unfilledorder,
            R.drawable.store_icon_waiting,
            R.drawable.store_icon_service,
    };
    private String[] titles ;

    public OrderColumnAdapter(Context context) {
        this.context = context;
        titles = context.getResources().getStringArray(R.array.order);
    }

    @Override
    public int getCount() {
        return imgIds.length;
    }

    @Override
    public Object getItem(int position) {
        return imgIds[position];
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
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_column_order_item,null);
            vh.tv_order = convertView.findViewById(R.id.tv_order);
            vh.tv_order_dot = convertView.findViewById(R.id.tv_order_dot);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_order.setText(titles[position]);
        Drawable drawable = context.getResources().getDrawable(imgIds[position]);
//        drawable.setBounds(0,0,context.getResources().getDimensionPixelOffset(R.dimen.dp_54),context.getResources().getDimensionPixelOffset(R.dimen.dp_54));
        vh.tv_order.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
        vh.tv_order.setCompoundDrawablePadding(context.getResources().getDimensionPixelOffset(R.dimen.dp_16));
        return convertView;
    }

    private static class ViewHolder {
        private TextView tv_order,tv_order_dot;
    }
}
