package com.juggist.baseandroid.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.eventbus.HomeTabChangeEvent;
import com.juggist.baseandroid.present.home.SessionPresent;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.baseandroid.ui.BigViewPagerPhotoActivity;
import com.juggist.baseandroid.ui.HomeActivity;
import com.juggist.baseandroid.ui.home.adapter.SessionItemAdapter;
import com.juggist.baseandroid.ui.home.adapter.SessionItemAdapter.Listener;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.AlertDialog;
import com.juggist.baseandroid.view.CreateShareView;
import com.juggist.baseandroid.view.DialogDownload;
import com.juggist.baseandroid.view.DialogForBuy;
import com.juggist.baseandroid.view.DialogSessionSetting;
import com.juggist.jcore.base.SmartRefreshViewModel;
import com.juggist.jcore.bean.ProductBean;
import com.juggist.jcore.bean.ShopCarBean;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SessionActivity extends BackBaseActivity implements CreateShareView.SaveBitmapListener{

    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.tv_shopping_num)
    TextView tvShoppingNum;
    @BindView(R.id.shoppingCart)
    ConstraintLayout shoppingCart;
    @BindView(R.id.createShareView)
    CreateShareView createShareView;

    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;

    private SessionItemAdapter adapter;
    private SessionContract.Present present;

    private int addPrice = 0;//加价
    private ProductBean.DataBean.GoodsListBean goodsListBean;//当前选择的商品

    private static final int PERMISSION_MEGER_PIC = 0;//合并图片
    private static final int PERMISSION_PIC = 1;//直接保存合并图片
    private int permissionTag = PERMISSION_MEGER_PIC;
    @Override
    protected void onDestroy() {
        present.detach();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_session;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initView() {
        iv_setting.setVisibility(View.VISIBLE);

        statusView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_net_error, null);
        statusIv = statusView.findViewById(R.id.lv_iv);
        statusTv = statusView.findViewById(R.id.lv_tv);

        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.item_bg))
                .sizeResId(R.dimen.dp_30)
                .build());


    }

    @Override
    protected void initListener() {
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSessionSetting dss = new DialogSessionSetting(new MySettingListener());
                FragmentTransaction ft = SessionActivity.this.getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                dss.show(ft, "dss");
            }
        });
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                present.loadMoreOnSellProductsList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                present.refreshOnSellProductsList();
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
        //跳转购物车页面
        shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new HomeTabChangeEvent.TabChange(HomeActivity.PAGE_BUY));
                SessionActivity.this.finish();
            }
        });
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        String group_name = bundle.getString("group_name");
        String group_id = bundle.getString("group_id");

        tv_title.setText(group_name);

        initAdapter();
        new SessionPresent(new ViewModel(), group_id);
        present.start();
    }

    @Override
    protected String getTitile() {
        return null;
    }


    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new SessionItemAdapter(R.layout.adapter_session_item, new ArrayList<ProductBean.DataBean.GoodsListBean>(), this, new AdapterListener());
        adapter.setEmptyView(statusView);
        lv.setAdapter(adapter);
    }


    /**
     * 保存图片返回结果
     * @param saveAble
     */
    @Override
    public void saveResult(boolean saveAble) {
        dismissLoading();
        if (saveAble) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    dismissLoading();
                    new AlertDialog(SessionActivity.this).builder()
                            .setTitle(getResources().getString(R.string.save_bitmap_succeed))
                            .setMsg(getResources().getString(R.string.save_bitmap_succeed_todo))
                            .show();
                }
            });
        } else {
            Logger.d("保存失败，请重试");
        }
    }

    /**
     * 适配器listener
     */
    private class AdapterListener implements Listener {

        @Override
        public void toBigPic(ArrayList<String> picUrls, int position) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("picUrl", picUrls);
            bundle.putInt("position", position);
            Intent intent = new Intent(SessionActivity.this, BigViewPagerPhotoActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void toBuy(ProductBean.DataBean.GoodsListBean goodsListBean,int positin) {
            FragmentTransaction ft = SessionActivity.this.getSupportFragmentManager().beginTransaction();
            DialogForBuy dfb = new DialogForBuy(goodsListBean,new ShopCarListener(),positin);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            dfb.show(ft, "dfb");
        }

        @Override
        public void toDownload(int position) {
            present.preparDownload(position);
            FragmentTransaction ft = SessionActivity.this.getSupportFragmentManager().beginTransaction();
            DialogDownload dd = new DialogDownload(new DownLoadListener());
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            dd.show(ft, "dd");
        }

        @Override
        public void toSale(ProductBean.DataBean.GoodsListBean goodsListBean) {
            SessionActivity.this.goodsListBean = goodsListBean;
            permissionTag = PERMISSION_MEGER_PIC;
            SessionActivityPermissionsDispatcher.saveShareBitmapWithPermissionCheck(SessionActivity.this);
        }
    }

    /**
     * 下载监听事件
     */
    private class DownLoadListener implements DialogDownload.Listener {

        @Override
        public void startDownload() {
            permissionTag = PERMISSION_PIC;
            SessionActivityPermissionsDispatcher.saveShareBitmapWithPermissionCheck(SessionActivity.this);
        }
    }

    /**
     * 设置回调事件
     */
    private class MySettingListener implements DialogSessionSetting.SettingListener {

        @Override
        public void addPrice(int addPrice) {
            SessionActivity.this.addPrice = addPrice;
        }
    }

    /**
     * 添加到购物车回调事件
     *
     */
    private class ShopCarListener implements DialogForBuy.Listener{

        @Override
        public void addShop(int position,int num) {
            present.addShop(position,num);
        }
    }
    private class ViewModel extends SmartRefreshViewModel<ProductBean.DataBean.GoodsListBean> implements SessionContract.View {


        @Override
        public SmartRefreshLayout getSmartRefreshLayout() {
            return srl;
        }

        @Override
        public BaseQuickAdapter getBaseAdapter() {
            return adapter;
        }

        @Override
        public void getListEmpty() {
            super.getListEmpty();
            statusTv.setText(getResources().getString(R.string.lv_data_empty));
        }

        @Override
        public void getListEmptyFail(String extMsg) {
            super.getListEmptyFail(extMsg);
            showErrorDialog(extMsg);
            statusTv.setText(getResources().getString(R.string.lv_net_error));
        }

        @Override
        public void getListFail(String extMsg, boolean refresh) {
            super.getListFail(extMsg, refresh);
            showErrorDialog(extMsg);

        }

        @Override
        public void downloadShareSucceed() {
            ToastUtil.showLong(getResources().getString(R.string.save_bitmap_succeed_todo));
            SessionActivity.this.dismissLoading();
        }

        @Override
        public void downloadShareFail(final String msg) {
            showErrorDialog(msg);
            SessionActivity.this.dismissLoading();
        }


        @Override
        public void queryShopCarSucceed(ArrayList<ShopCarBean> shopCarBeans) {
            tvShoppingNum.setText(String.valueOf(shopCarBeans.size()));
        }

        @Override
        public void queryShopCarFail(String extMsg) {
            showErrorDialog(extMsg);
        }

        @Override
        public void addShopCarSucceed(String msg) {
            ToastUtil.showLong(getResources().getString(R.string.toast_add_shopcar_succeed));
        }

        @Override
        public void addShopCarFail(String extMsg) {
            showErrorDialog(extMsg);
        }


        @Override
        public void setPresent(SessionContract.Present present) {
            SessionActivity.this.present = present;
        }

        @Override
        public void showErrorDialog(String message) {
            ToastUtil.showLong(message);
        }

        @Override
        public void showLoading() {
            statusTv.setText(getResources().getString(R.string.lv_loading));
            SessionActivity.this.showLoading();
        }

        @Override
        public void dismissLoading() {
            SessionActivity.this.dismissLoading();
        }
    }


    /**
     * 动态权限
     */
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void saveShareBitmap() {
        showLoading();
        if(permissionTag == PERMISSION_MEGER_PIC){
            createShareView.setData(goodsListBean,addPrice);
        }else if(permissionTag == PERMISSION_PIC){
            present.startDownload();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SessionActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void saveShareBitmapRationale(final PermissionRequest request) {
        showPermissionSaveShareBitmapFail();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void saveShareBitmapDenied() {
        showPermissionSaveShareBitmapFail();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void saveShareBitmapNever() {
        showPermissionSaveShareBitmapFail();
    }
}
