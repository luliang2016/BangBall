package com.android.assignment3.DAO;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.util.Log;

import com.android.assignment3.R;
import com.android.assignment3.bangball.Game;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Administrator on 2016/4/22.
 */
public class BangBall {
    private final float RADIUS = Game.GlobalVar.asserts.ball_red.getWidth()/2f;
    private final int QUEUE_SIZE = Game.GlobalVar.resources.getInteger(R.integer.speed_queue_size);
    private final float INITIAL_ACCELERATION_RATE = Game.GlobalVar.resources.getInteger(R.integer.initial_acceleration_rate);
    private final float COLLIDED_ACCELERATION_RATE = Game.GlobalVar.resources.getInteger(R.integer.collided_acceleration_rate);
    private float cerX,cerY;
//    private int color;
    private Queue<Float> queueX,queueY;
    private float speedX,speedY;
    private float accelerationX,accelerationY;
    private float radian;
    private boolean isInitial; // true: initial status
    private boolean running;
    private Bitmap bangBallB;
    private int player;

    public BangBall(int color){
        queueX = new LinkedList<>();
        queueY = new LinkedList<>();
        speedX = 0;
        speedY = 0;
        isInitial = true;
        running = false;
        switch (color){
            case 1:bangBallB = Game.GlobalVar.asserts.ball_red;break;
            case 2:bangBallB = Game.GlobalVar.asserts.ball_blue;break;
            case 3:bangBallB = Game.GlobalVar.asserts.ball_green;break;
            case 4:bangBallB = Game.GlobalVar.asserts.ball_purple;break;
            case 5:bangBallB = Game.GlobalVar.asserts.ball_orange;break;
        }
//        bangBallB = Game.GlobalVar.asserts.ball_blue;
    }

    public BangBall(int color,int player){
        queueX = new LinkedList<>();
        queueY = new LinkedList<>();
        speedX = 0;
        speedY = 0;
        isInitial = true;
        running = false;
        switch (color){
            case 1:bangBallB = Game.GlobalVar.asserts.ball_red;break;
            case 2:bangBallB = Game.GlobalVar.asserts.ball_blue;break;
            case 3:bangBallB = Game.GlobalVar.asserts.ball_green;break;
            case 4:bangBallB = Game.GlobalVar.asserts.ball_purple;break;
            case 5:bangBallB = Game.GlobalVar.asserts.ball_orange;break;
        }
        this.player = player;
    }

    public void setCoordinate(float frameGap){
        if (speedX >= 0){ //forward to right
            if (speedX-accelerationX <= 0)
                speedX = 0;
            else
                speedX-=accelerationX;
        }else{ // forward to left
            if (speedX+accelerationX >= 0)
                speedX = 0;
            else
                speedX+=accelerationX;
        }
        cerX = cerX + speedX * frameGap;

        if (speedY >= 0){// forward to bottom
            if (speedY - accelerationY <= 0)
                speedY = 0;
            else
                speedY-=accelerationY;
        }else {// forward to top
            if (speedY +accelerationY >=0)
                speedY = 0;
            else
                speedY +=accelerationY;
        }
        cerY = cerY + speedY * frameGap;
    }

    public void setCoordinate(float cerX,float cerY){
        this.cerX = cerX* Game.GlobalVar.scaleX;
        this.cerY = cerY* Game.GlobalVar.scaleY;
    }

    public void addQueue(float cerX,float cerY){
        if (queueX.size() >= QUEUE_SIZE){
            queueX.remove();
        }
        if (queueY.size() >= QUEUE_SIZE){
            queueY.remove();
        }
        queueX.add(cerX);
        queueY.add(cerY);
    }

    public void setInitialAttributes(float frameGap){
        speedX = (cerX - queueX.element())*2;
        speedY = (cerY - queueY.element())*2;

        accelerationX = Math.abs(speedX/INITIAL_ACCELERATION_RATE) * frameGap;
        accelerationY = Math.abs(speedY/INITIAL_ACCELERATION_RATE) * frameGap;

        setRadian(speedX, speedY);
    }

