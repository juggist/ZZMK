package com.juggist.baseandroid.ui.mine.fragment;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;

/**
 * @author juggist
 * @date 2018/11/15 4:57 PM
 */
public class DiscountCardContract {
    public interface View extends BaseView<Present> {

    }

    public interface Present extends BasePresent {

        void refreshDiscountCardList();

        void loadMoreDiscountCardList();

    }
}
