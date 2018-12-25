package com.juggist.buy.exposeservice;

import com.juggist.buy.ui.BuyFragment;
import com.juggist.componentservice.buy.BuyService;

import androidx.fragment.app.Fragment;

/**
 * @author juggist
 * @date 2018/12/20 4:10 PM
 */
public class BuyServiceImp implements BuyService {
    @Override
    public Fragment getBuyFragment() {
        return new BuyFragment();
    }
}
