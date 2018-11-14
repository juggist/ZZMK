package com.juggist.jcore.service;

import com.juggist.jcore.base.BaseService;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.ArticleBean;
import com.juggist.jcore.http.ApiServiceGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * @author juggist
 * @date 2018/11/9 9:56 AM
 */
public class ArticleService extends BaseService implements IArticleService {
    private ArticleServiceApi articleServiceApi;
    public ArticleService() {
        articleServiceApi = ApiServiceGenerator.createService(ArticleServiceApi.class);
    }

    @Override
    public void getArticleList(String page_index, String page_size, final ResponseCallback<List<ArticleBean>> callback) {
        HashMap<String,String> params = new HashMap<>();
        params.put("controller","Article");
        params.put("action","getArticleList");
        params.put("page_index",page_index);
        params.put("page_size",page_size);

        this.getFilterResponse(articleServiceApi.getArticleList(params), AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<ArticleBean>>() {
                    @Override
                    public void accept(ArrayList<ArticleBean> articleBeans) throws Exception {
                        callback.onSucceed(articleBeans);
                    }
                },new ConsumerThrowable<>(callback));
    }
}
