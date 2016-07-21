package com.android.assignment3.DAO;

import android.graphics.Rect;

import com.android.assignment3.bangball.Game;

import java.util.List;

/**
 * Created by Administrator on 2016/4/20.
 */
public class TouchEvent {
    public static final int ACTION_UP = 1;
    public static final int ACTION_DOWN = 2;
    public static final int ACTION_MOVE = 3;

    private float x,y;
    private float pressure;
    private int touchID; // Reserved for multi_touch
    private int touchIndex; // Reserved for multi_touch
    private int touchType;

    public boolean isInRect(Rect rect){
        if (x<rect.right&&x>rect.left&&y<rect.bottom&&y>rect.top){
            return true;
        }
        return false;
    }

    public boolean isInBoundary(Boundary boundary){
        return (Math.pow(x-boundary.getCerX(),2)+Math.pow(y-boundary.getCerY(),2))
                <(Math.pow((double)(boundary.getRadius()),2));
    }

    public boolean isInObstacle(List<Obstacle> obstacles){
        float radius = Game.GlobalVar.asserts.ball_red.getWidth()/2;
        for (int i=0;i<obstacles.size();i++){
            if (((x<obstacles.get(i).getRect().left&&(this.y<obstacles.get(i).getRect().top||y>obstacles.get(i).getRect().bottom))
                    || (x>obstacles.get(i).getRect().right&&(y<obstacles.get(i).getRect().top||y>obstacles.get(i).getRect().bottom)))
                    ){
                if (distanceSquareToPoint(obstacles.get(i))<= radius*radius)
                    return true; // Collide point of obstacle
            }else if (distanceToEdge(obstacles.get(i)) <= radius)
                return true; // Collide edge
            return false;// Does not touch any obstacle
        }
        return false;
    }

    private float distanceSquareToPoint(Obstacle obstacle){
        // Distance to top_left point
        float toPoint1 = (float)Math.pow(this.x-obstacle.getRect().left,2)+(float)Math.pow(this.y-obstacle.getRect().top,2);
        // Distance to top_right point
        float toPoint2 = (float)Math.pow(this.x-obstacle.getRect().right,2)+(float)Math.pow(this.y-obstacle.getRect().top,2);
        // Distance to bottom_right point
        float toPoint3 = (float)Math.pow(this.x-obstacle.getRect().right,2)+(float)Math.pow(this.y-obstacle.getRect().bottom,2);
        // Distance to bottom_left point
        float toPoint4 = (float)Math.pow(this.x-obstacle.getRect().left,2)+(float)Math.pow(this.y-obstacle.getRect().bottom,2);
        float distance = Math.min(Math.min(Math.min(toPoint1,toPoint2),toPoint3),toPoint4);
        if (distance == toPoint1)
            obstacle.setCollideType(Obstacle.COLLIDE_POINT_1);
        else if (distance == toPoint2)
            obstacle.setCollideType(Obstacle.COLLIDE_POINT_2);
        else if (distance == toPoint3)
            obstacle.setCollideType(Obstacle.COLLIDE_POINT_3);
        else if (distance == toPoint4)
            obstacle.setCollideType(Obstacle.COLLIDE_POINT_4);
        return distance;
    }

    private float distanceToEdge(Obstacle obstacle){
        float distance = 0;
        if (this.x<=obstacle.getRect().left){
            distance = obstacle.getRect().left-this.x;
            obstacle.setCollideType(Obstacle.COLLIDE_LEFT);
        }
        if (this.y<=obstacle.getRect().top){
            distance = obstacle.getRect().top-this.y;
            obstacle.setCollideType(Obstacle.COLLIDE_TOP);
        }
        if (this.x>=obstacle.getRect().right){
            distance = this.x - obstacle.getRect().right;
            obstacle.setCollideType(Obstacle.COLLIDE_RIGHT);
        }
        if (this.y>=obstacle.getRect().bottom){
            distance = this.y - obstacle.getRect().bottom;
            obstacle.setCollideType(Obstacle.COLLIDE_BOTTOM);
        }
        return distance;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public int getTouchType() {
        return touchType;
    }

    public void setTouchType(int touchType) {
        this.touchType = touchType;
    }

    public int getTouchID() {
        return touchID;
    }

    public void setTouchID(int touchID) {
        this.touchID = touchID;
    }

    public int getTouchIndex() {
        return touchIndex;
    }

    public void setTouchIndex(int touchIndex) {
        this.touchIndex = touchIndex;
    }
}
