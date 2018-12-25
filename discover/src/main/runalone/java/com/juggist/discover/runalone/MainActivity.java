package com.juggist.discover.runalone;


import com.juggist.discover.R;
import com.juggist.discover.ui.DiscoverFragment;
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
        return R.layout.discover_activity_main;
    }

    @Override
    protected void initView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.discover_main,new DiscoverFragment());
        ft.commit();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
