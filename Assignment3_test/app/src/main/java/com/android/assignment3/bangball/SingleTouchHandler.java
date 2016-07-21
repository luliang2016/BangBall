package com.android.assignment3.bangball;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.android.assignment3.DAO.TouchEvent;

/**
 * Created by Administrator on 2016/4/20.
 */
public class SingleTouchHandler extends OnTouchHandler {

    int count = 0 ;

    public SingleTouchHandler(float scaleX,float scaleY){
        super(scaleX,scaleY);
    }


    /**
     * When touch action is triggered, gets one instance from the pool and add this instance to the
     * touch event queue. It will be later taken out by different screen and being handled
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (Game.GlobalVar.touchAble&&!Game.GlobalVar.runningBall){
            TouchEvent touchEvent = Game.GlobalVar.touchEventPool.getInstance();
            if (event.getAction() == MotionEvent.ACTION_DOWN){
                touchEvent.setTouchType(TouchEvent.ACTION_DOWN);
            }
            if (event.getAction() == MotionEvent.ACTION_UP){
                touchEvent.setTouchType(TouchEvent.ACTION_UP);
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE){
                touchEvent.setTouchType(TouchEvent.ACTION_MOVE);
            }
            touchEvent.setX(event.getX() * scaleX);
            touchEvent.setY(event.getY() * scaleY);
            touchEvent.setPressure(event.getPressure());

            if (touchEvent.getTouchType()==TouchEvent.ACTION_DOWN
                    ||touchEvent.getTouchType()==TouchEvent.ACTION_UP
                    ||(touchEvent.getTouchType()==TouchEvent.ACTION_MOVE&& Game.GlobalVar.eventPermit)){
                if (Game.GlobalVar.boundary == null){
                    Game.GlobalVar.touchEventsList.add(touchEvent);
                }else {
                    if (!Game.GlobalVar.interrupt){
                        if (touchEvent.getTouchType()==TouchEvent.ACTION_DOWN &&
                                (touchEvent.isInBoundary(Game.GlobalVar.boundary)||touchEvent.isInObstacle(Game.GlobalVar.obstacleList))){
                            Game.GlobalVar.interrupt = true;
                            Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
                        }else if (touchEvent.getTouchType()==TouchEvent.ACTION_MOVE &&
                                (touchEvent.isInBoundary(Game.GlobalVar.boundary)||touchEvent.isInObstacle(Game.GlobalVar.obstacleList))){
                            touchEvent.setTouchType(TouchEvent.ACTION_UP);
                            Game.GlobalVar.touchEventsList.add(touchEvent);
                            Game.GlobalVar.interrupt = true;
                        }
                        else {
//                            if (count >= 30){
//                                touchEvent.setTouchType(TouchEvent.ACTION_UP);
//                                Game.GlobalVar.interrupt = true;
//                            }
//                        count++;
                            Game.GlobalVar.touchEventsList.add(touchEvent);
                        }
                    }else {
                        if (touchEvent.getTouchType()==TouchEvent.ACTION_UP){
                            Game.GlobalVar.interrupt = false;
                            Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
                            count = 0;
                        }else {
                            Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
                        }
                    }
                }
                return true;
            }
        }
        return false;
        }
}
