package com.juggist.order.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juggist.jcore.SmartRefreshViewModel;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.bean.OrderBean;
import com.juggist.jcore.utils.ToastUtil;
import com.juggist.order.R;
import com.juggist.order.R2;
import com.juggist.order.present.OrderPresent;
import com.juggist.order.ui.OrderRefundActivity;
import com.juggist.order.ui.OrderRefundAllActivity;
import com.juggist.order.ui.OrderTransportActivity;
import com.juggist.order.ui.adapter.OrderAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 */
public class OrderFragment extends BaseFragment {

    @BindView(R2.id.order_lv)
    RecyclerView lv;
    @BindView(R2.id.order_srl)
    SmartRefreshLayout srl;
    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;

    private static final String ARG_PARAM1 = "position";
    private int position;

    private Drawable defaultHeaderIcon;


    private OrderContract.Present present;
    private OrderAdapter adapter;

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
        return R.layout.order_fragment;
    }

    @Override
    protected void initView(View view) {
        statusView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_net_error, null);
        statusIv = statusView.findViewById(R.id.lv_iv);
        statusTv = statusView.findViewById(R.id.lv_tv);

        lv.setLayoutManager(new LinearLayoutManager(getContext()));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.item_bg))
                .sizeResId(R.dimen.dp_20)
                .build());
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
        //网络异常，点击屏幕重新加载
        statusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusTv.getText().toString().equals(getResources().getString(R.string.lv_net_error))) {
                    present.start();
                }
            }
        });
    }

    @Override
    protected void initData() {
        initAdapter();
        new OrderPresent(new ViewModel(), getCondition());
        present.start();
    }

    /**
     * 根据下标获取condition
     *
     * @return
     */
    private String getCondition() {
        switch (position) {
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

    private void initAdapter() {
        adapter = new OrderAdapter(getActivity(),R.layout.order_adapter, new ArrayList<OrderBean>());
        adapter.setEmptyView(statusView);
        lv.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int id = view.getId();
                if(id == R.id.order_btn_order_refund_about){
                    Intent intent = new Intent(getActivity(),OrderRefundActivity.class);
                    intent.putExtra("refundId",OrderFragment.this.present.getRefundId(position));
                    getActivity().startActivity(intent);
                } else if (id == R.id.order_btn_order_transport) {
                    Intent intent2 = new Intent(getActivity(),OrderTransportActivity.class);
                    intent2.putExtra("orderId",OrderFragment.this.present.getOrderId(position));
                    getActivity().startActivity(intent2);
                }else if(id == R.id.order_btn_order_refund){
                    Intent intent3 = new Intent(getActivity(),OrderRefundAllActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("order",OrderFragment.this.present.getOrder(position));
                    intent3.putExtras(bundle);
                    getActivity().startActivity(intent3);
                }
            }
        });
    }


    private class ViewModel extends SmartRefreshViewModel<OrderBean> implements OrderContract.View {

        @Override
        public void getListEmpty() {
            super.getListEmpty();
            statusTv.setText(getResources().getString(R.string.lv_data_empty));
        }

        @Override
        public void getListEmptyFail(String extMsg) {
            super.getListEmptyFail(extMsg);
            showErrorDialog(extMsg);
            statusTv.setText(getResources().getString(R.string.lv_net_error));
        }

        @Override
        public void getListFail(String extMsg, boolean refresh) {
            super.getListFail(extMsg, refresh);
            showErrorDialog(extMsg);
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
            statusTv.setText(getResources().getString(R.string.lv_loading));
        }

        @Override
        public void dismissLoading() {

        }

        @Override
        public SmartRefreshLayout getSmartRefreshLayout() {
            return srl;
        }

        @Override
        public BaseQuickAdapter getBaseAdapter() {
            return adapter;
        }
    }
}
