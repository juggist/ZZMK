package com.juggist.jcore.base;

/**
 * @author juggist
 * @date 2018/11/7 4:28 PM
 */
public interface BaseView<T> {

    void setPresent(T present);

    void showErrorDialog(String message);

    void showLoading();

    void dismissLoading();
}
