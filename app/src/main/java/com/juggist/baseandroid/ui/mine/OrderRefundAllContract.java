package com.juggist.baseandroid.ui.mine;

import android.content.Intent;
import android.os.Bundle;

import com.juggist.jcore.base.BasePresent;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.bean.OrderBean;

import java.util.List;

/**
 * @author juggist
 * @date 2018/12/4 10:31 AM
 */
public class OrderRefundAllContract {
    public interface View extends BaseView<Present> {

        void updateAdapter(List<OrderBean.GoodsListBean> goodsListBeans);
        void showChoosePicDialog();
        void openCamera(Intent intent);
        void toBigPicView(Bundle bundle);

        void refundDispatchedSucceed();
        void refundDispatchedFail(String msg);
    }

    public interface Present extends BasePresent {
        void openCamera();
        void addPic();
        void clickPic(int position);
        void refundDispatched(String des,String reason);
    }
}
