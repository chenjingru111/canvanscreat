package com.dfgadgh.iv_move;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class DownloadImage {
    public static String saveBitmapToGallery_background(Context context, Bitmap mergedBitmap) {
        // 首先创建一个保存目录
        String fileName = "IMG_" + System.currentTimeMillis() + ".jpg";
        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 指定文件路径和名称
        File file = new File(dir, fileName);
        try {
            // 将 Bitmap 保存到文件中
            FileOutputStream out = new FileOutputStream(file);
            mergedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            // 通知系统扫描文件
            Uri uri = Uri.fromFile(file);
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
            context.sendBroadcast(mediaScanIntent);
            // 弹出保存成功提示
            Toast.makeText(context, "succeed to save", Toast.LENGTH_SHORT).show();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            return fileName;
        }
    }
}
