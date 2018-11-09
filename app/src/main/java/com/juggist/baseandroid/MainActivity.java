package com.juggist.baseandroid;

import android.content.Intent;

import com.juggist.baseandroid.ui.HomeActivity;
import com.juggist.jcore.base.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        startActivity(new Intent(this, HomeActivity.class));
    }


}
