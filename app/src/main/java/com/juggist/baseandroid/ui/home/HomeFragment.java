package com.juggist.baseandroid.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.juggist.baseandroid.GlideApp;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.present.home.HomePresent;
import com.juggist.baseandroid.ui.home.adapter.HomeItemAdapter;
import com.juggist.baseandroid.utils.GlideImageLoader;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.base.BaseUpdateAdapter;
import com.juggist.jcore.base.SmartRefreshViewModel;
import com.juggist.jcore.bean.SessionBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;

import java.util.ArrayList;

import androidx.annotation.NonNull;
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

    private ViewModel viewModel;

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
                //存在header，所以原来的position会增加一个
                present.toSession(position - 1);
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

    /**
     * 跳转专场
     */
    @Override
    public void toSessionActivity(int position) {
        present.toSession(position);
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
        public BaseUpdateAdapter getBaseAdapter() {
            return adapter;
        }

        @Override
        public void getListEmpty() {
            super.getListEmpty();
            lvTv.setText(getResources().getString(R.string.lv_data_empty));
            GlideApp.with(getActivity()).load(getResources().getDrawable(R.drawable.home_pic_nonet)).into(lvIv);
        }

        @Override
        public void getListEmptyFail(String extMsg) {
            super.getListEmptyFail(extMsg);
            showErrorDialog(extMsg);
            lvTv.setText(getResources().getString(R.string.lv_net_error));
            GlideApp.with(getActivity()).load(getResources().getDrawable(R.drawable.home_pic_nonet)).into(lvIv);
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
            GlideApp.with(getActivity()).load(getResources().getDrawable(R.drawable.home_pic_nonet)).into(lvIv);
        }

        @Override
        public void dismissLoading() {

        }
    }
}
