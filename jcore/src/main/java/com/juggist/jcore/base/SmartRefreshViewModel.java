package com.juggist.jcore.base;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

/**
 * @author juggist
 * @date 2018/11/14 12:29 PM
 */
public abstract class SmartRefreshViewModel<T>{

    public abstract SmartRefreshLayout getSmartRefreshLayout();

    public abstract BaseUpdateAdapter getBaseAdapter();


    public void getListEmpty() {
        if(getSmartRefreshLayout() != null){
            getSmartRefreshLayout().finishRefresh();
        }else{
            try {
                throw new Exception(" srl is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void getListSucceed(List<T> beans, boolean refresh) {
        if(getBaseAdapter() != null){
            getBaseAdapter().update(beans);
        }else{
            try {
                throw new Exception(" adapter is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(getSmartRefreshLayout() != null){
            if (refresh) {
                getSmartRefreshLayout().finishRefresh();
            } else {
                getSmartRefreshLayout().finishLoadMore();
            }
        }else{
            try {
                throw new Exception(" srl is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    public void getListSucceedEnd(List<T> beans, boolean refresh) {
        if(getBaseAdapter() != null){
            getBaseAdapter().update(beans);
        }else{
            try {
                throw new Exception(" adapter is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(getSmartRefreshLayout() != null){
            if (refresh) {
                getSmartRefreshLayout().finishRefresh();
                getSmartRefreshLayout().setNoMoreData(true);
            } else {
                getSmartRefreshLayout().finishLoadMoreWithNoMoreData();
            }
        }else{
            try {
                throw new Exception(" srl is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    public void getListEmptyFail(String extMsg) {
        if(getSmartRefreshLayout() != null){
            getSmartRefreshLayout().finishRefresh();
        }else{
            try {
                throw new Exception(" srl is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void getListFail(String extMsg, boolean refresh) {
        if(getSmartRefreshLayout() != null){
            if (refresh) {
                getSmartRefreshLayout().finishRefresh();
            } else {
                getSmartRefreshLayout().finishLoadMore();
            }
        }else {
            try {
                throw new Exception(" srl is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
