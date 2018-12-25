package com.juggist.discover.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juggist.discover.R;
import com.juggist.discover.R2;
import com.juggist.discover.present.DiscoverPresent;
import com.juggist.discover.ui.adapter.DiscoverAdapter;
import com.juggist.jcore.BigViewPagerPhotoActivity;
import com.juggist.jcore.SmartRefreshViewModel;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.bean.ArticleBean;
import com.juggist.jcore.utils.ToastUtil;
import com.juggist.jcore.view.DialogDownload;
import com.juggist.jcore.view.DialogShare;
import com.juggist.jcore.view.StatusView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import androidx.annotation.NonNull;
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

/**
 *
 */
@RuntimePermissions
public class DiscoverFragment extends BaseFragment {

    @BindView(R2.id.discover_lv)
    RecyclerView lv;
    @BindView(R2.id.discover_srl)
    SmartRefreshLayout srl;


    private StatusView statusView;
    private DiscoverContract.Present present;
    private DiscoverAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.discover_fragment;
    }

    @Override
    protected void initView(View view) {
        statusView = new StatusView(getActivity());

        lv.setLayoutManager(new LinearLayoutManager(getActivity()));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getResources().getColor(R.color.item_bg))
                .sizeResId(R.dimen.dp_20)
                .build());
    }

    @Override
    protected void initListener() {
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                present.loadMoreArticleList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                present.refreshArticleList();
            }
        });
        //网络异常，点击屏幕重新加载
        statusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(statusView.getStatusTv().equals(getResources().getString(R.string.lv_net_error))){
                    present.start();
                }
            }
        });
    }

    @Override
    protected void initData() {
        initAdapter();
        new DiscoverPresent(new ViewModel());
        present.start();
    }

    @Override
    public void onDetach() {
        present.detach();
        super.onDetach();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new DiscoverAdapter(R.layout.discover_adapter_discover_item,new ArrayList<ArticleBean>(),getActivity(),new AdapterListener());
        adapter.setEmptyView(statusView);
        lv.setAdapter(adapter);
    }

    private class AdapterListener implements DiscoverAdapter.Listener {

        @Override
        public void share() {
            DialogShare ds = new DialogShare();
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ds.show(ft, "ds");
        }

        @Override
        public void download(int position) {
            present.preparDownload(position);
            DialogDownload dd = new DialogDownload(new DownLoadListener());
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            dd.show(ft, "dd");
        }

        @Override
        public void toBigPic(ArrayList<String> urls, int position) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("picUrl",urls);
            bundle.putInt("position",position);
            Intent intent = new Intent(getActivity(), BigViewPagerPhotoActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }
    /**
     * 下载监听事件
     */
    private class DownLoadListener implements DialogDownload.Listener {

        @Override
        public void startDownload() {
            DiscoverFragmentPermissionsDispatcher.saveShareBitmapWithPermissionCheck(DiscoverFragment.this);
        }
    }

    private class ViewModel extends SmartRefreshViewModel<ArticleBean> implements DiscoverContract.View {


        @Override
        public void getListEmpty() {
            super.getListEmpty();
            statusView.setStatusTv(R.string.lv_data_empty);
        }

        @Override
        public void getListEmptyFail(String extMsg) {
            super.getListEmptyFail(extMsg);
            showErrorDialog(extMsg);
            statusView.setStatusTv(R.string.lv_net_error);
        }

        @Override
        public void getListFail(String extMsg, boolean refresh) {
            super.getListFail(extMsg,refresh);
            showErrorDialog(extMsg);
        }
        @Override
        public void downloadShareSucceed() {
            ToastUtil.showLong(getResources().getString(R.string.save_bitmap_succeed_todo));
            DiscoverFragment.this.dismissLoading();
        }

        @Override
        public void downloadShareFail(final String msg) {
            showErrorDialog(msg);
            DiscoverFragment.this.dismissLoading();
        }


        @Override
        public void setPresent(DiscoverContract.Present present) {
            DiscoverFragment.this.present = present;
        }

        @Override
        public void showErrorDialog(String message)  {
            ToastUtil.showLong(message);
        }

        @Override
        public void showLoading() {
            statusView.setStatusTv(R.string.lv_loading);
        }

        @Override
        public void dismissLoading() {

        }

        @Override
        public SmartRefreshLayout getSmartRefreshLayout() {
            return srl;
        }

        @Override
        public BaseQuickAdapter getBaseAdapter() {
            return adapter;
        }
    }

    /**
     * 动态权限
     */
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void saveShareBitmap() {
        showLoading();
        present.startDownload();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        DiscoverFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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