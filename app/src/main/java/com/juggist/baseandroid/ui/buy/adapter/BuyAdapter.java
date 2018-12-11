package com.juggist.baseandroid.ui.buy.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.AddOrMinusButton;
import com.juggist.baseandroid.view.AlertDialog;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.ShopCarBean;
import com.juggist.jcore.service.AccountService;
import com.juggist.jcore.service.IAccountService;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author juggist
 * @date 2018/11/7 2:01 PM
 * 购物车适配器
 */
public class BuyAdapter extends BaseQuickAdapter<ShopCarBean,BaseViewHolder> implements AddOrMinusButton.Listener {
    private Context context;
    private IAccountService accountService;
    private Listener listener;

    private List<ShopCarBean> shopCarBeans;
    private List<ShopCarBean> selectArray;
    private HashMap<Integer, List<ShopCarBean>> waitPostMap = new HashMap<>();//请求队列
    private HashMap<Integer, List<Integer>> stepCountMap = new HashMap<>();//对应请求队列的stepcount

    public BuyAdapter(int layoutResId, @Nullable List<ShopCarBean> data, Context context, Listener listener) {
        super(layoutResId, data);
        this.context = context;
        this.listener = listener;
        shopCarBeans = data;
        selectArray = new ArrayList<>();
        accountService = new AccountService();
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShopCarBean item) {
        Glide.with(context).load(item.getMain_pic()).into((ImageView) helper.getView(R.id.iv));
        helper.setText(R.id.tv_content, item.getGoods_name())
                .setText(R.id.tv_price, item.getUser_price());
        if (item.getAttr() != null && item.getAttr().size() > 0) {
            helper.setText(R.id.tv_spes, item.getAttr().get(0).getAttr_name() + ":" + item.getAttr().get(0).getValue());
        }
        if (selectArray.contains(item)) {
            helper.getView(R.id.iv_select).setSelected(true);
        } else {
            helper.getView(R.id.iv_select).setSelected(false);
        }

        helper.getView(R.id.iv_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectArray.contains(item)) {
                    selectArray.remove(item);
                } else {
                    selectArray.add(item);
                }
                updateSelect(false);

            }
        });
        ((AddOrMinusButton) helper.getView(R.id.addOrMinus)).init(helper.getLayoutPosition(), item.getGoods_number(), this);
    }
    public void update(List<ShopCarBean> t) {
        selectArray.clear();
        shopCarBeans.clear();
        shopCarBeans.addAll(t);
        notifyDataSetChanged();
    }

    /**
     * 加减按钮回调事件
     *
     * @param position
     * @param num
     */
    @Override
    public void changeNum(int position, int num) {
        shopCarBeans.get(position).setGoods_number(String.valueOf(num));
        notifyDataSetChanged();
    }

    @Override
    public void addOrMinusEnd(int position, int stepCount) {
        final ShopCarBean item = shopCarBeans.get(position);
        if (waitPostMap.get(position) == null) {
            List<ShopCarBean> list = new ArrayList<>();
            List<Integer> stepCountList = new ArrayList<>();
            list.add(item);
            stepCountList.add(stepCount);
            waitPostMap.put(position, list);
            stepCountMap.put(position, stepCountList);
            postUpdateshopNum(position, stepCount);
        } else {
            waitPostMap.get(position).add(item);
            stepCountMap.get(position).add(stepCount);
        }
    }

    @Override
    public void delete(final int position) {
        final ShopCarBean item = shopCarBeans.get(position);
        if (context != null) {
            new AlertDialog(context).builder()
                    .setTitle(context.getResources().getString(R.string.toast_delete_product))
                    .setNegativeButton(context.getResources().getString(R.string.cancle), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setPositiveButton(context.getResources().getString(R.string.ensure), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            accountService.deleteShop(shopCarBeans.get(position).getGoods_id(), new ResponseCallback<String>() {
                                @Override
                                public void onSucceed(String s) {
                                    shopCarBeans.remove(item);
                                    if (selectArray.contains(item))
                                        selectArray.remove(item);
                                    updateSelect(false);

                                }

                                @Override
                                public void onError(String message) {
                                    ToastUtil.showLong(message);
                                }

                                @Override
                                public void onApiError(String state, String message) {
                                    ToastUtil.showLong(message + " ; " + state);
                                }
                            });
                        }
                    })
                    .show();
        }
    }

    /**
     * 发起更新商品数据的请求
     *
     * @param position
     * @param stepCount
     */
    private void postUpdateshopNum(final int position, final int stepCount) {
        final ShopCarBean item = shopCarBeans.get(position);
        accountService.updateShopNum(item.getGoods_id(), item.getGoods_number(), new ResponseCallback<String>() {
            @Override
            public void onSucceed(String s) {
                updateSelectTotalMoney();
                removeRequest(position);
            }

            @Override
            public void onError(String message) {
                removeRequest(position);
                changeNum(position, Integer.valueOf(item.getGoods_number()) - stepCount);
                ToastUtil.showLong(message);
            }

            @Override
            public void onApiError(String state, String message) {
                removeRequest(position);
                changeNum(position, Integer.valueOf(item.getGoods_number()) - stepCount);
                ToastUtil.showLong(message + " ; " + state);
            }
        });

    }

    /**
     * 请求结束后从map移除
     *
     * @param position
     */
    private void removeRequest(int position) {
        waitPostMap.get(position).remove(0);
        stepCountMap.get(position).remove(0);
        if (waitPostMap.get(position).size() > 0) {
            postUpdateshopNum(position, stepCountMap.get(position).get(0));
        } else {
            waitPostMap.remove(position);
            stepCountMap.remove(position);
        }
    }
    /**
     * 是否全选后者全不选
     *
     * @param all
     */
    public void updateSelect(boolean all) {
        if (selectArray.containsAll(shopCarBeans)) {
            if (all) {
                selectArray.clear();
                if (listener != null)
                    listener.updateSelectAll(false);
            } else {
                if (listener != null)
                    listener.updateSelectAll(true);
            }
        } else {
            if (all) {
                selectArray.addAll(shopCarBeans);
                if (listener != null)
                    listener.updateSelectAll(true);
            } else {
                if (listener != null)
                    listener.updateSelectAll(false);
            }
        }
        notifyDataSetChanged();
        updateSelectTotalMoney();
    }

    /**
     * 刷新总订单金额
     */
    private void updateSelectTotalMoney() {
        float selectTotalMoney = 0;
        for (ShopCarBean item : selectArray) {
            selectTotalMoney = selectTotalMoney + Float.parseFloat(item.getUser_price()) * Integer.parseInt(item.getGoods_number());
        }
        Logger.d("money = " + selectTotalMoney);
        if (listener != null)
            listener.updateSelectMoney(String.valueOf(selectTotalMoney));
    }

    public void destory() {
        listener = null;
        context = null;
    }

    public interface Listener {
        void updateSelectAll(boolean select);

        void updateSelectMoney(String money);
    }

    public List<ShopCarBean> getSelectArray(){
        return selectArray;
    }
}
