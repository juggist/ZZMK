package com.juggist.baseandroid.ui.home;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.ui.BackBaseActivity;
import com.juggist.baseandroid.ui.home.adapter.SessionItemAdapter;
import com.juggist.baseandroid.ui.home.adapter.SessionItemAdapter.Listener;
import com.juggist.baseandroid.view.DialogDownload;
import com.juggist.baseandroid.view.DialogForBuy;
import com.juggist.baseandroid.view.DialogSessionSetting;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import butterknife.BindView;

public class SessionActivity extends BackBaseActivity{

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.srf)
    SmartRefreshLayout srf;

    private SessionItemAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_session;
    }

    @Override
    protected void initView() {
        iv_setting.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogSessionSetting dss = new DialogSessionSetting();
                FragmentTransaction ft = SessionActivity.this.getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                dss.show(ft,"dss");
            }
        });
    }

    @Override
    protected void initData() {
        adapter = new SessionItemAdapter(this,new AdapterListener());
        lv.setAdapter(adapter);
    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.session_title);
    }

    /**
     * 适配器listener
     */
    private class AdapterListener implements Listener{
        @Override
        public void download() {
            DialogDownload dd = new DialogDownload();
            FragmentTransaction ft = SessionActivity.this.getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            dd.show(ft,"dd");
        }

        @Override
        public void buy() {
            DialogForBuy dfb = new DialogForBuy();
            FragmentTransaction ft = SessionActivity.this.getSupportFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            dfb.show(ft,"dfb");
        }
    }

}
