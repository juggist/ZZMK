package com.juggist.baseandroid.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.juggist.baseandroid.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @author juggist
 * @date 2018/11/6 5:50 PM
 *
 */
public class DialogDownload extends DialogFragment {

    private ImageView iv_close;
    private Button btn_download;

    private Listener listener;

    public DialogDownload() {
    }

    @SuppressLint("ValidFragment")
    public DialogDownload(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Theme_AppCompat_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_download, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //声明控件
        iv_close = view.findViewById(R.id.iv_close);
        btn_download = view.findViewById(R.id.btn_download);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.startDownload();
                dismiss();
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //注意下面这个方法会将布局的根部局忽略掉，所以需要嵌套一个布局
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.gravity = Gravity.BOTTOM;//改变在屏幕中的位置,如果需要改变上下左右具体的位置，比如100dp，则需要对布局设置margin
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        lp.width = defaultDisplay.getWidth();  //改变宽度
//        lp.height = (int) getResources().getDimension(R.dimen.dp_400);//改变高度

        dialogWindow.setAttributes(lp);

        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {//可以在这拦截返回键啊home键啊事件
                dialog.dismiss();
                return false;
            }
        });
    }

    public interface Listener {
        void startDownload();
    }
}
