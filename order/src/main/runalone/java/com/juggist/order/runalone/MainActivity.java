package com.juggist.order.runalone;


import com.juggist.jcore.base.BaseActivity;
import com.juggist.order.R;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author juggist
 * @date 2018/12/18 11:43 AM
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.order_activity_main;
    }

    @Override
    protected void initView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
//        ft.add(R.id.order_main,new DiscoverFragment());
        ft.commit();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
