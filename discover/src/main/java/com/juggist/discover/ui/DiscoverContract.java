package com.juggist.discover.ui;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;

/**
 * @author juggist
 * @date 2018/11/9 9:58 AM
 */
public class DiscoverContract {
    public interface View extends BaseView<Present> {
        
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
