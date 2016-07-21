package com.android.assignment3.DAO;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.android.assignment3.R;
import com.android.assignment3.bangball.Game;

/**
 * Created by Administrator on 2016/5/13.
 */
public class Obstacle {
    public static final int BLOCK1 = 1;
    public static final int BLOCK2 = 2;
    public static final int BLOCK3 = 3;
    public static final int BLOCK4 = 4;
    public static final int COLLIDE_POINT_1 = 1; // Top left point
    public static final int COLLIDE_POINT_2 = 2; // Top right point
    public static final int COLLIDE_POINT_3 = 3; // Bottom right point
    public static final int COLLIDE_POINT_4 = 4; // Bottom left point
    public static final int COLLIDE_LEFT = 5;
    public static final int COLLIDE_TOP = 6;
    public static final int COLLIDE_RIGHT = 7;
    public static final int COLLIDE_BOTTOM = 8;


    private int type;
    private int collideType;
    private Bitmap obstacleB;
    /**
     * Left : Points()[0]
     * Top : Points()[1]
     * Right : Points()[2]
     * Bottom : Points()[3]
     */
    private Rect rect;

    public Obstacle(int type,float topLeftX,float topLeftY){
        int width = 0,height = 0;
        this.type = type;
        switch (type){
            case Obstacle.BLOCK1:
                width = Game.GlobalVar.asserts.block_1.getWidth();
                height = Game.GlobalVar.asserts.block_1.getHeight();
                obstacleB = Game.GlobalVar.asserts.block_1;
                break;
            case Obstacle.BLOCK2:
                width = Game.GlobalVar.asserts.block_2.getWidth();
                height = Game.GlobalVar.asserts.block_2.getHeight();
                obstacleB = Game.GlobalVar.asserts.block_2;
                break;
            case Obstacle.BLOCK3:
                width = Game.GlobalVar.asserts.block_3.getWidth();
                height = Game.GlobalVar.asserts.block_3.getHeight();
                obstacleB = Game.GlobalVar.asserts.block_3;
                break;
            case Obstacle.BLOCK4:
                width = Game.GlobalVar.asserts.block_4.getWidth();
                height = Game.GlobalVar.asserts.block_4.getHeight();
                obstacleB = Game.GlobalVar.asserts.block_4;
                break;
        }
        rect = new Rect(
                (int)(topLeftX),
                (int)(topLeftY),
                (int)((topLeftX+width)),
                (int)((topLeftY+height)));
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Rect getRect() {
        return rect;
    }

    public int getCollideType() {
        return collideType;
    }

    public void setCollideType(int collideType) {
        this.collideType = collideType;
    }

    public Bitmap getObstacleB() {
        return obstacleB;
    }
}
