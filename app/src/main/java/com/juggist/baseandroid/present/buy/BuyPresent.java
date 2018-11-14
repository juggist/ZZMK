package com.juggist.baseandroid.present.buy;

import com.juggist.baseandroid.ui.buy.BuyContract;
import com.juggist.jcore.Constants;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.ShopCarBean;
import com.juggist.jcore.service.ISessionService;
import com.juggist.jcore.service.SessionService;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author juggist
 * @date 2018/11/12 2:13 PM
 */
public class BuyPresent implements BuyContract.Present {
    private static final int maxNum = 99;//最大购买数量
    private static final int minNum = 0;
    private final ExecutorService task;

    private BuyContract.View view;
    private ISessionService sessionService;


    private ArrayList<ShopCarBean> shopCarBeans;
    private ArrayList<ShopCarBean> selectShopCarBeans;


    public BuyPresent(BuyContract.View view) {
        this.view = view;
        view.setPresent(this);
        sessionService = new SessionService();
        shopCarBeans = new ArrayList<>();
        selectShopCarBeans = new ArrayList<>();
        task = Executors.newSingleThreadExecutor();
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
    public void addGoodsNum(int position) {
        //商品不存在
        if(position >= shopCarBeans.size()){
            if(view != null)
                view.updateGoodsNumFail(Constants.ERROR.SHOPCAR_UPDATE_NUM_OUT_OF_LENGTH);
            return;
        }
        ShopCarBean shopCarBean = shopCarBeans.get(position);
        int goods_number = Integer.parseInt(shopCarBean.getGoods_number());
        //购买数量大于预设最大数
        if(goods_number >= maxNum){
            if(view != null)
                view.addGoodsNumMax();
            return;
        }
        updateGoodsNum(shopCarBean.getGoods_id(),String.valueOf(++goods_number));
    }

    @Override
    public void miunsGoodsNum(int position) {
        //商品不存在
        if(position >= shopCarBeans.size()){
            if(view != null)
                view.updateGoodsNumFail(Constants.ERROR.SHOPCAR_UPDATE_NUM_OUT_OF_LENGTH);
            return;
        }
        ShopCarBean shopCarBean = shopCarBeans.get(position);
        int goods_number = Integer.parseInt(shopCarBean.getGoods_number());
        //购买数量大于预设最大数
        if(goods_number <= minNum){
            if(view != null)
                view.minusGoodsNumMin();
            return;
        }
        updateGoodsNum(shopCarBean.getGoods_id(),String.valueOf(--goods_number));
    }

    private void updateGoodsNum(String good_id,String number){
        sessionService.updateShopCar(good_id,number,new ResponseCallback<String>(){

            @Override
            public void onSucceed(String s) {
                if(view != null)
                    view.updateGoodsNumSucceed();
            }

            @Override
            public void onError(String message) {

            }

            @Override
            public void onApiError(String state, String message) {

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
