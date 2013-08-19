package com.druid.mayawabas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class SurfView extends SurfaceView implements SurfaceHolder.Callback {
    public volatile Mayactivity pMayactivity = null;
    public volatile Matrix matrix = new Matrix();
    public volatile Matrix savedMatrix = new Matrix();

    public volatile Context mContext = null;
    public volatile SurfaceHolder mHolder = null;
    public volatile Handler mHandler = null;

    public volatile Paint mSolidPaint;
    public volatile Bitmap mIttar;

    public SurfView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHandler = new Handler();

        OnTouchListener rootListener = new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN: {
                    
                }
                    break;
                }
                return true;
            }
        };

        this.setOnTouchListener(rootListener);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mSolidPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
        mSolidPaint.setAntiAlias(true);
        mSolidPaint.setStyle(Paint.Style.FILL);
        mSolidPaint.setARGB(255, 255, 255, 255);

        mIttar = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas tmpoCanvas = new Canvas(mIttar);
        tmpoCanvas.drawPaint(mSolidPaint);
        
        Thread workthread = new Thread(new RenderSurfaceView());
        workthread.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        matrix.reset();
        savedMatrix.reset();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIttar.recycle();
    }

    // //////////////////////////////////////////////////////////////////////////
    class RenderSurfaceView implements Runnable {

        public void run() {
            Canvas canvas = null;
            try {

                canvas = mHolder.lockCanvas(null);
                if (canvas != null) {
                    synchronized (mHolder) {
                        canvas.save();
                        canvas.drawBitmap(mIttar, matrix, mSolidPaint);
                        canvas.restore();

                    }
                } else {
                    Log.e(null, " RenderSurfaceView !*!*!*!!**!!");
                }
            } catch (Exception e) {
                Log.e(this.getClass().getName(), "Exception", e);
            } finally {
                if (canvas != null) {
                    mHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

}
