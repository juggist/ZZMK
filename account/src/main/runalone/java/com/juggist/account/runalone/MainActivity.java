package com.juggist.account.runalone;


import android.view.View;

import com.juggist.account.R;
import com.juggist.jcore.base.BaseActivity;
import com.luojilab.component.componentlib.router.ui.UIRouter;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author juggist
 * @date 2018/12/18 11:43 AM
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_main;
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

    public void to(View view){
        UIRouter.getInstance().openUri(MainActivity.this,"ZZMK://user/loginOrRegister",null);
    }
}
