package com.juggist.user.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author juggist
 * @date 2018/12/24 2:09 PM
 */
public class UserAppLike implements IApplicationLike {
    UIRouter uiRouter = UIRouter.getInstance();
    @Override
    public void onCreate() {
        uiRouter.registerUI("user");
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("user");
    }
}
