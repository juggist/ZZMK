package com.juggist.jcore.service.ext;

import com.juggist.jcore.Constants;
import com.juggist.jcore.bean.ResponseBean;
import com.juggist.jcore.http.exception.ApiCodeErrorException;

import io.reactivex.functions.Function;

/**
 * @author juggist
 * @date 2018/10/31 11:01 AM
 * 服务器返回response过滤器
 */
public class FilterResponseFunc<T> implements Function<ResponseBean<T>,T> {


    @Override
    public T apply(ResponseBean<T> tResponseBean){
        if(tResponseBean.getStatus().contentEquals(Constants.REQUEST_SUCCESS)){
            return tResponseBean.getData();
        }else{
            throw new ApiCodeErrorException(tResponseBean.getStatus(),tResponseBean.getMessage());
        }

    }
}
