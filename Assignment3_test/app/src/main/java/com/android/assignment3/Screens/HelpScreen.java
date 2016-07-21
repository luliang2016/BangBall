package com.android.assignment3.Screens;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.inputmethod.BaseInputConnection;

import com.android.assignment3.DAO.TouchEvent;
import com.android.assignment3.bangball.Game;
import com.android.assignment3.bangball.Screen;
import com.android.assignment3.tool.Graphic;

/**
 * Created by Administrator on 2016/6/15.
 */
public class HelpScreen extends Screen {
    int currentHelp;
    Bitmap currentB;
    Graphic graphic;
    Canvas canvas;
    Rect backB;
    Rect nextB;
    Rect exitB;

    public HelpScreen(){
        currentHelp = 1;
        Game.GlobalVar.boundary = null;
        Game.GlobalVar.obstacleList = null;
        Game.GlobalVar.touchAble=true;
        graphic = new Graphic();
        currentB = Game.GlobalVar.asserts.help_1p_1;


        float screenW = Game.GlobalVar.drawingBitmap.getWidth();
        float screenH = Game.GlobalVar.drawingBitmap.getHeight();

        backB = new Rect(
                (int)(10),
                (int)(screenH- Game.GlobalVar.asserts.help_backB.getHeight()-10),
                (int)(10+ Game.GlobalVar.asserts.help_backB.getWidth()),
                (int)(screenH-10));
        exitB = new Rect(
                (int)(10),
                (10),
                (int)(10+ Game.GlobalVar.asserts.help_exitB.getWidth()),
                (int)(10+ Game.GlobalVar.asserts.help_exitB.getHeight()));
        nextB = new Rect(
                (int)((screenW- Game.GlobalVar.asserts.help_nextB.getWidth()-10)),
                (int)((screenH- Game.GlobalVar.asserts.help_nextB.getHeight()-10)),
                (int)((screenW-10)),
                (int)(screenH-10));
    }

    @Override
    public void update(float frameGap) {
        switch (currentHelp){
            case 1:currentB = Game.GlobalVar.asserts.help_1p_1;break;
            case 2:currentB = Game.GlobalVar.asserts.help_1p_2;break;
            case 3:currentB = Game.GlobalVar.asserts.help_1p_3;break;
            case 4:currentB = Game.GlobalVar.asserts.help_1p_4;break;
            case 5:currentB = Game.GlobalVar.asserts.help_1p_5;break;
            case 6:currentB = Game.GlobalVar.asserts.help_2p_1;break;
            case 7:currentB = Game.GlobalVar.asserts.help_2p_2;break;
            case 8:currentB = Game.GlobalVar.asserts.help_2p_3;break;
        }

        if (Game.GlobalVar.touchEventsList.size()>0){
            TouchEvent touchEvent = Game.GlobalVar.touchEventsList.remove();
            if (touchEvent.isInRect(backB)&&touchEvent.getTouchType()==TouchEvent.ACTION_UP&&currentHelp!=1){
//                Game.GlobalVar.touchAble = false;
                currentHelp--;
                Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
            }
            if (touchEvent.isInRect(exitB)&&touchEvent.getTouchType()==TouchEvent.ACTION_UP){
                Game.GlobalVar.touchAble = false;
                Game.GlobalVar.screen = new MenuScreen();
                Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
            }
            if (touchEvent.isInRect(nextB)&&touchEvent.getTouchType()==TouchEvent.ACTION_UP&&currentHelp!=8){
//                Game.GlobalVar.touchAble = false;
                currentHelp++;
                Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
            }
        }
    }

    @Override
    public void present(float frameGap) {
        canvas = new Canvas(Game.GlobalVar.drawingBitmap);
        graphic.drawBackground(canvas, currentB);
        if (currentHelp!=1){
            graphic.drawBitmap(canvas, Game.GlobalVar.asserts.help_backB, backB);
        }
        if (currentHelp!=8){
            graphic.drawBitmap(canvas, Game.GlobalVar.asserts.help_nextB, nextB);
        }
        graphic.drawBitmap(canvas,Game.GlobalVar.asserts.help_exitB,exitB);
    }
}
