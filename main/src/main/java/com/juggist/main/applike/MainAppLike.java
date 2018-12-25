package com.juggist.main.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author juggist
 * @date 2018/12/24 3:43 PM
 */
public class MainAppLike implements IApplicationLike {
    UIRouter uiRouter = UIRouter.getInstance();
    @Override
    public void onCreate() {
        uiRouter.registerUI("main");
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("main");
    }
}
