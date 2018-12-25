package com.juggist.account.ui;

import android.content.Context;
import android.view.View;

import com.juggist.account.R;
import com.juggist.account.R2;
import com.juggist.account.ui.adapter.DiscountCardViewPagerAdapter;
import com.juggist.account.ui.fragment.DiscountCardFragment;
import com.juggist.jcore.base.BackBaseActivity;
import com.juggist.jcore.utils.DensityConst;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * 优惠券
 */

public class DiscountCardActivity extends BackBaseActivity {

    @BindView(R2.id.account_mi)
    MagicIndicator mi;
    @BindView(R2.id.account_vp)
    ViewPager vp;

    CommonNavigator commonNavigator;

    private String[] titles;
    private List<DiscountCardFragment> fragmentList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.account_activity_discount_card;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        DensityConst.initDensity(this);
        titles = getResources().getStringArray(R.array.account_discount_card);
        for(int i = 0;i < titles.length;i++){
            fragmentList.add(DiscountCardFragment.newInstance(i));
        }
        initIndicator();
        initAdapter();
        ViewPagerHelper.bind(mi,vp);
        vp.setCurrentItem(0);
    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.account_title_discount_card);
    }

    private void initIndicator(){
        commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);//设置等分
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int i) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.font_normal));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.font_select));
                simplePagerTitleView.setTextSize(getResources().getDimension(R.dimen.sp_20));
                simplePagerTitleView.setText(titles[i]);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        vp.setCurrentItem(i);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setColors(getResources().getColor(R.color.font_select));
                linePagerIndicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                linePagerIndicator.setLineHeight(getResources().getDimension(R.dimen.dp_4));
                return linePagerIndicator;
            }
        });
        mi.setNavigator(commonNavigator);
    }
    private void initAdapter(){
        vp.setAdapter(new DiscountCardViewPagerAdapter(getSupportFragmentManager(),fragmentList));
    }
}
