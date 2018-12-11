package com.juggist.baseandroid.ui.buy;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.baseandroid.GlideApp;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.eventbus.DiscountCardEvent;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.jcore.bean.OrderPreBean;
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
    private OrderPreBean orderPreBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_discount_card_choose;
    }

    @Override
    protected void initView() {

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
            orderPreBean = (OrderPreBean) getIntent().getSerializableExtra("OrderPreBean");
        }
    }
    private void initAdapter(){
        Adapter adapter =new Adapter(R.layout.adapter_discount_card_item,orderPreBean.getAvailable_coupon());
        lv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                EventBus.getDefault().post(new DiscountCardEvent.UseDiscountCard(orderPreBean.getAvailable_coupon().get(position).getDerate()));
                DiscountCardChooseActivity.this.finish();
            }
        });
    }
    private class Adapter extends BaseQuickAdapter<OrderPreBean.AvailableCouponBean,BaseViewHolder>{

        public Adapter(int layoutResId, @Nullable List<OrderPreBean.AvailableCouponBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, OrderPreBean.AvailableCouponBean item) {
            GlideApp.with(DiscountCardChooseActivity.this).load(item.getImage_normal()).into((ImageView) helper.getView(R.id.img));
        }
    }
}
