package com.jkdys.doctor.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

public final class EncodingHandler {
    private static final int BLACK = 0xff000000;

    public static Bitmap createQRCode(String str, int widthAndHeight) throws WriterException {
        Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");

        BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, widthAndHeight, widthAndHeight);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = BLACK;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static Bitmap createQRCodeWithLogo(String content, Bitmap logo, int widthAndHeight, int logoWidthAndHeight)
            throws Exception {
        BarcodeFormat format = BarcodeFormat.AZTEC.QR_CODE;
        Matrix m = new Matrix();
        float sx = (float) 2 * logoWidthAndHeight / logo.getWidth();
        float sy = (float) 2 * logoWidthAndHeight
                / logo.getHeight();
        m.setScale(sx, sy);//设置缩放信息
        //将logo图片按martix设置的信息缩放
        logo = Bitmap.createBitmap(logo, 0, 0,
                logo.getWidth(), logo.getHeight(), m, false);
        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable hst = new Hashtable();
        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");//设置字符编码
        BitMatrix matrix = writer.encode(content, format, widthAndHeight, widthAndHeight, hst);//生成二维码矩阵信息
        matrix = deleteWhite(matrix);
        int width = matrix.getWidth();//矩阵高度
        int height = matrix.getHeight();//矩阵宽度
        int halfW = width / 2;
        int halfH = height / 2;
        int[] pixels = new int[width * height];//定义数组长度为矩阵高度*矩阵宽度，用于记录矩阵中像素信息

        boolean flag = false;
        int startX=0;
        int startY=0;
        int l=0;
        int w=0;
        for (int y = 0; y < height; y++) {//从行开始迭代矩阵
            for (int x = 0; x < width; x++) {//迭代列

                if(matrix.get(x, y) && !flag) {
                    flag = true;
                    startX = x;
                    startY = y;
                    //Log.e("TTTT","找到起始点 startY="+y+" startX="+x);
                }

                if (x > halfW - logoWidthAndHeight && x < halfW + logoWidthAndHeight
                        && y > halfH - logoWidthAndHeight
                        && y < halfH + logoWidthAndHeight) {//该位置用于存放图片信息
                    //记录图片每个像素信息
                    pixels[y * width + x] = logo.getPixel(x - halfW
                            + logoWidthAndHeight, y - halfH + logoWidthAndHeight);
                } else {
                    if (matrix.get(x, y)) {//如果有黑块点，记录信息
                        pixels[y * width + x] = 0xff000000;//记录黑块信息
                    }
                }

                if(flag && y == startY && x>startX && l==0) {
                    if(!matrix.get(x, y)) {
                        l = x;
                    }
                }
            }
        }

        int half = l/2;
        for(int x=startX;x<width;x++) {
            if(!matrix.get(x, half) && w == 0) {
                w = x-startX;
            }
        }

//        for (int y = startX; y < height-startY-w; y++) {//从行开始迭代矩阵
//            for (int x = startY; x < width-startX-w; x++) {//迭代列
//
//                //左上角
//                if(x>w+startX && x<l-w
//                        && y>w+startY && y<l-w && matrix.get(x, y)) {
//                    pixels[y * width + x] = 0xff0091ea;//变蓝
//                }
//
//                //右上角
//                if(x>width-l+w
//                        && y>w+startY && y<l-w && matrix.get(x, y)) {
//                    pixels[y * width + x] = 0xff0091ea;//变蓝
//                }
//
//                //左下角
//                if(x>w+startX && x<l-w
//                        && y>height-l+w && matrix.get(x, y)) {
//                    pixels[y * width + x] = 0xff0091ea;//变蓝
//                }
//            }
//        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 去掉二维码白边
     * @param matrix
     * @return
     */
    public static BitMatrix deleteWhite(BitMatrix matrix){
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }
}