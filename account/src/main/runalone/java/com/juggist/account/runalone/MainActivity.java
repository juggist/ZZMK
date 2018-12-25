package com.juggist.account.runalone;


import android.view.View;

import com.juggist.account.R;
import com.juggist.account.ui.AccountFragment;
import com.juggist.jcore.Constants;
import com.juggist.jcore.base.BaseActivity;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.UserBean;
import com.juggist.jcore.service.IUserService;
import com.juggist.jcore.service.UserService;
import com.juggist.jcore.utils.SPUtil;
import com.luojilab.component.componentlib.router.ui.UIRouter;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * @author juggist
 * @date 2018/12/18 11:43 AM
 */
public class MainActivity extends BaseActivity {
    IUserService userService =  new UserService();
    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        userService.login("13242001948", "123456", new ResponseCallback<UserBean>() {
            @Override
            public void onSucceed(UserBean userBean) {
                SPUtil.saveObjectToShare(Constants.SP.USER_INFO,userBean);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.account_main,new AccountFragment());
                ft.commit();
            }

            @Override
            public void onError(String message) {
            }

            @Override
            public void onApiError(String state, String message) {
            }
        });
    }

    public void to(View view){
        UIRouter.getInstance().openUri(MainActivity.this,"ZZMK://user/loginOrRegister",null);
    }
}
