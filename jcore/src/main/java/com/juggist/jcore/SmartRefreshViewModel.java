package com.juggist.jcore;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author juggist
 * @date 2018/11/14 12:29 PM
 */
public abstract class SmartRefreshViewModel<T>{

    public abstract SmartRefreshLayout getSmartRefreshLayout();

    public abstract BaseQuickAdapter getBaseAdapter();


    public void getListEmpty() {
        if(adapterExist()){
            getBaseAdapter().setNewData(new ArrayList());
        }
        if(smartRefreshLayoutExist()){
            getSmartRefreshLayout().finishRefresh();
            getSmartRefreshLayout().setNoMoreData(true);
        }
    }


    public void getListSucceed(List<T> beans, boolean refresh) {
        if(adapterExist()){
            if(refresh){
                getBaseAdapter().setNewData(beans);
            }else {
                getBaseAdapter().addData(beans);
            }
        }
        if(smartRefreshLayoutExist()){
            if (refresh) {
                getSmartRefreshLayout().finishRefresh();
            } else {
                getSmartRefreshLayout().finishLoadMore();
            }
        }
    }


    public void getListSucceedEnd(List<T> beans, boolean refresh) {
        if(adapterExist()){
            if(refresh){
                getBaseAdapter().setNewData(beans);
            }else {
                getBaseAdapter().addData(beans);
            }
        }
        if(smartRefreshLayoutExist()){
            if (refresh) {
                getSmartRefreshLayout().finishRefresh();
                getSmartRefreshLayout().setNoMoreData(true);
            } else {
                getSmartRefreshLayout().finishLoadMoreWithNoMoreData();
            }
        }
    }


    public void getListEmptyFail(String extMsg) {
        if(smartRefreshLayoutExist()){
            getSmartRefreshLayout().finishRefresh();
            getSmartRefreshLayout().setNoMoreData(true);
        }
    }


    public void getListFail(String extMsg, boolean refresh) {
        if(smartRefreshLayoutExist()){
            if (refresh) {
                getSmartRefreshLayout().finishRefresh();
            } else {
                getSmartRefreshLayout().finishLoadMore();
            }
        }
    }
    private boolean smartRefreshLayoutExist(){
        if(getSmartRefreshLayout() != null){
            return true;
        }else{
            try {
                throw new Exception("srl is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }
    private boolean adapterExist(){
        if(getBaseAdapter() != null){
           return true;
        }else{
            try {
                throw new Exception(" adapter is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
