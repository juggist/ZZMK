package com.juggist.baseandroid.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.eventbus.AddressEvent;
import com.juggist.baseandroid.present.mine.AddressListPresent;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.baseandroid.ui.mine.adapter.AddressListAdapter;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.AlertDialog;
import com.juggist.jcore.bean.AddressBean;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 地址列表
 */
public class AddressListActivity extends BackBaseActivity {

    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.btn_address_add)
    Button btnAddressAdd;

    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;

    private AddressListAdapter adapter;
    private AddressListContract.Present present;

    private int tag;
    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_address_list;
    }

    @Override
    protected void initView() {
        statusView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_net_error, null);
        statusIv = statusView.findViewById(R.id.lv_iv);
        statusTv = statusView.findViewById(R.id.lv_tv);

        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.item_bg))
                .sizeResId(R.dimen.dp_20)
                .build());
    }

    @Override
    protected void initListener() {
        btnAddressAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("tag",0);
                gotoActivity(AddressAddActivity.class,bundle);
            }
        });
        //网络异常，点击屏幕重新加载
        statusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (statusTv.getText().toString().equals(getResources().getString(R.string.lv_net_error))) {
                    present.start();
                }
            }
        });

    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        parseBundle();
        initAdapter();
        new AddressListPresent(new ViewModel());
        present.start();
    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.title_address);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new AddressListAdapter(R.layout.adapter_address_item, new ArrayList<AddressBean>());
        adapter.setEmptyView(statusView);
        lv.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(tag == 1){
                    EventBus.getDefault().post(new AddressEvent.AddressChoose(present.getAddress(position)));
                    AddressListActivity.this.finish();
                }
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_select:
                    case R.id.ibtn_select:
                        present.checkDefaultAddress(position);
                        break;
                    case R.id.tv_del_title:
                    case R.id.iv_del:
                        present.checkDeleteAddress(position);
                        break;
                    case R.id.tv_edit_title:
                    case R.id.tv_edit:
                        Bundle bundle = new Bundle();
                        bundle.putInt("tag",1);
                        bundle.putSerializable("addressBean",AddressListActivity.this.adapter.getAddressBean(position));
                        AddressListActivity.this.gotoActivity(AddressAddActivity.class,bundle);
                        break;
                }
            }
        });
    }
    private void parseBundle(){
        tag = getIntent().getExtras().getInt("tag",1);
    }
    private class ViewModel implements AddressListContract.View {

        @Override
        public void getAddressListEmpty() {
            statusTv.setText(getResources().getString(R.string.lv_data_empty));
        }

        @Override
        public void getAddressListSucceed(List<AddressBean> addressBeans) {
            adapter.update(addressBeans);
        }

        @Override
        public void getAddressListFail(String msg) {
            statusTv.setText(getResources().getString(R.string.lv_net_error));
            showErrorDialog(msg);
        }

        @Override
        public void deleteAddressSucceed(List<AddressBean> addressBeans) {
            AddressListActivity.this.adapter.update(addressBeans);
        }

        @Override
        public void deleteAddressEmptySucceed() {
            statusTv.setText(getResources().getString(R.string.lv_data_empty));
            AddressListActivity.this.adapter.update(new ArrayList<AddressBean>());
        }

        @Override
        public void deleteAddressFail(String msg) {
            showErrorDialog(msg);
        }

        @Override
        public void showSetDefaultAddressDialog() {
            new AlertDialog(AddressListActivity.this).builder()
                    .setTitle(getResources().getString(R.string.address_set_default_title))
                    .setPositiveButton(getResources().getString(R.string.ensure), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            present.setDefaultAddress();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancle), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
        }

        @Override
        public void setDefaultAddressSucceed(List<AddressBean> addressBeans) {
            AddressListActivity.this.adapter.update(addressBeans);
        }

        @Override
        public void showDeleteAddressDialog() {
            new AlertDialog(AddressListActivity.this).builder()
                    .setTitle(getResources().getString(R.string.address_set_delete_title))
                    .setPositiveButton(getResources().getString(R.string.ensure), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            present.deleteAddress();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.cancle), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .show();
        }


        @Override
        public void setDefaultAddressFail(String msg) {
            showErrorDialog(msg);
        }

        @Override
        public void setPresent(AddressListContract.Present present) {
            AddressListActivity.this.present = present;
        }

        @Override
        public void showErrorDialog(String message) {
            ToastUtil.showLong(message);
        }

        @Override
        public void showLoading() {
            statusTv.setText(getResources().getString(R.string.lv_loading));
            AddressListActivity.this.showLoading();
        }

        @Override
        public void dismissLoading() {
            AddressListActivity.this.dismissLoading();
        }
    }
    @Subscribe
    public void updateAddressList(AddressEvent.AddressListUpdate event){
        present.start();
    }

}
