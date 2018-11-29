package com.juggist.baseandroid.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.jcore.utils.KeyboardUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @author juggist
 * @date 2018/11/6 3:51 PM
 * <p>
 * 专场 设置dialog
 */
public class DialogSessionSetting extends DialogFragment {
    private Adapter adapter;
    private ImageView iv_close;
    private ListView lv;
    private Button btn_ensure;
    private EditText et;

    private SettingListener listener;

    private int selectIndex = -1;

    private int addPrice = 0;

    public DialogSessionSetting() {
    }

    @SuppressLint("ValidFragment")
    public DialogSessionSetting(SettingListener listener) {
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
        View view = inflater.inflate(R.layout.dialog_session_setting, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //声明控件
        iv_close = view.findViewById(R.id.iv_close);
        lv = view.findViewById(R.id.lv);
        btn_ensure = view.findViewById(R.id.btn_ensure);
        et = view.findViewById(R.id.et);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.clearFocus();
                dismiss();
            }
        });
        btn_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(et.getText().toString())){
                    addPrice = Integer.parseInt(et.getText().toString());
                }
                if( listener != null)
                    listener.addPrice(addPrice);
                //隐藏软键盘
                KeyboardUtils.hideSoftInput(et);
                dismiss();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectIndex = position;
                et.clearFocus();
                adapter.notifyDataSetChanged();
                switch (position){
                    case 0:
                        addPrice = 0;
                        break;
                    case 1:
                        addPrice = 10;
                        break;
                    case 2:
                        addPrice = 20;
                        break;
                }
            }
        });
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    selectIndex = -1;
                    adapter.notifyDataSetChanged();
                }else{
                    et.setText("");
                    //隐藏软键盘
                    KeyboardUtils.hideSoftInput(et);
                }
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
                et.clearFocus();
                return false;
            }
        });
        adapter = new Adapter();
        lv.setAdapter(adapter);
    }

    private class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 3;
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
            String text = "不加价";
            switch (position){
                case 0:
                    break;
                case 1:
                    text = "加价10元";
                    break;
                case 2:
                    text = "加价20元";
                    break;
            }
            vh.tv.setText(text);
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
        void addPrice(int addPrice);
    }
}
