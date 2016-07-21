package com.android.assignment3.bangball;

import android.view.View;

/**
 * Created by Administrator on 2016/4/20.
 */
public abstract class OnTouchHandler implements View.OnTouchListener {
    float scaleX,scaleY;

    public OnTouchHandler(float scaleX,float scaleY){
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }
}
