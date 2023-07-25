package com.dfgadgh.iv_move;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CanvanCreat {

    public static Bitmap captureBackgroundAndPicture(RelativeLayout relativeLayout, ImageView imageView) {
        // 创建一个Bitmap对象并将Canvas绘制到上面
        Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getWidth(), relativeLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // 在Canvas上绘制背景视图
        relativeLayout.draw(canvas);

        // 计算图片视图在Canvas中的位置
        int[] location = new int[2];
        imageView.getLocationOnScreen(location);

        // 在Canvas上绘制图片视图
        canvas.save();
        canvas.translate(0, relativeLayout.getHeight());
        canvas.clipRect(location[0], location[1] - relativeLayout.getHeight(), location[0] + imageView.getWidth(), location[1]);
        imageView.draw(canvas);
        canvas.restore();

        return Bitmap.createScaledBitmap(bitmap, relativeLayout.getWidth(), relativeLayout.getHeight(), true);
    }
}
