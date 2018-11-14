package com.juggist.baseandroid.ui.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juggist.baseandroid.R;
import com.juggist.jcore.bean.SessionBean;
import com.juggist.jcore.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author juggist
 * @date 2018/11/5 4:48 PM
 */
public class HomeItemAdapter extends BaseAdapter {
    private Context context;
    private OnClickListener listener;
    private ArrayList<SessionBean.DataBean> sessionBeans;
    private LinearLayout.LayoutParams lp;

    public HomeItemAdapter(Context context,OnClickListener listener) {
        this.context = context;
        this.listener = listener;
        sessionBeans = new ArrayList<>();
        lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.sp_20);
    }

    @Override
    public int getCount() {
        return sessionBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return sessionBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_home_item,null);
            vh.iv_session_icon = convertView.findViewById(R.id.img_session_icon);
            vh.tv_session_theme = convertView.findViewById(R.id.tv_session_theme);
            vh.tv_session_start_time = convertView.findViewById(R.id.tv_session_start_time);
            vh.tv_session_content = convertView.findViewById(R.id.tv_session_content);
            vh.ll_views = convertView.findViewById(R.id.ll_views);
            vh.tv_session_end_time = convertView.findViewById(R.id.tv_session_end_time);
            vh.ibtn_session_join = convertView.findViewById(R.id.ibtn_session_join);
            vh.ibtn_session_share = convertView.findViewById(R.id.ibtn_session_share);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        final SessionBean.DataBean item = sessionBeans.get(position);
        Glide.with(context).load(item.getGroup_pic()).into(vh.iv_session_icon);
        vh.tv_session_theme.setText(item.getGroup_name());
        vh.tv_session_start_time.setText(item.getSort_number() + "款   " + TimeUtils.millis2String(Long.parseLong(item.getPublic_time()),new SimpleDateFormat("MM-dd")) + "  开场");
        vh.tv_session_content.setText(item.getAlt());
        vh.tv_session_end_time.setText(TimeUtils.millis2String(TimeUtils.string2Millis(item.getEnd_time()),new SimpleDateFormat("MM-dd HH:mm")));

        updateProductViews(vh.ll_views,item.getGoods_list());

        vh.ibtn_session_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.toSessionActivity(position);
            }
        });
        return convertView;
    }

    void updateProductViews(LinearLayout parent, List<SessionBean.DataBean.GoodsListBean> items){
        parent.removeAllViews();
        for(SessionBean.DataBean.GoodsListBean goodsListBean : items){
            View view = LayoutInflater.from(context).inflate(R.layout.view_session_product,null);
            view.setLayoutParams(lp);
            ImageView iv = view.findViewById(R.id.iv);
            TextView tv = view.findViewById(R.id.tv);
            Glide.with(context).load(goodsListBean.getMain_pic()).into(iv);
            tv.setText(String.format("￥%s",goodsListBean.getPrice()));
            parent.addView(view);
        }
    }
    private static class ViewHolder{
        private ImageView iv_session_icon;
        private TextView tv_session_theme,tv_session_start_time,tv_session_content,tv_session_end_time;
        private ImageButton ibtn_session_join,ibtn_session_share;
        private LinearLayout ll_views;
    }
    public interface OnClickListener{
        void toSessionActivity(int position);
    }
    public void update(ArrayList<SessionBean.DataBean> sessionBeans){
        this.sessionBeans.clear();
        this.sessionBeans.addAll(sessionBeans);
        notifyDataSetChanged();
    }
}
