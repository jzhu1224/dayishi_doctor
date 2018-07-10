/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.camera.CameraManager;
import com.scan.framework.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder rectangle and partial
 * transparency outside it, as well as the laser scanner animation and result points.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

    private static final long ANIMATION_DELAY = 10L;
    private static final int CURRENT_POINT_OPACITY = 0xA0;
    private static final int POINT_SIZE = 6;

    private CameraManager cameraManager;
    private final Paint paint;
    private final Paint scanLinePaint;
    private Bitmap resultBitmap;
    private final int maskColor;//扫描框外部的背景色
    private final int borderColor;//扫描线框颜色
    private final int cornerBorderColor;//扫描线框四个角的边框颜色
    private final int resultColor;
    private final int border; //扫描线框宽度
    private final int cornerBorder; //扫描线框宽度
    private final int cornerBorderLenth;//扫描线框四个角的边框的长度
    private Bitmap scanLine;
    private int index;
    boolean startDraw = false;

    // This constructor is used when the class is built from an XML resource.
    public ViewfinderView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Initialize these once for performance rather than calling them every time in onDraw().
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scanLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Resources resources = getResources();

        border = resources.getDimensionPixelOffset(R.dimen.viewfinder_border);
        cornerBorder = resources.getDimensionPixelOffset(R.dimen.viewfinder_corner_border);
        cornerBorderLenth = resources.getDimensionPixelOffset(R.dimen.viewfinder_corner_border_length);
        maskColor = resources.getColor(R.color.viewfinder_mask);
        resultColor = resources.getColor(R.color.result_view);
        borderColor = resources.getColor(R.color.viewfinder_border);
        cornerBorderColor = resources.getColor(R.color.viewfinder_corner_border);
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (cameraManager == null) {
            return; // not ready yet, early draw before done configuring
        }

        Rect frame = cameraManager.getFramingRect();
        /*Rect frame = cameraManager.getFramingRect(
                getContext().getResources().getDimensionPixelOffset(R.dimen.camera_scan_width),
                getContext().getResources().getDimensionPixelOffset(R.dimen.camera_scan_height),
                0,0,
                getContext().getResources().getDimensionPixelOffset(R.dimen.camera_scan_marginTop),
                0);*/
        //Rect previewFrame = cameraManager.getFramingRectInPreview();
        if (frame == null) {
            return;
        }

        if (scanLine == null || scanLine.isRecycled()) {
            scanLine = BitmapFactory.decodeResource(getResources(), R.drawable.scan_line);
            int width = scanLine.getWidth();
            int height = scanLine.getHeight();
            float scaleWidth = ((float) frame.width()) / width;
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleWidth);
            Bitmap newbm = Bitmap.createBitmap(scanLine, 0, 0, width, height, matrix, true);
            scanLine.recycle();
            scanLine = newbm;
        }

        int width = canvas.getWidth();
        int height = canvas.getHeight();

        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);

        //Draw 扫描线框
        paint.setColor(borderColor);
        canvas.drawRect(frame.left, frame.top, frame.left + border, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.top, frame.right, frame.top + border, paint);
        canvas.drawRect(frame.right - border, frame.top, frame.right, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - border, frame.right, frame.bottom, paint);

        //Draw 扫描线框四个角的边框
        paint.setColor(cornerBorderColor);
        //左上
        canvas.drawRect(frame.left, frame.top, frame.left + cornerBorder, frame.top + cornerBorderLenth, paint);
        canvas.drawRect(frame.left, frame.top, frame.left + cornerBorderLenth, frame.top + cornerBorder, paint);
        //右上
        canvas.drawRect(frame.right - cornerBorderLenth, frame.top, frame.right, frame.top + cornerBorder, paint);
        canvas.drawRect(frame.right - cornerBorder, frame.top, frame.right, frame.top + cornerBorderLenth, paint);
        //右下
        canvas.drawRect(frame.right - cornerBorder, frame.bottom - cornerBorderLenth, frame.right, frame.bottom, paint);
        canvas.drawRect(frame.right - cornerBorderLenth, frame.bottom - cornerBorder, frame.right, frame.bottom, paint);
        //左下
        canvas.drawRect(frame.left, frame.bottom - cornerBorderLenth, frame.left + cornerBorder, frame.bottom, paint);
        canvas.drawRect(frame.left, frame.bottom - cornerBorder, frame.left + cornerBorderLenth, frame.bottom, paint);

        if (resultBitmap == null) {
            // Draw a red "laser scanner" line through the middle to show decoding is active
            /*paint.setColor(laserColor);
            paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
            scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
            int middle = frame.height() / 2 + frame.top;
            canvas.drawRect(frame.left + 2, middle - 1, frame.right - 1, middle + 2, paint);*/


            int top = frame.top + index * POINT_SIZE;
            if (top > frame.bottom) {
                index = 0;
                top = frame.top;
            }
            canvas.drawBitmap(scanLine, frame.left + ((frame.width() - scanLine.getWidth()) / 2), top, scanLinePaint);
            // Request another update at the animation interval, but only repaint the laser line,
            // not the entire viewfinder mask.
            index++;
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top,
                    frame.right, frame.bottom);
        } else {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        }
    }

    public void drawViewfinder() {
        Bitmap resultBitmap = this.resultBitmap;
        this.resultBitmap = null;
        if (resultBitmap != null) {
            resultBitmap.recycle();
        }
        invalidate();
    }

    public void addPossibleResultPoint(ResultPoint point) {

    }
}