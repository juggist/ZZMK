package com.juggist.account.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.juggist.account.R;
import com.juggist.account.R2;
import com.juggist.account.present.DiscountCardPresent;
import com.juggist.account.ui.adapter.DiscountCardAdapter;
import com.juggist.jcore.SmartRefreshViewModel;
import com.juggist.jcore.base.BaseFragment;
import com.juggist.jcore.bean.DiscountCardBean;
import com.juggist.jcore.utils.MyImageLoader;
import com.juggist.jcore.utils.ToastUtil;
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
    @BindView(R2.id.account_lv)
    RecyclerView lv;
    @BindView(R2.id.account_srl)
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
        return R.layout.account_fragment_discount_card;
    }

    @Override
    protected void initView(View view) {

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
        adapter = new DiscountCardAdapter(R.layout.account_adapter_discount_card_item,new ArrayList<DiscountCardBean>(),getActivity());
        adapter.setEmptyView(statusView);
        lv.setAdapter(adapter);
    }
    private class ViewModel extends SmartRefreshViewModel<DiscountCardBean> implements DiscountCardContract.View{

        @Override
        public void getListEmpty() {
            super.getListEmpty();
            statusTv.setText(getResources().getString(R.string.account_lv_discount_card_empty));
            MyImageLoader.getInstance().loadImage(R.drawable.account_coupon_pic_nocoupon,statusIv);
        }

        @Override
        public void getListEmptyFail(String extMsg) {
            super.getListEmptyFail(extMsg);
            showErrorDialog(extMsg);
            statusTv.setText(getResources().getString(R.string.lv_net_error));
            MyImageLoader.getInstance().loadImage(R.drawable.account_coupon_pic_nocoupon,statusIv);
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
            MyImageLoader.getInstance().loadImage(R.drawable.account_coupon_pic_nocoupon,statusIv);
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
