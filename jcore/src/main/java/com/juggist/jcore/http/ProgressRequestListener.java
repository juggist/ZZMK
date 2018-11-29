package com.juggist.jcore.http;

/**
 * @author juggist
 * @date 2018/11/26 4:43 PM
 * 请求体进度回调接口，用于文件上传进度回调
 */

public interface ProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
