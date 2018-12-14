package com.juggist.baseandroid.ui.buy;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.baseandroid.GlideApp;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.eventbus.DiscountCardEvent;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.jcore.bean.OrderCreateTmpBean;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class DiscountCardChooseActivity extends BackBaseActivity {

    @BindView(R.id.lv)
    RecyclerView lv;
    private OrderCreateTmpBean orderPreBean;

    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discount_card_choose;
    }

    @Override
    protected void initView() {

        statusView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_net_error,null);
        statusIv = statusView.findViewById(R.id.lv_iv);
        statusTv = statusView.findViewById(R.id.lv_tv);
        statusTv.setText(getResources().getString(R.string.discount_card_empty));

        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.item_bg))
                .sizeResId(R.dimen.dp_20)
                .build());
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        parseBundle();
        initAdapter();
    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.title_discount_card_choose);
    }
    private void parseBundle() {
        if (getIntent() != null) {
            orderPreBean = (OrderCreateTmpBean) getIntent().getSerializableExtra("OrderPreBean");
        }
    }
    private void initAdapter(){
        Adapter adapter =new Adapter(R.layout.adapter_discount_card_item,orderPreBean.getAvailable_coupon());
        lv.setAdapter(adapter);
        adapter.setEmptyView(statusView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(new DiscountCardEvent.UseDiscountCard(orderPreBean.getAvailable_coupon().get(position).getDerate(),position));
                DiscountCardChooseActivity.this.finish();
            }
        });
    }
    private class Adapter extends BaseQuickAdapter<OrderCreateTmpBean.AvailableCouponBean,BaseViewHolder>{

        public Adapter(int layoutResId, @Nullable List<OrderCreateTmpBean.AvailableCouponBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderCreateTmpBean.AvailableCouponBean item) {
            GlideApp.with(DiscountCardChooseActivity.this).load(item.getImage_normal()).into((ImageView) helper.getView(R.id.img));
        }
    }
}
