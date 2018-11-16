package com.juggist.baseandroid.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.present.home.BatchForwardPresent;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.baseandroid.ui.home.adapter.BatchForwardItemAdapter;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.DialogSessionSetting;
import com.juggist.jcore.bean.ProductBean;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 批量转发
 */
public class BatchForwardActivity extends BackBaseActivity {

    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.tv_batch_forward)
    TextView tvBatchForward;

    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;

    private BatchForwardContract.Present present;
    private BatchForwardItemAdapter adapter;

    @Override
    protected void onDestroy() {
        present.detach();
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_batch_forward;
    }

    @Override
    protected void initView() {
        iv_setting.setVisibility(View.VISIBLE);
        tvBatchForward.setText(String.format(getResources().getString(R.string.product_batch_forward),"0"));

        statusView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.view_net_error, null);
        statusIv = statusView.findViewById(R.id.lv_iv);
        statusTv = statusView.findViewById(R.id.lv_tv);

        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .color(getResources().getColor(R.color.color_invitecode))
                .sizeResId(R.dimen.dp_1)
                .marginResId(R.dimen.dp_80,R.dimen.dp_0)
                .build());
    }

    @Override
    protected void initListener() {
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSessionSetting dss = new DialogSessionSetting();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                dss.show(ft, "dss");
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
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        String group_id = bundle.getString("group_id");

        initAdapter();
        new BatchForwardPresent(new ViewModel(), group_id);
        present.start();

    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.title_batch_forward);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new BatchForwardItemAdapter(R.layout.adapter_batch_forward_item, new ArrayList<ProductBean.DataBean.GoodsListBean>(), this);
        adapter.setEmptyView(statusView);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.ibtn_select:
                        int selectCount = BatchForwardActivity.this.adapter.updateSelect(position);
                        tvBatchForward.setText(String.format(getResources().getString(R.string.product_batch_forward),String.valueOf(selectCount)));
                        break;
                }
            }
        });
        lv.setAdapter(adapter);
    }


    private class ViewModel implements BatchForwardContract.View {

        @Override
        public void getListEmpty() {
            statusTv.setText(getResources().getString(R.string.lv_data_empty));
        }

        @Override
        public void getListSucceed(List<ProductBean.DataBean.GoodsListBean> dataBeans) {
            adapter.update(dataBeans);
        }

        @Override
        public void getListFail(String extMsg) {
            showErrorDialog(extMsg);
            statusTv.setText(getResources().getString(R.string.lv_net_error));
        }

        @Override
        public void setPresent(BatchForwardContract.Present present) {
            BatchForwardActivity.this.present = present;
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
