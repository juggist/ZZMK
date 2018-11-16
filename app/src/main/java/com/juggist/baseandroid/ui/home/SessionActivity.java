package com.juggist.baseandroid.ui.home;

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
import com.juggist.baseandroid.view.DialogDownload;
import com.juggist.baseandroid.view.DialogForBuy;
import com.juggist.baseandroid.view.DialogSessionSetting;
import com.juggist.jcore.base.BaseUpdateAdapter;
import com.juggist.jcore.base.SmartRefreshViewModel;
import com.juggist.jcore.bean.ProductBean;
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

public class SessionActivity extends BackBaseActivity {

    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.tv_shopping_num)
    TextView tvShoppingNum;
    @BindView(R.id.shoppingCart)
    ConstraintLayout shoppingCart;

    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;

    private SessionItemAdapter adapter;
    private SessionContract.Present present;
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
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.ibtn_sale:
                        break;
                    case R.id.ibtn_download:
                        FragmentTransaction ft = SessionActivity.this.getSupportFragmentManager().beginTransaction();
                        DialogDownload dd = new DialogDownload();
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        dd.show(ft, "dd");
                        break;
                }

            }
        });
        lv.setAdapter(adapter);
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
        public void toBuy(ProductBean.DataBean.GoodsListBean goodsListBean) {
            FragmentTransaction ft = SessionActivity.this.getSupportFragmentManager().beginTransaction();
            DialogForBuy dfb = new DialogForBuy(goodsListBean);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            dfb.show(ft, "dfb");
        }
    }

    private class ViewModel extends SmartRefreshViewModel<ProductBean.DataBean.GoodsListBean> implements SessionContract.View {
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
        }

        @Override
        public void dismissLoading() {

        }


    }
}
