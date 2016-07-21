package com.android.assignment3.Screens;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;

import com.android.assignment3.DAO.BangBall;
import com.android.assignment3.DAO.Boundary;
import com.android.assignment3.DAO.Obstacle;
import com.android.assignment3.DAO.TargetArea;
import com.android.assignment3.DAO.TouchEvent;
import com.android.assignment3.bangball.Game;
import com.android.assignment3.bangball.OnePlayerResultDialog;
import com.android.assignment3.bangball.PauseDialog;
import com.android.assignment3.bangball.Screen;
import com.android.assignment3.bangball.TwoPlayersResultDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/22.
 */
public class TwoPlayersScreen extends Screen {
    int bangBallListSize = 2;
    List<BangBall> bangBallList;
    TargetArea targetArea;
    int modelType; // 1: pre-placed balls 2: places ball when touch
    int ballN;
    int ballsInTargetArea = -1;
    boolean interrupt;
    boolean actionDownInBoundary;
    TwoPlayersResultDialog twoPlayersResultDialog;
    int P1Color = 1;
    int P2Color = 2;
    int turn = 1; // odd:player1  even:player2
    int level = 0;
    int p1Count=0,p2Count=0;
    Rect pauseR;

    public TwoPlayersScreen(){
        super();
        bangBallList = new ArrayList<>();
        Game.GlobalVar.obstacleList = new ArrayList<>();
        Game.GlobalVar.touchAble = true;
        interrupt = false;
        actionDownInBoundary = false;
        ballN=-1;
        modelType = 2;
        targetArea = new TargetArea(1, Game.GlobalVar.drawingBitmap.getWidth()/2, Game.GlobalVar.drawingBitmap.getHeight()/2,2/3f);
        Game.GlobalVar.boundary = new Boundary(targetArea.getCerX(),targetArea.getCerY(),targetArea.getRadius()+50);
        pauseR = new Rect(10,10, Game.GlobalVar.asserts.pause.getWidth(), Game.GlobalVar.asserts.pause.getHeight());
        //Test
//        modelType = 2;
//        Game.GlobalVar.obstacleList.add(new Obstacle(1,10,200));
//        targetArea = new TargetArea(1,600,700,1/2f);
//        Game.GlobalVar.boundary = new Boundary(targetArea.getCerX(),targetArea.getCerY(),targetArea.getRadius()+50);
//        paint.setStyle(Paint.Style.STROKE);

//        BangBall bangBall1 = new BangBall();
//        bangBall1.setCoordinate(100, 700);
//        BangBall bangBall2 = new BangBall();
//        bangBall2.setCoordinate(300, 700);
//        BangBall bangBall3 = new BangBall();
//        bangBall3.setCoordinate(200, 800);
//        bangBallList.add(bangBall1);
//        bangBallList.add(bangBall2);
//        bangBallList.add(bangBall3);
    }

