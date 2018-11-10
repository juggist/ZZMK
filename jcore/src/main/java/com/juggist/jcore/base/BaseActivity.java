package com.juggist.jcore.base;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.juggist.jcore.R;
import com.juggist.jcore.utils.DynamicDensityGenerate;

import butterknife.ButterKnife;

/**
 * @author juggist
 * @date 2018/11/1 4:15 PM
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected ImmersionBar immersionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //动态注入denisty，适配ui，放在super前
        DynamicDensityGenerate.setCustomDensity(this,getApplication());
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initNavView();
        initNavListener();
        initNavData();
        initView();
        initListener();
        initData();
        initSystemStatusBar();

    }
    @Override
    public void onBackPressed() {
        this.finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (immersionBar != null)
            immersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initListener();
    protected abstract void initData();

    /**
     * 沉浸式状态栏
     */
    protected void initSystemStatusBar(){
        immersionBar = ImmersionBar.with(this);
        immersionBar.statusBarDarkFont(true, 0.2f).fitsSystemWindows(true,R.color.white).keyboardEnable(true).init();
    }

    /**
     * nav导航栏
     */
    protected void  initNavView(){};
    protected void initNavListener(){};
    protected void initNavData(){};

    /**
     * 点击屏幕空白区域 收起软键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS
                );
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    // Return whether touch the view.
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
}
