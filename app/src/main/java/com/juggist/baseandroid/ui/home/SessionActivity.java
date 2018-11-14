package com.juggist.baseandroid.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.eventbus.HomeTabChangeEvent;
import com.juggist.baseandroid.present.home.SessionPresent;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.baseandroid.ui.BigViewPagerPhotoActivity;
import com.juggist.baseandroid.ui.HomeActivity;
import com.juggist.baseandroid.ui.home.adapter.SessionItemAdapter;
import com.juggist.baseandroid.ui.home.adapter.SessionItemAdapter.Listener;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.DialogDownload;
import com.juggist.baseandroid.view.DialogForBuy;
import com.juggist.baseandroid.view.DialogSessionSetting;
import com.juggist.jcore.bean.ProductBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

public class SessionActivity extends BackBaseActivity {

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
    @BindView(R.id.tv_shopping_num)
    TextView tvShoppingNum;
    @BindView(R.id.shoppingCart)
    ConstraintLayout shoppingCart;

    private SessionItemAdapter adapter;
    private SessionContract.Present present;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_session;
    }

    @Override
    protected void initView() {
        iv_setting.setVisibility(View.VISIBLE);
        lv.setEmptyView(lvLl);
    }

    @Override
    protected void initListener() {
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSessionSetting dss = new DialogSessionSetting();
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
        lvLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvTv.getText().toString().equals(getResources().getString(R.string.lv_net_error))) {
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

        new SessionPresent(new ViewModel(), group_id);
        initAdapter();
        present.start();


    }

    @Override
    protected String getTitile() {
        return null;
    }

    @Override
    protected void onDestroy() {
        present.detach();
        super.onDestroy();
    }

    private void parseBundle() {


    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new SessionItemAdapter(this, new AdapterListener());
        lv.setAdapter(adapter);
    }


    /**
     * 适配器listener
     */
    private class AdapterListener implements Listener {
        @Override
        public void download() {
            DialogDownload dd = new DialogDownload();
            FragmentTransaction ft = SessionActivity.this.getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            dd.show(ft, "dd");
        }

        @Override
        public void buy(ProductBean.DataBean.GoodsListBean goodsListBean) {
            DialogForBuy dfb = new DialogForBuy(goodsListBean);
            FragmentTransaction ft = SessionActivity.this.getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            dfb.show(ft, "dfb");
        }

        @Override
        public void toBigPic(ArrayList<String> picUrls, int position) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("picUrl", picUrls);
            bundle.putInt("position", position);
            Intent intent = new Intent(SessionActivity.this, BigViewPagerPhotoActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private class ViewModel implements SessionContract.View {

        @Override
        public void getOnSellProductsListEmpty() {
            srl.finishRefresh();
            lvTv.setText(getResources().getString(R.string.lv_data_empty));
        }

        @Override
        public void getOnSellProductsListSucceed(ArrayList<ProductBean.DataBean.GoodsListBean> dataBeans, boolean refresh) {
            adapter.update(dataBeans);
            if (refresh) {
                srl.finishRefresh();
            } else {
                srl.finishLoadMore();
            }
        }

        @Override
        public void getOnSellProductsListSucceedEnd(ArrayList<ProductBean.DataBean.GoodsListBean> dataBeans, boolean refresh) {
            adapter.update(dataBeans);
            if (refresh) {
                srl.finishRefresh();
                srl.setNoMoreData(true);
            } else {
                srl.finishLoadMoreWithNoMoreData();
            }
        }

        @Override
        public void getOnSellProductsListEmptyFail(String extMsg) {
            showErrorDialog(extMsg);
            srl.finishRefresh();
            lvTv.setText(getResources().getString(R.string.lv_net_error));
        }

        @Override
        public void getOnSellProductsListFail(String extMsg, boolean refresh) {
            showErrorDialog(extMsg);
            if (refresh) {
                srl.finishRefresh();
            } else {
                srl.finishLoadMore();
            }
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
            lvTv.setText(getResources().getString(R.string.lv_loading));
        }

        @Override
        public void dismissLoading() {

        }
    }
}
