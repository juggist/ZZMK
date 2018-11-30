package com.juggist.baseandroid.ui.mine.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.present.mine.OrderPresent;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.ui.BaseFragment;
import com.juggist.jcore.bean.OrderBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.OnClick;

/**
 */
public class OrderFragment extends BaseFragment {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.lv_iv)
    ImageView lvIv;
    @BindView(R.id.lv_tv)
    TextView lvTv;
    @BindView(R.id.lv_ll)
    LinearLayout lvLl;


    private static final String ARG_PARAM1 = "position";
    private int position;

    private Drawable defaultHeaderIcon;


    private OrderContract.Present present;
    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance(int position) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_PARAM1);
        }
    }
    @Override
    public void onDestroyView() {
        present.detach();
        super.onDestroyView();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initView() {
        lv.setEmptyView(lvLl);
    }

    @Override
    protected void initListener() {
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                present.loadMoreOrderList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                present.refreshOrderList();
            }
        });
    }

    @Override
    protected void initData() {

        new OrderPresent(new ViewModel(),getCondition());
        present.start();
    }

    /**
     * 根据下标获取condition
     * @return
     */
    private String getCondition(){
        switch (position){
            case 0:
                return "all";
            case 1:
                return "wait_pay";
            case 2:
                return "wait_send";
            case 3:
                return "wait_take";
            case 4:
                return "after_sell";

        }
        return "all";
    }
    @OnClick({R.id.lv_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.lv_iv:
                if(lvTv.getText().toString().equals(getActivity().getResources().getString(R.string.lv_net_error)))
                    present.start();
                break;

        }
    }
    private class ViewModel implements OrderContract.View {

        @Override
        public void getOrderListEmpty() {
//            adapter.update(new ArrayList<ShopCarBean>());
            lvTv.setText(getActivity().getResources().getString(R.string.lv_data_empty));
            Glide.with(getActivity()).load(getActivity().getResources().getDrawable(R.drawable.order_pic_default)).into(lvIv);
        }

        @Override
        public void getOrderListSucceed(List<OrderBean> orderBeans, boolean refresh) {
//            adapter.update(articleBeans);
            if (refresh) {
                srl.finishRefresh();
            } else {
                srl.finishLoadMore();
            }
        }

        @Override
        public void getOrderListSucceedEnd(List<OrderBean> orderBeans, boolean refresh) {
//            adapter.update(articleBeans);
            if (refresh) {
                srl.finishRefresh();
                srl.setNoMoreData(true);
            } else {
                srl.finishLoadMoreWithNoMoreData();
            }
        }

        @Override
        public void getOrderListEmptyFail(String extMsg) {
            showErrorDialog(extMsg);
            srl.finishRefresh();
            lvTv.setText(getResources().getString(R.string.lv_net_error));
            Glide.with(getActivity()).load(getActivity().getResources().getDrawable(R.drawable.order_pic_default)).into(lvIv);
        }

        @Override
        public void getOrderListFail(String extMsg, boolean refresh) {
            showErrorDialog(extMsg);
            if (refresh) {
                srl.finishRefresh();
            } else {
                srl.finishLoadMore();
            }
        }

        @Override
        public void setPresent(OrderContract.Present present) {
            OrderFragment.this.present = present;
        }

        @Override
        public void showErrorDialog(String message) {
            ToastUtil.showLong(message);
        }

        @Override
        public void showLoading() {
            lvTv.setText(getResources().getString(R.string.lv_loading));
            Glide.with(getActivity()).load(getActivity().getResources().getDrawable(R.drawable.order_pic_default)).into(lvIv);
        }

        @Override
        public void dismissLoading() {

        }
    }
}
