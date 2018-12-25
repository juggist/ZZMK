package com.juggist.jcore.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juggist.jcore.R;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @author juggist
 * @date 2018/12/18 10:56 AM
 */

public class StatusView extends LinearLayout {
    private ImageView statusIv;
    private TextView statusTv;

    public StatusView(Context context) {
        super(context);
        init();
    }

    public StatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        LinearLayout view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.view_status,this,true);
        view.setOrientation(VERTICAL);
        view.setGravity(Gravity.CENTER);
        statusIv = view.findViewById(R.id.lv_iv);
        statusTv = view.findViewById(R.id.lv_tv);
    }
    public void setStatus(int strId,int resId){
        setStatus(getResources().getString(strId),resId);
    }
    public void setStatus(String str,int resId){
        setStatusTv(str);
        setStatusIv(resId);
    }

    public void setStatusTv(int strId){
        setStatusTv(getResources().getString(strId));
    }
    public void setStatusTv(String str){
        statusTv.setText(str);
    }
    public void setStatusIv(int resId){
        statusIv.setBackgroundResource(resId);
    }

    public String getStatusTv(){
        return statusTv.getText().toString();
    }
}
