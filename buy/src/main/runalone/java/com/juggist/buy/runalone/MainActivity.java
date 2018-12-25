package com.juggist.buy.runalone;


import com.juggist.buy.R;
import com.juggist.buy.ui.BuyFragment;
import com.juggist.jcore.base.BaseActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author juggist
 * @date 2018/12/18 11:43 AM
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.buy_activity_main;
    }

    @Override
    protected void initView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.buy_main,new BuyFragment());
        ft.commit();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
