package com.android.assignment3.bangball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Administrator on 2016/4/20.
 */
public class UIThreadView extends SurfaceView implements Runnable,SurfaceHolder.Callback{
    boolean running;
    SurfaceHolder holder;

    public UIThreadView(Context context){
        super(context);
        getHolder().addCallback(this);
        this.holder = getHolder();
    }

    @Override
    public void run() {
        Canvas canvas;
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while (running){
            if(!holder.getSurface().isValid())
                continue;
            canvas = holder.lockCanvas();

            Game.GlobalVar.eventPermit = true;

            float frameGap = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            synchronized (this){
                Game.GlobalVar.screen.update(frameGap);
                Game.GlobalVar.screen.present(frameGap);
                Game.GlobalVar.eventPermit = false;
            }

            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(Game.GlobalVar.drawingBitmap, null, dstRect, null);
            holder.unlockCanvasAndPost(canvas);

        }
    }

    public void onResume(){
        Thread thread = new Thread(this);
        thread.start();
        running = true;
    }

    public void onPause(){
        running = false;
    }



    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