    @Override
    public void update(float frameGap) {
        if (Game.GlobalVar.touchEventsList.size() > 0) {
            TouchEvent touchEvent = Game.GlobalVar.touchEventsList.remove();
            if (modelType == 2){
                if (touchEvent.getTouchType() == TouchEvent.ACTION_DOWN) {
                    if (touchEvent.isInRect(pauseR)){
                        Game.GlobalVar.touchAble = false;
                        Game.GlobalVar.uiThreadView.onPause();
                        Bundle bundle = new Bundle();
                        bundle.putInt("level", level);
                        PauseDialog pauseDialog = new PauseDialog();
                        pauseDialog.setArguments(bundle);
                        pauseDialog.show(Game.GlobalVar.fragmentManager,"PauseDialog");
                    }else {
                        BangBall bangBall = null;
                        if (turn%2==1)
                            bangBall = new BangBall(P1Color,1);
                        else
                            bangBall = new BangBall(P2Color,2);
                        turn++;
                        bangBall.setCoordinate(touchEvent.getX(), touchEvent.getY());
                        bangBall.addQueue(touchEvent.getX(), touchEvent.getY());
                        bangBallList.add(bangBall);
                    }

                    Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
                }
                if (touchEvent.getTouchType() == TouchEvent.ACTION_MOVE) {
                    if (bangBallList.size()>0){
                        bangBallList.get(bangBallList.size() - 1).addQueue(touchEvent.getX(), touchEvent.getY());
                        bangBallList.get(bangBallList.size() - 1).setCoordinate(touchEvent.getX(), touchEvent.getY());
                    }
                    Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
                }
                if (touchEvent.getTouchType() == TouchEvent.ACTION_UP) {
                    if (bangBallList.size()>0){
                        bangBallList.get(bangBallList.size() - 1).setCoordinate(touchEvent.getX(), touchEvent.getY());
                        bangBallList.get(bangBallList.size() - 1).setInitialAttributes(frameGap);
                    }
                    if (bangBallList.size() >= bangBallListSize) {
                        Game.GlobalVar.touchAble = false;
                    }
                    Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
                }
            } else if (modelType == 1){
                if (touchEvent.getTouchType() == TouchEvent.ACTION_DOWN) {
                    ballN=-1;
                    for (int i =0;i<bangBallList.size();i++){
                        if (bangBallList.get(i).touchingBall(touchEvent.getX(),touchEvent.getY())
                                &&bangBallList.get(i).isInitial()){
                            bangBallList.get(i).addQueue(touchEvent.getX(), touchEvent.getY());
                            bangBallList.get(i).setCoordinate(touchEvent.getX(), touchEvent.getY());
                            ballN = i;
                            Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
                            break;
                        }
                    }
                }
                if (touchEvent.getTouchType() == TouchEvent.ACTION_MOVE) {
                    if (ballN!=-1){
                        bangBallList.get(ballN).addQueue(touchEvent.getX(), touchEvent.getY());
                        bangBallList.get(ballN).setCoordinate(touchEvent.getX(), touchEvent.getY());
                    }
                    Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
                }
                if (touchEvent.getTouchType() == TouchEvent.ACTION_UP) {
                    if (ballN!=-1){
                        bangBallList.get(ballN).setCoordinate(touchEvent.getX(), touchEvent.getY());
                        bangBallList.get(ballN).setInitialAttributes(frameGap);
                        bangBallList.get(ballN).setInitial(false);
                        ballN=-1;
                        Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
                        boolean ballsAllTouched = true;
                        for (int i =0;i<bangBallList.size();i++){
                            if (bangBallList.get(i).isInitial())
                                ballsAllTouched = false;
                        }
                        if (ballsAllTouched)
                            Game.GlobalVar.touchAble = false;
                    }
                }
            }
        }else {
            Game.GlobalVar.runningBall = false;
            for (int i = 0; i < bangBallList.size(); i++) {
                bangBallList.get(i).setCoordinate(frameGap);
                bangBallList.get(i).handleCollision(bangBallList,Game.GlobalVar.obstacleList,frameGap);
                if (bangBallList.get(i).getSpeedX() != 0){
                    Game.GlobalVar.runningBall = true;
                }
            }
            if (!Game.GlobalVar.touchAble&&!Game.GlobalVar.runningBall){
                ballsInTargetArea = 0;
                for (int i=0;i<bangBallList.size();i++){
                    if (bangBallList.get(i).isBallInTargetArea(targetArea)){
                        ballsInTargetArea++;
                        if (bangBallList.get(i).getPlayer() == 1){
                            p1Count+=1;
                        }else {
                            p2Count+=1;
                        }
                    }
                }
            }
        }
        if (ballsInTargetArea!=-1){
            Game.GlobalVar.uiThreadView.onPause();
            Bundle bundle = new Bundle();
            int player=-1;
            if (p1Count>p2Count)
                player = 1;
            else if (p1Count<p2Count)
                player = 2;
            bundle.putInt("player",player);
            twoPlayersResultDialog = new TwoPlayersResultDialog();
            twoPlayersResultDialog.setArguments(bundle);
            twoPlayersResultDialog.show(Game.GlobalVar.fragmentManager, "TwoPlayersResultDialog");
        }
    }

    @Override
    public void present(float frameGap) {
        Canvas canvas = new Canvas(Game.GlobalVar.drawingBitmap);
        graphic.drawBackground(canvas, Game.GlobalVar.asserts.background_1);
        graphic.drawBitmap(canvas, targetArea.getTargetB(), targetArea.getRect());
        graphic.drawBoundary(canvas, Game.GlobalVar.boundary);
        graphic.drawBitmap(canvas, Game.GlobalVar.asserts.pause, pauseR);
        for (int i = 0; i < Game.GlobalVar.obstacleList.size();i++){
            Obstacle obstacle = Game.GlobalVar.obstacleList.get(i);
            graphic.drawObstacle(canvas, obstacle);
        }
        for (int i = 0; i < bangBallList.size(); i++) {
            BangBall bangBall = bangBallList.get(i);
            graphic.drawBall(canvas,bangBall);
        }
    }
}
