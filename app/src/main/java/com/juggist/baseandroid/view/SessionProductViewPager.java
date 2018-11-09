package com.juggist.baseandroid.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.juggist.baseandroid.R;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/6 10:24 AM
 *
 * 自动回滚的viewpager
 */
public class SessionProductViewPager extends ViewPager {
    private ArrayList<Object> items;
    private ArrayList<View> views;
    private Context context;
    private PagerAdapter adapter;

    public SessionProductViewPager(@NonNull Context context) {
        super(context);
    }

    public SessionProductViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    void initView(Context context){
        items = new ArrayList<>();
        views = new ArrayList<>();
        this.context = context;
        adapter = new Adapter();
        this.setAdapter(adapter);
        update(items);

    }

    public void update(ArrayList<Object> items){
        this.items.clear();
        this.items.addAll(items);
        this.views.clear();
        for(Object item : items){
            View view = LayoutInflater.from(context).inflate(R.layout.view_session_product,null);
            this.views.add(view);
        }
        adapter.notifyDataSetChanged();

    }

    class Adapter extends PagerAdapter {

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

    }
}
