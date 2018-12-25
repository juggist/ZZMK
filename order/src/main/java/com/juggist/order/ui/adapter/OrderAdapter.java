package com.juggist.order.ui.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.jcore.bean.OrderBean;
import com.juggist.jcore.view.NoScrollRecycleView;
import com.juggist.order.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author juggist
 * @date 2018/11/13 4:58 PM
 */
public class OrderAdapter extends BaseQuickAdapter<OrderBean,BaseViewHolder> {
    private Context context;

    public OrderAdapter(Context context, int layoutResId, @Nullable List<OrderBean> data) {
        super(layoutResId, data);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, OrderBean item) {
        helper.setText(R.id.order_tv_time, item.getCreate_time())
                .setText(R.id.order_tv_status, item.getStatus_name())
                .setText(R.id.order_tv_total_num, "共" + String.valueOf(item.getGoods_list().size()) + "件")
                .setText(R.id.order_tv_total_money, "￥" + item.getCost())
                .setGone(R.id.order_btn_order_review, false)//查看订单
                .setGone(R.id.order_btn_order_option, false)//确认收货
                .setGone(R.id.order_btn_order_transport, false)//查看物流
                .setGone(R.id.order_btn_order_delete, false)//删除订单
                .setGone(R.id.order_btn_order_pay, false)//去付款
                .setGone(R.id.order_btn_order_refund, false)//退款
                .setGone(R.id.order_btn_order_refund_about, false)
                .setGone(R.id.order_ll_status, true)
                .addOnClickListener(R.id.order_btn_order_review)
                .addOnClickListener(R.id.order_btn_order_option)
                .addOnClickListener(R.id.order_btn_order_transport)
                .addOnClickListener(R.id.order_btn_order_delete)
                .addOnClickListener(R.id.order_btn_order_pay)
                .addOnClickListener(R.id.order_btn_order_refund)
                .addOnClickListener(R.id.order_btn_order_refund_about)
        ;
        ;
        switch (Integer.parseInt(item.getNow_status())) {
            case 0:
                helper.setGone(R.id.order_ll_status, false);
                break;
            case 1://去付款
                helper.setGone(R.id.order_btn_order_pay, true);
                break;
            case 2://退款,确认收货
                helper.setGone(R.id.order_btn_order_refund, true)
                        .setGone(R.id.order_btn_order_option, true);
                break;
            case 3://退款,确认收货,查看物流
                helper.setGone(R.id.order_btn_order_refund, true)
                        .setGone(R.id.order_btn_order_option, true)
                        .setGone(R.id.order_btn_order_transport, true);
                break;
            case 4://退款,查看物流
                helper.setGone(R.id.order_btn_order_refund, true)
                        .setGone(R.id.order_btn_order_transport, true);
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
                helper.setText(R.id.order_tv_status, statusName)
                        .setGone(R.id.order_btn_order_refund_about, true);
                break;
            case 7:
                break;
            case 8://退款
                helper.setGone(R.id.order_btn_order_refund, true);
                break;
        }
        OrderItemAdapter adapter = new OrderItemAdapter(R.layout.order_adapter_item, new ArrayList<OrderBean.GoodsListBean>());
        NoScrollRecycleView lv = ((NoScrollRecycleView) helper.getView(R.id.order_lv));
        lv.setLayoutManager(new LinearLayoutManager(context));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context)
                .color(context.getResources().getColor(R.color.item_bg))
                .sizeResId(R.dimen.dp_20)
                .build());
        lv.setAdapter(adapter);
        adapter.update(item.getGoods_list());
    }
}