    /**
     * Figures out the amount of radian according to speedX and speedY
     * then find out the direction according to whether speedX and speedY are positive or negative
     *
     * @param adjacent
     * @param opposite
     */
    public void setRadian(float adjacent,float opposite){
        float hypotenuse = (float)Math.sqrt(adjacent*adjacent + opposite*opposite);
        float cosR = (hypotenuse*hypotenuse+adjacent*adjacent-opposite*opposite)
                / (2*hypotenuse*Math.abs(adjacent));
        float tempRadian = (float)Math.acos(cosR);
        if (speedX >= 0 && speedY <= 0)
            radian = tempRadian;
        if (speedX < 0 && speedY <= 0)
            radian = (float)Math.PI - tempRadian;
        if (speedX < 0 && speedY > 0)
            radian = (float)Math.PI + tempRadian;
        if (speedX > 0 && speedY >= 0)
            radian = 2*(float)Math.PI - tempRadian;
    }

    public void handleCollision(List<BangBall> bangBallList,List<Obstacle> obstacleList,float frameGap){
        for (int i = 0;i<bangBallList.size()&&this!=bangBallList.get(i);i++){
            if (isCollidedWithBall(bangBallList.get(i))){
                collideBall(bangBallList.get(i),frameGap);// handle collision with ball
            }
        }
        for (int i = 0;i<obstacleList.size();i++){
            if (isCollideWithObstacle(obstacleList.get(i))!=0){
                collideObstacle(obstacleList.get(i),frameGap);// handle collision with obstacle
            }
        }
    }

    private boolean isCollidedWithBall(BangBall bangBall){
        float distance = (float)(Math.pow((this.getCerY() - bangBall.getCerY()),2)+
                Math.pow(this.getCerX()-bangBall.getCerX(),2));
        return distance <= (this.RADIUS + bangBall.RADIUS)*(this.RADIUS + bangBall.RADIUS);
    }

    private int isCollideWithObstacle(Obstacle obstacle){
        if (((this.cerX<obstacle.getRect().left&&(this.cerY<obstacle.getRect().top||this.cerY>obstacle.getRect().bottom))
                || (this.cerX>obstacle.getRect().right&&(this.cerY<obstacle.getRect().top||this.cerY>obstacle.getRect().bottom)))
                ){
            if (distanceSquareToPoint(obstacle)<=this.RADIUS*this.RADIUS)
                return 1; // Collide point of obstacle
        }else if (distanceToEdge(obstacle) <= this.RADIUS)
            return 2; // Collide edge
        return 0;// Does not touch any obstacle
    }

    private float distanceSquareToPoint(Obstacle obstacle){
        // Distance to top_left point
        float toPoint1 = (float)Math.pow(this.cerX-obstacle.getRect().left,2)+(float)Math.pow(this.cerY-obstacle.getRect().top,2);
        // Distance to top_right point
        float toPoint2 = (float)Math.pow(this.cerX-obstacle.getRect().right,2)+(float)Math.pow(this.cerY-obstacle.getRect().top,2);
        // Distance to bottom_right point
        float toPoint3 = (float)Math.pow(this.cerX-obstacle.getRect().right,2)+(float)Math.pow(this.cerY-obstacle.getRect().bottom,2);
        // Distance to bottom_left point
        float toPoint4 = (float)Math.pow(this.cerX-obstacle.getRect().left,2)+(float)Math.pow(this.cerY-obstacle.getRect().bottom,2);

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
        if (this.cerX<=obstacle.getRect().left){
            distance = obstacle.getRect().left-this.cerX;
            obstacle.setCollideType(Obstacle.COLLIDE_LEFT);
        }
        if (this.cerY<=obstacle.getRect().top){
            distance = obstacle.getRect().top-this.cerY;
            obstacle.setCollideType(Obstacle.COLLIDE_TOP);
        }
        if (this.cerX>=obstacle.getRect().right){
            distance = this.cerX - obstacle.getRect().right;
            obstacle.setCollideType(Obstacle.COLLIDE_RIGHT);
        }
        if (this.cerY>=obstacle.getRect().bottom){
            distance = this.cerY - obstacle.getRect().bottom;
            obstacle.setCollideType(Obstacle.COLLIDE_BOTTOM);
        }
        return distance;
    }

