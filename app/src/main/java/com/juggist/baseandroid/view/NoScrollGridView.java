package com.juggist.baseandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * @author juggist
 * @date 2018/11/6 3:36 PM
 *
 * 解决嵌套在listview内只显示一行的问题
 */
public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context) {
        super(context);
    }
    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
