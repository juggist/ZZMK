package com.juggist.baseandroid.ui.mine;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.eventbus.AddressEvent;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.AlertDialog;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.AddressBean;
import com.juggist.jcore.bean.AddressParseBean;
import com.juggist.jcore.service.IUserService;
import com.juggist.jcore.service.UserService;
import com.juggist.jcore.utils.RegexUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * 新增地址
 */
public class AddressAddActivity extends BackBaseActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_address_detail)
    EditText etAddressDetail;
    @BindView(R.id.btn_ensure)
    Button btnEnsure;
    @BindView(R.id.tv_address_content)
    TextView tvAddressContent;
    @BindView(R.id.ibtn_select)
    ImageButton ibtnSelect;


    private IUserService userService;

    private ArrayList<AddressParseBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private static final int REQUEST_LOAD = 0x001;
    private static final int REQUEST_FINISH = 0x002;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private int load_status = MSG_LOAD_DATA;
    private int request_status = REQUEST_FINISH;
    private MyHander myHander;
    private Thread thread;

    private int tag;
    private AddressBean addressBean;
    private String provinceid;
    private String cityid;
    private String areaid;


    private class MyHander extends Handler {
        private final WeakReference<Activity> weakReference;

        public MyHander(AddressAddActivity addressAddActivity) {
            weakReference = new WeakReference<Activity>(addressAddActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final AddressAddActivity activity = (AddressAddActivity) weakReference.get();
            if (activity == null)
                return;
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        load_status = MSG_LOAD_DATA;
                        thread.start();
                    }
                    break;
                case MSG_LOAD_SUCCESS:
                    load_status = MSG_LOAD_SUCCESS;
                    if (request_status == REQUEST_LOAD) {
                        showPickerView();
                    }
                    break;
                case MSG_LOAD_FAILED:
                    load_status = MSG_LOAD_FAILED;
                    if (request_status == REQUEST_LOAD) {
                        ToastUtil.showLong(getResources().getString(R.string.address_choose_fail));
                    }
                    break;
            }
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_add;
    }

    @Override
    protected void initView() {
        tvAddress.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.dp_20));
    }

    @Override
    protected void initListener() {
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_status = REQUEST_LOAD;
                switch (load_status) {
                    case MSG_LOAD_DATA:
                        break;
                    case MSG_LOAD_SUCCESS:
                        showPickerView();
                        break;
                    case MSG_LOAD_FAILED:
                        myHander.sendEmptyMessage(MSG_LOAD_DATA);
                        break;
                }
            }
        });
        btnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String address = tvAddressContent.getText().toString();
                String addressDetail = etAddressDetail.getText().toString();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address) || TextUtils.isEmpty(addressDetail)) {
                    ToastUtil.showLong(getResources().getString(R.string.toast_un_empty));
                    return;
                }
                if (!RegexUtils.isMobileExact(phone)) {
                    ToastUtil.showLong(getResources().getString(R.string.toast_login_phone_un_right));
                    return;
                }
                if (tag == 0) {
                    addAddress(addressDetail, provinceid, cityid, areaid, phone, name, ibtnSelect.isSelected() ? "1" : "0");
                } else if (tag == 1) {
                    updateAddress(addressBean.getAuto_id(),addressDetail, provinceid, cityid, areaid, phone, name, ibtnSelect.isSelected() ? "1" : "0");
                }
            }
        });
        ibtnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(v.isSelected() ? false : true);
            }
        });
    }

    @Override
    protected void initData() {
        myHander = new MyHander(this);
        myHander.sendEmptyMessage(MSG_LOAD_DATA);
        userService = new UserService();
        parseBundle();
    }

    @Override
    protected String getTitile() {
        return "";
    }

    /**
     * 解析bundle数据
     */
    private void parseBundle() {
        tag = getIntent().getIntExtra("tag", 0);
        if (tag == 0) {//新增
            tv_title.setText(getResources().getString(R.string.title_address_add));
        } else if (tag == 1) {//修改
            tv_title.setText(getResources().getString(R.string.title_address_edit));
            addressBean = (AddressBean) getIntent().getSerializableExtra("addressBean");
            etName.setText(addressBean.getConsignee());
            etPhone.setText(addressBean.getCellphone());
            tvAddressContent.setText(addressBean.getProvince_name() + addressBean.getCity_name() + addressBean.getAreas_name());
            etAddressDetail.setText(addressBean.getAddr());
            ibtnSelect.setSelected(addressBean.getIs_default().equals("0") ? false : true);
            provinceid = addressBean.getProvince();
            cityid = addressBean.getCity();
            areaid = addressBean.getAreas();
        }

    }

    /**
     * 添加地址
     *
     * @param detail
     * @param province_id
     * @param city_id
     * @param area_id
     * @param cellphone
     * @param consignee
     * @param is_default
     */
    private void addAddress(String detail, String province_id, String city_id, String area_id, String cellphone, String consignee, String is_default) {
        showLoading();
        userService.addAddress(detail, province_id, city_id, area_id, cellphone, consignee, is_default, new ResponseCallback<String>() {
            @Override
            public void onSucceed(String s) {
                dismissLoading();
                EventBus.getDefault().post(new AddressEvent.AddressListUpdate());
                new AlertDialog(AddressAddActivity.this).builder()
                        .setTitle(getResources().getString(R.string.address_set_succeed))
                        .setNegativeButton(getResources().getString(R.string.ensure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AddressAddActivity.this.finish();
                            }
                        })
                        .setCancelable(false)
                        .setOutsideTouchable(false)
                        .show();
            }

            @Override
            public void onError(String message) {
                showFailAlert(getResources().getString(R.string.address_set_fail));
            }

            @Override
            public void onApiError(String state, String message) {
                showFailAlert(getResources().getString(R.string.address_set_fail));
            }
        });
    }


    /**
     * 编辑地址
     *
     * @param detail
     * @param province_id
     * @param city_id
     * @param area_id
     */
    private void updateAddress(String address_id,String detail, String province_id, String city_id, String area_id,String cellphone, String consignee, String is_default) {
        userService.changeAddress(address_id,detail, province_id, city_id, area_id,cellphone,consignee,is_default, new ResponseCallback<String>() {
            @Override
            public void onSucceed(String s) {
                dismissLoading();
                EventBus.getDefault().post(new AddressEvent.AddressListUpdate());
                new AlertDialog(AddressAddActivity.this).builder()
                        .setTitle(getResources().getString(R.string.address_update_succeed))
                        .setNegativeButton(getResources().getString(R.string.ensure), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AddressAddActivity.this.finish();
                            }
                        })
                        .setCancelable(false)
                        .setOutsideTouchable(false)
                        .show();
            }

            @Override
            public void onError(String message) {
                showFailAlert(getResources().getString(R.string.address_update_fail));
            }

            @Override
            public void onApiError(String state, String message) {
                showFailAlert(getResources().getString(R.string.address_update_fail));
            }
        });
    }

    private void showFailAlert(String msg) {
        dismissLoading();
        new AlertDialog(AddressAddActivity.this).builder()
                .setTitle(getResources().getString(R.string.address_set_fail))
                .show();
    }

    /**
     * 地址弹窗
     */
    OptionsPickerView pvOptions;

    private void showPickerView() {// 弹出选择器
        request_status = REQUEST_FINISH;
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                provinceid = options1Items.get(options1).getProvinceid();
                cityid = options1Items.get(options1).getCity().get(options2).getCityid();
                areaid = options1Items.get(options1).getCity().get(options2).getArea().get(options3).getAreaid();
                tvAddressContent.setText(tx);
            }
        })
                .setLayoutRes(R.layout.pickview_options_custom, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        Button btnCancel = v.findViewById(R.id.btnCancel);
                        Button btnSubmit = v.findViewById(R.id.btnSubmit);
                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.dismiss();
                            }
                        });
                        btnSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvOptions.dismiss();
                                pvOptions.returnData();
                            }
                        });
                    }
                })
                .setContentTextSize(getResources().getDimensionPixelSize(R.dimen.sp_30))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    /**
     * json解析asset下的地址数据
     */
    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("province.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String JsonData = stringBuilder.toString();//获取assets目录下的json文件数据

        ArrayList<AddressParseBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCity().get(c).getCity();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCity().get(c).getArea() == null || jsonBean.get(i).getCity().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (AddressParseBean.CityBean.AreaBean area : jsonBean.get(i).getCity().get(c).getArea()) {
                        City_AreaList.add(area.getArea());
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
        myHander.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<AddressParseBean> parseData(String result) {//Gson 解析
        ArrayList<AddressParseBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                AddressParseBean entity = gson.fromJson(data.optJSONObject(i).toString(), AddressParseBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            myHander.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

}
