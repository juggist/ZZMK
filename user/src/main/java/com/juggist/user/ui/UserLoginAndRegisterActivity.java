package com.juggist.user.ui;

import android.widget.FrameLayout;

import com.juggist.jcore.base.BaseActivity;
import com.juggist.jcore.view.NavigationTabStrip;
import com.juggist.user.R;
import com.juggist.user.R2;
import com.juggist.user.ui.fragment.UserLoginFragment;
import com.juggist.user.ui.fragment.UserRegisterFragment;
import com.luojilab.router.facade.annotation.RouteNode;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * 登录，注册页面
 */

@RouteNode(path = "/loginOrRegister",desc = "注册或者登陆页面")
public class UserLoginAndRegisterActivity extends BaseActivity {

    @BindView(R2.id.user_nts)
    NavigationTabStrip nts;
    @BindView(R2.id.user_fl_container)
    FrameLayout flContainer;


    private static final int PAGE_LOGIN = 0;
    private static final int PAGE_REGISTER = 1;

    private Fragment fLogin,fRegister;



    protected int getLayoutId() {
        return R.layout.user_activity_login_and_register;
    }

    @Override
    protected void initView() {

    }


    protected void initListener() {
        nts.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {

            }

            @Override
            public void onEndTabSelected(String title, int index) {
                switchFragment(index);
            }
        });
    }

    protected void initData() {
        //初始化数据
        nts.setTabIndex(PAGE_LOGIN);
        switchFragment(PAGE_LOGIN);
    }
    /**
     * 切换fragment页卡
     * @param index
     */
    void switchFragment(int index){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        switch (index){
            case PAGE_LOGIN:
                if(null == fLogin){
                    fLogin = new UserLoginFragment();
                    ft.add(R.id.user_fl_container,fLogin);
                }else{
                    ft.show(fLogin);
                }
                if(null != fRegister){
                    ft.hide(fRegister);
                }
                ft.commit();
                break;
            case PAGE_REGISTER:
                if(null == fRegister){
                    fRegister = new UserRegisterFragment();
                    ft.add(R.id.user_fl_container,fRegister);
                }else{
                    ft.show(fRegister);
                }
                if(null != fLogin){
                    ft.hide(fLogin);
                }
                ft.commit();
                break;
        }
    }


}
