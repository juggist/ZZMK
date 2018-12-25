package com.juggist.main.ui;

import android.content.Intent;

import com.juggist.jcore.Constants;
import com.juggist.jcore.base.BaseActivity;
import com.juggist.jcore.bean.UserInfo;
import com.juggist.jcore.utils.SPUtil;
import com.juggist.main.R;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.luojilab.router.facade.annotation.RouteNode;

/**
 *
 * 测试账号
 * 17718397309
 *
 * 13242001948
 * 123456
 */
@RouteNode(path = "/splash",desc = "导航页面")
public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_splash;
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
                UIRouter.getInstance().openUri(this,"ZZMK://user/loginOrRegister",null);
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
