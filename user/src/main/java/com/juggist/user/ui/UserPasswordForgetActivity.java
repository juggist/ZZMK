package com.juggist.user.ui;

import com.juggist.jcore.base.BackBaseActivity;
import com.juggist.user.R;

public class UserPasswordForgetActivity extends BackBaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_password_forget;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.user_title_psw_forget);
    }
}
