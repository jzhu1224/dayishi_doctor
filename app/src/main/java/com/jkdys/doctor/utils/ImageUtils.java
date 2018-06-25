package com.jkdys.doctor.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.hyphenate.easeui.utils.EaseImageUtils;

/**
 * Created by zhujiang on 2017/11/14.
 */

public class ImageUtils {

    public static Bitmap decodeScaleImage(String file, int width, int height) {
        BitmapFactory.Options options = EaseImageUtils.getBitmapOptions(file);
        int inSampleSize = EaseImageUtils.calculateInSampleSize(options, width, height);
        if (inSampleSize > 2)
            return EaseImageUtils.decodeScaleImage(file,width,height);

        return EaseImageUtils.decodeScaleImage(file,width,height);
    }
    public static Bitmap scaleImage(String path, int width, int height) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        BitmapFactory.Options options = EaseImageUtils.getBitmapOptions(path);

        int ow = options.outWidth;
        int oh = options.outHeight;

        float var5 = 1.f;

        if(width > ow || height > oh) {
            float var6 = (float)width / (float)ow;
            float var7 = (float)height / (float)oh;
            var5 = var6 < var7?var6:var7;
        }

        return Bitmap.createScaledBitmap(bitmap, (int)(var5*ow), (int)(var5*oh), false);
    }
}
