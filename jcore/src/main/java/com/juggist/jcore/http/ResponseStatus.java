package com.juggist.jcore.http;

import com.juggist.jcore.base.BaseBean;

/**
 * @author juggist
 * @date 2018/10/30 2:55 PM
 *
 * 判断服务器状态
 */
public class ResponseStatus extends BaseBean {
    private String message;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
