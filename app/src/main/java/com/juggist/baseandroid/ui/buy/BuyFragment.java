package com.juggist.baseandroid.ui.buy;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.present.buy.BuyPresent;
import com.juggist.baseandroid.ui.BaseFragment;
import com.juggist.baseandroid.ui.buy.adapter.BuyAdapter;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.jcore.bean.ShopCarBean;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class BuyFragment extends BaseFragment {

    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.iv_select)
    ImageView ivSelect;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_calculate)
    TextView tvCalculate;


    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;


    private BuyAdapter adapter;
    private BuyContract.Present present;

    @Override
    public void onDestroyView() {
        present.detach();
        adapter.destory();
        super.onDestroyView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_buy;
    }

    @Override
    protected void initView() {
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
        present.queryShopCar();

    }

    private void initAdapter(){
        adapter = new BuyAdapter(R.layout.adapter_buy_item,new ArrayList<ShopCarBean>(),getActivity(),new AdapterListener());
        adapter.setEmptyView(statusView);
        lv.setAdapter(adapter);
    }
    @OnClick({R.id.iv_select, R.id.tv_calculate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_select:
                adapter.updateSelect(true);

                break;
            case R.id.tv_calculate:
                startActivity(new Intent(getActivity(), OrderSubmitActivity.class));
                break;
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
            tvTotalMoney.setText("合计:$"+money);
        }
    }
    private class ViewModel implements BuyContract.View {

        @Override
        public void queryShopCarEmpty() {
            adapter.update(new ArrayList<ShopCarBean>());
            Glide.with(getActivity()).load(getActivity().getResources().getDrawable(R.drawable.shoppingcart_pic_default)).into(statusIv);
            statusTv.setText(getActivity().getResources().getString(R.string.lv_data_empty));
        }

        @Override
        public void queryShopCarSucceed(ArrayList<ShopCarBean> shopCarBeans) {
            adapter.update(shopCarBeans);
        }

        @Override
        public void queryShopCarFail(String extMsg) {
            showErrorDialog(extMsg);
            Glide.with(getActivity()).load(getActivity().getResources().getDrawable(R.drawable.home_pic_nonet)).into(statusIv);
            statusTv.setText(getActivity().getResources().getString(R.string.lv_net_error));
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
            Glide.with(getActivity()).load(getActivity().getResources().getDrawable(R.drawable.shoppingcart_pic_default)).into(statusIv);
            statusTv.setText(getActivity().getResources().getString(R.string.lv_loading));
        }

        @Override
        public void dismissLoading() {

        }
    }
}