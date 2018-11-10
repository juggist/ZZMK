package com.juggist.jcore.http.interceptor;

import android.os.Build;
import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.juggist.jcore.utils.EncryptUtil;

import java.io.IOException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author juggist
 * @date 2018/10/30 3:54 PM
 *
 * 全局拦截器
 * 对参数进行加密
 */
public class EncryptionInterceptor implements Interceptor {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Map<String, String> requestMap = new HashMap<>();
        List<String> keys = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        FormBody b = (FormBody) request.body();
        for (int i = 0; i < b.size(); i++) {
            requestMap.put(b.name(i), b.value(i));
            keys.add(b.name(i));

        }
        Collections.sort(keys,new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Collator collator = Collator.getInstance(java.util.Locale.CHINA);
                return collator.getCollationKey(o1).compareTo(collator.getCollationKey(o2));
            }
        });
        for(String key : keys){
            sb.append(key);
        }
        sb.append("d1bf8585d5ea4a6e21740632d2efce4c");
        String param = EncryptUtil.DES(new Gson().toJson(requestMap).toString());
        String sign = EncryptUtil.MD5(sb.toString());
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        bodyBuilder.add("param",param);
        bodyBuilder.add("sign",sign);
        request = request.newBuilder().post(bodyBuilder.build()).build();
        return chain.proceed(request);
    }
}

