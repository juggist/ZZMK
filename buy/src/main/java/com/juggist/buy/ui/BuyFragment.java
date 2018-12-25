package com.juggist.buy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juggist.buy.R;
import com.juggist.buy.R2;
import com.juggist.buy.present.BuyPresent;
import com.juggist.buy.ui.adapter.BuyAdapter;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.bean.OrderCreateTmpBean;
import com.juggist.jcore.bean.ShopCarBean;
import com.juggist.jcore.utils.MyImageLoader;
import com.juggist.jcore.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class BuyFragment extends BaseFragment {

    @BindView(R2.id.buy_lv)
    RecyclerView lv;
    @BindView(R2.id.buy_iv_select)
    ImageView ivSelect;
    @BindView(R2.id.buy_tv_total_money)
    TextView tvTotalMoney;
    @BindView(R2.id.buy_tv_calculate)
    TextView tvCalculate;

    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;

    private BuyAdapter adapter;
    private BuyContract.Present present;


    @Override
    public void onStart() {
        super.onStart();
        present.start();
        Logger.d("start");
    }

    @Override
    public void onDestroyView() {
        present.detach();
        adapter.destory();
        super.onDestroyView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.buy_fragment;
    }

    @Override
    protected void initView(View view) {
        statusView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_net_error,null);
        statusIv = statusView.findViewById(R.id.lv_iv);
        statusTv = statusView.findViewById(R.id.lv_tv);

        lv.setLayoutManager(new LinearLayoutManager(getContext()));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.item_bg))
                .sizeResId(R.dimen.dp_1)
                .build());
    }

    @Override
    protected void initListener() {
        //网络异常，点击屏幕重新加载
        statusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statusTv.getText().toString().equals(getResources().getString(R.string.lv_net_error))){
                    present.start();
                }
            }
        });
    }

    @Override
    protected void initData() {
        initAdapter();
        new BuyPresent(new ViewModel());
    }

    private void initAdapter(){
        adapter = new BuyAdapter(R.layout.buy_adapter_buy_item,new ArrayList<ShopCarBean>(),getActivity(),new AdapterListener());
        adapter.setEmptyView(statusView);
        lv.setAdapter(adapter);
    }
    @OnClick({R2.id.buy_iv_select, R2.id.buy_tv_calculate})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(id == R.id.buy_iv_select){
            adapter.updateSelect(true);
        }else if(id == R.id.buy_tv_calculate){
            List<ShopCarBean> list = adapter.getSelectArray();
            if(list == null || list.size() == 0){
                ToastUtil.showLong(getResources().getString(R.string.buy_toast_choose_product));
            }else{
                present.createTmpOrder(list);
            }
        }
    }

    /**
     * 适配器事件回调
     */
    private class AdapterListener implements BuyAdapter.Listener{

        @Override
        public void updateSelectAll(boolean select) {
            ivSelect.setSelected(select);
        }

        @Override
        public void updateSelectMoney(String money) {
            tvTotalMoney.setText("合计:￥"+money);
        }
    }
    private class ViewModel implements BuyContract.View {

        @Override
        public void queryShopCarEmpty() {
            adapter.update(new ArrayList<ShopCarBean>());
            MyImageLoader.getInstance().loadImage(R.drawable.buy_shoppingcart_pic_default,statusIv);
            statusTv.setText(getActivity().getResources().getString(R.string.buy_lv_shopcar_empty));
        }

        @Override
        public void queryShopCarSucceed(ArrayList<ShopCarBean> shopCarBeans) {
            adapter.update(shopCarBeans);
        }

        @Override
        public void queryShopCarFail(String extMsg) {
            showErrorDialog(extMsg);
            MyImageLoader.getInstance().loadImage(R.drawable.home_pic_nonet,statusIv);
            statusTv.setText(getActivity().getResources().getString(R.string.lv_net_error));
        }

        @Override
        public void crateTmpOrderSucceed(OrderCreateTmpBean orderPreBean) {
            Intent intent = new Intent(getActivity(),OrderSubmitActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("OrderPreBean",orderPreBean);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void crateTmpOrderFail(String extMsg) {
            showErrorDialog(extMsg);
        }

        @Override
        public void setPresent(BuyContract.Present present) {
            BuyFragment.this.present = present;
        }

        @Override
        public void showErrorDialog(String message) {
            ToastUtil.showLong(message);
        }

        @Override
        public void showLoading() {
            MyImageLoader.getInstance().loadImage(R.drawable.buy_shoppingcart_pic_default,statusIv);
            statusTv.setText(getActivity().getResources().getString(R.string.lv_loading));
        }

        @Override
        public void dismissLoading() {

        }
    }
}