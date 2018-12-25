package com.juggist.home.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juggist.home.R;
import com.juggist.home.R2;
import com.juggist.home.present.HomePresent;
import com.juggist.home.ui.adapter.HomeItemAdapter;
import com.juggist.home.view.GlideImageLoader;
import com.juggist.jcore.SmartRefreshViewModel;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.bean.SessionBean;
import com.juggist.jcore.utils.MyImageLoader;
import com.juggist.jcore.utils.ToastUtil;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

    @BindView(R2.id.home_lv)
    RecyclerView lv;
    @BindView(R2.id.home_srl)
    SmartRefreshLayout srl;

    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;

    private View headView;
    private Banner banner;

    private HomeContract.Present present;
    private HomeItemAdapter adapter;

    private ViewModel viewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView(View view) {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.home_view_banner, null);
        banner = headView.findViewById(R.id.home_banner);

        statusView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.view_net_error,null);
        statusIv = statusView.findViewById(R.id.lv_iv);
        statusTv = statusView.findViewById(R.id.lv_tv);

        lv.setLayoutManager(new LinearLayoutManager(getContext()));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(getActivity().getResources().getColor(R.color.item_bg))
                .sizeResId(R.dimen.dp_30)
                .build());
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
        initBanner();
        initAdapter();
        viewModel = new ViewModel();
        new HomePresent(viewModel);
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

    @Override
    public void onDestroy() {
        present.detach();
        super.onDestroy();

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
        adapter = new HomeItemAdapter(new ArrayList<SessionBean.DataBean>(),getActivity(),new AdapterListener());
        adapter.addHeaderView(headView);
        adapter.setEmptyView(statusView);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                present.toSession(position);
            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SuppressLint("InvalidR2Usage")
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.d("id = " +view.getId());
                int id = view.getId();
                if(id == R.id.home_ibtn_session_share){
                    present.toShare(position);
                }else if(id == R.id.home_ibtn_session_join){
                    present.toSession(position);
                }
            }
        });
        lv.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        present.detach();
        super.onDestroyView();
    }

    /**
     * 适配器回调事件
     */
    class AdapterListener implements HomeItemAdapter.Listener{

        @Override
        public void itemRecommendClick(int position) {
            present.toSession(position);
        }

        @Override
        public void itemRecommendClick(String groupName, String id) {
            present.toSession(groupName,id);
        }
    }

    private class ViewModel extends SmartRefreshViewModel<SessionBean.DataBean> implements HomeContract.View<SessionBean.DataBean> {


        @Override
        public void getBannerSucceed(ArrayList<String> banners) {
            banner.update(banners);
        }

        @Override
        public void getBannerFail(String extMsg) {
            showErrorDialog(extMsg);
        }

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
            MyImageLoader.getInstance().loadImage(R.drawable.home_pic_nonet,statusIv);
        }

        @Override
        public void getListEmptyFail(String extMsg) {
            super.getListEmptyFail(extMsg);
            showErrorDialog(extMsg);
            statusTv.setText(getResources().getString(R.string.lv_net_error));
            MyImageLoader.getInstance().loadImage(R.drawable.home_pic_nonet,statusIv);
        }

        @Override
        public void getListFail(String extMsg, boolean refresh) {
            super.getListFail(extMsg,refresh);
            showErrorDialog(extMsg);
        }

        @Override
        public void toSession(String group_name, String group_id) {
            Bundle bundle = new Bundle();
            bundle.putString("group_name",group_name);
            bundle.putString("group_id",group_id);
            Intent intent = new Intent(getActivity(),SessionActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }

        @Override
        public void toShare(String group_id) {
            Bundle bundle = new Bundle();
            bundle.putString("group_id",group_id);
            Intent intent = new Intent(getActivity(),BatchForwardActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
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
            statusTv.setText(getResources().getString(R.string.lv_loading));
            MyImageLoader.getInstance().loadImage(R.drawable.home_pic_nonet,statusIv);
        }

        @Override
        public void dismissLoading() {

        }
    }
}
