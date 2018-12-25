package com.juggist.main.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.juggist.componentservice.account.AccountService;
import com.juggist.componentservice.buy.BuyService;
import com.juggist.componentservice.discover.DiscoverService;
import com.juggist.componentservice.eventbus.HomeTabChangeEvent;
import com.juggist.componentservice.home.HomeService;
import com.juggist.jcore.base.BaseActivity;
import com.juggist.main.R;
import com.juggist.main.view.FragmentTabHost;
import com.luojilab.component.componentlib.router.Router;
import com.luojilab.router.facade.annotation.RouteNode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


@RouteNode(path = "/index",desc = "主页面")
public class HomeActivity extends BaseActivity {

    FragmentTabHost tabhost;


    private int mTabViewArray[] = {
            R.drawable.main_bottom_nav_home,
            R.drawable.main_bottom_nav_discover,
            R.drawable.main_bottom_nav_buy,
            R.drawable.main_bottom_nav_mine};
    private String[] mTabStringArray;

    private Class<?> fragmentArray[] = {
        null,null,null,null
    };

    public static final int PAGE_HOME = 0,PAGE_DISCOVER =1,PAGE_BUY = 2,PAGE_MINE = 3;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_home;
    }

    @Override
    protected void initView() {
        Router router = Router.getInstance();
        if(router.getService(HomeService.class.getSimpleName()) != null){
            HomeService homeService = (HomeService) router.getService(HomeService.class.getSimpleName());
            fragmentArray[0] = homeService.getHomeFragment().getClass();
        }
        if(router.getService(DiscoverService.class.getSimpleName()) != null){
            DiscoverService discoverService = (DiscoverService) router.getService(DiscoverService.class.getSimpleName());
            fragmentArray[1] = discoverService.getDiscoverFragment().getClass();
        }
        if(router.getService(BuyService.class.getSimpleName()) != null){
            BuyService buyService = (BuyService) router.getService(BuyService.class.getSimpleName());
            fragmentArray[2] = buyService.getBuyFragment().getClass();
        }
        if(router.getService(AccountService.class.getSimpleName()) != null){
            AccountService accountService = (AccountService) router.getService(AccountService.class.getSimpleName());
            fragmentArray[3] = accountService.getAccountFragment().getClass();
        }
        tabhost = findViewById(android.R.id.tabhost);
        tabhost.setup(this, getSupportFragmentManager(), R.id.main_fl_container);
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
        View view = LayoutInflater.from(this).inflate(R.layout.main_nav_bottom_item, null);
        ImageView imageView = (ImageView) view
                .findViewById(R.id.main_iv_nav_image);
        imageView.setImageResource(mTabViewArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.main_tv_nav_title);
        textView.setText(mTabStringArray[index]);
        return view;
    }
    void switchFragment(int position){
        switch (position){
            case PAGE_HOME:
                tabhost.setCurrentTab(PAGE_HOME);
                ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true, R.color.white).keyboardEnable(true).init();
                break;
            case PAGE_DISCOVER:
                tabhost.setCurrentTab(PAGE_DISCOVER);
                ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true, R.color.white).keyboardEnable(true).init();
                break;
            case PAGE_BUY:
                tabhost.setCurrentTab(PAGE_BUY);
                ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true, R.color.white).keyboardEnable(true).init();
                break;
            case PAGE_MINE:
                tabhost.setCurrentTab(PAGE_MINE);
                ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true, R.color.font_select).keyboardEnable(true).init();
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void tabChange(HomeTabChangeEvent.TabChange event){
        switchFragment(event.getPosition());
    }
}
