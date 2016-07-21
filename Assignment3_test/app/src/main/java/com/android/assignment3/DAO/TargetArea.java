package com.android.assignment3.DAO;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.android.assignment3.bangball.Game;

/**
 * Created by Administrator on 2016/5/31.
 */
public class TargetArea {
    private int type;
    private float radius;
    private float cerX,cerY;
    private Bitmap targetB;
    private Rect rect;

    public TargetArea(int type, float cerX, float cerY,float rate){
        this.type = type;
        this.cerX = cerX;
        this.cerY = cerY;
        this.radius = Game.GlobalVar.asserts.target.getWidth()/2f * rate;
        this.targetB = Game.GlobalVar.asserts.target;
        this.rect = new Rect(
                (int)(cerX-radius),
                (int)(cerY-radius),
                (int)(cerX+radius),
                (int)(cerY+radius));
    }

    public int getType() {
        return type;
    }

    public float getRadius() {
        return radius;
    }

    public float getCerX() {
        return cerX;
    }

    public float getCerY() {
        return cerY;
    }

    public Bitmap getTargetB() {
        return targetB;
    }

    public Rect getRect() {
        return rect;
    }
}
