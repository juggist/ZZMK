package com.juggist.discover.applike;

import com.juggist.componentservice.discover.DiscoverService;
import com.juggist.discover.exposeservice.DiscoverServiceImp;
import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author juggist
 * @date 2018/12/19 11:21 AM
 */
public class DiscoverAppLike implements IApplicationLike {
    UIRouter uiRouter = UIRouter.getInstance();
    Router router = Router.getInstance();
    @Override
    public void onCreate() {
        uiRouter.registerUI("discover");
        router.addService(DiscoverService.class.getSimpleName(),new DiscoverServiceImp());
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("discover");
        router.removeService(DiscoverService.class.getSimpleName());
    }
}
