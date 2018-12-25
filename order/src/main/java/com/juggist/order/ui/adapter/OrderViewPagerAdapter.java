package com.juggist.order.ui.adapter;


import com.juggist.order.ui.fragment.OrderFragment;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * @author juggist
 * @date 2018/11/13 3:46 PM
 */
public class OrderViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<OrderFragment> fragments;

    public OrderViewPagerAdapter(FragmentManager fm, List<OrderFragment> fragments) {
        super(fm);
        this.fragments = fragments;
        fm.beginTransaction().commitAllowingStateLoss();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
