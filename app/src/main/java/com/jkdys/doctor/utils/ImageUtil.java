package com.jkdys.doctor.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * 图片上传工具
 */
public class ImageUtil {

    /**
     * 拿到照片的字节流
     *
     * @param file file
     * @return byte[]
     */
    public static byte[] getBytesFromFile(File file) {
        byte[] ret = null;
        try {
            if (file == null) {
                return null;
            }
            FileInputStream in = new FileInputStream(file);
            ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            in.close();
            out.close();
            ret = out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 获取指定文件大小
     *
     * @return
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            size = fis.available();
        }
        return size;
    }


    /**
     * 按照一定的宽高比例裁剪图片
     *
     * @param num1 长边的比例
     * @param num2 短边的比例
     * @return
     */
    public static Bitmap ImageCrop(String filePath, int num1, int num2,
                                   boolean isRecycled) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        // 获取到原始图片的尺寸
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        // 得到图片的宽，高
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int retX, retY;
        int nw, nh;
        if (w > h) {
            if (h > w * num2 / num1) {
                nw = w;
                nh = w * num2 / num1;
                retX = 0;
                retY = (h - nh) / 2;
            } else {
                nw = h * num1 / num2;
                nh = h;
                retX = (w - nw) / 2;
                retY = 0;
            }
        } else {
            if (w > h * num2 / num1) {
                nh = h;
                nw = h * num2 / num1;
                retY = 0;
                retX = (w - nw) / 2;
            } else {
                nh = w * num1 / num2;
                nw = w;
                retY = (h - nh) / 2;
                retX = 0;
            }
        }
        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, nw, nh, null,
                false);
        if (isRecycled && bitmap != null && !bitmap.equals(bmp)
                && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return bmp;
    }

    public static Bitmap getImageThumb(String imagePath, int width,
                                       int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);

        float scale;
        if (bitmap.getWidth() >= bitmap.getHeight()) {
            scale = width / (float) bitmap.getWidth();
        } else {
            scale = height / (float) bitmap.getHeight();
        }

        Matrix matrix = new Matrix();
        matrix.setScale(scale, scale);

        Bitmap bm = null;
        try {
            bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            if (baos.toByteArray().length / 1024 > 1024) {
                baos.reset();
                bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            }
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bm;
    }


    public static Bitmap ImageCrop(String filePath, int width, int height) {
        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }
        // 获取到原始图片的尺寸
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        return ImageCropRatio(bitmap, width, height);
    }

    /**
     * 按固定宽高4:3裁剪图片
     *
     * @param bitmap
     * @param maxWidth
     * @param maxHeight
     * @return
     */
    public static Bitmap ImageCropRatio(Bitmap bitmap, int maxWidth, int maxHeight) {
        int cut_width;
        int cut_height;
        int startx;
        int starty;
        Bitmap resultBitmap;

        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();

        if (srcWidth > srcHeight) {
            cut_width = (srcHeight * 4) / 3;
            cut_height = srcHeight;
            float xb = ((float) maxWidth) / cut_width;
            float yb = ((float) maxHeight) / cut_height;
            Matrix cut_matrix = new Matrix();
            cut_matrix.setScale(xb, yb);
            startx = (srcWidth - cut_width) / 2;
            resultBitmap = Bitmap.createBitmap(bitmap, startx, 0, cut_width, cut_height, cut_matrix, true);
        } else if (srcWidth < srcHeight) {
            cut_width = srcWidth;
            cut_height = (srcWidth * 3) / 4;
            float xb = ((float) maxWidth) / cut_width;
            float yb = ((float) maxHeight) / cut_height;
            Matrix cut_matrix = new Matrix();
            cut_matrix.setScale(xb, yb);
            starty = (srcHeight - cut_height) / 2;
            resultBitmap = Bitmap.createBitmap(bitmap, 0, starty, cut_width, cut_height, cut_matrix, true);
        } else {
            cut_width = srcWidth;
            cut_height = (srcWidth * 3) / 4;
            float xb = ((float) maxWidth) / cut_width;
            float yb = ((float) maxHeight) / cut_height;
            Matrix cut_matrix = new Matrix();
            cut_matrix.setScale(xb, yb);
            starty = (srcHeight - cut_height) / 2;
            resultBitmap = Bitmap.createBitmap(bitmap, 0, starty, cut_width, cut_height, cut_matrix, true);

            /*
            float scale;
            if (bitmap.getWidth() >= bitmap.getHeight()) {
                scale = width / (float) bitmap.getWidth();
            } else {
                scale = height / (float) bitmap.getHeight();
            }
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            */
        }
        return resultBitmap;
    }

    /**
     * 压缩保存Bitmap,返回保存路径
     *
     * @return
     */
    public static String saveCompressBitmap(Bitmap bm, String path) {
        if (bm == null) {
            return "";
        }

        String compressdPicPath = "";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        /*
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        }
        */
        int options = 100;
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        while (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();
            options -= 10;
            if (options < 11) {
                bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
                break;
            }
            bm.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        String oldName = path.substring(path.lastIndexOf("/"), path.lastIndexOf("."));
        String name = oldName + "_compress.jpg";
        String saveDir = Environment.getExternalStorageDirectory()
                + "/fdb";
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir();
        }

        // 保存
        File saveFile = new File(saveDir, name);
        try {
            FileOutputStream out = new FileOutputStream(saveFile);
            out.write(baos.toByteArray());
            compressdPicPath = saveFile.getAbsolutePath();
            out.flush();
            out.close();
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compressdPicPath;
    }


}
