package com.juggist.baseandroid.ui.home;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.present.home.BatchForwardPresent;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.baseandroid.ui.home.adapter.BatchForwardItemAdapter;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.AlertDialog;
import com.juggist.baseandroid.view.CreateShareView;
import com.juggist.baseandroid.view.DialogSessionSetting;
import com.juggist.baseandroid.view.LoadingDialog;
import com.juggist.jcore.bean.ProductBean;
import com.orhanobut.logger.Logger;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import hugo.weaving.DebugLog;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 批量转发
 */
@DebugLog
@RuntimePermissions
public class BatchForwardActivity extends BackBaseActivity implements CreateShareView.SaveBitmapListener {

    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.tv_batch_forward)
    TextView tvBatchForward;
    @BindView(R.id.createShareView)
    CreateShareView createShareView;

    private int addPrice = 0;//加价
    private int selectCount = 0;//批量转发个数

    private LoadingDialog loadingDialog;
    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;

    private Handler mHandler;
    private BatchForwardContract.Present present;
    private BatchForwardItemAdapter adapter;

    private List<ProductBean.DataBean.GoodsListBean> shareGoodList;

    @Override
    protected void onDestroy() {
        present.detach();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_batch_forward;
    }

    @Override
    protected void initView() {
        iv_setting.setVisibility(View.VISIBLE);
        tvBatchForward.setText(String.format(getResources().getString(R.string.product_batch_forward), "0"));

        statusView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_net_error, null);
        statusIv = statusView.findViewById(R.id.lv_iv);
        statusTv = statusView.findViewById(R.id.lv_tv);

        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.color_invitecode))
                .sizeResId(R.dimen.dp_1)
                .marginResId(R.dimen.dp_80, R.dimen.dp_0)
                .build());
    }

    @Override
    protected void initListener() {
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSessionSetting dss = new DialogSessionSetting(new MySettingListener());
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                dss.show(ft, "dss");
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
        //批量转发
        tvBatchForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                shareGoodList = adapter.getSelectList();
                if(shareGoodList.size() == 0){
                    ToastUtil.showLong(getResources().getString(R.string.toast_choose_share_goods));
                    return;
                }
                showLoading();
                BatchForwardActivityPermissionsDispatcher.saveShareBitmapWithPermissionCheck(BatchForwardActivity.this);
            }
        });
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        String group_id = bundle.getString("group_id");

        initHandler();
        initAdapter();
        new BatchForwardPresent(new ViewModel(), group_id);
        present.start();
    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.title_batch_forward);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new BatchForwardItemAdapter(R.layout.adapter_batch_forward_item, new ArrayList<ProductBean.DataBean.GoodsListBean>(), this);
        adapter.setEmptyView(statusView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.ibtn_select:
                        selectCount = BatchForwardActivity.this.adapter.updateSelect(position);
                        updateButtonInfo();
                        break;
                }
            }
        });
        lv.setAdapter(adapter);
    }

    /**
     * 实例化handler
     */
    private void initHandler() {
        mHandler = new Handler(BatchForwardActivity.this.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        final ProductBean.DataBean.GoodsListBean item = shareGoodList.get(0);
                        createShareView.setData(item, addPrice);
                        break;
                }
            }
        };
    }

    /**
     * 图片保存结果
     *
     * @param saveAble
     */
    @Override
    public void saveResult(boolean saveAble) {
        if (saveAble) {
            shareGoodList.remove(0);
            BatchForwardActivityPermissionsDispatcher.saveShareBitmapWithPermissionCheck(BatchForwardActivity.this);
        } else {
            dismissLoading();
            Logger.d("保存失败，请重试");
        }
    }

    /**
     * 更新批量转发按钮信息
     */
    private void updateButtonInfo(){
        tvBatchForward.setText(String.format(getResources().getString(R.string.product_batch_forward), String.valueOf(selectCount) + (addPrice == 0 ? "" : "/加价:" + addPrice)));
    }
    private class ViewModel implements BatchForwardContract.View {

        @Override
        public void getListEmpty() {
            statusTv.setText(getResources().getString(R.string.lv_data_empty));
        }

        @Override
        public void getListSucceed(List<ProductBean.DataBean.GoodsListBean> dataBeans) {
            adapter.update(dataBeans);
        }

        @Override
        public void getListFail(String extMsg) {
            showErrorDialog(extMsg);
            statusTv.setText(getResources().getString(R.string.lv_net_error));
        }

        @Override
        public void setPresent(BatchForwardContract.Present present) {
            BatchForwardActivity.this.present = present;
        }

        @Override
        public void showErrorDialog(String message) {
            ToastUtil.showLong(message);
        }

        @Override
        public void showLoading() {
            statusTv.setText(getResources().getString(R.string.lv_loading));
        }

        @Override
        public void dismissLoading() {

        }

    }

    /**
     * 设置按钮回调事件
     */
    private class MySettingListener implements DialogSessionSetting.SettingListener {

        @Override
        public void addPrice(int addPrice) {
            BatchForwardActivity.this.addPrice = addPrice;
            updateButtonInfo();
        }
    }

    /**
     * 动态权限申请
     */
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void saveShareBitmap() {
        if (shareGoodList.size() > 0) {
            mHandler.sendEmptyMessage(0);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dismissLoading();
                    new AlertDialog(BatchForwardActivity.this).builder()
                            .setTitle(getResources().getString(R.string.save_bitmap_succeed))
                            .setMsg(getResources().getString(R.string.save_bitmap_succeed_todo))
                            .show();
                }
            });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        BatchForwardActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void saveShareBitmapRationale(final PermissionRequest request) {
        showPermissionSaveShareBitmapFail();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void saveShareBitmapDenied() {
        showPermissionSaveShareBitmapFail();
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void saveShareBitmapNever() {
        showPermissionSaveShareBitmapFail();
    }


}
