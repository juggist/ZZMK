package com.juggist.jcore.base;

import com.google.gson.JsonSyntaxException;
import com.juggist.jcore.Constants;
import com.juggist.jcore.CoreInject;
import com.juggist.jcore.bean.ResponseBean;
import com.juggist.jcore.http.ErrorCode;
import com.juggist.jcore.http.exception.ApiCodeErrorException;
import com.juggist.jcore.service.ext.FilterResponseFunc;
import com.juggist.jcore.service.ext.TokenErrorListener;
import com.orhanobut.logger.Logger;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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
                .observeOn(Schedulers.io())
                .map(new Function<T, T>() {
                    @Override
                    public T apply(T t) throws Exception {
                        if (t != null) {
                            return t;
                        }
                        throw new ApiCodeErrorException(ErrorCode.DATA_NULL);
                    }
                })
                .observeOn(callBackScheduler);
    }

    /**
     * 异常处理分发
     *
     * @param <T> 定义泛型
     */
    protected class ConsumerThrowable<T> implements Consumer<Throwable> {
        ResponseCallback<T> callback;

        public ConsumerThrowable(ResponseCallback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void accept(Throwable throwable) {
            if (callback == null) {
                return;
            }
            if (throwable instanceof ApiCodeErrorException) {
                String state = ((ApiCodeErrorException) throwable).getState();
                String message = ((ApiCodeErrorException) throwable).getMsg();
                Logger.t("ApiCodeErrorException").e("state:%s ; msg:%s ", state, message);
                if (state.equals(Constants.ERROR.TOKEN_ERROR_CODE) && message.equals(Constants.ERROR.TOKEN_ERROR_MSG)) {
                    TokenErrorListener tokenErrorListener = CoreInject.getInstance().getTokenErrorListener();
                    if (tokenErrorListener != null)
                        tokenErrorListener.tokenError();
                }
                callback.onApiError(state, message);
            } else {
                Logger.t("ThrowableException").e(throwable.toString());
                if(throwable instanceof JsonSyntaxException){
                    callback.onError(Constants.ERROR.DATA_GOSN_ERROR);
                }else{
                    callback.onError(throwable.toString());
                }
            }
        }
    }
}
