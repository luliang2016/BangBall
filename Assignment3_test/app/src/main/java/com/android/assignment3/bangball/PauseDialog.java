package com.android.assignment3.bangball;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.assignment3.R;
import com.android.assignment3.Screens.LevelSelector;
import com.android.assignment3.Screens.MenuScreen;
import com.android.assignment3.Screens.OnePlayerScreen;
import com.android.assignment3.Screens.TwoPlayersScreen;

import java.lang.reflect.Constructor;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/6/6.
 */
public class PauseDialog extends DialogFragment {
    int level;

    @Override
    public void onResume() {
        super.onResume();
        PauseDialog.this.getDialog().getWindow().setLayout(
                (int)(Game.GlobalVar.drawingBitmap.getWidth()*0.5),
                Game.GlobalVar.asserts.pauseGame.getHeight());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        PauseDialog.this.getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        int backgroundW = Game.GlobalVar.asserts.pauseGame.getWidth();
        int backgroundH = Game.GlobalVar.asserts.pauseGame.getHeight();
        Game.GlobalVar.inDialog = true;
        Bundle bundle = getArguments();
        level = bundle.getInt("level");
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),R.style.Theme_Light_WallpaperSettings);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.pause, null);
        ImageButton resume = (ImageButton)view.findViewById(R.id.resumeB);
        ImageButton playAgain = (ImageButton)view.findViewById(R.id.playAgainB);
        ImageButton exit = (ImageButton)view.findViewById(R.id.exitB);
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PauseDialog.this.getDialog().cancel();
                Game.GlobalVar.touchAble = true;
                Game.GlobalVar.inDialog = false;
                Game.GlobalVar.uiThreadView.onResume();
            }
        });
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level!=0){
                    String className = "com.android.assignment3.Screens.SLevel"+level;
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
                }else{
                    Game.GlobalVar.screen = new TwoPlayersScreen();
                }
                Game.GlobalVar.inDialog = false;
                PauseDialog.this.getDialog().cancel();
                Game.GlobalVar.uiThreadView.onResume();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (level != 0)
                    Game.GlobalVar.screen = new LevelSelector();
                else
                    Game.GlobalVar.screen = new MenuScreen();
                Game.GlobalVar.inDialog = false;
                PauseDialog.this.getDialog().cancel();
                Game.GlobalVar.uiThreadView.onResume();
            }
        });
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        this.setCancelable(false);
        Dialog dialog = builder.create();
        return  dialog;
    }
}
