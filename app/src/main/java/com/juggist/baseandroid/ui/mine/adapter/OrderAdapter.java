package com.juggist.baseandroid.ui.mine.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.view.NoScrollRecycleListView;
import com.juggist.jcore.base.BaseUpdateAdapter;
import com.juggist.jcore.bean.OrderBean;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author juggist
 * @date 2018/11/13 4:58 PM
 */
public class OrderAdapter extends BaseUpdateAdapter<OrderBean> {
    private List<OrderBean> data;
    private Context context;

    public OrderAdapter(Context context, int layoutResId, @Nullable List<OrderBean> data) {
        super(layoutResId, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public void update(List<OrderBean> t) {
        this.data.clear();
        this.data.addAll(t);
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        helper.setText(R.id.tv_time, item.getCreate_time())
                .setText(R.id.tv_status, item.getStatus_name())
                .setText(R.id.tv_total_num, "共" + String.valueOf(item.getGoods_list().size()) + "件")
                .setText(R.id.tv_total_money, "￥" + item.getCost())
                .setGone(R.id.btn_order_review, false)//查看订单
                .setGone(R.id.btn_order_option, false)//确认收货
                .setGone(R.id.btn_order_transport, false)//查看物流
                .setGone(R.id.btn_order_delete, false)//删除订单
                .setGone(R.id.btn_order_pay, false)//去付款
                .setGone(R.id.btn_order_refund, false)//退款
                .setGone(R.id.btn_order_refund_about, false)
                .setGone(R.id.ll_status, true)
                .addOnClickListener(R.id.btn_order_review)
                .addOnClickListener(R.id.btn_order_option)
                .addOnClickListener(R.id.btn_order_transport)
                .addOnClickListener(R.id.btn_order_delete)
                .addOnClickListener(R.id.btn_order_pay)
                .addOnClickListener(R.id.btn_order_refund)
                .addOnClickListener(R.id.btn_order_refund_about)
        ;
        ;
        switch (Integer.parseInt(item.getNow_status())) {
            case 0:
                helper.setGone(R.id.ll_status, false);
                break;
            case 1://去付款
                helper.setGone(R.id.btn_order_pay, true);
                break;
            case 2://退款,确认收货
                helper.setGone(R.id.btn_order_refund, true)
                        .setGone(R.id.btn_order_option, true);
                break;
            case 3://退款,确认收货,查看物流
                helper.setGone(R.id.btn_order_refund, true)
                        .setGone(R.id.btn_order_option, true)
                        .setGone(R.id.btn_order_transport, true);
                break;
            case 4://退款,查看物流
                helper.setGone(R.id.btn_order_refund, true)
                        .setGone(R.id.btn_order_transport, true);
                break;
            case 5:
                break;
            case 6:
                String statusName = "";
                switch (item.getRefund_status()) {
                    case 1:
                        statusName = "退款申请中";
                        break;
                    case 2:
                        statusName = "退款完成";
                        break;
                    case 3:
                        statusName = "拒绝退款";
                        break;
                    case 4:
                        statusName = "退货完成";
                        break;
                    case 5:
                        statusName = "物流状态";
                        break;
                    case 6:
                        statusName = "收到货物";
                        break;
                    case 7:
                        statusName = "拒绝退货";
                        break;
                }
                helper.setText(R.id.tv_status, statusName)
                        .setGone(R.id.btn_order_refund_about, true);
                break;
            case 7:
                break;
            case 8://退款
                helper.setGone(R.id.btn_order_refund, true);
                break;
        }
        OrderItemAdapter adapter = new OrderItemAdapter(R.layout.adapter_order_item, new ArrayList<OrderBean.GoodsListBean>());
        NoScrollRecycleListView lv = ((NoScrollRecycleListView) helper.getView(R.id.lv));
        lv.setLayoutManager(new LinearLayoutManager(context));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .color(context.getResources().getColor(R.color.item_bg))
                .sizeResId(R.dimen.dp_20)
                .build());
        lv.setAdapter(adapter);
        adapter.update(item.getGoods_list());
    }
    public String getRefundId(int position){
        return String.valueOf(data.get(position).getRefund_id());
    }
    public String getOrderId(int position){
        return String.valueOf(data.get(position).getOrder_id());
    }
    public OrderBean getOrder(int position){
        return data.get(position);
    }
}

