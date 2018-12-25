package com.juggist.account.applike;

import com.juggist.account.exposeservice.AccountServiceImp;
import com.juggist.componentservice.account.AccountService;
import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author juggist
 * @date 2018/12/21 5:17 PM
 */
public class AccountAppLike implements IApplicationLike {
    UIRouter uiRouter = UIRouter.getInstance();
    Router router = Router.getInstance();
    @Override
    public void onCreate() {
        uiRouter.registerUI("account");
        router.addService(AccountService.class.getSimpleName(),new AccountServiceImp());
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("account");
        router.removeService(AccountService.class.getSimpleName());
    }
}
