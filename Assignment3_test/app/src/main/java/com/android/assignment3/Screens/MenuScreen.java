package com.android.assignment3.Screens;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.android.assignment3.DAO.TouchEvent;
import com.android.assignment3.bangball.Game;
import com.android.assignment3.bangball.Screen;
import com.android.assignment3.tool.Graphic;

/**
 * Created by Administrator on 2016/6/3.
 */
public class MenuScreen extends Screen {
    Graphic graphic;
    Rect onePB;
    Rect twoPB;
    Rect helpB;

    public MenuScreen(){
        super();
        Game.GlobalVar.boundary = null;
        Game.GlobalVar.obstacleList = null;
        graphic = new Graphic();
        Game.GlobalVar.touchAble=true;

        float screenW = Game.GlobalVar.drawingBitmap.getWidth();
        float screenH = Game.GlobalVar.drawingBitmap.getHeight();
        int buttonTop = 600;
        int buttonGap = 300;

        onePB = new Rect(
                (int)((screenW/2- Game.GlobalVar.asserts.onePB.getWidth()/2)),
                (buttonTop),
                (int)((screenW/2+Game.GlobalVar.asserts.onePB.getWidth()/2)),
                ((buttonTop+ Game.GlobalVar.asserts.onePB.getHeight())));
        twoPB = new Rect(
                (int)((screenW/2- Game.GlobalVar.asserts.twoPB.getWidth()/2)),
                ((buttonTop+buttonGap)),
                (int)((screenW/2+Game.GlobalVar.asserts.twoPB.getWidth()/2)),
                ((buttonTop+buttonGap+ Game.GlobalVar.asserts.twoPB.getHeight())));
        helpB = new Rect(
                (int)((screenW- Game.GlobalVar.asserts.helpB.getWidth()-10)),
                (int)((screenH- Game.GlobalVar.asserts.helpB.getHeight()-10)),
                (int)((screenW-10)),
                (int)(screenH-10));
    }

    @Override
    public void update(float frameGap) {
        if (Game.GlobalVar.touchEventsList.size()>0){
            TouchEvent touchEvent = Game.GlobalVar.touchEventsList.remove();
            if (touchEvent.isInRect(onePB)&&touchEvent.getTouchType()==TouchEvent.ACTION_UP){
                Game.GlobalVar.touchAble = false;
                Game.GlobalVar.screen = new LevelSelector();
                Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
            }
            if (touchEvent.isInRect(twoPB)&&touchEvent.getTouchType()==TouchEvent.ACTION_UP){
                Game.GlobalVar.touchAble = false;
                Game.GlobalVar.screen = new TwoPlayersScreen();
                Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
            }
            if (touchEvent.isInRect(helpB)&&touchEvent.getTouchType()==TouchEvent.ACTION_UP){
                Game.GlobalVar.touchAble = false;
                Game.GlobalVar.screen = new HelpScreen();
                Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
            }
        }
    }

    @Override
    public void present(float frameGap) {
        Canvas canvas = new Canvas(Game.GlobalVar.drawingBitmap);
        graphic.drawBackground(canvas, Game.GlobalVar.asserts.menu);
        graphic.drawBitmap(canvas, Game.GlobalVar.asserts.onePB, onePB);
        graphic.drawBitmap(canvas, Game.GlobalVar.asserts.twoPB,twoPB);
        graphic.drawBitmap(canvas, Game.GlobalVar.asserts.helpB,helpB);
    }
}
