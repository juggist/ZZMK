package com.juggist.buy.ui;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.juggist.buy.R;
import com.juggist.buy.R2;
import com.juggist.buy.present.OrderSubmitPresent;
import com.juggist.buy.ui.adapter.OrderSubmitAdapter;
import com.juggist.buy.view.DialogID;
import com.juggist.componentservice.TargetBean;
import com.juggist.componentservice.eventbus.AddressEvent;
import com.juggist.buy.eventbus.DiscountCardEvent;
import com.juggist.jcore.base.BackBaseActivity;
import com.juggist.jcore.Constants;
import com.juggist.jcore.bean.AddressBean;
import com.juggist.jcore.bean.OrderCreateBean;
import com.juggist.jcore.bean.OrderCreateTmpBean;
import com.juggist.jcore.utils.ToastUtil;
import com.juggist.jcore.view.ActionSheetDialog;
import com.juggist.jcore.view.NoScrollRecycleView;
import com.juggist.jcore.view.chooseImg.ImageBucketActivity;
import com.juggist.jcore.view.chooseImg.config.Bimp;
import com.luojilab.component.componentlib.router.ui.UIRouter;
import com.luojilab.component.componentlib.service.JsonService;
import com.orhanobut.logger.Logger;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * 提交订单
 */
@RuntimePermissions
public class OrderSubmitActivity extends BackBaseActivity {

    @BindView(R2.id.buy_lv)
    NoScrollRecycleView lv;
    @BindView(R2.id.buy_rl_discount_card)
    RelativeLayout rlDiscountCard;
    @BindView(R2.id.buy_tv_discount_card)
    TextView tvDiscountCard;
    @BindView(R2.id.buy_tv_total_money02)
    TextView tvTotalMoney02;
    @BindView(R2.id.buy_tv_total_money)
    TextView tvTotalMoney;
    @BindView(R2.id.buy_tv_total_num)
    TextView tvTotalNum;
    @BindView(R2.id.buy_tv_order_submit)
    TextView tvOrderSubmit;
    @BindView(R2.id.buy_tv_name)
    TextView tvName;
    @BindView(R2.id.buy_tv_phone)
    TextView tvPhone;
    @BindView(R2.id.buy_tv_address)
    TextView tvAddress;
    @BindView(R2.id.buy_tv_add_address)
    TextView tvAddAddress;
    @BindView(R2.id.buy_ll_add_address)
    LinearLayout llAddAddress;

    private OrderSubmitContract.Present present;
    private OrderSubmitAdapter adapter;