    /**
     * There are two types of collision
     *      one is a moving ball collides a static ball
     *      second is two moving balls collide
     *
     * @param bangBall
     * @param frameGap
     */
    private void collideBall(BangBall bangBall,float frameGap){
        //a moving ball collides a static ball
        if ((bangBall.getSpeedX()==0 && bangBall.getSpeedY()==0)){
            float adjacent = bangBall.getCerX() - this.getCerX();
            float opposite = bangBall.getCerY() - this.getCerY();
            this.setRadian(adjacent, opposite);
            bangBall.setRadian(this.getRadian());
            float tempRadian = this.getRadian()+(float)Math.PI;
            if (tempRadian >= 2*Math.PI)
                tempRadian = tempRadian - 2*(float)Math.PI;
            this.setRadian(tempRadian);
            this.setSpeed(this.getSpeedX(), this.getSpeedY(), 1 / 3f,frameGap);
            bangBall.setSpeed(this.getSpeedX(), this.getSpeedY(), 2 / 3f,frameGap);
        }else if ((this.getSpeedX()==0 && this.getSpeedY()==0)) {
            float adjacent = bangBall.getCerX() - this.getCerX();
            float opposite = bangBall.getCerY() - this.getCerY();

            bangBall.setRadian(adjacent, opposite);
            this.setRadian(bangBall.getRadian());
            float tempRadian = bangBall.getRadian()+(float)Math.PI;
            if (tempRadian >= 2*Math.PI)
                tempRadian = tempRadian - 2*(float)Math.PI;
            bangBall.setRadian(tempRadian);
            bangBall.setSpeed(bangBall.getSpeedX(), bangBall.getSpeedY(), 1 / 3f, frameGap);
            this.setSpeed(bangBall.getSpeedX(), bangBall.getSpeedY(), 2 / 3f, frameGap);

        }
        //two moving balls collide
        //just exchange two radians and speeds
        else {
            if (this.speedX > 0) {
                if (Math.abs(this.getSpeedX() + 1 - bangBall.getSpeedX()) < Math.abs(this.getSpeedX() - bangBall.getSpeedX())) {
                    float tempRadian = this.radian;
                    this.radian = bangBall.radian;
                    bangBall.radian = tempRadian;

                    this.setSpeed(bangBall.getSpeedX(), bangBall.getSpeedY(), 1, frameGap);
                    bangBall.setSpeed(this.getSpeedX(), this.getSpeedY(), 1, frameGap);
                }
            }
            if (this.speedX < 0) {
                if (Math.abs(this.getSpeedX() - 1 - bangBall.getSpeedX()) < Math.abs(this.getSpeedX() - bangBall.getSpeedX())) {
                    float tempRadian = this.radian;
                    this.radian = bangBall.radian;
                    bangBall.radian = tempRadian;

                    this.setSpeed(bangBall.getSpeedX(), bangBall.getSpeedY(), 1, frameGap);
                    bangBall.setSpeed(this.getSpeedX(), this.getSpeedY(), 1, frameGap);
                }
            }
        }
    }

