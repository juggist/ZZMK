package com.juggist.baseandroid.present.home;

import android.text.TextUtils;

import com.juggist.baseandroid.ui.home.HomeContract;
import com.juggist.jcore.Constants;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.base.SmartRefreshResponseCallback;
import com.juggist.jcore.bean.BannerBean;
import com.juggist.jcore.bean.SessionBean;
import com.juggist.jcore.bean.UserInfo;
import com.juggist.jcore.service.ISessionService;
import com.juggist.jcore.service.ISystemService;
import com.juggist.jcore.service.SessionService;
import com.juggist.jcore.service.SystemService;

import java.util.ArrayList;
import java.util.List;

import static com.juggist.jcore.bean.SessionBean.DataBean.ITEM_HOT_BRAND;

/**
 * @author juggist
 * @date 2018/11/8 3:20 PM
 */
public class HomePresent implements HomeContract.Present {
    private HomeContract.View view;
    private ISystemService systemService;
    private ISessionService sessionService;

    private int page = 1;
    private static final int page_size = 15;

    private ArrayList<String> banners = new ArrayList<>();//banner集合
    private ArrayList<SessionBean.DataBean> totalSessionBeans = new ArrayList<>();//获取的所有专场集合，有三个类型

    public HomePresent(HomeContract.View view) {
        this.view = view;
        view.setPresent(this);
        systemService = new SystemService();
        sessionService = new SessionService();
    }

    @Override
    public void getBanner() {
        showLoading();
        systemService.getBanner(UserInfo.userId(), UserInfo.token(), new ResponseCallback<ArrayList<BannerBean>>() {
            @Override
            public void onSucceed(ArrayList<BannerBean> bannerBeans) {
                banners.clear();
                for (BannerBean bannerBean : bannerBeans) {
                    banners.add(bannerBean.getAd_pic());
                }
                if (view != null) {
                    view.dismissLoading();
                    view.getBannerSucceed(banners);
                }
            }

            @Override
            public void onError(String message) {
                if (view != null) {
                    view.dismissLoading();
                    view.getBannerFail(message);
                }
            }

            @Override
            public void onApiError(String state, String message) {
                if (view != null) {
                    view.dismissLoading();
                    view.getBannerFail(message + " : " + state);
                }
            }
        });
    }

    @Override
    public void refreshSessionList() {
        page = 1;
        getSessionList();
    }

    @Override
    public void loadMoreSessionList() {
        getSessionList();
    }

    @Override
    public void toShare(int position) {
        if (view == null)
            return;
        if (position < totalSessionBeans.size()) {
            view.toShare(totalSessionBeans.get(position).getId());
        } else {
            view.showErrorDialog(Constants.ERROR.DATA_OUT_OF_LENGTH);
        }
    }

    @Override
    public void toSession(int position) {
        if (view == null)
            return;
        if (position < totalSessionBeans.size()) {
            //如果是人气品牌就拦截
            if (totalSessionBeans.get(position).getItemType() == ITEM_HOT_BRAND)
                return;
            view.toSession(totalSessionBeans.get(position).getGroup_name(), totalSessionBeans.get(position).getId());
        } else {
            view.showErrorDialog(Constants.ERROR.DATA_OUT_OF_LENGTH);
        }
    }

    @Override
    public void toSession(String groupName,String id) {
        if (view == null)
            return;
        if(TextUtils.isEmpty(groupName) || TextUtils.isEmpty(id)){
            view.showErrorDialog(Constants.ERROR.DATA_OUT_OF_LENGTH);
            return;
        }
        view.toSession(groupName, id);
    }

    @Override
    public void start() {
        getBanner();
        refreshSessionList();
    }

    @Override
    public void detach() {
        view = null;
    }

    void getSessionList() {
        sessionService.getSessionList(UserInfo.userId(), UserInfo.token(), String.valueOf(page), String.valueOf(page_size), new SmartRefreshResponseCallback<SessionBean.DataBean>() {
            @Override
            public int getPage() {
                return page;
            }

            @Override
            public int getPageSize() {
                return page_size;
            }

            @Override
            public void setPage(int page) {
                HomePresent.this.page = page;
            }

            @Override
            public void clearTotalList() {
                totalSessionBeans.clear();
            }

            @Override
            public List<SessionBean.DataBean> getTotalList() {
                return totalSessionBeans;
            }

            @Override
            public void addToTotalList(List<SessionBean.DataBean> t) {
                if (page == 1) {//刷新
                    ArrayList<SessionBean.DataBean> hotBrandList = new ArrayList<>();//人气品牌，单独领出来，特殊处理
                    for (int i = 0; i < t.size(); i++) {
                        SessionBean.DataBean dataBean = t.get(i);
                        dataBean.setPosition(i);
                        if (i == 0) {
                            dataBean.setItemType(SessionBean.DataBean.ITEM_TOP);
                            totalSessionBeans.add(dataBean);
                        } else if (i > 0 && i < 9) {
                            hotBrandList.add(dataBean);
                        } else if (i == 9) {
                            dataBean.setItemType(SessionBean.DataBean.ITEM_HOT_RECOMMED);
                            totalSessionBeans.add(dataBean);
                        } else {
                            dataBean.setItemType(SessionBean.DataBean.ITEM_NORMAL);
                            totalSessionBeans.add(dataBean);
                        }
                    }
                    if (hotBrandList.size() > 0) {
                        SessionBean.DataBean tmpDataBean = new SessionBean.DataBean();
                        tmpDataBean.setItemType(ITEM_HOT_BRAND);
                        tmpDataBean.setSpecialData(hotBrandList);
                        totalSessionBeans.add(1, tmpDataBean);
                    }
                } else {//加载更多
                    for(int i = 0; i < t.size();i++){
                        //由于返回数据比修改page早，所以这里不对page做减一处理
                        t.get(i).setPosition(page_size * page + i);
                    }
                    totalSessionBeans.addAll(t);
                }
            }

            @Override
            public BaseView getView() {
                return view;
            }

            @Override
            public void onSucceed(List<SessionBean.DataBean> t) {
                super.onSucceed(t);
                dismissLoading();
            }

            @Override
            public void onError(String message) {
                super.onError(message);
                dismissLoading();
            }

            @Override
            public void onApiError(String state, String message) {
                super.onApiError(state, message);
                dismissLoading();
            }
        });
    }

    private void showLoading() {
        if (view != null)
            view.showLoading();
    }

    private void dismissLoading() {
        if (view != null)
            view.dismissLoading();
    }
}