    private static final int TAKE_PICTURE = 0x000000;
    private String pickImagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        present.detach();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.buy_activity_order_submit;
    }

    @Override
    protected void initView() {
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.item_bg))
                .sizeResId(R.dimen.dp_1)
                .build());

        SpannableString spannableString = new SpannableString(getResources().getString(R.string.buy_address_add_two));
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.font_select));
        spannableString.setSpan(colorSpan, 9, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvAddAddress.setText(spannableString);
    }


    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        parseBundle();
        initAddress();
        initAdapter();
        initDiscountCard("");
        initCalculate(0);
    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.buy_title_order_submit);
    }


    private void parseBundle() {
        if (getIntent() != null) {
            OrderCreateTmpBean orderPreBean = (OrderCreateTmpBean) getIntent().getSerializableExtra("OrderPreBean");
            new OrderSubmitPresent(viewModel,orderPreBean);
        }
    }

    /**
     * 初始化地址
     */
    private void initAddress() {
        AddressBean addressBean = present.getOrderCreateTmpBean().getAddress();
        if (!addressBean.isEmpty()) {
            tvName.setText(String.format(getResources().getString(R.string.buy_address_user_name), addressBean.getConsignee()));
            tvAddress.setText(addressBean.getProvince_name() + addressBean.getCity_name() + addressBean.getAreas_name() + addressBean.getAddr());
            tvPhone.setText(addressBean.getCellphone());
            llAddAddress.setVisibility(View.GONE);
        } else {
            llAddAddress.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new OrderSubmitAdapter(R.layout.buy_adapter_order_submit_item, present.getOrderCreateTmpBean().getGoods_list(), this);
        lv.setAdapter(adapter);
    }

    /*
     *初始化优惠券
     */
    private void initDiscountCard(String content) {
        OrderCreateTmpBean orderCreateTmpBean = present.getOrderCreateTmpBean();
        tvDiscountCard.setText(!TextUtils.isEmpty(content) ? content : orderCreateTmpBean.getAvailable_coupon() == null ? "0张可用" : orderCreateTmpBean.getAvailable_coupon().size() + "张可用");
    }

    /**
     * 初始化计算
     */
    private void initCalculate(float unlimit) {
        OrderCreateTmpBean orderCreateTmpBean = present.getOrderCreateTmpBean();
        tvTotalNum.setText("共" + orderCreateTmpBean.getGoods_list().size() + "件商品");
        tvTotalMoney02.setText("￥" + (Float.parseFloat(orderCreateTmpBean.getTotal_price()) - unlimit));
        tvTotalMoney.setText("￥" + (Float.parseFloat(orderCreateTmpBean.getTotal_price()) - unlimit));
    }
    @OnClick({R2.id.buy_rl_discount_card, R2.id.buy_tv_order_submit,R2.id.buy_rl_address})
    public void onViewClicked(View view) {
        int id = view.getId();
        if(id == R.id.buy_rl_discount_card){
            Intent intent = new Intent(OrderSubmitActivity.this, DiscountCardChooseActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("OrderPreBean", present.getOrderCreateTmpBean());
            intent.putExtras(bundle);
            startActivity(intent);
        }else if(id == R.id.buy_tv_order_submit){
            present.submitOrder();
        }else if(id == R.id.buy_rl_address){
            TargetBean target = new TargetBean(1);
            Bundle bundle = new Bundle();
            bundle.putString("target",JsonService.Factory.getSingletonImpl().toJsonString(target));
            UIRouter.getInstance().openUri(this,"ZZMK://address/list",bundle);
        }
    }

    /**
     * 身份证dialog回调事件
     */
    private class DialogIdListener implements DialogID.Listener{

        @Override
        public void showChoosePicDialog() {
            new ActionSheetDialog(OrderSubmitActivity.this)
                    .builder()
                    .setTitle(getResources().getString(R.string.choose_pic))
                    .setCancelable(true)
                    .setCanceledOnTouchOutside(false)
                    .addSheetItem(getResources().getString(R.string.choose_pic_from_album), ActionSheetDialog.SheetItemColor.Blue,
                            new ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    OrderSubmitActivityPermissionsDispatcher.openAlbumWithPermissionCheck(OrderSubmitActivity.this);
                                }
                            })
                    .addSheetItem(getResources().getString(R.string.choose_pic_from_camera), ActionSheetDialog.SheetItemColor.Blue,
                            new ActionSheetDialog.OnSheetItemClickListener() {
                                @Override
                                public void onClick(int which) {
                                    OrderSubmitActivityPermissionsDispatcher.openCameraWithPermissionCheck(OrderSubmitActivity.this);
                                }
                            })
                    .show();
        }

        @Override
        public void createOrder(String id, String cn_id_bg, String cd_id_front) {
            present.createIDOrder(id,cn_id_bg,cd_id_front);
        }

    }
    /**
     * EvenBus
     */
    //使用优惠券
    @Subscribe
    public void useDiscount(DiscountCardEvent.UseDiscountCard event) {
        present.updateDiscountCard(event.getPosition());
        initCalculate(Float.parseFloat(event.getUnlimit()));
        initDiscountCard(event.getUnlimit() + "元优惠券");
    }

    //刷新地址
    @Subscribe
    public void updateAddress(AddressEvent.AddressChoose event) {
        Logger.d("updateAddress");
        present.updateAddress(event.getAddressBean());
        initAddress();
    }

    OrderSubmitContract.View viewModel = new OrderSubmitContract.View() {
        DialogID di = null;
        @Override
        public void showIDDialog(int user_info) {
            di = new DialogID(user_info,new OrderSubmitActivity.DialogIdListener());
            FragmentTransaction ft = OrderSubmitActivity.this.getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            di.show(ft, "di");
        }

        @Override
        public void createOrderSucceed(OrderCreateBean orderCreateBean) {
            if(di != null)
                di.dismiss();
            Bundle bundle = new Bundle();
            bundle.putSerializable("OrderCreateBean",orderCreateBean);
            gotoActivity(OrderPayActivity.class,bundle);
        }

        @Override
        public void createOrderFail(String msg) {
            showErrorDialog(msg);
        }

        @Override
        public void setPresent(OrderSubmitContract.Present present) {
            OrderSubmitActivity.this.present = present;
        }

        @Override
        public void showErrorDialog(String message) {
            ToastUtil.showLong(message);
        }

        @Override
        public void showLoading() {
            OrderSubmitActivity.this.showLoading();
        }

        @Override
        public void dismissLoading() {
            OrderSubmitActivity.this.dismissLoading();
        }
    };
    /**
     * 动态权限
     */
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void openAlbum() {
        Intent intent = new Intent(OrderSubmitActivity.this,
                ImageBucketActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OrderSubmitActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void openAlbumRationale(final PermissionRequest request) {
        pickImgPermissionFail();
    }

    @OnPermissionDenied({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void openAlbumDenied() {
        pickImgPermissionFail();
    }

    @OnNeverAskAgain({Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void openAlbumNever() {
        pickImgPermissionFail();
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void openCamera() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory()
                + Constants.PATH.PATH_ID_PIC, String.valueOf(System.currentTimeMillis())
                + ".jpg");
        if (!new File(Environment.getExternalStorageDirectory()
                + Constants.PATH.PATH_ID_PIC).exists()) {
            new File(Environment.getExternalStorageDirectory()
                    + Constants.PATH.PATH_ID_PIC).mkdirs();
        }
        pickImagePath = file.getPath();
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, pickImagePath);
        Uri imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void openCameraRationale(final PermissionRequest request) {
        pickImgPermissionFail();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void openCameraDenied() {
        pickImgPermissionFail();
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void openCameraNever() {
        pickImgPermissionFail();
    }

    private void pickImgPermissionFail() {
        showPermissionFail(getResources().getString(R.string.choose_pic_permission_fail),getResources().getString(R.string.choose_pic_permission_todo));
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (resultCode == -1){
                    if (Bimp.drr.size() < Bimp.MAX_SIZE) {
                        Bimp.drr.add(pickImagePath);
                    }
                }
                break;
        }
    }
}
