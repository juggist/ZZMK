package com.juggist.baseandroid.utils;

import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juggist.baseandroid.R;
import com.juggist.jcore.MyBaseApplication;

/**
 * @author juggist
 * @date 2018/11/8 1:51 PM
 * 自定义Toast
 * 为了适配今日头条方案
 * 第三方库与今日头条方案结合有bug
 */
public class ToastUtil {
    public static void showLong(String msg){
        Toast toast = Toast.makeText(MyBaseApplication.getInstance(), msg, Toast.LENGTH_LONG);
        LinearLayout layout = (LinearLayout) toast.getView();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = (int)MyBaseApplication.getInstance(). getResources().getDimension(R.dimen.dp_20);
        lp.rightMargin = (int) MyBaseApplication.getInstance().getResources().getDimension(R.dimen.dp_20);
        lp.bottomMargin = (int) MyBaseApplication.getInstance().getResources().getDimension(R.dimen.dp_9);
        lp.topMargin = (int) MyBaseApplication.getInstance().getResources().getDimension(R.dimen.dp_9);
        lp.gravity = Gravity.CENTER;
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setLayoutParams(lp);
        v.setTextColor(Color.WHITE);
        v.setTextSize(MyBaseApplication.getInstance().getResources().getDimension(R.dimen.sp_18));
        toast.show();
    }

    public static void showLong(int id){
        showLong(MyBaseApplication.getInstance().getResources().getString(id));
    }
}
