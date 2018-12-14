package com.juggist.baseandroid.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.chooseImg.PhotoActivity;
import com.juggist.baseandroid.view.chooseImg.adapter.ImagePickAdapter;
import com.juggist.baseandroid.view.chooseImg.config.Bimp;
import com.juggist.baseandroid.view.chooseImg.ustils.FileUtils;
import com.juggist.jcore.utils.FileIOUtils;
import com.juggist.jcore.utils.KeyboardUtils;
import com.juggist.jcore.utils.RegexUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * @author juggist
 * @date 2018/12/12 4:33 PM
 */
public class DialogID extends DialogFragment {


    private ImageView iv_close;
    private Button btn_commit;
    private GridView gv;
    private EditText et;
    private View line;
    private TextView tv_choose_pic_title;
    private LinearLayout ll;

    private int flag;
    private ImagePickAdapter imagePickAdapter;
    private Listener listener;


    public DialogID() {
    }

    @SuppressLint("ValidFragment")
    public DialogID(int flag, Listener listener) {
        this.flag = flag;
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bimp.MAX_SIZE = 2;
        setStyle(STYLE_NORMAL, R.style.Theme_AppCompat_Dialog);
    }

    @Override
    public void onResume() {
        imagePickAdapter.update();
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //数据reset
        Bimp.max = 0;
        Bimp.act_bool = false;
        //清空图片数据(内存图片)
        Bimp.bmp.clear();
        //清空图片地址数据（sd卡路径）
        Bimp.drr.clear();
        //删除压缩处理的图片
        FileUtils.deleteDir();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_id, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //声明控件
        iv_close = view.findViewById(R.id.iv_close);
        btn_commit = view.findViewById(R.id.btn_commit);
        gv = view.findViewById(R.id.gv);
        et = view.findViewById(R.id.et);
        line = view.findViewById(R.id.line);
        ll = view.findViewById(R.id.ll);
        tv_choose_pic_title = view.findViewById(R.id.tv_choose_pic_title);
        if (flag == 2) {
            line.setVisibility(View.GONE);
            tv_choose_pic_title.setVisibility(View.GONE);
            gv.setVisibility(View.GONE);
        }
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        btn_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null)
                    return;
                String id = et.getText().toString();
                if (!RegexUtils.isIDCard18(id)) {
                    ToastUtil.showLong(getResources().getString(R.string.id_hint));
                    return;
                }
                if (flag == 2) {
                    listener.createOrder(id, "", "");
                } else if (flag == 3) {
                    if(Bimp.drr.size() != 2){
                        byte[] cn_id_bg_byte = FileIOUtils.readFile2BytesByStream(Bimp.drr.get(0));
                        byte[] cd_id_front_byte = FileIOUtils.readFile2BytesByStream(Bimp.drr.get(1));
                        String cn_id_bg = new String(cn_id_bg_byte);
                        String cn_id_front = new String(cd_id_front_byte);
                        listener.createOrder(id,cn_id_bg,cn_id_front);
                    }else{
                        ToastUtil.showLong(getResources().getString(R.string.id_choose_pic));
                    }
                }
            }
        });

        imagePickAdapter = new ImagePickAdapter(getActivity());
        gv.setAdapter(imagePickAdapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Bimp.bmp.size()) {
                    if (listener != null)
                        listener.showChoosePicDialog();
                } else {
                    Intent intent = new Intent(getActivity(), PhotoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", position);
                    intent.putExtras(bundle);
                    startActivity(intent);
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
                return false;
            }
        });
    }


    public interface Listener {
        void showChoosePicDialog();

        void createOrder(String id, String cn_id_bg, String cd_id_front);
    }

    @Override
    public void dismiss(){
        KeyboardUtils.hideSoftInput(et);
        super.dismiss();
    }
}
