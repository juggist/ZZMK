package com.juggist.buy.applike;

import com.juggist.buy.exposeservice.BuyServiceImp;
import com.juggist.componentservice.buy.BuyService;
import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author juggist
 * @date 2018/12/20 4:08 PM
 */
public class BuyAppLike implements IApplicationLike {
    UIRouter uiRouter = UIRouter.getInstance();
    Router router = Router.getInstance();
    @Override
    public void onCreate() {
        uiRouter.registerUI("buy");
        router.addService(BuyService.class.getSimpleName(),new BuyServiceImp());
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("buy");
        router.removeService(BuyService.class.getSimpleName());
    }
}
