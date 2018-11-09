package com.juggist.jcore.base;

/**
 * @author juggist
 * @date 2018/10/31 11:37 AM
 * 拿到服务器结果后回调接口
 */
public interface ResponseCallback<T> {
    void onSucceed(T t);
    void onError(String message);
    void onApiError(String state,String message);

}
