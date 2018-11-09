package com.juggist.baseandroid.ui.buy;

import android.widget.ListView;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.baseandroid.ui.buy.adapter.OrderSubmitAdapter;

import butterknife.BindView;

/**
 * 提交订单
 */
public class OrderSubmitActivity extends BackBaseActivity {


    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.tv_total_money02)
    TextView tvTotalMoney02;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_order_submit)
    TextView tvOrderSubmit;

    private OrderSubmitAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_submit;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        adapter = new OrderSubmitAdapter(this);
        lv.setAdapter(adapter);
    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.title_order_submit);
    }


}
