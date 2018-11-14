package com.juggist.baseandroid.ui.discover.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.juggist.baseandroid.GlideApp;
import com.juggist.baseandroid.R;
import com.juggist.jcore.base.BaseUpdateAdapter;
import com.juggist.jcore.bean.ArticleBean;
import com.juggist.jcore.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * @author juggist
 * @date 2018/11/7 11:35 AM
 */
public class DiscoverAdapter extends BaseUpdateAdapter<ArticleBean> {

    private Context context;
    private Listener listener;
    private List<ArticleBean> articleBeans;

    public DiscoverAdapter(int layoutResId, @Nullable List<ArticleBean> data,Context context,Listener listener) {
        super(layoutResId, data);
        this.context = context;
        this.listener = listener;
        this.articleBeans = data;
    }



    @Override
    protected void convert(BaseViewHolder helper, final ArticleBean item) {
        GlideApp.with(context).load(item.getUser_headimg()).into((ImageView) helper.getView(R.id.iv_header));
        helper.setText(R.id.tv_name,item.getUser_name())
        .setText(R.id.tv_time,TimeUtils.millis2String(Long.parseLong(item.getArticle_time()),new SimpleDateFormat("HH:mm")))
        .setText(R.id.tv_review,item.getLookCount())
        .setText(R.id.tv_title,item.getArticle_name())
        .setText(R.id.tv_content,item.getArticle_content());
        GridView gv_product = (GridView)helper.getView(R.id.gv_product);
        ImageView iv_prdocut = (ImageView)helper.getView(R.id.iv_prdocut);
        gv_product.setVisibility(View.GONE);
        iv_prdocut.setVisibility(View.GONE);
        if(item.getPic_url().size() == 1){
            iv_prdocut.setVisibility(View.VISIBLE);
            Glide.with(context).load(item.getPic_url().get(0)).into(iv_prdocut);
        }else if(item.getPic_url().size() > 1){
            gv_product.setVisibility(View.VISIBLE);
            gv_product.setAdapter(new Adapter(item.getPic_url()));
        }
        iv_prdocut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.toBigPic(item.getPic_url(),0);
            }
        });
        gv_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener != null)
                    listener.toBigPic(item.getPic_url(),position);
            }
        });
        helper.getView(R.id.ll_download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.download();
            }
        });
        helper.getView(R.id.ll_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null)
                    listener.share();
            }
        });
    }



    @Override
    public void update(List<ArticleBean> t) {
        this.articleBeans.clear();
        this.articleBeans.addAll(t);
        notifyDataSetChanged();
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
            GlideApp.with(context).load(urls.get(position)).into(iv);
            return iv;
        }
    }
    public interface Listener{
        void share();
        void download();
        void toBigPic(ArrayList<String> urls,int position);
    }

}
