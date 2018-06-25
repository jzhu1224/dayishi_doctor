package com.jkdys.doctor.ui.chat.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.jkdys.doctor.R;

public class PorterShapeImageView extends PorterImageView {
    private Drawable shape;
    private Matrix matrix;
    private Matrix drawMatrix;

    public static final PorterDuffXfermode CLEAR = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);

    Bitmap bgBitmap;
    Canvas bgCanvas;
    Drawable bgDrawable;
    Paint paint;

    public PorterShapeImageView(Context context) {
        super(context);
        setup(context, null, 0);
    }

    public PorterShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs, 0);
    }

    public PorterShapeImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setup(context, attrs, defStyle);
    }

    private void setup(Context context, AttributeSet attrs, int defStyle) {
        if(attrs != null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShaderImageView, defStyle, 0);
            shape = typedArray.getDrawable(R.styleable.ShaderImageView_siShape);
            typedArray.recycle();
        }
        matrix = new Matrix();
    }

    @Override
    protected void paintMaskCanvas(Canvas maskCanvas, Paint maskPaint, int width, int height) {
        if(shape != null) {
            if (shape instanceof BitmapDrawable) {
                configureBitmapBounds(width, height);
                if(drawMatrix != null) {
                    int drawableSaveCount = maskCanvas.getSaveCount();
                    maskCanvas.save();
                    maskCanvas.concat(matrix);
                    shape.draw(maskCanvas);
                    maskCanvas.restoreToCount(drawableSaveCount);
                    return;
                }
            }

            shape.setBounds(0, 0, width, height);
            shape.draw(maskCanvas);
        }


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //if (bgBitmap == null) {
//            bgBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
//            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            bgCanvas = new Canvas(bgBitmap);
//            bgDrawable = getResources().getDrawable(R.drawable.corner_90000000);
//            bgDrawable.setBounds(0, 0, w, h);
        //}
    }

    private void configureBitmapBounds(int viewWidth, int viewHeight) {
        drawMatrix = null;
        int drawableWidth = shape.getIntrinsicWidth();
        int drawableHeight = shape.getIntrinsicHeight();
        boolean fits = viewWidth == drawableWidth && viewHeight == drawableHeight;

        if (drawableWidth > 0 && drawableHeight > 0 && !fits) {
            shape.setBounds(0, 0, drawableWidth, drawableHeight);
            float widthRatio = (float) viewWidth / (float) drawableWidth;
            float heightRatio = (float) viewHeight / (float) drawableHeight;
            float scale = Math.min(widthRatio, heightRatio);
            float dx = (int) ((viewWidth - drawableWidth * scale) * 0.5f + 0.5f);
            float dy = (int) ((viewHeight - drawableHeight * scale) * 0.5f + 0.5f);

            matrix.setScale(scale, scale);
            matrix.postTranslate(dx, dy);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        LogUtil.d("zhujiang","showMask:"+showMask + "     status:"+status);
//
//        if (getWidth() == 0 || getHeight() == 0)
//            return;
//
//        if (showMask != null && showMask && status == 0) {
//
//            if (bgBitmap == null) {
//                bgBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
//                paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//                bgCanvas = new Canvas(bgBitmap);
//                bgDrawable = getResources().getDrawable(R.drawable.corner_90000000);
//                bgDrawable.setBounds(0, 0, getWidth(), getHeight());
//            }
//
//            paint.reset();
//            paint.setXfermode(PORTER_DUFF_XFERMODE);
//            bgDrawable.draw(bgCanvas);
//            bgCanvas.drawBitmap(drawableBitmap,0,0,paint);
//            paint.setXfermode(null);
//            canvas.drawBitmap(bgBitmap,0.0f,0.0f, paint);
//            status = 1;
//        } else if (showMask != null && !showMask && status == 1){
//            if (paint == null) {
//                paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//            }
//            paint.reset();
//            paint.setXfermode(CLEAR);
//            bgDrawable.draw(bgCanvas);
//            bgCanvas.drawBitmap(drawableBitmap,0,0,paint);
//            paint.setXfermode(null);
//            canvas.drawBitmap(bgBitmap,0.0f,0.0f, paint);
//            status = 0;
//        }
    }

    Boolean showMask = null;
    int status =0;//0 无mask 1有mask

    public void showMask() {
        showMask = true;
        //invalidate();
    }

    public void hideMask() {
        showMask = false;
        //invalidate();
    }
}