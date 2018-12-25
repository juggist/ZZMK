package com.juggist.jcore.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juggist.jcore.view.AlertDialog;
import com.juggist.jcore.view.LoadingDialog;
import com.juggist.jcore.R;
import com.juggist.jcore.utils.AppUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author juggist
 * @date 2018/11/1 5:14 PM
 */
public abstract class BaseFragment  extends Fragment {
    Unbinder unbinder;
    private LoadingDialog loadingDialog;
    private boolean destory = false;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(),null);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initListener();
        initData();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        destory = true;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract int getLayoutId();
    protected abstract void initView(View view);
    protected abstract void initListener();
    protected abstract void initData();

    /**
     * loading
     */
    protected void showLoading() {
        loadingDialog = LoadingDialog.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        loadingDialog.show(ft, "loadingDialog");
    }

    protected void dismissLoading() {
        if (loadingDialog != null)
            loadingDialog.dismiss();
    }


    /**
     * 请求保存图片
     * WRITE_EXTERNAL_STORAGE
     * READ_EXTERNAL_STORAGE
     * 权限拒绝
     */
    protected void showPermissionSaveShareBitmapFail() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog(getActivity()).builder()
                        .setTitle(getResources().getString(R.string.save_bitmap_permission_fail))
                        .setMsg(getResources().getString(R.string.save_bitmap_permission_todo))
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton("去设置", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转到自己app设置页面
                                AppUtil.toSetting(getActivity());
                            }
                        })
                        .show();
            }
        });
    }
    protected void onApiError(String state, String message){
        if(state.equals("302") && message.equals("token错误!")){
//            if(!destory && getActivity() != null)
//                ((BaseActivity)getActivity()).gotoActivity(UserLoginAndRegisterActivity.class);
        }
    }

}
