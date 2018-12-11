package com.juggist.baseandroid.present.buy;

import com.juggist.baseandroid.ui.buy.BuyContract;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.ShopCarBean;
import com.juggist.jcore.service.ISessionService;
import com.juggist.jcore.service.SessionService;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/12 2:13 PM
 */
public class BuyPresent implements BuyContract.Present {

    private BuyContract.View view;
    private ISessionService sessionService;


    private ArrayList<ShopCarBean> shopCarBeans;


    public BuyPresent(BuyContract.View view) {
        this.view = view;
        view.setPresent(this);
        sessionService = new SessionService();
        shopCarBeans = new ArrayList<>();
    }

    @Override
    public void queryShopCar() {
        showLoading();
        sessionService.queryShopCar(new ResponseCallback<ArrayList<ShopCarBean>>() {
            @Override
            public void onSucceed(ArrayList<ShopCarBean> shopCarBeans) {
                BuyPresent.this.shopCarBeans.clear();
                BuyPresent.this.shopCarBeans.addAll(shopCarBeans);
                if(view == null)
                    return;
                view.dismissLoading();
                if(shopCarBeans.size() == 0){
                    view.queryShopCarEmpty();
                }else{
                    view.queryShopCarSucceed(shopCarBeans);
                }
            }

            @Override
            public void onError(String message) {
                if(view != null){
                    view.dismissLoading();
                    view.showErrorDialog(message);
                }
            }

            @Override
            public void onApiError(String state, String message) {
                if(view != null){
                    view.dismissLoading();
                    view.queryShopCarFail(message + " : " + state);
                }
            }
        });
    }


    @Override
    public void start() {
        queryShopCar();
    }

    @Override
    public void detach() {
        view = null;
    }

    void showLoading(){
        if(view != null)
            view.showLoading();

    }


}
