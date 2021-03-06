package com.juggist.home.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.home.R;
import com.juggist.jcore.bean.SessionBean;
import com.juggist.jcore.utils.MyImageLoader;
import com.juggist.jcore.utils.TimeUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yqritc.recyclerviewflexibledivider.VerticalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.juggist.jcore.bean.SessionBean.DataBean.ITEM_HOT_BRAND;
import static com.juggist.jcore.bean.SessionBean.DataBean.ITEM_HOT_RECOMMED;
import static com.juggist.jcore.bean.SessionBean.DataBean.ITEM_NORMAL;
import static com.juggist.jcore.bean.SessionBean.DataBean.ITEM_TOP;

/**
 * @author juggist
 * @date 2018/11/5 4:48 PM
 */
public class HomeItemAdapter extends BaseMultiItemQuickAdapter<SessionBean.DataBean,BaseViewHolder> {
    private Context context;
    private List<SessionBean.DataBean> sessionBeans;
    private LinearLayout.LayoutParams lp;

    private Listener listener;

    public HomeItemAdapter(List<SessionBean.DataBean> data, Context context, Listener listener) {
        super(data);
        this.listener = listener;
        this.context = context;
        sessionBeans = data;
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.sp_20);
        addItemType(ITEM_TOP, R.layout.home_adapter_home_item_type_one);
        addItemType(ITEM_HOT_BRAND, R.layout.home_adapter_home_item_type_two);
        addItemType(ITEM_HOT_RECOMMED, R.layout.home_adapter_home_item_type_three);
        addItemType(ITEM_NORMAL, R.layout.home_adapter_home_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, SessionBean.DataBean item) {
        switch (item.getItemType()) {
            case ITEM_TOP:
                helper.setText(R.id.home_tv_title, item.getGroup_name());
                MyImageLoader.getInstance().loadImage(item.getGroup_pic(),(ImageView) helper.getView(R.id.home_iv));
                RecyclerView lv_top = helper.getView(R.id.home_lv);
                lv_top.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                //解决重复添加itemDecorationbug
                int itemDecorationCount = lv_top.getItemDecorationCount();
                if(itemDecorationCount == 0){
                     lv_top.addItemDecoration(new VerticalDividerItemDecoration.Builder(context).color(context.getResources().getColor(R.color.white)).sizeResId(R.dimen.dp_30).build());
                 }
                lv_top.setAdapter(new AdapterItemOne(R.layout.home_adapter_home_item_type_one_item, item.getGoods_list()));
                break;
            case ITEM_HOT_BRAND:
                RecyclerView lv_hot_brand = helper.getView(R.id.home_lv);
                lv_hot_brand.setLayoutManager(new GridLayoutManager(context, 4));
                //解决重复添加itemDecorationbug
                int itemDecorationCount2 = lv_hot_brand.getItemDecorationCount();
                if(itemDecorationCount2 == 0){
                    lv_hot_brand.addItemDecoration(new VerticalDividerItemDecoration.Builder(context).color(context.getResources().getColor(R.color.item_bg)).sizeResId(R.dimen.dp_0_5).build());
                    lv_hot_brand.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).color(context.getResources().getColor(R.color.item_bg)).sizeResId(R.dimen.dp_0_5).build());
                }
                lv_hot_brand.setAdapter(new AdapterItemTwo(R.layout.home_adapter_home_item_type_two_item, item.getSpecialData()));
                break;
            case ITEM_HOT_RECOMMED:
                RecyclerView lv_recommend = helper.getView(R.id.home_lv);
                lv_recommend.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
                //解决重复添加itemDecorationbug
                int itemDecorationCount3 = lv_recommend.getItemDecorationCount();
                if(itemDecorationCount3 == 0){
                    lv_recommend.addItemDecoration(new HorizontalDividerItemDecoration.Builder(context).color(context.getResources().getColor(R.color.item_bg)).sizeResId(R.dimen.dp_1).build());
                }
                lv_recommend.setAdapter(new AdapterItemThree(R.layout.home_adapter_home_item_type_three_item, item.getGoods_list(), helper.getLayoutPosition() - getHeaderLayoutCount()));
                break;
            case ITEM_NORMAL:
                MyImageLoader.getInstance().loadImage(item.getGroup_pic(),(ImageView) helper.getView(R.id.home_img_session_icon));
                helper.setText(R.id.home_tv_session_theme, item.getGroup_name())
                        .setText(R.id.home_tv_session_start_time, item.getSort_number() + "款   " + TimeUtils.millis2String(Long.parseLong(item.getPublic_time()), new SimpleDateFormat("MM-dd")) + "  开场")
                        .setText(R.id.home_tv_session_content, item.getAlt())
                        .setText(R.id.home_tv_session_end_time, TimeUtils.millis2String(TimeUtils.string2Millis(item.getEnd_time()), new SimpleDateFormat("MM-dd HH:mm")))
                        .addOnClickListener(R.id.home_ibtn_session_join)
                        .addOnClickListener(R.id.home_ibtn_session_share);
                updateProductViews((LinearLayout) helper.getView(R.id.home_ll_views), item.getGoods_list());
                break;
        }
    }

    void updateProductViews(LinearLayout parent, List<SessionBean.DataBean.GoodsListBean> items) {
        parent.removeAllViews();
        for (SessionBean.DataBean.GoodsListBean goodsListBean : items) {
            View view = LayoutInflater.from(context).inflate(R.layout.home_view_session_product, null);
            view.setLayoutParams(lp);
            ImageView iv = view.findViewById(R.id.home_iv);
            TextView tv = view.findViewById(R.id.home_tv);
            MyImageLoader.getInstance().loadImage(goodsListBean.getMain_pic(),iv);
            tv.setText(String.format("￥%s", goodsListBean.getPrice()));
            parent.addView(view);
        }

    }

    class AdapterItemOne extends BaseQuickAdapter<SessionBean.DataBean.GoodsListBean, BaseViewHolder> {

        public AdapterItemOne(int layoutResId, @Nullable List<SessionBean.DataBean.GoodsListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SessionBean.DataBean.GoodsListBean item) {
            MyImageLoader.getInstance().loadImage(item.getMain_pic(),(ImageView) helper.getView(R.id.home_iv));
            helper.setText(R.id.home_tv_sale_money, item.getShoppe())
                    .setText(R.id.home_tv_money, item.getPrice());
            ((TextView) helper.getView(R.id.home_tv_money)).setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    class AdapterItemTwo extends BaseQuickAdapter<SessionBean.DataBean, BaseViewHolder> {

        public AdapterItemTwo(int layoutResId, @Nullable List<SessionBean.DataBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, final SessionBean.DataBean item) {
            helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.itemRecommendClick(item.getGroup_name(), item.getId()
                        );
                }
            });
            MyImageLoader.getInstance().loadImage(item.getGroup_pic(),(ImageView) helper.getView(R.id.home_iv));

        }
    }

    class AdapterItemThree extends BaseQuickAdapter<SessionBean.DataBean.GoodsListBean, BaseViewHolder> {

        int position;

        public AdapterItemThree(int layoutResId, @Nullable List<SessionBean.DataBean.GoodsListBean> data, int position) {
            super(layoutResId, data);
            this.position = position;
        }

        @Override
        protected void convert(final BaseViewHolder helper, SessionBean.DataBean.GoodsListBean item) {
            helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.itemRecommendClick(position);
                }
            });
            MyImageLoader.getInstance().loadImage(item.getMain_pic(),(ImageView) helper.getView(R.id.home_iv));
            helper.setText(R.id.home_tv_sale_money, item.getShoppe())
                    .setText(R.id.home_tv_money, item.getPrice())
                    .setText(R.id.home_tv_name, item.getGoods_name());
            ((TextView) helper.getView(R.id.home_tv_money)).setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    public interface Listener {
        void itemRecommendClick(int position);

        void itemRecommendClick(String groupName, String id);
    }
}
