package com.juggist.baseandroid.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.juggist.baseandroid.R;
import com.juggist.jcore.Constants;
import com.juggist.jcore.bean.ProductBean;
import com.juggist.jcore.utils.CreateBitmapByViewUtil;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * @author juggist
 * @date 2018/11/23 1:37 PM
 */
public class CreateShareView extends LinearLayout {
    private Context context;
    private TextView tvSessionName, tvSessionWeight, tvSessionNo, tvSessionNewPrice, tvSessionCounterPrice;
    private GridView gv;

    public CreateShareView(Context context) {
        super(context);
        init(context);
    }

    public CreateShareView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CreateShareView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.view_create_share, null);
        tvSessionName = view.findViewById(R.id.tv_session_name);
        tvSessionWeight = view.findViewById(R.id.tv_session_weight);
        tvSessionNo = view.findViewById(R.id.tv_session_no);
        tvSessionNewPrice = view.findViewById(R.id.tv_session_new_price);
        tvSessionCounterPrice = view.findViewById(R.id.tv_session_counter_price);
        gv = view.findViewById(R.id.gv);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(lp);
        addView(view);
    }

    private int adapterCount = 0;
    ProductBean.DataBean.GoodsListBean item;

    public void setData(ProductBean.DataBean.GoodsListBean tmpItem,int addPrice) {
        item = tmpItem;
        tvSessionName.setText(item.getGoods_name());
        ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean> spes = (ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean>) item.getAttr();
        if (spes != null && spes.size() > 0) {
            ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean.ValueBean> valus = (ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean.ValueBean>) spes.get(0).getValue();
            if (valus != null && valus.size() > 0) {
                tvSessionWeight.setText(spes.get(0).getAttrname() + ":" + valus.get(0).getContent());
            }
        }
        tvSessionNo.setText("货号:" + item.getSn());
        tvSessionNewPrice.setText("活动价:" + (Float.parseFloat(item.getWholesale_price()) + addPrice));
        tvSessionCounterPrice.setText(item.getShoppe() == null ? "" : "专柜价:￥" + item.getShoppe());
        final ArrayList<String> mainPics = (ArrayList<String>) item.getMain_pic();
        if (mainPics != null && mainPics.size() > 0) {
            adapterCount = mainPics.size();
            gv.setAdapter(new Adapter(mainPics));

        }
    }

    private class Adapter extends BaseAdapter {
        ArrayList<String> mainPics;

        public Adapter(ArrayList<String> mainPics) {
            this.mainPics = mainPics;
        }

        @Override
        public int getCount() {
            return mainPics.size();
        }

        @Override
        public Object getItem(int position) {
            return mainPics.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder2 vh = null;
            if (convertView == null) {
                vh = new ViewHolder2();
                convertView = LayoutInflater.from(context).inflate(R.layout.adapter_session_item_iv, null);
                vh.iv = convertView.findViewById(R.id.iv);
                LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.height = context.getResources().getDimensionPixelOffset(R.dimen.dp_260);
                vh.iv.setLayoutParams(layoutParams);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder2) convertView.getTag();
            }
            Glide.with(context).load(mainPics.get(position)).addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    if (context instanceof SaveBitmapListener) {
                        SaveBitmapListener listener = (SaveBitmapListener) context;
                        listener.saveResult(false);
                    }
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    adapterCount--;
                    if (adapterCount == 0) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                final Bitmap bitmap = CreateBitmapByViewUtil.loadBitmapFromView(CreateShareView.this);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //保存到系统相册
                                        MediaStore.Images.Media.insertImage(context.getContentResolver(),
                                                bitmap, item.getGoods_name(), null);
                                        //发送广播通知相册刷新，否则相册内看不到新增的图片
                                        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                        Uri uri = Uri.fromFile(new File(Constants.PATH.PATH_SAVE_SHARE_PIC + item.getGoods_name()));
                                        intent.setData(uri);
                                        context.sendBroadcast(intent);
                                        if (context instanceof SaveBitmapListener) {
                                            SaveBitmapListener listener = (SaveBitmapListener) context;
                                            listener.saveResult(true);
                                        }
                                    }
                                }).start();
                            }
                        }, 50);

                    }
                    return false;
                }
            }).into(vh.iv);
            return convertView;
        }
    }

    private static class ViewHolder2 {
        private ImageView iv;
    }

    public interface SaveBitmapListener {
        void saveResult(boolean saveAble);
    }
}
