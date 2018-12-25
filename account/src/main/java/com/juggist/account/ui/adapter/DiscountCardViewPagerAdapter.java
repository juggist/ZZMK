package com.juggist.account.ui.adapter;

import com.juggist.account.ui.fragment.DiscountCardFragment;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * @author juggist
 * @date 2018/11/15 5:18 PM
 */
public class DiscountCardViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<DiscountCardFragment> fragments;
    public DiscountCardViewPagerAdapter(FragmentManager fm, List<DiscountCardFragment> fragments) {
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
