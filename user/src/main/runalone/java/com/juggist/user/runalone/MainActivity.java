package com.juggist.user.runalone;


import com.juggist.jcore.base.BaseActivity;
import com.juggist.user.R;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author juggist
 * @date 2018/12/18 11:43 AM
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_main;
    }

    @Override
    protected void initView() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.commit();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
