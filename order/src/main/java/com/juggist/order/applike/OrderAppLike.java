package com.juggist.order.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author juggist
 * @date 2018/12/21 3:49 PM
 */
public class OrderAppLike implements IApplicationLike {

    UIRouter uiRouter = UIRouter.getInstance();
    @Override
    public void onCreate() {
        uiRouter.registerUI("order");
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("order");
    }
}
