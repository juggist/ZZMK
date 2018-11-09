package com.juggist.baseandroid.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.juggist.baseandroid.R;
import com.juggist.baseandroid.utils.ToastUtil;
import com.juggist.baseandroid.view.ActionSheetDialog;
import com.juggist.jcore.Constants;
import com.juggist.jcore.utils.ImageUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class BigViewPagerPhotoActivity extends BackBaseActivity {
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private ArrayList<String> pics = new ArrayList<>();//照片集合
    private int position = 0;//点选的第几张照片

    private ImageView tmpImg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_big_view_pager_photo;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        pics.addAll(bundle.getStringArrayList("picUrl"));
        position = bundle.getInt("position", 0);

        viewpager.setAdapter(new ImageAdapter(pics));
        viewpager.setCurrentItem(position);
    }

    @Override
    protected String getTitile() {
        return getResources().getString(R.string.title_review_big_pic);
    }


    @Override
    protected void initSystemStatusBar() {
        super.initSystemStatusBar();
        immersionBar.statusBarDarkFont(false).fitsSystemWindows(true,android.R.color.black).keyboardEnable(true).init();
    }

    private class ImageAdapter extends PagerAdapter {
        private ArrayList<String> pics;

        public ImageAdapter(ArrayList<String> pics) {
            this.pics = pics;
        }

        @Override
        public int getCount() {
            return pics.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
//            final PhotoView img = new PhotoView(BigViewPagerPhotoActivity.this);// 构造textView对象
//            img.setBackgroundColor(0xff000000);
//            Glide.with(BigViewPagerPhotoActivity.this).load(pics.get(position)).into(img);
//            img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
//                    ViewGroup.LayoutParams.FILL_PARENT));
//            container.addView(img);
//
//            img.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    new ActionSheetDialog(BigViewPagerPhotoActivity.this)
//                            .builder()
//                            .setTitle("是否保存图片")
//                            .setCancelable(true)
//                            .setCanceledOnTouchOutside(false)
//                            .addSheetItem("确定", ActionSheetDialog.SheetItemColor.Blue,
//                                    new ActionSheetDialog.OnSheetItemClickListener() {
//                                        @Override
//                                        public void onClick(int which) {
//                                            tmpImg = img;
//                                            tmpImg.setTag(pics.get(position));
////                                            PermissionGen.needPermission(BigViewPagerPhotoActivity.this, 100, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                                        }
//                                    })
//                            .show();
//
//                    return false;
//                }
//            });
//            return img;
            return null;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

    /**
     * 保存图片
     */
    private void savePic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                tmpImg.setDrawingCacheEnabled(true);
                boolean saveAble = ImageUtils.save(tmpImg.getDrawingCache(), Constants.PATH.PATH_SAVE_SESSION_PIC + tmpImg.getTag(), Bitmap.CompressFormat.JPEG);
                tmpImg.setDrawingCacheEnabled(false);
                tmpImg = null;
                if (!BigViewPagerPhotoActivity.this.isFinishing()) {
                    final Bundle bundle = new Bundle();
                    ToastUtil.showLong(saveAble ? "保存图片成功" : "保存图片失败");
                }
            }
        }).start();

    }

//    @PermissionSuccess(requestCode = 100)
//    public void doSomethingSucceed() {
//        savePic();
//    }
//
//    @PermissionFail(requestCode = 100)
//    public void doSomethingFail() {
//        ConfirmDialog.createConfirmDialog(this, "可前往应用权限管理开启读写手机存储", "用户已拒绝访问相册", "确认", new ConfirmDialog.OnBtnConfirmListener() {
//            @Override
//            public void onConfirmListener() {
//
//            }
//        });
//    }


}
