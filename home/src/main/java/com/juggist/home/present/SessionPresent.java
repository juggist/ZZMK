package com.juggist.home.present;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.juggist.home.ui.SessionContract;
import com.juggist.jcore.Constants;
import com.juggist.jcore.MyBaseApplication;
import com.juggist.jcore.SmartRefreshResponseCallback;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.ProductBean;
import com.juggist.jcore.bean.ShopCarBean;
import com.juggist.jcore.bean.UserInfo;
import com.juggist.jcore.service.AccountService;
import com.juggist.jcore.service.IAccountService;
import com.juggist.jcore.service.ISessionService;
import com.juggist.jcore.service.SessionService;
import com.juggist.jcore.utils.ClipboardUtils;
import com.juggist.jcore.utils.StringUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import zlc.season.rxdownload3.RxDownload;
import zlc.season.rxdownload3.core.Deleted;
import zlc.season.rxdownload3.core.Downloading;
import zlc.season.rxdownload3.core.Failed;
import zlc.season.rxdownload3.core.Mission;
import zlc.season.rxdownload3.core.Normal;
import zlc.season.rxdownload3.core.Status;
import zlc.season.rxdownload3.core.Succeed;
import zlc.season.rxdownload3.core.Suspend;
import zlc.season.rxdownload3.core.Waiting;

/**
 * @author juggist
 * @date 2018/11/9 5:22 PM
 */
public class SessionPresent implements SessionContract.Present {
    private SessionContract.View view;
    private ISessionService sessionService;
    private IAccountService accountService;

    private int downloadPosition = -1;
    private int page = 1;
    private static final int page_size = 10;
    private String group_id;
    private ArrayList<ProductBean.DataBean.GoodsListBean> totalProducts = new ArrayList<>();
    private Disposable downLoadDisposable;

    private ArrayList<ShopCarBean> shopCarBeans;

    public SessionPresent(SessionContract.View view, String group_id) {
        this.view = view;
        this.group_id = group_id;
        view.setPresent(this);
        shopCarBeans = new ArrayList<>();
        sessionService = new SessionService();
        accountService = new AccountService();
    }

    @Override
    public void refreshOnSellProductsList() {
        page = 1;
        getProductList();
    }

    @Override
    public void loadMoreOnSellProductsList() {
        getProductList();
    }

    /**
     * 准备下载，存入下标
     *
     * @param position
     */
    @Override
    public void preparDownload(int position) {
        downloadPosition = position;
    }

