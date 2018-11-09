package com.juggist.baseandroid.ui.discover.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.view.NoScrollGridView;
import com.juggist.jcore.bean.ArticleBean;
import com.juggist.jcore.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/7 11:35 AM
 */
public class DiscoverAdapter extends BaseAdapter {

    private Context context;
    private Listener listener;
    private ArrayList<ArticleBean> articleBeans;

    public DiscoverAdapter(Context context,Listener listener) {
        this.context = context;
        this.listener = listener;
        articleBeans = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return articleBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return articleBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_discover_item,null);
            vh.iv_header = convertView.findViewById(R.id.iv_header);
            vh.iv_prdocut = convertView.findViewById(R.id.iv_prdocut);
            vh.tv_name = convertView.findViewById(R.id.tv_name);
            vh.tv_time = convertView.findViewById(R.id.tv_time);
            vh.tv_review = convertView.findViewById(R.id.tv_review);
            vh.tv_title = convertView.findViewById(R.id.tv_title);
            vh.tv_content = convertView.findViewById(R.id.tv_content);
            vh.gv_product = convertView.findViewById(R.id.gv_product);
            vh.ll_download = convertView.findViewById(R.id.ll_download);
            vh.ll_share = convertView.findViewById(R.id.ll_share);

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.ll_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.download();
            }
        });
        vh.ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.share();
            }
        });
        final ArticleBean item = articleBeans.get(position);
        Glide.with(context).load(item.getUser_headimg()).into(vh.iv_header);
        vh.tv_name.setText(item.getUser_name());
        vh.tv_time.setText(TimeUtils.millis2String(Long.parseLong(item.getArticle_time()),new SimpleDateFormat("HH:mm")));
        vh.tv_review.setText(item.getLookCount());
        vh.tv_title.setText(item.getArticle_name());
        vh.tv_content.setText(item.getArticle_content());
        vh.gv_product.setVisibility(View.GONE);
        vh.iv_prdocut.setVisibility(View.GONE);
        if(item.getPic_url().size() == 1){
            vh.iv_prdocut.setVisibility(View.VISIBLE);
            Glide.with(context).load(item.getPic_url().get(0)).into(vh.iv_prdocut);
        }else if(item.getPic_url().size() > 1){
            vh.gv_product.setVisibility(View.VISIBLE);
            vh.gv_product.setAdapter(new Adapter(item.getPic_url()));
        }
        vh.iv_prdocut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.toBigPic(item.getPic_url(),0);
            }
        });
        vh.gv_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener != null)
                    listener.toBigPic(item.getPic_url(),position);
            }
        });
        return convertView;
    }

    private static class ViewHolder{
        private ImageView iv_header,iv_prdocut;
        private TextView tv_name,tv_time,tv_review,tv_title,tv_content;
        private NoScrollGridView gv_product;
        private LinearLayout ll_download,ll_share;
    }

    /**
     * 图片适配器
     */
    private class Adapter extends BaseAdapter{
        private ArrayList<String> urls;

        public Adapter(ArrayList<String> urls) {
            this.urls = urls;
        }

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public Object getItem(int position) {
            return urls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView iv = new ImageView(context);
            ViewGroup.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.width = context.getResources().getDimensionPixelOffset(R.dimen.dp_170);
            lp.height = context.getResources().getDimensionPixelOffset(R.dimen.dp_170);
            iv.setLayoutParams(lp);
            Glide.with(context).load(urls.get(position)).into(iv);
            return iv;
        }
    }
    public interface Listener{
        void share();
        void download();
        void toBigPic(ArrayList<String> urls,int position);
    }

    public void update(ArrayList<ArticleBean> articleBeans){
        this.articleBeans.clear();
        this.articleBeans.addAll(articleBeans);
        notifyDataSetChanged();
    }
}
