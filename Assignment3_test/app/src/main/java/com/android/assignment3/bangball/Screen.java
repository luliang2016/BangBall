package com.android.assignment3.bangball;

import com.android.assignment3.tool.Graphic;

/**
 * Created by Administrator on 2016/4/20.
 */
public abstract class Screen {
    public Graphic graphic;

    public Screen(){
        this.graphic = new Graphic();
    }

    public abstract void update(float frameGap);
    public abstract void present(float frameGap);
}
