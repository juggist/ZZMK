package com.juggist.baseandroid.present.mine;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import com.juggist.baseandroid.ui.mine.OrderRefundAllContract;
import com.juggist.baseandroid.view.chooseImg.config.Bimp;
import com.juggist.baseandroid.view.chooseImg.ustils.FileUtils;
import com.juggist.jcore.Constants;
import com.juggist.jcore.base.ResponseCallback;
import com.juggist.jcore.bean.OrderBean;
import com.juggist.jcore.service.AccountService;
import com.juggist.jcore.service.IAccountService;
import com.juggist.jcore.utils.FileIOUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author juggist
 * @date 2018/12/4 10:31 AM
 */
public class OrderRefundAllPresent implements OrderRefundAllContract.Present {
    OrderRefundAllContract.View view;
    private Context context;
    private IAccountService accountService;
    private OrderBean order;

    private String pickImagePath = "";//选取图片的路径
    public OrderRefundAllPresent(OrderRefundAllContract.View view,Context context, OrderBean order) {
        Bimp.MAX_SIZE = 3;
        this.view = view;
        view.setPresent(this);
        this.order = order;
        this.context = context;
        accountService = new AccountService();
    }

    @Override
    public void openCamera() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory()
                + Constants.PATH.PATH_REFUND_PIC, String.valueOf(System.currentTimeMillis())
                + ".jpg");
        if (!new File(Environment.getExternalStorageDirectory()
                + Constants.PATH.PATH_REFUND_PIC).exists()) {
            new File(Environment.getExternalStorageDirectory()
                    + Constants.PATH.PATH_REFUND_PIC).mkdirs();
        }
        pickImagePath = file.getPath();
        ContentValues contentValues = new ContentValues(1);
        contentValues.put(MediaStore.Images.Media.DATA, pickImagePath);
        Uri imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        if (view != null) {
            view.openCamera(openCameraIntent);
        }
    }

    @Override
    public void addPic() {
        if (Bimp.drr.size() < Bimp.MAX_SIZE) {
            Bimp.drr.add(pickImagePath);
        }
    }

    @Override
    public void clickPic(int position) {
        if (view == null) {
            return;
        }
        if (position == Bimp.bmp.size()) {
            view.showChoosePicDialog();
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("ID", position);
            view.toBigPicView(bundle);
        }
    }

    @Override
    public void refundDispatched(String des,String reason) {
        if(view != null)
            view.showLoading();
        List<String> imgs = new ArrayList<>();
        for (String imgPath:Bimp.drr) {
            byte[] bytes = FileIOUtils.readFile2BytesByStream(imgPath);
            byte[] string = Base64.encode(bytes, Base64.DEFAULT);
            imgs.add(new String(string));
        }
        accountService.refundDispatched(order.getOrder_id(), des, reason, imgs, new ResponseCallback<String>() {
            @Override
            public void onSucceed(String s) {
                if(view != null){
                    view.showLoading();
                    view.refundDispatchedSucceed();
                }
            }

            @Override
            public void onError(String message) {
                if(view != null){
                    view.showLoading();
                    view.refundDispatchedFail(message);
                }

            }

            @Override
            public void onApiError(String state, String message) {
                if(view != null){
                    view.showLoading();
                    view.refundDispatchedFail(message + " ; " + state);
                }

            }
        });
    }

    @Override
    public void start() {
        if(view != null)
            view.updateAdapter(order.getGoods_list());
    }

    @Override
    public void detach() {
        view = null;
        //数据reset
        Bimp.max = 0;
        Bimp.act_bool = false;
        //清空图片数据(内存图片)
        Bimp.bmp.clear();
        //清空图片地址数据（sd卡路径）
        Bimp.drr.clear();
        //删除压缩处理的图片
        FileUtils.deleteDir();
    }
}
