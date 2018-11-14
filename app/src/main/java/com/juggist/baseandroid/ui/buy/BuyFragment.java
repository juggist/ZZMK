package com.juggist.baseandroid.ui.buy;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.present.buy.BuyPresent;
import com.juggist.baseandroid.ui.buy.adapter.BuyAdapter;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.bean.ShopCarBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class BuyFragment extends BaseFragment {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.iv_select)
    ImageView ivSelect;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_calculate)
    TextView tvCalculate;
    @BindView(R.id.lv_iv)
    ImageView lvIv;
    @BindView(R.id.lv_tv)
    TextView lvTv;
    @BindView(R.id.lv_ll)
    LinearLayout lvLl;

    private BuyAdapter adapter;
    private BuyContract.Present present;

    @Override
    public void onDestroyView() {
        present.detach();
        super.onDestroyView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buy;
    }

    @Override
    protected void initView() {
        lv.setEmptyView(lvLl);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        new BuyPresent(new ViewModel());
        initAdapter();
        present.queryShopCar();

    }

    private void initAdapter(){
        adapter = new BuyAdapter(getActivity());
        lv.setAdapter(adapter);
    }
    @OnClick({R.id.iv_select, R.id.tv_calculate,R.id.lv_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_select:
                break;
            case R.id.tv_calculate:
                startActivity(new Intent(getActivity(), OrderSubmitActivity.class));
                break;
            case R.id.lv_iv:
                if(lvTv.getText().toString().equals(getActivity().getResources().getString(R.string.lv_net_error)))
                    present.queryShopCar();
                break;

        }
    }

    private class ViewModel implements BuyContract.View {

        @Override
        public void queryShopCarEmpty() {
            adapter.update(new ArrayList<ShopCarBean>());
            Glide.with(getActivity()).load(getActivity().getResources().getDrawable(R.drawable.shoppingcart_pic_default)).into(lvIv);
            lvTv.setText(getActivity().getResources().getString(R.string.lv_data_empty));
        }

        @Override
        public void queryShopCarSucceed(ArrayList<ShopCarBean> shopCarBeans) {
            adapter.update(shopCarBeans);
        }

        @Override
        public void queryShopCarFail(String extMsg) {
            showErrorDialog(extMsg);
            Glide.with(getActivity()).load(getActivity().getResources().getDrawable(R.drawable.home_pic_nonet)).into(lvIv);
            lvTv.setText(getActivity().getResources().getString(R.string.lv_net_error));
        }

        @Override
        public void updateGoodsNumSucceed() {

        }

        @Override
        public void addGoodsNumMax() {

        }

        @Override
        public void minusGoodsNumMin() {

        }

        @Override
        public void updateGoodsNumFail(String extMsg) {

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
            Glide.with(getActivity()).load(getActivity().getResources().getDrawable(R.drawable.shoppingcart_pic_default)).into(lvIv);
            lvTv.setText(getActivity().getResources().getString(R.string.lv_loading));
        }

        @Override
        public void dismissLoading() {

        }
    }
}