package com.juggist.jcore.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * @author juggist
 * @date 2018/11/23 1:45 PM
 */
public class CreateBitmapByViewUtil {
    public static Bitmap loadBitmapFromView(View view) {
        if (view == null)
            return null;
        Bitmap viewBit;
        viewBit = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(viewBit);
        canvas.translate(-view.getScrollX(), -view.getScaleY());
        view.draw(canvas);
        return viewBit;
    }
}
