package com.juggist.user.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import com.juggist.jcore.utils.SPUtil;
import com.juggist.jcore.utils.StringUtil;
import com.juggist.user.R;

import androidx.appcompat.widget.AppCompatButton;

/**
 * @author juggist
 * @date 2018/11/8 9:40 AM
 * 自定义倒计时按钮
 */
public class ButtonCountDown extends AppCompatButton {
    private Context context;
    private String tag;
    private long millisInFuture, countDownInterval;
    private CountDownTime countDownTime;
    private boolean destory = false;//视图是否被摧毁，防止内存泄漏

    public ButtonCountDown(Context context) {
        super(context);
        init(context);
    }

    public ButtonCountDown(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ButtonCountDown(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        this.context = context;
    }

    /**
     * @param tag               每个button 存储的倒计时信息（剩余多少时间）
     * @param millisInFuture    倒计时时长
     * @param countDownInterval 倒计时步长
     */
    public void setTag(String tag, long millisInFuture, long countDownInterval) {
        this.tag = tag;
        this.millisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
        long startTime = SPUtil.getLong(tag);
        long currentTime = System.currentTimeMillis();
        long intervalTime = currentTime - startTime;
        if (intervalTime < millisInFuture) {//间隔时长未超过设定的时长,继续倒计时,加50毫秒是防止计数器不准确bug
            countDownTime = new CountDownTime(millisInFuture - intervalTime + 50, countDownInterval);
            destory = false;
            countDownTime.start();
        }

    }

    public void start() {
        destory = false;
        countDownTime = new CountDownTime(millisInFuture + 50, countDownInterval);
        SPUtil.saveLong(tag, System.currentTimeMillis());
        countDownTime.start();
    }

    public void cancel() {
        //正常操作,重置时间
        destory = false;
        if(countDownTime != null)
            countDownTime.cancel();
        SPUtil.saveLong(tag, 0);
        setEnabled(true);
        setText(getResources().getString(R.string.user_code_send));

    }

    public void destory() {
        destory = true;
        if(countDownTime != null)
            countDownTime.cancel();
        setEnabled(true);
        setText(getResources().getString(R.string.user_code_send));
    }

    /**
     * 倒计时
     */
    class CountDownTime extends CountDownTimer {
        long countDownInterval;
        public CountDownTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.countDownInterval = countDownInterval;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            if (destory) {
                return;
            }
            int lastTime = StringUtil.getInt(millisUntilFinished / (double) countDownInterval);
            setText(String.format("重新获取(%s)", String.valueOf(lastTime)));
            setEnabled(false);
        }

        @Override
        public void onFinish() {
            //只有正常结束才会执行，cancel后不会进入finish
            //重置时间
            SPUtil.saveLong(tag, 0);
            if (destory) {
                return;
            }
            setEnabled(true);
            setText(getResources().getString(R.string.user_code_send));
        }

    }


}
