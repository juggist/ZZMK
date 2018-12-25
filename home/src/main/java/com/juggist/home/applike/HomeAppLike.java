package com.juggist.home.applike;

import com.juggist.componentservice.home.HomeService;
import com.juggist.home.exposeservice.HomeServiceImp;
import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author juggist
 * @date 2018/12/19 5:13 PM
 */
public class HomeAppLike implements IApplicationLike {
    UIRouter uiRouter = UIRouter.getInstance();
    Router router = Router.getInstance();
    @Override
    public void onCreate() {
        uiRouter.registerUI("home");
        router.addService(HomeService.class.getSimpleName(),new HomeServiceImp());
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("home");
        router.removeService(HomeService.class.getSimpleName());
    }
}
