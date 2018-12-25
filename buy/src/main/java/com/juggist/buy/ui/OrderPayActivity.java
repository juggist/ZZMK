package com.juggist.buy.ui;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.juggist.buy.R;
import com.juggist.buy.R2;
import com.juggist.jcore.base.BackBaseActivity;
import com.juggist.jcore.bean.OrderCreateBean;

import butterknife.BindView;

public class OrderPayActivity extends BackBaseActivity {

    @BindView(R2.id.buy_tv_order_no)
    TextView tvOrderNo;
    @BindView(R2.id.buy_tv_order_money)
    TextView tvOrderMoney;
    @BindView(R2.id.buy_iv_pay_select)
    CheckBox ivPaySelect;
    @BindView(R2.id.buy_btn_pay)
    Button btnPay;

    private OrderCreateBean orderCreateBean;

    @Override
    protected int getLayoutId() {
        return R.layout.buy_activity_order_pay;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        parseBundle();
        if(orderCreateBean == null)
            return;
        tvOrderNo.setText(orderCreateBean.getSn());
        tvOrderMoney.setText(orderCreateBean.getTotal_price());
    }

    @Override
    protected String getTitile() {
        return null;
    }

    private void parseBundle(){
        if(getIntent() != null){
            orderCreateBean = (OrderCreateBean) getIntent().getSerializableExtra("OrderCreateBean");
        }
    }

}
