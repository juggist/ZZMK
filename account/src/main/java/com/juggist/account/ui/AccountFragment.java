package com.juggist.account.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.juggist.account.R;
import com.juggist.account.R2;
import com.juggist.account.ui.adapter.OrderColumnAdapter;
import com.juggist.account.ui.adapter.SettingColumnAdapter;
import com.juggist.account.view.RoundImageView;
import com.juggist.componentservice.TargetBean;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.bean.UserInfo;
import com.juggist.jcore.utils.MyImageLoader;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.luojilab.component.componentlib.service.JsonService;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class AccountFragment extends BaseFragment {

    @BindView(R2.id.account_iv_header)
    RoundImageView ivHeader;
    @BindView(R2.id.account_tv_name)
    TextView tvName;
    @BindView(R2.id.account_tv_sale_money)
    TextView tvSaleMoney;
    @BindView(R2.id.account_tv_sale_num)
    TextView tvSaleNum;
    @BindView(R2.id.account_tv_order_more)
    TextView tvOrderMore;
    @BindView(R2.id.account_gv_order)
    GridView gvOrder;
    @BindView(R2.id.account_gv_setting)
    GridView gvSetting;

    private OrderColumnAdapter orderAdapter;
    private SettingColumnAdapter settingAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.account_fragment;
    }

    @Override
    protected void initView(View view) {
        tvOrderMore.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.dp_20));
    }

    @Override
    protected void initListener() {
        gvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TargetBean target = new TargetBean(position + 1);
                Bundle bundle = new Bundle();
                bundle.putString("target",JsonService.Factory.getSingletonImpl().toJsonString(target));
                UIRouter.getInstance().openUri(getActivity(),"ZZMK://order/list",bundle);
            }
        });
        gvSetting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        TargetBean target = new TargetBean(2);
                        Bundle bundle = new Bundle();
                        bundle.putString("target",JsonService.Factory.getSingletonImpl().toJsonString(target));
                        UIRouter.getInstance().openUri(getActivity(),"ZZMK://address/list",bundle);
                        break;
                    case 2:
                        startActivity(new Intent(getActivity(),DiscountCardActivity.class));
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
        Drawable defaultHeaderIcon = getResources().getDrawable(R.drawable.account_pic_default);
        MyImageLoader.getInstance().loadImage(UserInfo.headPic(),ivHeader);

        tvName.setText(UserInfo.nickName());
        tvSaleMoney.setText(String.valueOf(UserInfo.todayOrdersNumber()));
        tvSaleNum.setText(String.valueOf(UserInfo.totalPrice()));
        initAdapter();

    }

    @OnClick({R2.id.account_iv_header, R2.id.account_tv_order_more})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(id == R.id.account_iv_header){

        }else if(id == R.id.account_tv_order_more){
            TargetBean target = new TargetBean(0);
            Bundle bundle = new Bundle();
            bundle.putString("target",JsonService.Factory.getSingletonImpl().toJsonString(target));
            UIRouter.getInstance().openUri(getActivity(),"ZZMK://order/list",bundle);
        }
    }
    void initAdapter(){
        orderAdapter = new OrderColumnAdapter(getActivity());
        gvOrder.setAdapter(orderAdapter);
        settingAdapter = new SettingColumnAdapter(getActivity());
        gvSetting.setAdapter(settingAdapter);
    }
}