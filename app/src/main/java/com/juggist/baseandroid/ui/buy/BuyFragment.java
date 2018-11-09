package com.juggist.baseandroid.ui.buy;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.ui.buy.adapter.BuyAdapter;
import com.juggist.jcore.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class BuyFragment extends BaseFragment {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.iv_select)
    ImageView ivSelect;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_calculate)
    TextView tvCalculate;

    private BuyAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buy;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        adapter = new BuyAdapter(getActivity());
        lv.setAdapter(adapter);
    }




    @OnClick({R.id.iv_select, R.id.tv_calculate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_select:
                break;
            case R.id.tv_calculate:
                startActivity(new Intent(getActivity(),OrderSubmitActivity.class));
                break;
        }
    }
}