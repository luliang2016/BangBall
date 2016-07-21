package com.android.assignment3.bangball;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.assignment3.DAO.BangBall;
import com.android.assignment3.DAO.Boundary;
import com.android.assignment3.DAO.Obstacle;
import com.android.assignment3.DAO.TouchEvent;
import com.android.assignment3.R;
import com.android.assignment3.tool.IOHandler;
import com.android.assignment3.tool.Pool;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Administrator on 2016/4/20.
 */
public abstract class Game extends FragmentActivity {

    /**
     *  GlobalVar class is used through the whole lift circle
     *
     *  drawingBitmap : Every drawing outcome will be shown on this Bitmap, and
     *                  it will be eventually drawn by UIThreadView
     *  screen :        Referring to current Screen, which is a sub-class of Screen,
     *                  handling all logical stuff relative to the same screen
     *  touchEventPool : A factory contain a certain of specific class instances,
     *                   using to reduce consumption of system memory
     *  touchEventList : Storing all touch events, which haven't been handled
     *                   It is a queue with first-in first_out principle
     *  resources : Using to fetch resources from XML files
     */
    public static class GlobalVar{
        public static Bitmap drawingBitmap;
        public static Screen screen;
        public static Pool<TouchEvent> touchEventPool;
        public static boolean runningBall;
        public static Queue<TouchEvent> touchEventsList;
        public static Resources resources;
        public static Asserts asserts;
        public static boolean touchAble;
        public static boolean interrupt;
        public static Boundary boundary;
        public static List<Obstacle> obstacleList;

        public static boolean eventPermit;
        public static float scaleX,scaleY;
        public static Rect screenR;
        public static FragmentManager fragmentManager;
        public static UIThreadView uiThreadView;
        public static IOHandler ioHandler;
        public static Game game;

        public static SharedPreferences levelSPF;

        public static boolean inDialog;
    }

    OnTouchHandler touchHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalVar.touchAble = true;
        GlobalVar.interrupt = false;
        GlobalVar.resources = getResources();
        GlobalVar.boundary = null;
        GlobalVar.obstacleList = null;
        GlobalVar.eventPermit = true;
        GlobalVar.asserts = new Asserts(getResources());
        GlobalVar.fragmentManager = getSupportFragmentManager();
        GlobalVar.ioHandler = new IOHandler(this);
        GlobalVar.game = this;
        GlobalVar.levelSPF = getSharedPreferences(GlobalVar.resources.getString(R.string.level_file), 0);
        GlobalVar.inDialog = false;
        // Default width and height of drawingBitmap, can be change according to different situations
//        final int WIDTH = GlobalVar.resources.getInteger(R.integer.drawing_bitmap_width),HEIGHT = GlobalVar.resources.getInteger(R.integer.drawing_bitmap_height);
        int screenW = getWindowManager().getDefaultDisplay().getWidth();
        int screenH = getWindowManager().getDefaultDisplay().getHeight();
        float ratio = (float)screenH/(float)screenW;
        GlobalVar.screenR = new Rect(0,0,getWindowManager().getDefaultDisplay().getWidth(),getWindowManager().getDefaultDisplay().getHeight());
        int WIDTH = GlobalVar.resources.getInteger(R.integer.drawing_bitmap_width_default),
                HEIGHT = GlobalVar.resources.getInteger(R.integer.drawing_bitmap_height_default);
        if (ratio>(16f/10f)&&ratio<(16f/8f)){
            WIDTH = GlobalVar.resources.getInteger(R.integer.drawing_bitmap_width_16_9);
            HEIGHT = GlobalVar.resources.getInteger(R.integer.drawing_bitmap_height_16_9);
        }else if (ratio>(16f/11f)&&ratio<(16f/9f)){
            WIDTH = GlobalVar.resources.getInteger(R.integer.drawing_bitmap_width_16_10);
            HEIGHT = GlobalVar.resources.getInteger(R.integer.drawing_bitmap_height_16_10);
        }
        GlobalVar.scaleX = (float)WIDTH/1080f;
        GlobalVar.scaleY = (float)HEIGHT/1920f;
        final int SIZE_OF_TOUCH_EVENT = GlobalVar.resources.getInteger(R.integer.touch_event_list_size);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        GlobalVar.drawingBitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
        GlobalVar.touchEventsList = new LinkedList<>();
        GlobalVar.touchEventPool = new Pool(new Pool.PoolObject<TouchEvent>(){
            @Override
            public TouchEvent createObjectInstance() {
                return new TouchEvent();
            }
        },SIZE_OF_TOUCH_EVENT);
        GlobalVar.runningBall = false;
        GlobalVar.uiThreadView = new UIThreadView(this);
        float scaleX = WIDTH / (float)getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = HEIGHT / (float)getWindowManager().getDefaultDisplay().getHeight();
        // If the device only support single touch, then uses SingleTouchHandler, otherwise uses MultiTouchHandler
        touchHandler = new SingleTouchHandler(scaleX,scaleY);
        GlobalVar.uiThreadView.setOnTouchListener(touchHandler);
        GlobalVar.ioHandler.initializeLevel(GlobalVar.resources.getString(R.string.level_file));

//        SharedPreferences levelSPF = getSharedPreferences(GlobalVar.resources.getString(R.string.level_file), 0);
//        int level1 = levelSPF.getInt("SLevel1", -1);
//        Log.d("Allan", "first:   " + level1 + "");
//        SharedPreferences.Editor editor = levelSPF.edit();
//        editor.putInt("SLevel1", 2);
//        editor.commit();
//        Log.d("Allan", "second:   " + levelSPF.getInt("SLevel1", -1) + "");

        setInitialScreen();
        setContentView(GlobalVar.uiThreadView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!GlobalVar.inDialog){
            GlobalVar.uiThreadView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        GlobalVar.uiThreadView.onPause();
    }

    private void initialLevel(){
        int levelN = GlobalVar.resources.getInteger(R.integer.initial_level_num);
    }

    public abstract void setInitialScreen();
}
