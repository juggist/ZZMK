package com.juggist.address.applike;

import com.luojilab.component.componentlib.applicationlike.IApplicationLike;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author juggist
 * @date 2018/12/20 4:54 PM
 */
public class AddressAppLike implements IApplicationLike {
    UIRouter uiRouter = UIRouter.getInstance();
    @Override
    public void onCreate() {
        uiRouter.registerUI("address");
    }

    @Override
    public void onStop() {
        uiRouter.unregisterUI("address");
    }
}
