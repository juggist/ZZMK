package com.juggist.baseandroid.ui.user;

import android.widget.FrameLayout;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.ui.user.fragment.UserLoginFragment;
import com.juggist.baseandroid.ui.user.fragment.UserRegisterFragment;
import com.juggist.baseandroid.view.NavigationTabStrip;
import com.juggist.jcore.base.BaseActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * 登录，注册页面
 */

public class UserLoginAndRegisterActivity extends BaseActivity {

    @BindView(R.id.nts)
    NavigationTabStrip nts;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;


    private static final int PAGE_LOGIN = 0;
    private static final int PAGE_REGISTER = 1;

    private Fragment fLogin,fRegister;



    protected int getLayoutId() {
        return R.layout.activity_user_login_and_register;
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
                    ft.add(R.id.fl_container,fLogin);
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
                    ft.add(R.id.fl_container,fRegister);
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
