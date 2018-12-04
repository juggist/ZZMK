package com.juggist.baseandroid.ui.mine;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.present.mine.OrderRefundAllPresent;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.baseandroid.ui.mine.adapter.OrderRefundAllIItemAdapter;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.ActionSheetDialog;
import com.juggist.baseandroid.view.AlertDialog;
import com.juggist.baseandroid.view.DialogRefundAll;
import com.juggist.baseandroid.view.NoScrollGridView;
import com.juggist.baseandroid.view.NoScrollRecycleListView;
import com.juggist.baseandroid.view.chooseImg.ImageBucketActivity;
import com.juggist.baseandroid.view.chooseImg.PhotoActivity;
import com.juggist.baseandroid.view.chooseImg.adapter.ImagePickAdapter;
import com.juggist.jcore.bean.OrderBean;
import com.juggist.jcore.utils.AppUtil;
import com.juggist.jcore.utils.KeyboardUtils;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 退货退款
 */
@RuntimePermissions
public class OrderRefundAllActivity extends BackBaseActivity {

    @BindView(R.id.tv_reason_choose)
    TextView tvReasonChoose;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.et)
    EditText et;
    @BindView(R.id.gv)
    NoScrollGridView gv;
    @BindView(R.id.lv)
    NoScrollRecycleListView lv;
    @BindView(R.id.btn_ensure)
    Button btnEnsure;
    private static final int TAKE_PICTURE = 0x000000;
    private static final int MAX_INTRODUCT_LENGTH = 20;

    private OrderRefundAllContract.Present present;
    private OrderRefundAllIItemAdapter goodsAdapter;
    private ImagePickAdapter imagePickAdapter;


    @Override
    protected void onResume() {
        imagePickAdapter.update();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detach();
        KeyboardUtils.hideSoftInput(et);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == -1)
                    present.addPic();
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_refund_all;
    }

    @Override
    protected void initView() {
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.item_bg))
                .sizeResId(R.dimen.dp_4)
                .build());

        tvReasonChoose.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.dp_20));
    }

    @Override
    protected void initListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                present.clickPic(position);
            }
        });
        tvReasonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = OrderRefundAllActivity.this.getSupportFragmentManager().beginTransaction();
                DialogRefundAll dra = new DialogRefundAll(new DialogListener());
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                dra.show(ft, "dfb");
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.toString().length();
                if (length > MAX_INTRODUCT_LENGTH) {
                    int index = et.getSelectionStart();
                    Editable editable = et.getText();
                    editable.delete(index - 1, index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String des = et.getText().toString();
                String reason = tvReason.getText().toString();
                if (TextUtils.isEmpty(reason)) {
                    new AlertDialog(OrderRefundAllActivity.this).builder()
                            .setTitle(getResources().getString(R.string.order_refund_reason_empty))
                            .show();
                    return;

                }
                present.refundDispatched(des, reason);
            }
        });
    }

    @Override
    protected void initData() {
        OrderBean order = (OrderBean) getIntent().getSerializableExtra("order");
        new OrderRefundAllPresent(new ViewModel(), this, order);
        initGoodsAdapter();
        initImgAdapter();
        present.start();
    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.title_order_refund_all);
    }


    /**
     * 初始化商品的adapter
     */
    private void initGoodsAdapter() {
        goodsAdapter = new OrderRefundAllIItemAdapter(R.layout.adapter_order_refund_all_item, new ArrayList<OrderBean.GoodsListBean>());
        lv.setAdapter(goodsAdapter);
    }

    /**
     * 初始化选择图片的adapter
     */
    private void initImgAdapter() {
        imagePickAdapter = new ImagePickAdapter(this);
        gv.setAdapter(imagePickAdapter);
    }


    /**
     * dialog回调事件
     */
    class DialogListener implements DialogRefundAll.SettingListener {

        @Override
        public void getReason(String reason) {
            tvReason.setText(reason);
        }
    }

    class ViewModel implements OrderRefundAllContract.View {

        @Override
        public void updateAdapter(List<OrderBean.GoodsListBean> goodsListBeans) {
            goodsAdapter.update(goodsListBeans);
        }

        @Override
        public void showChoosePicDialog() {
            new ActionSheetDialog(OrderRefundAllActivity.this)
                    .builder()
                    .setTitle(getResources().getString(R.string.choose_pic))
                    .setCancelable(true)
                    .setCanceledOnTouchOutside(false)
                    .addSheetItem(getResources().getString(R.string.choose_pic_from_album), ActionSheetDialog.SheetItemColor.Blue,
                            new ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    OrderRefundAllActivityPermissionsDispatcher.pickImgFromAlbumWithPermissionCheck(OrderRefundAllActivity.this);
                                }
                            })
                    .addSheetItem(getResources().getString(R.string.choose_pic_from_camera), ActionSheetDialog.SheetItemColor.Blue,
                            new ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    OrderRefundAllActivityPermissionsDispatcher.pickImgFromCameraWithPermissionCheck(OrderRefundAllActivity.this);
                                }
                            })
                    .show();
        }

        @Override
        public void openCamera(Intent intent) {
            startActivityForResult(intent, TAKE_PICTURE);
        }

        @Override
        public void toBigPicView(Bundle bundle) {
            gotoActivity(PhotoActivity.class, bundle);
        }

        @Override
        public void refundDispatchedSucceed() {
            new AlertDialog(OrderRefundAllActivity.this).builder()
                    .setTitle(getResources().getString(R.string.order_refund_dialog_succeed_title))
                    .setPositiveButton(getResources().getString(R.string.ensure), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            OrderRefundAllActivity.this.finish();
                        }
                    })
                    .show();
        }

        @Override
        public void refundDispatchedFail(String msg) {
            showErrorDialog(msg);
        }

        @Override
        public void setPresent(OrderRefundAllContract.Present present) {
            OrderRefundAllActivity.this.present = present;
        }

        @Override
        public void showErrorDialog(String message) {
            ToastUtil.showLong(message);
        }

        @Override
        public void showLoading() {
            OrderRefundAllActivity.this.showLoading();
        }

        @Override
        public void dismissLoading() {
            OrderRefundAllActivity.this.dismissLoading();
        }
    }

    /**
     * 动态权限
     */

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void pickImgFromAlbum() {
        Intent intent = new Intent(OrderRefundAllActivity.this,
                ImageBucketActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OrderRefundAllActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    void pickImgFromAlbumRationale(final PermissionRequest request) {
        pickImgPermissionFail();
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    void pickImgFromAlbumDenied() {
        pickImgPermissionFail();
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    void pickImgFromAlbumNever() {
        pickImgPermissionFail();
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void pickImgFromCamera() {
        present.openCamera();
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void pickImgFromCameraRationale(final PermissionRequest request) {
        pickImgPermissionFail();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void pickImgFromCameraDenied() {
        pickImgPermissionFail();
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void pickImgFromCameraNever() {
        pickImgPermissionFail();
    }


    private void pickImgPermissionFail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog(OrderRefundAllActivity.this).builder()
                        .setTitle(getResources().getString(R.string.choose_pic_permission_fail))
                        .setMsg(getResources().getString(R.string.choose_pic_permission_todo))
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton("去设置", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转到自己app设置页面
                                AppUtil.toSetting(OrderRefundAllActivity.this);
                            }
                        })
                        .show();
            }
        });
    }
}
