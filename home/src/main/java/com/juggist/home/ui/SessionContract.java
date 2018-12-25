package com.juggist.home.ui;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.ShopCarBean;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/9 5:09 PM
 */
public class SessionContract {
    public interface View extends BaseView<Present> {
        
        void downloadShareSucceed();
        void downloadShareFail(String msg);

        void queryShopCarSucceed(ArrayList<ShopCarBean> shopCarBeans);

        void queryShopCarFail(String extMsg);

        void addShopCarSucceed(String msg);

        void addShopCarFail(String extMsg);


    }

    public interface Present extends BasePresent {

        void refreshOnSellProductsList();

        void loadMoreOnSellProductsList();

        void preparDownload(int position);

        void startDownload();

        void getShopCar();

        void addShop(int position,int num);
    }
}
