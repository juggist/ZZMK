package com.juggist.jcore.base;

import java.util.List;

/**
 * @author juggist
 * @date 2018/11/14 1:59 PM
 */
public abstract class SmartRefreshResponseCallback<T> implements ResponseCallback<List<T>>{
    public abstract int getPage();
    public abstract int getPageSize();
    public abstract void setPage(int page);
    public abstract void clearTotalList();
    public abstract List<T> getTotalList();
    public abstract void setTotalList(List<T> t);
    public abstract BaseView getView();
    @Override
    public void onSucceed(List<T> t){
        /**
         * 数据
         */
        //添加数据
        if(getPage() == 1){//刷新
            clearTotalList();
        }
        setTotalList(t);

        /**
         * 视图
         */

        if(getView() == null)
            try {
                throw new Exception("view is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        if(getView() instanceof SmartRefreshViewModel){
            SmartRefreshViewModel view = (SmartRefreshViewModel) getView();
            if(getPage() == 1){//刷新
                if(t.size() == 0){ //页面数据为空
                    view.getListEmpty();
                }else if(t.size() < getPageSize()){
                    view.getListSucceedEnd(getTotalList(),true);
                }else{
                    view.getListSucceed(getTotalList(),true);
                }
            }else{//加载更多
                if(t.size() >= 0 && t.size() < getPage()){
                    view.getListSucceedEnd(getTotalList(),false);
                }else{
                    view.getListSucceed(getTotalList(),false);
                }
            }
        }else{
            try {
                throw new Exception(" view un instanceof SmartRefreshViewModel");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        /**
         * 设置page
         */
        if(t.size() == getPageSize()){
            setPage(getPage() + 1);
        }
    }

    @Override
    public void onError(String message) {
        getListError(message );
    }

    @Override
    public void onApiError(String state, String message) {
        getListError(message + " : " + state);
    }

    private void getListError(String extMsg) {
        if(getView() == null)
            try {
                throw new Exception("view is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        if(getView() instanceof SmartRefreshViewModel) {
            SmartRefreshViewModel view = (SmartRefreshViewModel) getView();
            if(getPage() == 1){//刷新
                if(getTotalList().size() == 0){//无数据
                    view.getListEmptyFail(extMsg);
                }else{//有数据
                    view.getListFail(extMsg,true);
                }
            }else{//加载更多
                view.getListFail(extMsg,false);
            }
        }else{
            try {
                throw new Exception(" view un instanceof SmartRefreshViewModel");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
