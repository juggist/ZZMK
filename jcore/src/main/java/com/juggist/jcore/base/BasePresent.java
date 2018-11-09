package com.juggist.jcore.base;

/**
 * @author juggist
 * @date 2018/11/7 4:28 PM
 */
public interface BasePresent {

    void start();

    /**
     * fragment is destroyed
     */
    void detach();

}
