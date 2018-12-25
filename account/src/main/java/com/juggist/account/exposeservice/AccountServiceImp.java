package com.juggist.account.exposeservice;

import com.juggist.account.ui.AccountFragment;
import com.juggist.componentservice.account.AccountService;

import androidx.fragment.app.Fragment;

/**
 * @author juggist
 * @date 2018/12/21 5:32 PM
 */
public class AccountServiceImp implements AccountService {
    @Override
    public Fragment getAccountFragment() {
        return new AccountFragment();
    }
}
