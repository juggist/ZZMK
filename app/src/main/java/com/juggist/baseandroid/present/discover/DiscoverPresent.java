package com.juggist.baseandroid.present.discover;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.juggist.baseandroid.ui.discover.DiscoverContract;
import com.juggist.jcore.Constants;
import com.juggist.jcore.MyBaseApplication;
import com.juggist.jcore.base.BaseView;
import com.juggist.jcore.base.SmartRefreshResponseCallback;
import com.juggist.jcore.bean.ArticleBean;
import com.juggist.jcore.service.ArticleService;
import com.juggist.jcore.service.IArticleService;
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
 * @date 2018/11/9 10:00 AM
 */
public class DiscoverPresent implements DiscoverContract.Present {
    private DiscoverContract.View view;
    private IArticleService articleService;

    private int page = 1;
    private static final int page_size = 10;
    private int downloadPosition = -1;

    private ArrayList<ArticleBean> totalArticleBeans = new ArrayList<>();

    private Disposable downLoadDisposable;


    public DiscoverPresent(DiscoverContract.View view) {
        this.view = view;
        view.setPresent(this);
        articleService = new ArticleService();
    }

    @Override
    public void refreshArticleList() {
        page = 1;
        getArticleList();
    }

    @Override
    public void loadMoreArticleList() {
        getArticleList();
    }

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
        if (downloadPosition < 0 || downloadPosition >= totalArticleBeans.size()) {
            if (view != null)
                view.downloadShareFail(Constants.ERROR.DATA_OUT_OF_LENGTH);
            return;
        }
//        生成需要下载任务的mission集合
        final ArticleBean item = totalArticleBeans.get(downloadPosition);
        final List<String> downLoadUrls = totalArticleBeans.get(downloadPosition).getPic_url();
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
                            ClipboardUtils.copyText(item.getArticle_name() + "\n"
                                    + item.getArticle_content()
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

    private void getArticleList() {
        showLoading();
        articleService.getArticleList(String.valueOf(page), String.valueOf(page_size), new SmartRefreshResponseCallback<ArticleBean>() {
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
                DiscoverPresent.this.page = page;
            }

            @Override
            public void clearTotalList() {
                totalArticleBeans.clear();
            }

            @Override
            public List<ArticleBean> getTotalList() {
                return totalArticleBeans;
            }

            @Override
            public void addToTotalList(List<ArticleBean> t) {
                totalArticleBeans.addAll(t);
            }

            @Override
            public BaseView getView() {
                return view;
            }

            @Override
            public void onSucceed(List<ArticleBean> t) {
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
        getArticleList();
    }

    @Override
    public void detach() {
        view = null;
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
