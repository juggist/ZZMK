package com.juggist.baseandroid.view;

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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.juggist.baseandroid.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @author juggist
 * @date 2018/12/4 11:38 AM
 */
public class DialogRefundAll extends DialogFragment {
    private Adapter adapter;
    private ImageView iv_close;
    private ListView lv;

    private SettingListener listener;

    private int selectIndex = -1;

    private String reason = "";

    public DialogRefundAll() {
    }

    @SuppressLint("ValidFragment")
    public DialogRefundAll(SettingListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Theme_AppCompat_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_refund_all, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //声明控件
        iv_close = view.findViewById(R.id.iv_close);
        lv = view.findViewById(R.id.lv);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectIndex = position;
                adapter.notifyDataSetChanged();
                reason = getItems()[position];
                if(listener != null)
                    listener.getReason(reason);
                dismiss();
            }
        });
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

        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {//可以在这拦截返回键啊home键啊事件
                dialog.dismiss();
                return false;
            }
        });
        adapter = new Adapter();
        lv.setAdapter(adapter);
    }

    private String[] getItems(){
        return getActivity().getResources().getStringArray(R.array.refund_all_reason);
    }
    private class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return getItems().length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.adapter_session_setting_item, null);
                vh.iv = convertView.findViewById(R.id.iv);
                vh.tv = convertView.findViewById(R.id.tv);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            vh.tv.setText(getItems()[position]);
            if (selectIndex == position) {
                vh.iv.setVisibility(View.VISIBLE);
            } else {
                vh.iv.setVisibility(View.GONE);
            }
            return convertView;
        }

    }

    private static class ViewHolder {
        private TextView tv;
        private ImageView iv;
    }
    public interface SettingListener{
        void getReason(String reason);
    }
}

