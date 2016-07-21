package com.android.assignment3.DAO;

import com.android.assignment3.bangball.Game;

/**
 * Created by Administrator on 2016/6/1.
 */
public class Boundary {
    private float radius;
    private float cerX,cerY;

    public Boundary(float cerX,float cerY,float radius){
        this.radius = radius;
        this.cerX = cerX* Game.GlobalVar.scaleX;
        this.cerY = cerY* Game.GlobalVar.scaleY;
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
}
