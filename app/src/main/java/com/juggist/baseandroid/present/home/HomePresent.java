package com.juggist.baseandroid.present.home;

import com.juggist.baseandroid.ui.home.HomeContract;
import com.juggist.jcore.Constants;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.BannerBean;
import com.juggist.jcore.bean.SessionBean;
import com.juggist.jcore.bean.UserInfo;
import com.juggist.jcore.service.ISessionService;
import com.juggist.jcore.service.ISystemService;
import com.juggist.jcore.service.SessionService;
import com.juggist.jcore.service.SystemService;

import java.util.ArrayList;

/**
 * @author juggist
 * @date 2018/11/8 3:20 PM
 */
public class HomePresent implements HomeContract.Present {
    private HomeContract.View view;
    private ISystemService systemService;
    private ISessionService sessionService;

    private int page = 0;
    private static final int page_size = 10;

    private ArrayList<String> banners = new ArrayList<>();//banner集合

    private ArrayList<SessionBean.DataBean> totalSessionBeans = new ArrayList<>();//获取的所有专场集合

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
                for(BannerBean bannerBean : bannerBeans){
                    banners.add(bannerBean.getAd_pic());
                }
                if(view != null){
                    view.dismissLoading();
                    view.getBannerSucceed(banners);
                }
            }

            @Override
            public void onError(String message) {
                if(view != null){
                    view.dismissLoading();
                    view.getBannerFail(message);
                }
            }

            @Override
            public void onApiError(String state, String message) {
                if(view != null){
                    view.dismissLoading();
                    view.getBannerFail(message + " : " + state);
                }
            }
        });
    }

    @Override
    public void refreshSessionList() {
        page = 0;
        getSessionList();
    }

    @Override
    public void loadMoreSessionList() {
        getSessionList();
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

    void getSessionList(){
        sessionService.getSessionList(UserInfo.userId(), UserInfo.token(), String.valueOf(page), String.valueOf(page_size), new ResponseCallback<ArrayList<SessionBean.DataBean>>() {
            @Override
            public void onSucceed(ArrayList<SessionBean.DataBean> sessionBeans) {
                if(sessionBeans != null){
                    /**
                     * 数据
                     */
                    //添加数据
                    if(page == 0){//刷新
                        totalSessionBeans.clear();
                    }
                    totalSessionBeans.addAll(sessionBeans);


                    /**
                     * 视图
                     */
                    if(view == null)
                        return;
                    view.dismissLoading();
                    if(page == 0){//刷新
                        if(sessionBeans.size() == 0){ //页面数据为空
                            view.getSessionListEmpty();
                        }else if(sessionBeans.size() < page_size){
                            view.getSessionListSucceedEnd(totalSessionBeans,true);
                        }else{
                            view.getSessionListSucceed(totalSessionBeans,true);
                        }
                    }else{//加载更多
                        if(sessionBeans.size() >= 0 && sessionBeans.size() < page_size){
                            view.getSessionListSucceedEnd(totalSessionBeans,false);
                        }else{
                            view.getSessionListSucceed(totalSessionBeans,false);
                        }
                    }

                    /**
                     * 设置page
                     */
                    if(sessionBeans.size() == page_size){
                        page++;
                    }
                }else{
                    getSessionListError(Constants.ERROR.DATA_IS_NULL);
                }
            }

            @Override
            public void onError(String message) {
                getSessionListError(message);
            }

            @Override
            public void onApiError(String state, String message) {
                getSessionListError(message + " : " + state);
            }
        });
    }

    private void getSessionListError(String extMsg){
        if(view == null)
            return;
        view.dismissLoading();
        if(page == 0){//刷新
            if(totalSessionBeans.size() == 0){//无数据
                view.getSessionListEmptyFail(extMsg);
            }else{//有数据
                view.getSessionListFail(extMsg,true);
            }
        }else{//加载更多
            view.getSessionListFail(extMsg,false);
        }

    }
    private void showLoading(){
        if(view != null)
            view.showLoading();
    }
}
