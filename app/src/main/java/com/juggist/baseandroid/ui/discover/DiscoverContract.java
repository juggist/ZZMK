package com.juggist.baseandroid.ui.discover;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;

/**
 * @author juggist
 * @date 2018/11/9 9:58 AM
 */
public class DiscoverContract {
    public interface View extends BaseView<Present> {
        
        void getListEmpty();

        void getListEmptyFail(String extMsg);
        void getListFail(String extMsg,boolean refresh /*true:下拉刷新，false:上拉加载*/);

        void downloadShareSucceed();
        void downloadShareFail(String msg);
    }

    public interface Present extends BasePresent {

        void refreshArticleList();

        void loadMoreArticleList();

        void preparDownload(int position);

        void startDownload();
    }
}
