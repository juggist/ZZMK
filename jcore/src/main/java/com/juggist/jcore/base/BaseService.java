package com.juggist.jcore.base;

import com.juggist.jcore.bean.ResponseBean;
import com.juggist.jcore.http.exception.ApiCodeErrorException;
import com.juggist.jcore.service.ext.FilterResponseFunc;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author juggist
 * @date 2018/10/31 11:09 AM
 */
public class BaseService {

    /**
     * 获取过滤后的对像
     *
     * @param observable        传入需要过滤的对象
     * @param callBackScheduler 回调所在的线程
     * @param <T>               返回的泛型
     * @return
     */
    protected <T> Observable<T> getFilterResponse(Observable<ResponseBean<T>> observable, Scheduler callBackScheduler) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new FilterResponseFunc<T>())
                .observeOn(callBackScheduler);
    }

    /**
     * 异常处理分发
     * @param <T> 定义泛型
     */
    protected class ConsumerThrowable<T> implements Consumer<Throwable> {
        ResponseCallback<T> callback;

        public ConsumerThrowable(ResponseCallback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void accept(Throwable throwable){
            if (callback == null) {
                return;
            }
            if (throwable instanceof ApiCodeErrorException) {
                Logger.t("ApiCodeErrorException").e("state:%s ; msg:%s ", ((ApiCodeErrorException) throwable).getState(), ((ApiCodeErrorException) throwable).getMsg());
                callback.onApiError(((ApiCodeErrorException) throwable).getState(), ((ApiCodeErrorException) throwable).getMsg());
            } else {
                Logger.t("ThrowableException").e(throwable.toString());
                callback.onError(throwable.toString());
            }
        }
    }
}
