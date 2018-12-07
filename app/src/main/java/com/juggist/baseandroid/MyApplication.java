package com.juggist.baseandroid;

import com.juggist.baseandroid.ui.user.UserLoginAndRegisterActivity;
import com.juggist.baseandroid.utils.ActivityUtils;
import com.juggist.jcore.Constants;
import com.juggist.jcore.CoreInject;
import com.juggist.jcore.MyBaseApplication;
import com.juggist.jcore.service.ext.TokenErrorListener;
import com.juggist.jcore.utils.SPUtil;

/**
 * @author juggist
 * @date 2018/10/31 10:54 AM
 */
public class MyApplication extends MyBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //注册token异常事件
        CoreInject.getInstance().registerTokenErrorListener(new TokenError());

    }
    /**
     * token异常回调事件
     */
    class TokenError implements TokenErrorListener{

        @Override
        public void tokenError() {
            //清除缓存信息
            SPUtil.clearObjectFromShare(Constants.SP.USER_INFO);
            //跳转到登录页面，并关闭其他所有页面
            ActivityUtils.startActivityWithFinishOthers(UserLoginAndRegisterActivity.class);
        }
    }

}
