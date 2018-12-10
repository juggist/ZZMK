package com.juggist.baseandroid.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.utils.ToastUtil;

import androidx.annotation.RequiresApi;

/**
 * @author juggist
 * @date 2018/12/10 2:26 PM
 * 加减按钮控件
 */
@SuppressLint("AppCompatCustomView")
public class AddOrMinusButton extends LinearLayout {
    private TextView tv_num;
    private ImageView iv_add, iv_minus;



    private static final int maxNum = 99;
    private static final int minNum = 1;


    private Listener listener;
    private int position;
    private int stepCount = 0;
    private Thread addOrMinusThread;
    private static final long MAX_INTERVAL = 500;
    private boolean postReady = false;
    private long CURRENT_CLICK_TIME = System.currentTimeMillis();

    public AddOrMinusButton(Context context) {
        super(context);
        init();
    }

    public AddOrMinusButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AddOrMinusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AddOrMinusButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(int position, String num, Listener listener) {
        this.position = position;
        tv_num.setText(num);
        this.listener = listener;
    }

    void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_button_add_or_minus, this);
        iv_add = view.findViewById(R.id.iv_add);
        iv_minus = view.findViewById(R.id.iv_minus);
        tv_num = view.findViewById(R.id.tv_num);
        iv_add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentNum = Integer.parseInt(tv_num.getText().toString());
                if (currentNum < maxNum) {
                    stepCount++;
                    tv_num.setText(String.valueOf(++currentNum));
                    if (listener != null)
                        listener.changeNum(position, currentNum);
                    CURRENT_CLICK_TIME = System.currentTimeMillis();
                    checkThread();
                } else {
                    ToastUtil.showLong(getContext().getResources().getString(R.string.toast_out_of_shop_max_num));
                }
            }
        });
        iv_minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentNum = Integer.parseInt(tv_num.getText().toString());
                if (currentNum > minNum) {
                    stepCount--;
                    tv_num.setText(String.valueOf(--currentNum));
                    if (listener != null)
                        listener.changeNum(position, currentNum);
                    CURRENT_CLICK_TIME = System.currentTimeMillis();
                    checkThread();
                } else {
                    if(listener != null)
                        listener.delete(position);
                }
            }
        });
    }

    private void checkThread() {
        if (addOrMinusThread == null) {
            addOrMinusThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if ((System.currentTimeMillis() - CURRENT_CLICK_TIME) > MAX_INTERVAL) {
                            postReady = true;
                        } else {
                            postReady = false;
                        }
                        if (postReady) {//正常结束
                            CURRENT_CLICK_TIME = Long.MAX_VALUE;
                            if (listener != null)
                                listener.addOrMinusEnd(position,stepCount);
                            stepCount = 0;
                        }
                    }
                }
            });
            addOrMinusThread.start();
        }
    }

    public interface Listener {
        void changeNum(int position, int num);

        void addOrMinusEnd(int position,int stepCount);

        void delete(int position);
    }
}
