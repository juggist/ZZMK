package com.juggist.main.runalone;


import android.view.View;

import com.juggist.jcore.base.BaseActivity;
import com.juggist.main.R;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.orhanobut.logger.Logger;

/**
 * @author juggist
 * @date 2018/12/18 11:43 AM
 */
public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        findViewById(R.id.main_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("click");
                UIRouter.getInstance().openUri(MainActivity.this,"ZZMK://user/loginOrRegister",null);
            }
        });
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(new Intent(MainActivity.this,SplashActivity.class));
//            }
//        },2000);
    }
}
