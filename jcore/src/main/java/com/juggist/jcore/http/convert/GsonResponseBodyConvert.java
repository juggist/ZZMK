package com.juggist.jcore.http.convert;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.juggist.jcore.Constants;
import com.juggist.jcore.http.ErrorCode;
import com.juggist.jcore.http.ResponseStatus;
import com.juggist.jcore.http.exception.ApiCodeErrorException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @author juggist
 * @date 2018/10/30 2:49 PM
 *
 * 自定义json解析器 解析服务器返回值统一判断
 */
public class GsonResponseBodyConvert<T> implements Converter<ResponseBody,T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    private static final Charset UTF8 = Charset.forName("UTF-8");

    public GsonResponseBodyConvert(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    /**
     *
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        if (value.contentLength() == 0) {
            throw new ApiCodeErrorException(ErrorCode.NET_ERROR);
        }
        String response = value.string();
        ResponseStatus responseStatus = gson.fromJson(response, ResponseStatus.class);
        //errorCode !=200 认为是异常
        if (!responseStatus.getStatus().contentEquals(Constants.REQUEST_SUCCESS)) {
            value.close();
            throw new ApiCodeErrorException(TextUtils.isEmpty(responseStatus.getStatus()) ? Constants.REQUEST_FAIL : responseStatus.getStatus(), TextUtils.isEmpty(responseStatus.getMessage()) ? Constants.SERVICE_BUSY : responseStatus.getMessage());
        }
        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }
}
