package com.juggist.jcore.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author juggist
 * @date 2018/12/20 2:28 PM
 */
public class NoScrollRecycleView  extends RecyclerView {
    public NoScrollRecycleView(Context context) {
        super(context);
    }
    public NoScrollRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}