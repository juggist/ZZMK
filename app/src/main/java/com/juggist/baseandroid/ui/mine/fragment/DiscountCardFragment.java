package com.juggist.baseandroid.ui.mine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juggist.baseandroid.GlideApp;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.present.home.DiscountCardPresent;
import com.juggist.baseandroid.ui.BaseFragment;
import com.juggist.baseandroid.ui.mine.adapter.DiscountCardAdapter;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.jcore.base.SmartRefreshViewModel;
import com.juggist.jcore.bean.DiscountCardBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author juggist
 * @date 2018/11/15 5:02 PM
 */
public class DiscountCardFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "tag";
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;

    private LinearLayout statusView;
    private ImageView statusIv;
    private TextView statusTv;

    private DiscountCardContract.Present present;
    private DiscountCardAdapter adapter;

    private int tag = 0;
    public DiscountCardFragment() {
        // Required empty public constructor
    }

    public static DiscountCardFragment newInstance(int tag) {
        DiscountCardFragment fragment = new DiscountCardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, tag);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tag = getArguments().getInt(ARG_PARAM1);
        }
    }
    @Override
    public void onDestroyView() {
        present.detach();
        super.onDestroyView();

    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_discount_card;
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
                present.loadMoreDiscountCardList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                present.refreshDiscountCardList();
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
        new DiscountCardPresent(new ViewModel(),tag);
        present.start();
    }
    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new DiscountCardAdapter(R.layout.adapter_discount_card_item,new ArrayList<DiscountCardBean>(),getActivity());
        adapter.setEmptyView(statusView);
        lv.setAdapter(adapter);
    }
    private class ViewModel extends SmartRefreshViewModel<DiscountCardBean> implements DiscountCardContract.View{

        @Override
        public void getListEmpty() {
            super.getListEmpty();
            statusTv.setText(getResources().getString(R.string.lv_discount_card_empty));
            GlideApp.with(getActivity()).load(getResources().getDrawable(R.drawable.coupon_pic_nocoupon)).into(statusIv);
        }

        @Override
        public void getListEmptyFail(String extMsg) {
            super.getListEmptyFail(extMsg);
            showErrorDialog(extMsg);
            statusTv.setText(getResources().getString(R.string.lv_net_error));
            GlideApp.with(getActivity()).load(getResources().getDrawable(R.drawable.coupon_pic_nocoupon)).into(statusIv);
        }

        @Override
        public void getListFail(String extMsg, boolean refresh) {
            super.getListFail(extMsg,refresh);
            showErrorDialog(extMsg);
        }

        @Override
        public void setPresent(DiscountCardContract.Present present) {
            DiscountCardFragment.this.present = present;
        }

        @Override
        public void showErrorDialog(String message) {
            ToastUtil.showLong(message);
        }

        @Override
        public void showLoading() {
            statusTv.setText(getResources().getString(R.string.lv_loading));
            GlideApp.with(getActivity()).load(getResources().getDrawable(R.drawable.coupon_pic_nocoupon)).into(statusIv);
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
}
