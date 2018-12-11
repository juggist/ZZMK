package com.juggist.baseandroid.ui.mine.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.baseandroid.R;
import com.juggist.jcore.bean.AddressBean;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author juggist
 * @date 2018/12/4 2:42 PM
 */
public class AddressListAdapter extends BaseQuickAdapter<AddressBean,BaseViewHolder> {
    List<AddressBean> data;

    public AddressListAdapter(int layoutResId, @Nullable List<AddressBean> data) {
        super(layoutResId, data);
        this.data = data;
    }

    public void update( List<AddressBean> data){
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    protected void convert(BaseViewHolder helper, AddressBean item) {
        helper.setText(R.id.tv_name, item.getConsignee())
                .setText(R.id.tv_phone, item.getCellphone())
                .setText(R.id.tv_address, item.getProvince_name() + item.getCity_name() + item.getAreas_name() + item.getAddr())
                .addOnClickListener(R.id.tv_del_title)
                .addOnClickListener(R.id.iv_del)
                .addOnClickListener(R.id.tv_edit_title)
                .addOnClickListener(R.id.tv_edit)
                .addOnClickListener(R.id.tv_select)
                .addOnClickListener(R.id.ibtn_select);
        ((ImageView) helper.getView(R.id.ibtn_select)).setSelected(item.getIs_default().equals("0") ? false : true);

    }

    public AddressBean getAddressBean(int position){
        return data.get(position);
    }

}
