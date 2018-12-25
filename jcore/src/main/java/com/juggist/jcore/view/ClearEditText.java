package com.juggist.jcore.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.juggist.jcore.R;


/**
 * Created by lufeisong on 2017/4/7.
 * 可清空的输入框
 *
 */

@SuppressLint("AppCompatCustomView")
public class ClearEditText extends EditText implements
        View.OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    private String textHint;
    private float textSize;
    private int leftDrawableSelectId ;
    private int leftDrawableNormalId ;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ClearEditText, 0, 0);
        textHint = typeArray.getString(R.styleable.ClearEditText_textHint);
        textSize = typeArray.getFloat(R.styleable.ClearEditText_textSize,26);
        leftDrawableSelectId = typeArray.getResourceId(R.styleable.ClearEditText_textLeftDrawableSelect,0);
        leftDrawableNormalId = typeArray.getResourceId(R.styleable.ClearEditText_textLeftDrawableNormal,0);
        init();
    }


    private void init() {


        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
//          throw new NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(R.drawable.clear_edittext_delete);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
        //新建一个可以添加属性的文本对象
        refreshHint(textHint);

    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        refreshLeftDrawable(visible?false:(getText().length() > 0 ? false:true));
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }


    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public void setTextHint(String hint) {
        refreshHint(hint);
    }

    void refreshHint(String hint) {
        if(hint != null){
            SpannableString ss = new SpannableString(hint);
            // 新建一个属性对象,设置文字的大小
            AbsoluteSizeSpan ass = new AbsoluteSizeSpan((int) textSize, true);
            // 附加属性到文本
            ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.font_normal)), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            // 设置hint
            this.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
        }
    }
    void setText(String text){
        if(text != null){
            SpannableString ss = new SpannableString(text);
            // 新建一个属性对象,设置文字的大小
            AbsoluteSizeSpan ass = new AbsoluteSizeSpan((int) textSize, true);
            // 附加属性到文本
            ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.font_normal)), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            // 设置hint
            this.setText(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
            this.setSelection(getText().length());
        }
    }

    void refreshLeftDrawable(boolean refresh){
        if(refresh){
            if(leftDrawableNormalId != 0){
                Drawable leftDrawableNormal = getResources().getDrawable(leftDrawableNormalId);
                leftDrawableNormal.setBounds(0, 0, leftDrawableNormal.getIntrinsicWidth(), leftDrawableNormal.getIntrinsicHeight());
                setCompoundDrawables(leftDrawableNormal,
                        getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
                setCompoundDrawablePadding(20);
            }
        }else{
            if(leftDrawableSelectId != 0){
                Drawable leftDrawableNormal = getResources().getDrawable(leftDrawableSelectId);
                leftDrawableNormal.setBounds(0, 0, leftDrawableNormal.getIntrinsicWidth(), leftDrawableNormal.getIntrinsicHeight());
                setCompoundDrawables(leftDrawableNormal,
                        getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
                setCompoundDrawablePadding(20);
            }
        }
    }
}