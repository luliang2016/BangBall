package com.android.assignment3.Screens;

import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.android.assignment3.DAO.Level;
import com.android.assignment3.DAO.TouchEvent;
import com.android.assignment3.R;
import com.android.assignment3.bangball.Game;
import com.android.assignment3.bangball.Screen;
import com.android.assignment3.tool.Graphic;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/7.
 */
public class LevelSelector extends Screen {
    int levelW = Game.GlobalVar.asserts.noStar.getWidth();
    int levelH = Game.GlobalVar.asserts.noStar.getHeight();
    JSONObject jsonObject = null;
    Graphic graphic;
    List<Level> levels;

    public LevelSelector(){
        Game.GlobalVar.touchAble = true;
        graphic = new Graphic();
//        jsonObject = Game.GlobalVar.ioHandler.readJSON(Game.GlobalVar.resources.getString(R.string.level_file));
//        graphic = new Graphic();
//        levels = new ArrayList<>(jsonObject.length());
//        Iterator<String> levelI = jsonObject.keys();
//        try {
//            while (levelI.hasNext()){
//                String key = levelI.next();
//                int index = Integer.parseInt(key);
//                levels.add(new Level(index,jsonObject.getInt(key),levelR(index-1)));
//            }
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
        int levelN = Game.GlobalVar.resources.getInteger(R.integer.initial_level_num);
        levels = new ArrayList<>(levelN);
        for (int i=1;i<=levelN; i++) {
            levels.add(new Level(i, Game.GlobalVar.levelSPF.getInt("SLevel"+i,-1),levelR(i-1)));
        }
    }

    private Rect levelR(int index){
        Rect rect = new Rect();
        int row;
        if (index%3==1){
            row = (index-1)/3 + 1;
        }else {
            row = index/3 + 1;
        }
        int column = index % 3 + 1;
        rect.left = 70 * column + levelW * (column-1);
        rect.top = 50 * row + levelH * (row-1);
        rect.right = rect.left + levelW;
        rect.bottom = rect.top + levelH;

        return rect;
    }

    @Override
    public void update(float frameGap) {
        if (Game.GlobalVar.touchEventsList.size()>0){
            TouchEvent touchEvent = Game.GlobalVar.touchEventsList.remove();
            for (int i=0;i<levels.size();i++){
                if (touchEvent.isInRect(levels.get(i).getRect())&&touchEvent.getTouchType()==TouchEvent.ACTION_UP){
                    Game.GlobalVar.touchAble = false;
                    String className = "com.android.assignment3.Screens.SLevel"+levels.get(i).getLevel();
                    try {
                        Class screenClass = Class.forName(className);
                        Constructor<OnePlayerScreen> constructor = screenClass.getConstructor(null);
                        OnePlayerScreen screen = constructor.newInstance(null);
                        Game.GlobalVar.screen = screen;
                    }catch (ClassNotFoundException e){
                        e.printStackTrace();
                    }catch (NoSuchMethodException e){
                        e.printStackTrace();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Game.GlobalVar.touchEventPool.freeInstance(touchEvent);
                }
            }
        }
    }

    @Override
    public void present(float frameGap) {
        Canvas canvas = new Canvas(Game.GlobalVar.drawingBitmap);
        graphic.drawBackground(canvas, Game.GlobalVar.asserts.background_1);
        for (int i=0;i<levels.size();i++){
            graphic.drawBitmap(canvas,levels.get(i).getLevelB(),levels.get(i).getRect());
            graphic.drawBitmap(canvas,levels.get(i).getLevelTensB(),levels.get(i).getRectT());
            graphic.drawBitmap(canvas,levels.get(i).getLevelUnitsB(),levels.get(i).getRectU());
        }
    }
}
