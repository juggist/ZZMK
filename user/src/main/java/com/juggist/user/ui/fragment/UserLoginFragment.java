package com.juggist.user.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.juggist.jcore.Constants;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.utils.KeyboardUtils;
import com.juggist.jcore.utils.RegexUtils;
import com.juggist.jcore.utils.ToastUtil;
import com.juggist.user.R;
import com.juggist.user.R2;
import com.juggist.user.present.UserLoginPresent;
import com.juggist.user.ui.UserPasswordForgetActivity;
import com.juggist.user.view.ButtonCountDown;
import com.juggist.jcore.view.ClearEditText;
import com.luojilab.component.componentlib.router.ui.UIRouter;

import butterknife.BindView;
import butterknife.OnClick;


public class UserLoginFragment extends BaseFragment {


    @BindView(R2.id.user_et_phone)
    ClearEditText etPhone;
    @BindView(R2.id.user_et_psw)
    ClearEditText etPsw;
    @BindView(R2.id.user_btn_login)
    Button btnLogin;
    @BindView(R2.id.user_tv_psw_forfet)
    TextView tvPswForfet;
    @BindView(R2.id.user_btn_code_send)
    ButtonCountDown btnCodeSend;



    private UserLoginContract.Present present;


    @Override
    protected int getLayoutId() {
        return R.layout.user_fragment_login;
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        new UserLoginPresent(new ViewModel());
        //初始化计时器
        btnCodeSend.setTag(Constants.SP.COUNT_DOWN_TIME_LOGIN,Constants.CountDownTime.LOGIN_AUTH_CODE,Constants.CountDownTime.COUNT_DOWN_INTERVAL);

    }


    @Override
    public void onResume() {
        super.onResume();
        //解决在onResume里无法唤起软键盘
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtils.showSoftInput(getActivity());
            }
        },500);

    }

    @Override
    public void onPause() {
        super.onPause();
        KeyboardUtils.hideSoftInput(getActivity());
    }

    @Override
    public void onDestroyView() {
        btnCodeSend.destory();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        present.detach();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            KeyboardUtils.hideSoftInput(getActivity());
        } else {
            KeyboardUtils.showSoftInput(getActivity());
        }
    }

    @OnClick({R2.id.user_btn_login, R2.id.user_tv_psw_forfet,R2.id.user_btn_code_send})
    public void onViewClicked(View view) {
        String phone = etPhone.getText().toString();
        String code = etPsw.getText().toString();
        int id = view.getId();
        if(id == R.id.user_btn_login){
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showLong(R.string.common_toast_phone_empty);
                return;
            }
            if (!RegexUtils.isMobileExact(phone)) {
                ToastUtil.showLong(R.string.common_toast_phone_un_right);
                return;
            }
            if (TextUtils.isEmpty(code)) {
                ToastUtil.showLong(R.string.common_toast_psw_un_right);
                return;
            }
            KeyboardUtils.hideSoftInput(getActivity());
            present.login(phone, code);
        }else if(id == R.id.user_tv_psw_forfet){
            startActivity(new Intent(getActivity(), UserPasswordForgetActivity.class));
        }else if(id == R.id.user_btn_code_send){
            if (TextUtils.isEmpty(phone)) {
                ToastUtil.showLong(R.string.common_toast_phone_empty);
                return;
            }
            if (!RegexUtils.isMobileExact(phone)) {
                ToastUtil.showLong(R.string.common_toast_phone_un_right);
                return;
            }
            present.getAuthCode(phone);
            btnCodeSend.start();
        }
    }


        private class ViewModel implements UserLoginContract.View {

        @Override
        public void getAuthCodeSucceed(String authCode) {
            ToastUtil.showLong(R.string.common_toast_auth_code);
        }

        @Override
        public void getAuthCodeFail(String extMsg) {
            btnCodeSend.cancel();
            showErrorDialog(extMsg);
        }

        @Override
        public void loginSucceed() {
            UIRouter.getInstance().openUri(getActivity(),"ZZMK://main/index",null);
            getActivity().finish();
        }

        @Override
        public void loginFail(String extMsg) {
            showErrorDialog(extMsg);
        }

        @Override
        public void setPresent(UserLoginContract.Present present) {
            UserLoginFragment.this.present = present;
        }

        @Override
        public void showErrorDialog(String message) {
            ToastUtil.showLong(message);
        }

        @Override
        public void showLoading() {

        }

        @Override
        public void dismissLoading() {

        }
    }

}