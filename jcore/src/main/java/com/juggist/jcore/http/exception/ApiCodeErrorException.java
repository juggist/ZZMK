package com.juggist.jcore.http.exception;

import android.os.Build;
import androidx.annotation.RequiresApi;

import com.juggist.jcore.http.ErrorCode;

/**
 * @author juggist
 * @date 2018/10/30 2:58 PM
 */
public class ApiCodeErrorException extends RuntimeException{
    private ErrorCode errorCode;
    private String state;
    private String msg;

    public ApiCodeErrorException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.state = errorCode.getCode();
        this.msg = errorCode.getDesc();
    }

    public ApiCodeErrorException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.state = errorCode.getCode();
        this.msg = errorCode.getDesc();
    }

    public ApiCodeErrorException(String message, Throwable cause, ErrorCode errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
        this.state = errorCode.getCode();
        this.msg = errorCode.getDesc();
    }

    public ApiCodeErrorException(Throwable cause, ErrorCode errorCode) {
        super(cause);
        this.errorCode = errorCode;
        this.state = errorCode.getCode();
        this.msg = errorCode.getDesc();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ApiCodeErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
        this.state = errorCode.getCode();
        this.msg = errorCode.getDesc();
    }

    public ApiCodeErrorException(String state, String msg) {
        this.state = state;
        this.msg = msg;
    }

    public ApiCodeErrorException(String message, String state, String msg) {
        super(message);
        this.state = state;
        this.msg = msg;
    }

    public ApiCodeErrorException(String message, Throwable cause, String state, String msg) {
        super(message, cause);
        this.state = state;
        this.msg = msg;
    }

    public ApiCodeErrorException(Throwable cause, String state, String msg) {
        super(cause);
        this.state = state;
        this.msg = msg;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ApiCodeErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String state, String msg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.state = state;
        this.msg = msg;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}
