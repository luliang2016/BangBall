package com.android.assignment3.Screens;

import com.android.assignment3.DAO.Boundary;
import com.android.assignment3.DAO.Obstacle;
import com.android.assignment3.DAO.TargetArea;
import com.android.assignment3.bangball.Game;

/**
 * Created by Administrator on 2016/6/7.
 */
public class SLevel1 extends OnePlayerScreen {
    public SLevel1(){
        super();
        level = 1;
        bangBallListSize = 3;
        modelType = 2;
        targetArea = new TargetArea(1,600,700,1/2f);
        Game.GlobalVar.boundary = new Boundary(targetArea.getCerX(),targetArea.getCerY(),targetArea.getRadius()+50);
        Game.GlobalVar.obstacleList.add(new Obstacle(1,10,200));
        Game.GlobalVar.obstacleList.add(new Obstacle(2,500,800));
        color = 2;
//        BangBall bangBall1 = new BangBall(color);
//        bangBall1.setCoordinate(100, 700);
//        BangBall bangBall2 = new BangBall(color);
//        bangBall2.setCoordinate(300, 700);
//        BangBall bangBall3 = new BangBall(color);
//        bangBall3.setCoordinate(200, 800);
//        bangBallList.add(bangBall1);
//        bangBallList.add(bangBall2);
//        bangBallList.add(bangBall3);
    }
}
