package com.juggist.appmajiad;

import com.juggist.jcore.Constants;
import com.juggist.jcore.CoreInject;
import com.juggist.jcore.MyBaseApplication;
import com.juggist.jcore.service.ext.TokenErrorListener;
import com.juggist.jcore.utils.ActivityUtils;
import com.juggist.jcore.utils.SPUtil;
import com.luojilab.component.componentlib.router.ui.UIRouter;

/**
 * @author juggist
 * @date 2018/12/24 3:47 PM
 */
public class MyApplication extends MyBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //注册token异常事件
        UIRouter.getInstance().registerUI("appmajiad");
        CoreInject.getInstance().registerTokenErrorListener(new TokenError());
    }
    /**
     * token异常回调事件
     */
    class TokenError implements TokenErrorListener {

        @Override
        public void tokenError() {
            //清除缓存信息
            SPUtil.clearObjectFromShare(Constants.SP.USER_INFO);
            //跳转到登录页面，并关闭其他所有页面
            ActivityUtils.finishAllActivities();
            UIRouter.getInstance().openUri(MyBaseApplication.getInstance(),"ZZMK://user/loginOrRegister",null);
        }
    }
}
