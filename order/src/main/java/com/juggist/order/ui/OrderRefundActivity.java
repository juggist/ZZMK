package com.juggist.order.ui;

import android.view.View;
import android.widget.TextView;

import com.juggist.jcore.base.BackBaseActivity;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.OrderRefundBean;
import com.juggist.jcore.service.AccountService;
import com.juggist.jcore.service.IAccountService;
import com.juggist.jcore.utils.TimeUtils;
import com.juggist.jcore.view.AlertDialog;
import com.juggist.order.R;
import com.juggist.order.R2;

import java.text.SimpleDateFormat;

import butterknife.BindView;

public class OrderRefundActivity extends BackBaseActivity {

    @BindView(R2.id.order_tv_refunde_about)
    TextView tvRefundeAbout;
    @BindView(R2.id.order_tv_order_no)
    TextView tvOrderNo;
    @BindView(R2.id.order_tv_order_time)
    TextView tvOrderTime;
    @BindView(R2.id.order_tv_order_money)
    TextView tvOrderMoney;
    @BindView(R2.id.order_tv_refunde_progress)
    TextView tvRefundeProgress;
    @BindView(R2.id.order_tv_order_progress)
    TextView tvOrderProgress;
    @BindView(R2.id.order_tv_order_answer)
    TextView tvOrderAnswer;


    private IAccountService accountService;
    private String refundId;

    @Override
    protected int getLayoutId() {
        return R.layout.order_activity_refund;
    }

    @Override
    protected void initView() {
        tvRefundeAbout.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.dp_20));
        tvRefundeProgress.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.dp_20));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        parseBundle();
        accountService = new AccountService();
        getData();
    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.order_title_refund);
    }

    private void parseBundle() {
        refundId = getIntent().getStringExtra("refundId");
    }

    private void getData() {
        showLoading();
        accountService.getOrderRefundDetail(refundId, new ResponseCallback<OrderRefundBean>() {
            @Override
            public void onSucceed(OrderRefundBean orderRefundBean) {
                dismissLoading();
                tvOrderNo.setText(orderRefundBean.getSn());
                tvOrderTime.setText(TimeUtils.millis2String(Long.parseLong(orderRefundBean.getApply_time()), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
                tvOrderMoney.setText(orderRefundBean.getRefund_money());
                tvOrderAnswer.setText(orderRefundBean.getReply_content());
                String status = "";
                switch (Integer.parseInt(orderRefundBean.getNow_status())) {
                    case 1:
                        status = "当前进度:退货/退款申请成功,平台正在处理";
                        break;
                    case 2:
                        status = "当前进度:退货/退款已完成";
                        break;
                    case 3:
                        status = "当前进度:退货/退款申请已被拒绝";
                        break;
                    case 4:
                        status = "当前进度:同意退货,请将货品寄回";
                        break;
                    case 5:
                        status = "当前进度:货物已发出";
                        break;
                    case 6:
                        status = "当前进度:已收到货品,平台将退款";
                        break;
                    case 7:
                        status = "当前进度:退货请求已被拒绝";
                        break;
                }
                tvOrderProgress.setText(status);
            }

            @Override
            public void onError(String message) {
                dismissLoading();
                showFailDialog();
            }

            @Override
            public void onApiError(String state, String message) {
                dismissLoading();
                showFailDialog();

            }
        });
    }

    private void showFailDialog() {
        new AlertDialog(this).builder()
                .setTitle(getResources().getString(R.string.order_refund_detail_get_fail_title))
                .setMsg(getResources().getString(R.string.order_refund_detail_get_fail_msg))
                .setNegativeButton(getResources().getString(R.string.common_quit), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OrderRefundActivity.this.finish();
                    }
                })
                .setPositiveButton(getResources().getString(R.string.common_ensure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getData();
                    }
                })
                .show();
    }
}
