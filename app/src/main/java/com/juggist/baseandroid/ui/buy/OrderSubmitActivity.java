package com.juggist.baseandroid.ui.buy;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.eventbus.DiscountCardEvent;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.baseandroid.ui.buy.adapter.OrderSubmitAdapter;
import com.juggist.baseandroid.view.NoScrollRecycleListView;
import com.juggist.jcore.bean.OrderPreBean;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;

/**
 * 提交订单
 */
public class OrderSubmitActivity extends BackBaseActivity {


    @BindView(R.id.lv)
    NoScrollRecycleListView lv;
    @BindView(R.id.rl_discount_card)
    RelativeLayout rlDiscountCard;
    @BindView(R.id.tv_discount_card)
    TextView tvDiscountCard;
    @BindView(R.id.tv_total_money02)
    TextView tvTotalMoney02;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_total_num)
    TextView tvTotalNum;
    @BindView(R.id.tv_order_submit)
    TextView tvOrderSubmit;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;
    @BindView(R.id.ll_add_address)
    LinearLayout llAddAddress;

    private OrderPreBean orderPreBean;
    private OrderSubmitAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_submit;
    }

    @Override
    protected void initView() {
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.item_bg))
                .sizeResId(R.dimen.dp_1)
                .build());

        SpannableString spannableString = new SpannableString(getResources().getString(R.string.address_add_two));
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.font_select));
        spannableString.setSpan(colorSpan,8,spannableString.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvAddAddress.setText(spannableString);
    }

    @Override
    protected void initListener() {
        llAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rlDiscountCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSubmitActivity.this,DiscountCardChooseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("OrderPreBean",orderPreBean);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        parseBundle();
        initAddress();
        initAdapter();
        initDiscountCard("");
        initCalculate(0);
    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.title_order_submit);
    }

    private void parseBundle() {
        if (getIntent() != null) {
            orderPreBean = (OrderPreBean) getIntent().getSerializableExtra("OrderPreBean");
        }
    }

    /**
     * 初始化地址
     */
    private void initAddress() {
        OrderPreBean.AddressBean addressBean = orderPreBean.getAddress();
        if (addressBean != null) {
            tvName.setText(String.format(getResources().getString(R.string.address_user_name), addressBean.getConsignee()));
            tvAddress.setText(addressBean.getProvince_name() + addressBean.getCity_name() + addressBean.getAreas_name() + addressBean.getAddr());
            tvPhone.setText(addressBean.getCellphone());
            llAddAddress.setVisibility(View.GONE);
        } else {
            llAddAddress.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new OrderSubmitAdapter(R.layout.adapter_order_submit_item,orderPreBean.getGoods_list(),this);
        lv.setAdapter(adapter);
    }
    /*
     *初始化优惠券
     */
    private void initDiscountCard(String content){
        tvDiscountCard.setText(!TextUtils.isEmpty(content)?content : orderPreBean.getAvailable_coupon() == null?"0张可用":orderPreBean.getAvailable_coupon().size() + "张可用");
    }

    /**
     * 初始化计算
     */
    private void initCalculate(float unlimit){
        tvTotalNum.setText("共" + orderPreBean.getGoods_list().size() + "件商品");
        tvTotalMoney02.setText("￥"+ (Float.parseFloat(orderPreBean.getTotal_price()) - unlimit) );
        tvTotalMoney.setText("￥"+(Float.parseFloat(orderPreBean.getTotal_price()) - unlimit));
    }

    /**
     *
     * EvenBus
     * 使用优惠券
     * @param event
     */
    @Subscribe
    public void useDiscount(DiscountCardEvent.UseDiscountCard event){
        initCalculate(Float.parseFloat(event.getUnlimit()));
        initDiscountCard(event.getUnlimit() + "元优惠券");
    }
}
