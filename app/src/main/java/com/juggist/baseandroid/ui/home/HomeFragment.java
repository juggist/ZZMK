package com.juggist.baseandroid.ui.home;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.present.home.HomePresent;
import com.juggist.baseandroid.ui.home.adapter.HomeItemAdapter;
import com.juggist.baseandroid.utils.GlideImageLoader;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.bean.SessionBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements HomeItemAdapter.OnClickListener {

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

    private Banner banner;

    private HomeContract.Present present;
    private HomeItemAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        View headView = LayoutInflater.from(getActivity()).inflate(R.layout.view_banner, null);
        banner = headView.findViewById(R.id.banner);
        lv.addHeaderView(headView);
        lv.setEmptyView(lvLl);
    }

    @Override
    protected void initListener() {
        srl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                present.loadMoreSessionList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                present.refreshSessionList();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toSessionActivity();
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
        new HomePresent(new ViewModel());
        initBanner();
        initAdapter();
        present.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopAutoPlay();
    }

    /**
     * 跳转专场
     */
    @Override
    public void toSessionActivity() {
        startActivity(new Intent(getActivity(), SessionActivity.class));
    }

    /**
     * 初始化banner
     */
    private void initBanner() {
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(new ArrayList<>());
        banner.setDelayTime(4000);
        banner.start();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new HomeItemAdapter(getActivity(), this);
        lv.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        present.detach();
        super.onDestroyView();
    }



    private class ViewModel implements HomeContract.View {


        @Override
        public void getBannerSucceed(ArrayList<String> banners) {
            banner.update(banners);
        }

        @Override
        public void getBannerFail(String extMsg) {
            showErrorDialog(extMsg);
        }

        @Override
        public void getSessionListEmpty() {
            srl.finishRefresh();
            lvTv.setText(getResources().getString(R.string.lv_data_empty));
        }

        @Override
        public void getSessionListSucceed(ArrayList<SessionBean.DataBean> dataBeans, boolean refresh) {
            adapter.update(dataBeans);
            if (refresh) {
                srl.finishRefresh();
            } else {
                srl.finishLoadMore();
            }
        }

        @Override
        public void getSessionListSucceedEnd(ArrayList<SessionBean.DataBean> dataBeans, boolean refresh) {
            adapter.update(dataBeans);
            if (refresh) {
                srl.finishRefresh();
                srl.setNoMoreData(true);
            } else {
                srl.finishLoadMoreWithNoMoreData();
            }
        }

        @Override
        public void getSessionListEmptyFail(String extMsg) {
            showErrorDialog(extMsg);
            srl.finishRefresh();
            lvTv.setText(getResources().getString(R.string.lv_net_error));
        }

        @Override
        public void getSessionListFail(String extMsg, boolean refresh) {
            showErrorDialog(extMsg);
            if (refresh) {
                srl.finishRefresh();
            } else {
                srl.finishLoadMore();
            }
        }

        @Override
        public void setPresent(HomeContract.Present present) {
            HomeFragment.this.present = present;
        }

        @Override
        public void showErrorDialog(String message) {
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
