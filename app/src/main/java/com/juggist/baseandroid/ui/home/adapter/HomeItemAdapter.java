package com.juggist.baseandroid.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.baseandroid.GlideApp;
import com.juggist.baseandroid.R;
import com.juggist.jcore.base.BaseUpdateAdapter;
import com.juggist.jcore.bean.SessionBean;
import com.juggist.jcore.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author juggist
 * @date 2018/11/5 4:48 PM
 */
public class HomeItemAdapter extends BaseUpdateAdapter<SessionBean.DataBean> {
    private Context context;
    private List<SessionBean.DataBean> sessionBeans;
    private LinearLayout.LayoutParams lp;

    public HomeItemAdapter(int layoutResId, @Nullable List<SessionBean.DataBean> data, Context context) {
        super(layoutResId, data);
        this.context = context;
        sessionBeans = data;
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.sp_20);
    }

    @Override
    protected void convert(BaseViewHolder helper, SessionBean.DataBean item) {
        GlideApp.with(context).load(item.getGroup_pic()).into((ImageView) helper.getView(R.id.img_session_icon));
        helper.setText(R.id.tv_session_theme, item.getGroup_name())
                .setText(R.id.tv_session_start_time, item.getSort_number() + "款   " + TimeUtils.millis2String(Long.parseLong(item.getPublic_time()), new SimpleDateFormat("MM-dd")) + "  开场")
                .setText(R.id.tv_session_content, item.getAlt())
                .setText(R.id.tv_session_end_time, TimeUtils.millis2String(TimeUtils.string2Millis(item.getEnd_time()), new SimpleDateFormat("MM-dd HH:mm")))
                .addOnClickListener(R.id.ibtn_session_join)
                .addOnClickListener(R.id.ibtn_session_share);
        updateProductViews((LinearLayout) helper.getView(R.id.ll_views), item.getGoods_list());
    }

    void updateProductViews(LinearLayout parent, List<SessionBean.DataBean.GoodsListBean> items) {
        parent.removeAllViews();
        for (SessionBean.DataBean.GoodsListBean goodsListBean : items) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_session_product, null);
            view.setLayoutParams(lp);
            ImageView iv = view.findViewById(R.id.iv);
            TextView tv = view.findViewById(R.id.tv);
            GlideApp.with(context).load(goodsListBean.getMain_pic()).into(iv);
            tv.setText(String.format("￥%s", goodsListBean.getPrice()));
            parent.addView(view);
        }
    }

    @Override
    public void update(List<SessionBean.DataBean> t) {
        sessionBeans.clear();
        sessionBeans.addAll(t);
        notifyDataSetChanged();
    }
}
