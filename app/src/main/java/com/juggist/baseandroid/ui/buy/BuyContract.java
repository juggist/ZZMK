package com.juggist.baseandroid.ui.buy;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.ShopCarBean;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/12 2:12 PM
 */
public class BuyContract {

    public interface View extends BaseView<Present> {

        void queryShopCarEmpty();

        void queryShopCarSucceed(ArrayList<ShopCarBean> shopCarBeans);

        void queryShopCarFail(String extMsg);


        void updateGoodsNumSucceed();
        void addGoodsNumMax();
        void minusGoodsNumMin();

        void updateGoodsNumFail(String extMsg);

    }



    public interface Present extends BasePresent {
        void queryShopCar();
        void addGoodsNum(int position);
        void miunsGoodsNum(int position);

    }
}
