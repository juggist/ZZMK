package com.juggist.discover.exposeservice;

import com.juggist.componentservice.discover.DiscoverService;
import com.juggist.discover.ui.DiscoverFragment;

import androidx.fragment.app.Fragment;

/**
 * @author juggist
 * @date 2018/12/19 11:23 AM
 */
public class DiscoverServiceImp implements DiscoverService {
    @Override
    public Fragment getDiscoverFragment() {
        return new DiscoverFragment();
    }
}
