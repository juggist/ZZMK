package com.juggist.jcore;

import com.juggist.jcore.service.ext.TokenErrorListener;

/**
 * @author juggist
 * @date 2018/12/5 5:21 PM
 */
public class CoreInject {
    private static CoreInject instance;
    private TokenErrorListener tokenErrorListener;

    public synchronized static CoreInject getInstance() {
        if (instance == null) {
            synchronized (CoreInject.class) {
                if (instance == null)
                    instance = new CoreInject();
            }
        }
        return instance;
    }

    public TokenErrorListener getTokenErrorListener() {
        return tokenErrorListener;
    }

    public void setTokenErrorListener(TokenErrorListener tokenErrorListener) {
        this.tokenErrorListener = tokenErrorListener;
    }
}