    private void collideObstacle(Obstacle obstacle,float frameGap){
        float pointX=0,pointY=0;
        switch (obstacle.getCollideType()){
            case Obstacle.COLLIDE_POINT_1 :
                pointX = obstacle.getRect().left;
                pointY = obstacle.getRect().top;
                break;
            case Obstacle.COLLIDE_POINT_2 :
                pointX = obstacle.getRect().right;
                pointY = obstacle.getRect().top;
                break;
            case Obstacle.COLLIDE_POINT_3 :
                pointX = obstacle.getRect().right;
                pointY = obstacle.getRect().bottom;
                break;
            case Obstacle.COLLIDE_POINT_4 :
                pointX = obstacle.getRect().left;
                pointY = obstacle.getRect().bottom;
                break;

            case Obstacle.COLLIDE_LEFT :
                if (this.speedX>0){
                    if (this.radian>3f/2f*Math.PI)
                        this.radian = 3f*(float)Math.PI-this.radian;
                    else
                        this.radian = (float)Math.PI-this.radian;
                }
                break;
            case Obstacle.COLLIDE_TOP :
                if (this.speedY>0)
                    this.radian = (float)Math.PI*2f - this.radian;
                break;
            case Obstacle.COLLIDE_RIGHT :
                if (this.speedX<0)
                    if (this.radian<Math.PI)
                        this.radian = (float)Math.PI-this.radian;
                    else
                        this.radian = 3f*(float)Math.PI-this.radian;
                break;
            case Obstacle.COLLIDE_BOTTOM :
                if (this.speedY<0)
                    this.radian = 2f*(float)Math.PI-this.radian;
                break;
        }

        if (pointX!=0&&pointY!=0){
            float adjacent = this.getCerX() - pointX;
            float opposite = this.getCerY() - pointY;
            this.setRadian(adjacent, opposite);
            float tempRadian = this.getRadian()+(float)Math.PI;
            if (tempRadian >= 2*Math.PI)
                tempRadian = tempRadian - 2*(float)Math.PI;
            this.setRadian(tempRadian);
        }
        this.setSpeed(this.getSpeedX(),this.getSpeedY(),1,frameGap);
    }

    /**
     * Uses to change speed and acceleration after collision
     *
     * @param speedX
     * @param speedY
     * @param rate : The percentage of speed left after collision
     * @param frameGap
     */
    public void setSpeed(float speedX,float speedY,float rate,float frameGap){
        float speed = (float)Math.sqrt(speedX * speedX + speedY * speedY) * rate;
        setSpeedX(speed * (float) Math.cos(this.radian));
        setSpeedY(speed * -(float) Math.sin(this.radian));
        accelerationX = Math.abs(speedX/COLLIDED_ACCELERATION_RATE) * frameGap;
        accelerationY = Math.abs(speedY/COLLIDED_ACCELERATION_RATE) * frameGap;
    }

    public boolean isBallInTargetArea(TargetArea targetArea){
        return (Math.pow(this.cerX-targetArea.getCerX(),2)+Math.pow(this.cerY-targetArea.getCerY(),2))
                <(Math.pow((double)(this.getRADIUS()+targetArea.getRadius()),2));
    }
    public boolean isBallInBoundaryArea(Boundary boundary){
        return (Math.pow(this.cerX-boundary.getCerX(),2)+Math.pow(this.cerY-boundary.getCerY(),2))
                <(Math.pow((double)(this.getRADIUS()+boundary.getRadius()),2));
    }

    public boolean touchingBall(float x,float y){
        return (Math.pow(this.cerX-x,2)+Math.pow(this.cerY-y,2))
                <(Math.pow(this.getRADIUS(),2));
    }

    public float getRADIUS() {
        return RADIUS;
    }

    public float getCerX() {
        return cerX;
    }

    public float getCerY() {
        return cerY;
    }

//    public int getColor() {
//        return color;
//    }
//
//    public void setColor(int color) {
//        this.color = color;
//    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getRadian() {
        return radian;
    }

    public void setRadian(float radian) {
        this.radian = radian;
    }

    public boolean isInitial() {
        return isInitial;
    }

    public void setInitial(boolean initial) {
        this.isInitial = initial;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Bitmap getBangBallB() {
        return bangBallB;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }
}
