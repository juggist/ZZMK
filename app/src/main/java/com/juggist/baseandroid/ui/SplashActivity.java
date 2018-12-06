package com.juggist.baseandroid.ui;

import android.content.Intent;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.ui.user.UserLoginAndRegisterActivity;
import com.juggist.jcore.Constants;
import com.juggist.jcore.bean.UserInfo;
import com.juggist.jcore.utils.SPUtil;

/**
 *
 * 测试账号
 * 17718397309
 * 123456
 */
public class SplashActivity extends BaseActivity{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if(SPUtil.getBoolean(Constants.SP.APP_ALREADY_OPEN)){
            if(UserInfo.isLogin()){
                startActivity(new Intent(this,HomeActivity.class));
            }else{
                startActivity(new Intent(this,UserLoginAndRegisterActivity.class));
            }
        }else{
            SPUtil.saveboolean(Constants.SP.APP_ALREADY_OPEN,true);
        }
        this.finish();
    }

    @Override
    protected void initSystemStatusBar() {
        super.initSystemStatusBar();
    }


}
