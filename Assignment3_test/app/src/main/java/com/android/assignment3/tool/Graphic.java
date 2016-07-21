package com.android.assignment3.tool;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.android.assignment3.DAO.BangBall;
import com.android.assignment3.DAO.Boundary;
import com.android.assignment3.DAO.Obstacle;
import com.android.assignment3.DAO.TargetArea;
import com.android.assignment3.bangball.Game;

/**
 *  Handling all drawing action and drawing things onto Game.GlobalVar.drawingBitmap
 *
 * Created by Administrator on 2016/4/20.
 */
public class Graphic {
    Rect screenR = new Rect(0,0, Game.GlobalVar.drawingBitmap.getWidth(), Game.GlobalVar.drawingBitmap.getHeight());

    public void drawBackground(Canvas canvas,Bitmap bitmap){
        canvas.drawBitmap(bitmap,null,screenR,null);
    }

    public void drawObstacle(Canvas canvas,Obstacle obstacle){
        canvas.drawBitmap(obstacle.getObstacleB(), null, obstacle.getRect(), null);
    }

    public void drawBall(Canvas canvas,BangBall bangBall){
        canvas.drawBitmap(bangBall.getBangBallB(), bangBall.getCerX()-bangBall.getRADIUS(),bangBall.getCerY()-bangBall.getRADIUS(), null);
    }

    public void drawTargetArea(Canvas canvas,TargetArea targetArea,float rate){
        drawBitmap(canvas,targetArea.getTargetB(),targetArea.getRect());
        canvas.drawBitmap(targetArea.getTargetB(), targetArea.getCerX()-targetArea.getRadius(),targetArea.getCerY()-targetArea.getRadius(), null);
    }

    public void drawBitmap(Canvas canvas,Bitmap bitmap,Rect dstR){
        dstR.left = (int)(dstR.left* Game.GlobalVar.scaleX);
        dstR.top = (int)(dstR.top* Game.GlobalVar.scaleY);
        dstR.right = (int)(dstR.right* Game.GlobalVar.scaleX);
        dstR.bottom = (int)(dstR.bottom* Game.GlobalVar.scaleY);
        canvas.drawBitmap(bitmap,null,dstR,null);
    }

    public void drawBoundary(Canvas canvas,Boundary boundary){
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);
        paint.setAlpha(150);
        canvas.drawCircle(boundary.getCerX(),boundary.getCerY(),boundary.getRadius(),paint);
    }

    public void drawColor(Canvas canvas,int color){
        canvas.drawColor(color);
    }
    public void drawCircle(Canvas canvas,float cerX,float cerY,float radius,Paint paint){
        canvas.drawCircle(cerX,cerY,radius,paint);
    }
    public void drawSquare(){}
    public void drawRect(Canvas canvas,Rect rect){
        canvas.drawRect(rect,new Paint());
    }
    public void drawPoint(){}
    public void drawLine(){}
    public void drawText(Canvas canvas,String text,float x,float y,Paint paint){
        canvas.drawText(text,x,y,paint);
    }
}
