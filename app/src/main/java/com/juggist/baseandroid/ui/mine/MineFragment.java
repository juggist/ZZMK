package com.juggist.baseandroid.ui.mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.juggist.baseandroid.GlideApp;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.ui.mine.adapter.OrderColumnAdapter;
import com.juggist.baseandroid.ui.mine.adapter.SettingColumnAdapter;
import com.juggist.baseandroid.view.RoundImageView;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.bean.UserInfo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.iv_header)
    RoundImageView ivHeader;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sale_money)
    TextView tvSaleMoney;
    @BindView(R.id.tv_sale_num)
    TextView tvSaleNum;
    @BindView(R.id.tv_order_more)
    TextView tvOrderMore;
    @BindView(R.id.gv_order)
    GridView gvOrder;
    @BindView(R.id.gv_setting)
    GridView gvSetting;

    private OrderColumnAdapter orderAdapter;
    private SettingColumnAdapter settingAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Drawable defaultHeaderIcon = getResources().getDrawable(R.drawable.user_pic_default);
        GlideApp.with(getActivity()).load(UserInfo.headPic()).placeholder(defaultHeaderIcon).into(ivHeader);

        tvName.setText(UserInfo.nickName());
        tvSaleMoney.setText(String.valueOf(UserInfo.todayOrdersNumber()));
        tvSaleNum.setText(String.valueOf(UserInfo.totalPrice()));
        initAdapter();

    }

    @OnClick({R.id.iv_header, R.id.tv_order_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_header:
                break;
            case R.id.tv_order_more:
                startActivity(new Intent(getActivity(),OrderActivity.class));
                break;

        }
    }
    void initAdapter(){
        orderAdapter = new OrderColumnAdapter(getActivity());
        gvOrder.setAdapter(orderAdapter);
        settingAdapter = new SettingColumnAdapter(getActivity());
        gvSetting.setAdapter(settingAdapter);
    }
}