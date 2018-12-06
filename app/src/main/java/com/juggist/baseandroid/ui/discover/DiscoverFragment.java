package com.juggist.baseandroid.ui.discover;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.present.discover.DiscoverPresent;
import com.juggist.baseandroid.ui.BaseFragment;
import com.juggist.baseandroid.ui.BigViewPagerPhotoActivity;
import com.juggist.baseandroid.ui.discover.adapter.DiscoverAdapter;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.DialogDownload;
import com.juggist.baseandroid.view.DialogShare;
import com.juggist.jcore.base.BaseUpdateAdapter;
import com.juggist.jcore.base.SmartRefreshViewModel;
import com.juggist.jcore.bean.ArticleBean;
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

    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;

    private DiscoverContract.Present present;
    private DiscoverAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView() {
        statusView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_net_error,null);
        statusIv = statusView.findViewById(R.id.lv_iv);
        statusTv = statusView.findViewById(R.id.lv_tv);

        lv.setLayoutManager(new LinearLayoutManager(getContext()));
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
                if(statusTv.getText().toString().equals(getResources().getString(R.string.lv_net_error))){
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
        adapter = new DiscoverAdapter(R.layout.adapter_discover_item,new ArrayList<ArticleBean>(),getActivity(),new AdapterListener());
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
            statusTv.setText(getResources().getString(R.string.lv_loading));
        }

        @Override
        public void dismissLoading() {

        }

        @Override
        public SmartRefreshLayout getSmartRefreshLayout() {
            return srl;
        }

        @Override
        public BaseUpdateAdapter getBaseAdapter() {
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

    @SuppressLint("NeedOnRequestPermissionsResult")
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