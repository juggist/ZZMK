package com.juggist.baseandroid.ui.user.fragment;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.present.user.UserRegisterPresent;
import com.juggist.baseandroid.ui.BaseFragment;
import com.juggist.baseandroid.ui.HomeActivity;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.ButtonCountDown;
import com.juggist.baseandroid.view.ClearEditText;
import com.juggist.baseandroid.view.DialogInviteCodeToGet;
import com.juggist.jcore.Constants;
import com.juggist.jcore.utils.KeyboardUtils;
import com.juggist.jcore.utils.RegexUtils;

import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;


public class UserRegisterFragment extends BaseFragment {

    @BindView(R.id.et_phone)
    ClearEditText etPhone;
    @BindView(R.id.btn_code_send)
    ButtonCountDown btnCodeSend;
    @BindView(R.id.et_code)
    ClearEditText etCode;
    @BindView(R.id.cb)
    CheckBox cb;
    @BindView(R.id.btn_register)
    Button btnRegister;


    private UserRegisterContract.Present present;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        new UserRegisterPresent(new ViewModel());
        //初始化计时器
        btnCodeSend.setTag(Constants.SP.COUNT_DOWN_TIME_REGISTER, Constants.CountDownTime.REGISTER_AUTH_CODE,Constants.CountDownTime.COUNT_DOWN_INTERVAL);
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
    @OnClick({R.id.btn_code_send, R.id.tv_inviteCode_to_get, R.id.btn_register})
    public void onViewClicked(View view) {
        String phone = etPhone.getText().toString();
        String code = etCode.getText().toString();
        switch (view.getId()) {
            case R.id.btn_code_send:
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showLong(R.string.toast_login_phone_empty);
                    return;
                }
                if (!RegexUtils.isMobileExact(phone)) {
                    ToastUtil.showLong(R.string.toast_login_phone_un_right);
                    return;
                }
                present.getAuthCode(phone);
                btnCodeSend.start();
                break;
            case R.id.tv_inviteCode_to_get:
                DialogInviteCodeToGet dictg = new DialogInviteCodeToGet();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                dictg.show(ft,"dictg");
                break;
            case R.id.btn_register:
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showLong(R.string.toast_login_phone_empty);
                    return;
                }
                if (!RegexUtils.isMobileExact(phone)) {
                    ToastUtil.showLong(R.string.toast_login_phone_un_right);
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showLong(R.string.toast_auth_code_empty);
                    return;
                }
                if(!cb.isChecked()){
                    ToastUtil.showLong(R.string.toast_protocol_check);
                    return;
                }
                KeyboardUtils.hideSoftInput(getActivity());
                present.register(phone, code);
                break;
        }
    }

    private class ViewModel implements UserRegisterContract.View{

        @Override
        public void getAuthCodeSucceed(String authCode) {
            ToastUtil.showLong(R.string.toast_auth_code);
        }

        @Override
        public void getAuthCodeFail(String extMsg) {
            btnCodeSend.cancel();
            showErrorDialog(extMsg);
        }

        @Override
        public void registerSucceed() {
            startActivity(new Intent(getActivity(), HomeActivity.class));
            getActivity().finish();
        }

        @Override
        public void registerFail(String extMsg) {
            showErrorDialog(extMsg);
        }

        @Override
        public void setPresent(UserRegisterContract.Present present) {
            UserRegisterFragment.this.present = present;
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