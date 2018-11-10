package com.juggist.baseandroid.ui.discover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.present.discover.DiscoverPresent;
import com.juggist.baseandroid.ui.BigViewPagerPhotoActivity;
import com.juggist.baseandroid.ui.discover.adapter.DiscoverAdapter;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.DialogDownload;
import com.juggist.baseandroid.view.DialogShare;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.bean.ArticleBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 *
 */
public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.lv_iv)
    ImageView lvIv;
    @BindView(R.id.lv_tv)
    TextView lvTv;
    @BindView(R.id.lv_ll)
    LinearLayout lvLl;

    private DiscoverContract.Present present;
    private DiscoverAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discover;
    }

    @Override
    protected void initView() {
        lv.setEmptyView(lvLl);
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
        lvLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lvTv.getText().toString().equals(getResources().getString(R.string.lv_net_error))){
                    present.start();
                }
            }
        });
    }

    @Override
    protected void initData() {
        new DiscoverPresent(new ViewModel());
        initAdapter();
        present.start();
    }



    @Override
    public void onDestroyView() {
        present.detach();
        super.onDestroyView();
    }
    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new DiscoverAdapter(getActivity(), new AdapterListener());
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
        public void download() {
            DialogDownload dd = new DialogDownload();
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

    private class ViewModel implements DiscoverContract.View {

        @Override
        public void getArticleListEmpty() {
            srl.finishRefresh();
            lvTv.setText(getResources().getString(R.string.lv_data_empty));
        }

        @Override
        public void getArticleListSucceed(ArrayList<ArticleBean> articleBeans, boolean refresh) {
            adapter.update(articleBeans);
            if (refresh) {
                srl.finishRefresh();
            } else {
                srl.finishLoadMore();
            }
        }

        @Override
        public void getArticleListSucceedEnd(ArrayList<ArticleBean> articleBeans, boolean refresh) {
            adapter.update(articleBeans);
            if (refresh) {
                srl.finishRefresh();
                srl.setNoMoreData(true);
            } else {
                srl.finishLoadMoreWithNoMoreData();
            }
        }

        @Override
        public void getArticleListEmptyFail(String extMsg) {
            showErrorDialog(extMsg);
            srl.finishRefresh();
            lvTv.setText(getResources().getString(R.string.lv_net_error));
        }

        @Override
        public void getArticleListFail(String extMsg, boolean refresh) {
            showErrorDialog(extMsg);
            if (refresh) {
                srl.finishRefresh();
            } else {
                srl.finishLoadMore();
            }
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
            lvTv.setText(getResources().getString(R.string.lv_loading));
        }

        @Override
        public void dismissLoading() {

        }
    }
}