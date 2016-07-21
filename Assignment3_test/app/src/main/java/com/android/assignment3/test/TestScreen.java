package com.android.assignment3.test;

import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.android.assignment3.DAO.BangBall;
import com.android.assignment3.DAO.Obstacle;
import com.android.assignment3.DAO.TouchEvent;
import com.android.assignment3.R;
import com.android.assignment3.bangball.Game;
import com.android.assignment3.bangball.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/22.
 */
public class TestScreen extends Screen {
    final int BANG_BALL_LIST_SIZE = Game.GlobalVar.resources.getInteger(R.integer.ball_list_size_multi);
    List<BangBall> bangBallList;
    List<Obstacle> obstacleList;
    int color = 1;

    public TestScreen(){
        super();
        bangBallList = new ArrayList<>(BANG_BALL_LIST_SIZE);
        obstacleList = new ArrayList<>();

        //Test
        obstacleList.add(new Obstacle(1,150,200));
    }

    @Override
    public void update(float frameGap) {
        if (Game.GlobalVar.touchEventsList.size() > 0) {
            TouchEvent touchEvent = Game.GlobalVar.touchEventsList.remove();
            if (touchEvent.getTouchType() == TouchEvent.ACTION_DOWN) {
                BangBall bangBall = new BangBall(color);
                bangBall.setCoordinate(touchEvent.getX(), touchEvent.getY());
                bangBall.addQueue(touchEvent.getX(), touchEvent.getY());
                bangBallList.add(bangBall);
            }
            if (touchEvent.getTouchType() == TouchEvent.ACTION_MOVE) {
                bangBallList.get(bangBallList.size() - 1).addQueue(touchEvent.getX(), touchEvent.getY());
                bangBallList.get(bangBallList.size() - 1).setCoordinate(touchEvent.getX(), touchEvent.getY());
            }
            if (touchEvent.getTouchType() == TouchEvent.ACTION_UP) {
                bangBallList.get(bangBallList.size() - 1).setCoordinate(touchEvent.getX(), touchEvent.getY());
                bangBallList.get(bangBallList.size() - 1).setInitialAttributes(frameGap);
            }
        }else {
            Game.GlobalVar.runningBall = false;
            for (int i = 0; i < bangBallList.size(); i++) {
                bangBallList.get(i).setCoordinate(frameGap);
                bangBallList.get(i).handleCollision(bangBallList,obstacleList,frameGap);
                if (bangBallList.get(i).getSpeedX() != 0)
                    Game.GlobalVar.runningBall = true;
            }
        }
    }

    @Override
    public void present(float frameGap) {
        Canvas canvas = new Canvas(Game.GlobalVar.drawingBitmap);
//        graphic.drawColor(canvas, Color.WHITE);
        graphic.drawBackground(canvas, Game.GlobalVar.asserts.background_1);
        for (int i = 0; i < obstacleList.size();i++){
            Obstacle obstacle = obstacleList.get(i);
            graphic.drawRect(canvas,obstacle.getRect());
        }
        for (int i = 0; i < bangBallList.size(); i++) {
            BangBall bangBall = bangBallList.get(i);
//            Paint paint = new Paint();
//            paint.setAntiAlias(true);
//            paint.setStrokeWidth(Game.GlobalVar.resources.getInteger(R.integer.ball_stroke_width));
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setColor(bangBall.getColor());
//            graphic.drawBall(canvas, Game.GlobalVar.asserts.ball_red_1, bangBall.getCerX(), bangBall.getCerY());
//            graphic.drawCircle(canvas, bangBall.getCerX(), bangBall.getCerY(), bangBall.getRADIUS(), paint);
        }
    }
}
