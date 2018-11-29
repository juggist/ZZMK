package com.juggist.jcore.http;

/**
 * @author juggist
 * @date 2018/11/26 4:41 PM
 * <p>
 * 响应体进度回调接口，用于文件下载进度回调
 */

public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
