package com.juggist.jcore.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.juggist.jcore.R;
import com.juggist.jcore.base.BaseActivity;

import androidx.annotation.Nullable;

/**
 * @author juggist
 * @date 2018/11/2 4:34 PM
 * 拥有通用导航栏
 */
public abstract class BackBaseActivity extends BaseActivity {
    public ImageView iv_back,iv_setting;
    public TextView tv_title,tv_right;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    protected void initNavView(){
        iv_back = findViewById(R.id.iv_back);
        iv_setting = findViewById(R.id.iv_setting);
        tv_title = findViewById(R.id.tv_title);
        tv_right = findViewById(R.id.tv_right);
    }
    protected void initNavListener(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    protected void initNavData(){
        tv_title.setText(getTitile() == null ? "":getTitile());
    }
    protected abstract String getTitile();


}
