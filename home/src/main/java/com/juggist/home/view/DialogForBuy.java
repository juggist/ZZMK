package com.juggist.home.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.juggist.home.R;
import com.juggist.jcore.bean.ProductBean;
import com.juggist.jcore.utils.MyImageLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @author juggist
 * @date 2018/11/7 10:35 AM
 * 立即购买dialog
 */
public class DialogForBuy extends DialogFragment {
    private ProductBean.DataBean.GoodsListBean goodsListBean;

    private Listener listener;
    private int position;
    private int maxNum = 100;
    private int num = 1;
    public DialogForBuy() {
    }

    @SuppressLint("ValidFragment")
    public DialogForBuy(ProductBean.DataBean.GoodsListBean goodsListBean,Listener listener,int position) {
        this.goodsListBean = goodsListBean;
        this.listener = listener;
        this.position = position;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Theme_AppCompat_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_dialog_for_buy, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        ImageView iv = view.findViewById(R.id.home_iv);
        TextView tv_product_name = view.findViewById(R.id.home_tv_product_name);
        TextView tv_price = view.findViewById(R.id.home_tv_price);
        GridView gv =view.findViewById(R.id.home_gv);
        TextView tv_up =view.findViewById(R.id.home_tv_up);
        final TextView tv_choose_num =view.findViewById(R.id.home_tv_choose_num);
        TextView tv_choose_down =view.findViewById(R.id.home_tv_choose_down);
        TextView tv_buy =view.findViewById(R.id.home_tv_buy);

        ArrayList<String> mainPics = (ArrayList<String>) goodsListBean.getMain_pic();
        if(mainPics != null && mainPics.size() > 0){
            MyImageLoader.getInstance().loadImage(mainPics.get(0),iv);
        }
        tv_product_name.setText(goodsListBean.getGoods_name());
        tv_price.setText("￥"+goodsListBean.getWholesale_price());

        List<ProductBean.DataBean.GoodsListBean.AttrBean> attr = goodsListBean.getAttr();
        if(attr != null && attr.size() > 0){
            List<ProductBean.DataBean.GoodsListBean.AttrBean.ValueBean> vaules = attr.get(0).getValue();
            if(vaules != null){
                gv.setAdapter(new Adapter((ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean.ValueBean>) vaules));
            }
        }
        tv_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num < maxNum){
                    tv_choose_num.setText(String.valueOf(++num));
                }
            }
        });
        tv_choose_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num > 1){
                    tv_choose_num.setText(String.valueOf(--num));
                }
            }
        });
        tv_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if(listener != null)
                    listener.addShop(position,num);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //声明控件
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //注意下面这个方法会将布局的根部局忽略掉，所以需要嵌套一个布局
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.gravity = Gravity.BOTTOM;//改变在屏幕中的位置,如果需要改变上下左右具体的位置，比如100dp，则需要对布局设置margin
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        lp.width = defaultDisplay.getWidth();  //改变宽度
//        lp.height = (int) getResources().getDimension(R.dimen.dp_400);//改变高度

        dialogWindow.setAttributes(lp);

        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {//可以在这拦截返回键啊home键啊事件
                dialog.dismiss();
                return false;
            }
        });
    }

    private class Adapter extends BaseAdapter{
        private ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean.ValueBean> items ;

        public Adapter(ArrayList<ProductBean.DataBean.GoodsListBean.AttrBean.ValueBean> items) {
            this.items = items;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(getActivity());
            textView.setTextSize(getActivity().getResources().getDimension(R.dimen.sp_20));
            textView.setTextColor(getActivity().getResources().getColor(R.color.white));
            textView.setBackground(getActivity().getResources().getDrawable(R.drawable.home_gridview_item_red_bg));
            textView.setText(items.get(position).getContent());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setPadding(getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_20),getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_4),getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_20),getActivity().getResources().getDimensionPixelOffset(R.dimen.dp_4));
            textView.setLayoutParams(lp);
            return textView;
        }
    }

    public interface Listener {
        void addShop(int position,int num);
    }
}
