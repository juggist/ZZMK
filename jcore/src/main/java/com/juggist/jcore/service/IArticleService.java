package com.juggist.jcore.service;

import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.ArticleBean;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/9 9:55 AM
 */
public interface IArticleService {
    void getArticleList(String page_index, String page_size, ResponseCallback<ArrayList<ArticleBean>> callback);
}
