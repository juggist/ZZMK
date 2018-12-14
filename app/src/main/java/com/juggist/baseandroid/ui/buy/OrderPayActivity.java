package com.juggist.baseandroid.ui.buy;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.jcore.bean.OrderCreateBean;

import butterknife.BindView;

public class OrderPayActivity extends BackBaseActivity {

    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.iv_pay_select)
    CheckBox ivPaySelect;
    @BindView(R.id.btn_pay)
    Button btnPay;

    private OrderCreateBean orderCreateBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_pay;
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
