package com.juggist.jcore.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import com.juggist.jcore.R;

import androidx.fragment.app.DialogFragment;

/**
 * loading 加载fragment
 */
public class LoadingDialog extends DialogFragment {



    public LoadingDialog() {
        // Required empty public constructor
    }

    public static LoadingDialog newInstance() {
        LoadingDialog fragment = new LoadingDialog();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Theme_AppCompat_Dialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        //设置dialog背景色不是半透明的
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.0f;
        window.setAttributes(windowParams);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading_dialog, container, false);
        getDialog().getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.loading_bg));
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //注意下面这个方法会将布局的根部局忽略掉，所以需要嵌套一个布局
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.gravity = Gravity.CENTER;//改变在屏幕中的位置,如果需要改变上下左右具体的位置，比如100dp，则需要对布局设置margin
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        lp.width = getResources().getDimensionPixelOffset(R.dimen.dp_250);  //改变宽度
        lp.height = getResources().getDimensionPixelOffset(R.dimen.dp_250);//改变高度

        dialogWindow.setAttributes(lp);

        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {//可以在这拦截返回键啊home键啊事件
                dismiss();
                return false;
            }
        });
    }
}
