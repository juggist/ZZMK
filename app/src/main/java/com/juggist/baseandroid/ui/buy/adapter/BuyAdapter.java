package com.juggist.baseandroid.ui.buy.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.jcore.bean.ShopCarBean;
import com.orhanobut.logger.Logger;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author juggist
 * @date 2018/11/7 2:01 PM
 * 购物车适配器
 */
public class BuyAdapter extends BaseAdapter {
    private final ExecutorService task;
    private Context context;
    private MyHandler myHandler;

    private ArrayList<ShopCarBean> shopCarBeans;
    private static final int maxNum = 99;
    private static final int minNum = 0;
    private ArrayList<ShopCarBean> selectArray;
    private int key = 0;
    private static ArrayList<Integer> keys = new ArrayList<>();

    class MyHandler extends Handler {
        WeakReference<Context> weakReference;

        public MyHandler(Context context) {
            this.weakReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Context context1 = weakReference.get();
            if (context1 == null)
                return;
            int position = msg.arg1;
            if (keys.get(0) % 4 == 1) {
                shopCarBeans.get(position).setGoods_number(String.valueOf(Integer.parseInt(shopCarBeans.get(position).getGoods_number() ) - 1));
                notifyDataSetChanged();
                Logger.d("%s:fail", String.valueOf(keys.get(0)));
            } else {
                Logger.d("%s:succeed", String.valueOf(keys.get(0)));
            }
            keys.remove(0);
        }
    }

    public BuyAdapter(Context context) {
        this.context = context;
        shopCarBeans = new ArrayList<>();
        selectArray = new ArrayList<>();
        task = Executors.newSingleThreadExecutor();
        myHandler = new MyHandler(context);

    }

    @Override
    public int getCount() {
        return shopCarBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return shopCarBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_buy_item, null);
            vh.iv_select = convertView.findViewById(R.id.iv_select);
            vh.iv = convertView.findViewById(R.id.iv);
            vh.iv_add = convertView.findViewById(R.id.iv_add);
            vh.iv_minus = convertView.findViewById(R.id.iv_minus);
            vh.tv_content = convertView.findViewById(R.id.tv_content);
            vh.tv_price = convertView.findViewById(R.id.tv_price);
            vh.tv_spes = convertView.findViewById(R.id.tv_spes);
            vh.tv_num = convertView.findViewById(R.id.tv_num);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final ShopCarBean item = shopCarBeans.get(position);

        Glide.with(context).load(item.getMain_pic()).into(vh.iv);
        vh.tv_num.setText(item.getGoods_number());
        vh.tv_content.setText(item.getGoods_name());
        if (item.getAttr() != null && item.getAttr().size() > 0) {
            vh.tv_spes.setText(item.getAttr().get(0).getAttr_name() + ":" + item.getAttr().get(0).getValue());
        }
        if (selectArray.contains(item)) {
            vh.iv_select.setSelected(true);
        } else {
            vh.iv_select.setSelected(false);
        }

        vh.iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectArray.contains(item)) {
                    selectArray.remove(item);
                } else {
                    selectArray.add(item);
                }
                notifyDataSetChanged();

            }
        });
        vh.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(item.getGoods_number()) < maxNum) {
                    item.setGoods_number(String.valueOf(Integer.parseInt(item.getGoods_number()) + 1));
                    notifyDataSetChanged();

                    task.execute(new Runnable() {
                        @Override
                        public void run() {
                            keys.add(key++);
                            Logger.d("%s:enter key", String.valueOf(keys.get(0)));
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Message msg = new Message();
                            msg.arg1 = position;
                            myHandler.sendMessage(msg);
                        }

                    });
                } else {
                    ToastUtil.showLong(context.getResources().getString(R.string.toast_out_of_shop_maxnum));
                }

            }
        });
        vh.iv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(item.getGoods_number()) > minNum) {
                    item.setGoods_number(String.valueOf(Integer.parseInt(item.getGoods_number()) - 1));
                    notifyDataSetChanged();
                    Logger.d("shopCard:" + selectArray.toString());
                } else {
                }

            }
        });
        return convertView;
    }

    private static class ViewHolder {
        private ImageView iv_select, iv, iv_add, iv_minus;
        private TextView tv_content, tv_price, tv_spes, tv_num;
    }

    public void update(ArrayList<ShopCarBean> shopCarBeans) {
        this.shopCarBeans.clear();
        this.shopCarBeans.addAll(shopCarBeans);
        notifyDataSetChanged();
    }
}
