package com.juggist.account.present;

import com.juggist.account.ui.fragment.DiscountCardContract;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.SmartRefreshResponseCallback;
import com.juggist.jcore.bean.DiscountCardBean;
import com.juggist.jcore.service.ISessionService;
import com.juggist.jcore.service.SessionService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author juggist
 * @date 2018/11/15 4:56 PM
 */
public class DiscountCardPresent implements DiscountCardContract.Present {
    private DiscountCardContract.View view;
    private ISessionService sessionService;

    private int page = 1;
    private static final int page_size = 10;
    private int tag;
    private List<DiscountCardBean> totalDiscountCards = new ArrayList<>();


    public DiscountCardPresent(DiscountCardContract.View view,int tag) {
        this.view = view;
        this.tag = tag;
        view.setPresent(this);
        sessionService = new SessionService();
    }

    @Override
    public void refreshDiscountCardList() {
        page = 1 ;
        getDiscountCardList();
    }

    @Override
    public void loadMoreDiscountCardList() {
        getDiscountCardList();
    }
    private void getDiscountCardList(){
        if(view != null){
            view.showLoading();
        }
        sessionService.getDiscountCardList(tag, String.valueOf(page), String.valueOf(page_size), new SmartRefreshResponseCallback<DiscountCardBean>() {
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
                DiscountCardPresent.this.page = page;
            }

            @Override
            public void clearTotalList() {
                totalDiscountCards.clear();
            }

            @Override
            public List<DiscountCardBean> getTotalList() {
                return totalDiscountCards;
            }

            @Override
            public void addToTotalList(List<DiscountCardBean> t) {
                totalDiscountCards.addAll(t);
            }

            @Override
            public BaseView getView() {
                return view;
            }

            @Override
            public void onSucceed(List<DiscountCardBean> t) {
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
    @Override
    public void start() {
        getDiscountCardList();
    }

    @Override
    public void detach() {
        view = null;
    }
    private void dismissLoading(){
        if(view != null)
            view.dismissLoading();
    }
}
