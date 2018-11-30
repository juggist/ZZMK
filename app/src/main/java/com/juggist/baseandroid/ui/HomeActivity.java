package com.juggist.baseandroid.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.juggist.baseandroid.R;
import com.juggist.baseandroid.eventbus.HomeTabChangeEvent;
import com.juggist.baseandroid.ui.buy.BuyFragment;
import com.juggist.baseandroid.ui.discover.DiscoverFragment;
import com.juggist.baseandroid.ui.home.HomeFragment;
import com.juggist.baseandroid.ui.mine.MineFragment;
import com.juggist.baseandroid.view.FragmentTabHost;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;


public class HomeActivity extends BaseActivity {

    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;


    private int mTabViewArray[] = {
            R.drawable.bottom_nav_home,
            R.drawable.bottom_nav_discover,
            R.drawable.bottom_nav_buy,
            R.drawable.bottom_nav_mine};
    private String[] mTabStringArray;

    private Class<?> fragmentArray[] = {
            HomeFragment.class,
            DiscoverFragment.class,
            BuyFragment.class,
            MineFragment.class,
    };

    public static final int PAGE_HOME = 0,PAGE_DISCOVER =1,PAGE_BUY = 2,PAGE_MINE = 3;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        tabhost.setup(this, getSupportFragmentManager(), R.id.fl_container);
        tabhost.getTabWidget().setDividerDrawable(R.color.white);
        mTabStringArray = getResources().getStringArray(R.array.main_bottom_nav);
        for (int i = 0; i < mTabViewArray.length; i++) {
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(mTabStringArray[i])
                    .setIndicator(getTabItemView(i));
            tabhost.addTab(tabSpec, fragmentArray[i], null);
            final int j = i;
            tabhost.getTabWidget().getChildAt(i)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            switchFragment(j);
                        }
                    });
        }
        tabhost.setCurrentTab(0);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }

    private View getTabItemView(int index) {
        View view = LayoutInflater.from(this).inflate(R.layout.nav_bottom_item, null);
        ImageView imageView = (ImageView) view
                .findViewById(R.id.iv_nav_image);
        imageView.setImageResource(mTabViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.tv_nav_title);
        textView.setText(mTabStringArray[index]);
        return view;
    }
    void switchFragment(int position){
        switch (position){
            case PAGE_HOME:
                tabhost.setCurrentTab(PAGE_HOME);
                immersionBar.statusBarDarkFont(true, 0.2f).fitsSystemWindows(true, R.color.white).keyboardEnable(true).init();
                break;
            case PAGE_DISCOVER:
                tabhost.setCurrentTab(PAGE_DISCOVER);
                immersionBar.statusBarDarkFont(true, 0.2f).fitsSystemWindows(true, R.color.white).keyboardEnable(true).init();
                break;
            case PAGE_BUY:
                tabhost.setCurrentTab(PAGE_BUY);
                immersionBar.statusBarDarkFont(true, 0.2f).fitsSystemWindows(true, R.color.white).keyboardEnable(true).init();
                break;
            case PAGE_MINE:
                tabhost.setCurrentTab(PAGE_MINE);
                immersionBar.statusBarDarkFont(true, 0.2f).fitsSystemWindows(true, R.color.font_select).keyboardEnable(true).init();

                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tabChange(HomeTabChangeEvent.TabChange event){
        switchFragment(event.getPosition());
    }
}
