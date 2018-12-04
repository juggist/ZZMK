package com.juggist.baseandroid.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author juggist
 * @date 2018/11/30 4:05 PM
 */
public class NoScrollRecycleListView extends RecyclerView {
    public NoScrollRecycleListView(Context context) {
        super(context);
    }
    public NoScrollRecycleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
