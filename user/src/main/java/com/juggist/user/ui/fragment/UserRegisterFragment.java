package com.juggist.user.ui.fragment;

import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.juggist.jcore.Constants;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.utils.KeyboardUtils;
import com.juggist.jcore.utils.RegexUtils;
import com.juggist.jcore.utils.ToastUtil;
import com.juggist.user.R;
import com.juggist.user.R2;
import com.juggist.user.present.UserRegisterPresent;
import com.juggist.user.view.ButtonCountDown;
import com.juggist.jcore.view.ClearEditText;
import com.juggist.user.view.DialogInviteCodeToGet;

import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;


public class UserRegisterFragment extends BaseFragment {

    @BindView(R2.id.user_et_phone)
    ClearEditText etPhone;
    @BindView(R2.id.user_btn_code_send)
    ButtonCountDown btnCodeSend;
    @BindView(R2.id.user_et_code)
    ClearEditText etCode;
    @BindView(R2.id.user_cb)
    CheckBox cb;
    @BindView(R2.id.user_btn_register)
    Button btnRegister;


    private UserRegisterContract.Present present;
    @Override
    protected int getLayoutId() {
        return R.layout.user_fragment_register;
    }

    @Override
    protected void initView(View view) {

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
    @OnClick({R2.id.user_btn_code_send, R2.id.user_tv_inviteCode_to_get, R2.id.user_btn_register})
    public void onViewClicked(View view) {
        String phone = etPhone.getText().toString();
        String code = etCode.getText().toString();
        int id = view.getId();
        if(id == R.id.user_btn_code_send){
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
        }else if(id == R.id.user_tv_inviteCode_to_get){
            DialogInviteCodeToGet dictg = new DialogInviteCodeToGet();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            dictg.show(ft,"dictg");
        }else if(id == R.id.user_btn_register){
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
            if(!cb.isChecked()){
                ToastUtil.showLong(R.string.user_toast_protocol_check);
                return;
            }
            KeyboardUtils.hideSoftInput(getActivity());
            present.register(phone, code);
        }
    }

    private class ViewModel implements UserRegisterContract.View{

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
        public void registerSucceed() {
//            startActivity(new Intent(getActivity(), HomeActivity.class));
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