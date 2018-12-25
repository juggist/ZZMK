package com.juggist.home.exposeservice;

import com.juggist.componentservice.home.HomeService;
import com.juggist.home.ui.HomeFragment;

import androidx.fragment.app.Fragment;

/**
 * @author juggist
 * @date 2018/12/19 5:14 PM
 */
public class HomeServiceImp implements HomeService {
    @Override
    public Fragment getHomeFragment() {
        return new HomeFragment();
    }
}
