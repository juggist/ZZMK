package com.juggist.order.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.jcore.base.BackBaseActivity;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.OrderTransportBean;
import com.juggist.jcore.service.AccountService;
import com.juggist.jcore.service.IAccountService;
import com.juggist.order.R;
import com.juggist.order.R2;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class OrderTransportActivity extends BackBaseActivity {

    @BindView(R2.id.order_lv)
    RecyclerView lv;

    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;

    private Adapter adapter;
    private IAccountService accountService;
    private String orderId;

    @Override
    protected int getLayoutId() {
        return R.layout.order_activity_transport;
    }

    @Override
    protected void initView() {
        statusView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_net_error,null);
        statusIv = statusView.findViewById(R.id.lv_iv);
        statusTv = statusView.findViewById(R.id.lv_tv);

        lv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initListener() {
        //网络异常，点击屏幕重新加载
        statusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statusTv.getText().toString().equals(getResources().getString(R.string.lv_net_error))){
                    getData();
                }
            }
        });
    }

    @Override
    protected void initData() {
        accountService = new AccountService();
        parseBundle();
        initAdapter();
        getData();
    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.order_title_transport);
    }

    void initAdapter() {
        adapter = new Adapter(R.layout.order_adapter_transport_item,new ArrayList<OrderTransportBean.TracesBean>());
        adapter.setEmptyView(statusView);
        lv.setAdapter(adapter);
    }

    private void parseBundle(){
        orderId = getIntent().getStringExtra("orderId");
    }
    private void getData(){
        showLoading();
        statusTv.setText(getResources().getString(R.string.lv_loading));
        accountService.getOrderTransport(orderId, new ResponseCallback<OrderTransportBean>() {
            @Override
            public void onSucceed(OrderTransportBean orderTransportBean) {
                dismissLoading();
                if(orderTransportBean.getTraces() == null || orderTransportBean.getTraces().size() == 0){
                    statusTv.setText(getResources().getString(R.string.lv_data_empty));
                }else{
                    adapter.update(orderTransportBean.getTraces());
                }
            }

            @Override
            public void onError(String message) {
                dismissLoading();
                statusTv.setText(message);
            }

            @Override
            public void onApiError(String state, String message) {
                dismissLoading();
                statusTv.setText(message);
            }
        });
    }

    class Adapter extends BaseQuickAdapter<OrderTransportBean.TracesBean,BaseViewHolder> {

        private List<OrderTransportBean.TracesBean> data;
        public Adapter(int layoutResId, @Nullable List<OrderTransportBean.TracesBean> data) {
            super(layoutResId, data);
            Adapter.this.data = data;
        }


        public void update(List<OrderTransportBean.TracesBean> t) {
            Adapter.this.data.clear();
            Adapter.this.data.addAll(t);
            notifyDataSetChanged();
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderTransportBean.TracesBean item) {
            helper.setText(R.id.order_tv,item.getAcceptStation() + "\n" + item.getAcceptTime());
        }
    }
}
