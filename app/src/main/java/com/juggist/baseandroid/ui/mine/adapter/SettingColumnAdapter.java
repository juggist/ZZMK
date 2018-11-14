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
 * @date 2018/11/13 11:44 AM
 */
public class SettingColumnAdapter extends BaseAdapter {
    private Context context;

    private int[] imgIds = new int[]{
            R.drawable.icon_balance,
            R.drawable.icon_address,
            R.drawable.icon_coupon,
            R.drawable.icon_contact,
            R.drawable.icon_setup,
            R.drawable.icon_setup,
    };
    private String[] titles;

    public SettingColumnAdapter(Context context) {
        this.context = context;
        titles = context.getResources().getStringArray(R.array.setting);
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
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_column_setting_item, null);
            vh.tv_setting = convertView.findViewById(R.id.tv_setting);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_setting.setText(titles[position]);
        Drawable drawable = context.getResources().getDrawable(imgIds[position]);
        vh.tv_setting.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        vh.tv_setting.setCompoundDrawablePadding(context.getResources().getDimensionPixelOffset(R.dimen.dp_14));
        if (position == (titles.length - 1)) {
            vh.tv_setting.setVisibility(View.GONE);
        } else {
            vh.tv_setting.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    private static class ViewHolder {
        private TextView tv_setting;
    }
}