    /**
     * 开始下载,获取缓存的下标
     */
    @Override
    public void startDownload() {
        Logger.d("startDownload");
        if (downloadPosition < 0 || downloadPosition >= totalProducts.size()) {
            if (view != null)
                view.downloadShareFail(Constants.ERROR.DATA_OUT_OF_LENGTH);
            return;
        }
//        生成需要下载任务的mission集合
        final ProductBean.DataBean.GoodsListBean item = totalProducts.get(downloadPosition);
        final List<String> downLoadUrls = totalProducts.get(downloadPosition).getMain_pic();
        List<Flowable<Status>> flowables = new ArrayList<>();
        for (final String downloadUrl : downLoadUrls) {
            Mission mission = new Mission(downloadUrl, StringUtil.getPNGNameFromUrl(downloadUrl), Constants.PATH.PATH_SAVE_SHARE_PIC);
            flowables.add(RxDownload.INSTANCE.create(mission, true));
        }
//        通过zip操作。获取所有的结果。
        downLoadDisposable = Flowable.zip(flowables, new Function<Object[], List<Status>>() {
            @Override
            public List<Status> apply(Object[] flowables) throws Exception {
                List<Status> statuses = new ArrayList<>();
                for (Object o : flowables) {
                    if (o instanceof Status) {
                        statuses.add((Status) o);
                    }
                }
                return statuses;
            }
        })
                .map(new Function<List<Status>, List<String>>() {
                    @Override
                    public List<String> apply(List<Status> statuses) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (Status status : statuses) {
                            if (status instanceof Normal) {
                                list.add("Normal");
                            } else if (status instanceof Suspend) {
                                list.add("Suspend");
                            } else if (status instanceof Waiting) {
                                list.add("Waiting");
                            } else if (status instanceof Downloading) {
                                list.add("Downloading");
                            } else if (status instanceof Failed) {
                                list.add("Failed");
                            } else if (status instanceof Succeed) {
                                list.add("Succeed");
                            } else if (status instanceof Deleted) {
                                list.add("Deleted");
                            }
                        }
                        return list;
                    }
                })
                .map(new Function<List<String>, String>() {
                    @Override
                    public String apply(List<String> strings) throws Exception {
                        if (strings.contains("Failed")) {
                            return "fail";
                        } else if (strings.contains("Normal") || strings.contains("Suspend") || strings.contains("Waiting") || strings.contains("Downloading") || strings.contains("Deleted")) {

                        } else {
                            //保存到系统相册
                            for (String downloadUrl : downLoadUrls) {
                                MediaStore.Images.Media.insertImage(MyBaseApplication.getInstance().getContentResolver(),
                                        Constants.PATH.PATH_SAVE_SHARE_PIC + StringUtil.getPNGNameFromUrl(downloadUrl), StringUtil.getPNGNameFromUrl(downloadUrl), null);
                                //发送广播通知相册刷新，否则相册内看不到新增的图片
                                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                Uri uri = Uri.fromFile(new File(Constants.PATH.PATH_SAVE_SHARE_PIC + StringUtil.getPNGNameFromUrl(downloadUrl)));
                                intent.setData(uri);
                                MyBaseApplication.getInstance().sendBroadcast(intent);
                            }
                            ClipboardUtils.copyText(item.getGoods_name() + "\n"
                                    + "代购价:￥" + item.getPrice() + "\n"
                                    + "货号:" + item.getSn()
                            );
                            return "succeed";
                        }
                        return "";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Logger.d("s : %s", s);
                        if (view == null)
                            return;
                        if (s.equals("succeed")) {
                            if (downLoadDisposable != null)
                                downLoadDisposable.dispose();
                            if (view != null)
                                view.downloadShareSucceed();
                        } else if (s.equals("fail")) {
                            if (downLoadDisposable != null)
                                downLoadDisposable.dispose();
                            if (view != null)
                                view.downloadShareFail(Constants.ERROR.DOWNLOAD_SHARE_IMG_FAIL);
                        }
                    }
                });
    }

    @Override
    public void getShopCar() {
        postGetShopCar();
    }

    @Override
    public void addShop(int position, int num) {
        if(position < 0 || position >= totalProducts.size()){
            if(view != null)
                view.showErrorDialog(Constants.ERROR.DATA_OUT_OF_LENGTH);
        }else{
           if(view != null)
               view.showLoading();
            postAddShop(position,num);
        }
    }

    private void getProductList() {
        if (view != null)
            view.showLoading();
        sessionService.getProductList(UserInfo.userId(), UserInfo.token(), String.valueOf(page), String.valueOf(page_size), group_id, new SmartRefreshResponseCallback<ProductBean.DataBean.GoodsListBean>() {
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
                SessionPresent.this.page = page;
            }

            @Override
            public void clearTotalList() {
                totalProducts.clear();
            }

            @Override
            public List<ProductBean.DataBean.GoodsListBean> getTotalList() {
                return totalProducts;
            }

            @Override
            public void addToTotalList(List<ProductBean.DataBean.GoodsListBean> t) {
                totalProducts.addAll(t);
            }

            @Override
            public BaseView getView() {
                return view;
            }

            @Override
            public void onSucceed(List<ProductBean.DataBean.GoodsListBean> t) {
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

    private void postGetShopCar(){
        sessionService.queryShopCar(new ResponseCallback<ArrayList<ShopCarBean>>() {
            @Override
            public void onSucceed(ArrayList<ShopCarBean> shopCarBeans) {
                SessionPresent.this.shopCarBeans.clear();
                SessionPresent.this.shopCarBeans.addAll(shopCarBeans);
                if(view == null)
                    return;
                view.dismissLoading();
                view.queryShopCarSucceed(shopCarBeans);
            }

            @Override
            public void onError(String message) {
                if(view != null){
                    view.dismissLoading();
                    view.showErrorDialog(message);
                }
            }

            @Override
            public void onApiError(String state, String message) {
                if(view != null){
                    view.dismissLoading();
                    view.queryShopCarFail(message + " : " + state);
                }
            }
        });
    }
    private void postAddShop(int position,int num){
        accountService.addShop(totalProducts.get(position).getGoods_id(), String.valueOf(num), new ResponseCallback<String>() {
            @Override
            public void onSucceed(String s) {
                dismissLoading();
                getShopCar();
                if(view != null)
                    view.addShopCarSucceed(s);
            }

            @Override
            public void onError(String message) {
                dismissLoading();
                if(view != null)
                    view.addShopCarFail(message);
            }

            @Override
            public void onApiError(String state, String message) {
                dismissLoading();
                if(view != null)
                    view.addShopCarFail(message + " ; " + state);
            }
        });
    }
    @Override
    public void start() {
        getProductList();
        getShopCar();
    }

    @Override
    public void detach() {
        view = null;
    }

    private void dismissLoading() {
        if (view != null)
            view.dismissLoading();
    }
}
