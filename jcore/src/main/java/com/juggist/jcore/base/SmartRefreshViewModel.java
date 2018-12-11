package com.juggist.jcore.base;

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
        if(getBaseAdapter() != null){
            getBaseAdapter().setNewData(new ArrayList());
        }else{
            try {
                throw new Exception(" adapter is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(getSmartRefreshLayout() != null){
            getSmartRefreshLayout().finishRefresh();
            getSmartRefreshLayout().setNoMoreData(true);
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
            if(refresh){
                getBaseAdapter().setNewData(beans);
            }else {
                getBaseAdapter().addData(beans);
            }
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
            if(refresh){
                getBaseAdapter().setNewData(beans);
            }else {
                getBaseAdapter().addData(beans);
            }
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
