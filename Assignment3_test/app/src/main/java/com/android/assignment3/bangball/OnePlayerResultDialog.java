package com.android.assignment3.bangball;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.android.assignment3.R;
import com.android.assignment3.Screens.LevelSelector;
import com.android.assignment3.Screens.MenuScreen;
import com.android.assignment3.Screens.OnePlayerScreen;
import com.android.assignment3.Screens.TwoPlayersScreen;

import java.lang.reflect.Constructor;

/**
 * Created by Administrator on 2016/6/6.
 */
public class OnePlayerResultDialog extends DialogFragment {
    int ballsInTargetArea;
    int level;
    int bangBallListSize;

    @Override
    public void onResume() {
        super.onResume();
        OnePlayerResultDialog.this.getDialog().getWindow().setLayout(
                (int)(Game.GlobalVar.drawingBitmap.getWidth()*0.5),
                Game.GlobalVar.asserts.pauseGame.getHeight());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        ballsInTargetArea = bundle.getInt("ballsInTargetArea");
        level = bundle.getInt("level");
        bangBallListSize = bundle.getInt("bangBallListSize");
//        Game.GlobalVar.ioHandler.writeFile(
//                Game.GlobalVar.resources.getString(R.string.level_file),
//                String.valueOf(level),
//                starNum(ballsInTargetArea, bangBallListSize));
        SharedPreferences.Editor editor = Game.GlobalVar.levelSPF.edit();
        int statN = starNum(ballsInTargetArea, bangBallListSize);
        editor.putInt("SLevel"+level, Game.GlobalVar.levelSPF.getInt("SLevel"+level,-1)>statN?Game.GlobalVar.levelSPF.getInt("SLevel"+level,-1):statN);
        editor.commit();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.oneplayer_result, null);
        RelativeLayout layout = (RelativeLayout)view.findViewById(R.id.one_player);
        ImageButton nextLevel = (ImageButton)view.findViewById(R.id.nextLevelB);
        ImageButton playAgain = (ImageButton)view.findViewById(R.id.playAgainB);
        ImageButton exit = (ImageButton)view.findViewById(R.id.exitB);
        switch (statN){
            case 0:
                layout.setBackgroundResource(R.drawable.zerostar);
                nextLevel.setBackgroundResource(R.drawable.next_level_zero);
                nextLevel.setEnabled(false);
                break;
            case 1:layout.setBackgroundResource(R.drawable.onestar);break;
            case 2:layout.setBackgroundResource(R.drawable.twostar);break;
            case 3:layout.setBackgroundResource(R.drawable.threestar);break;
        }
        builder.setView(view);
        nextLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnePlayerResultDialog.this.getDialog().cancel();
                int nextLevel = level + 1;
                String className = "com.android.assignment3.Screens.SLevel" + nextLevel;
                try {
                    Class screenClass = Class.forName(className);
                    Constructor<OnePlayerScreen> constructor = screenClass.getConstructor(null);
                    OnePlayerScreen screen = constructor.newInstance(null);
                    OnePlayerResultDialog.this.getDialog().cancel();
                    Game.GlobalVar.screen = screen;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Game.GlobalVar.touchAble = true;
                Game.GlobalVar.inDialog = false;
                OnePlayerResultDialog.this.getDialog().cancel();
                Game.GlobalVar.uiThreadView.onResume();
            }
        });
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                Game.GlobalVar.inDialog = false;
                OnePlayerResultDialog.this.getDialog().cancel();
                Game.GlobalVar.uiThreadView.onResume();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game.GlobalVar.screen = new LevelSelector();
                Game.GlobalVar.inDialog = false;
                OnePlayerResultDialog.this.getDialog().cancel();
                Game.GlobalVar.uiThreadView.onResume();
            }
        });



//
//                .setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                OnePlayerResultDialog.this.getDialog().cancel();
//                int nextLevel = level + 1;
//                String className = "com.android.assignment3.Screens.SLevel" + nextLevel;
//                try {
//                    Class screenClass = Class.forName(className);
//                    Constructor<OnePlayerScreen> constructor = screenClass.getConstructor(null);
//                    OnePlayerScreen screen = constructor.newInstance(null);
//                    OnePlayerResultDialog.this.getDialog().cancel();
//                    Game.GlobalVar.screen = screen;
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                } catch (NoSuchMethodException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Game.GlobalVar.uiThreadView.onResume();
//            }
//        })
//                .setNeutralButton(R.string.exit,new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        OnePlayerResultDialog.this.getDialog().cancel();
//                        Game.GlobalVar.screen = new LevelSelector();
//                        Game.GlobalVar.uiThreadView.onResume();
//                    }
//                })
//                .setNegativeButton(R.string.replay, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        String className = "com.android.assignment3.Screens.SLevel" + level;
//                        try {
//                            Class screenClass = Class.forName(className);
//                            Constructor<OnePlayerScreen> constructor = screenClass.getConstructor(null);
//                            OnePlayerScreen screen = constructor.newInstance(null);
//                            OnePlayerResultDialog.this.getDialog().cancel();
//                            Game.GlobalVar.screen = screen;
//                        } catch (ClassNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (NoSuchMethodException e) {
//                            e.printStackTrace();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        Game.GlobalVar.uiThreadView.onResume();
//                    }
//                });
        this.setCancelable(false);
        return  builder.create();
    }

    protected int starNum(int ballsInTargetArea, int bangBallListSize){
        int statNum = 0;
        if (bangBallListSize==5){
            switch (ballsInTargetArea){
                case 0:statNum=0;break;
                case 1:statNum=1;break;
                case 2:statNum=1;break;
                case 3:statNum=1;break;
                case 4:statNum=2;break;
                case 5:statNum=3;break;
            }
        }else if (bangBallListSize==3){
            switch (ballsInTargetArea){
                case 0:statNum=0;break;
                case 1:statNum=1;break;
                case 2:statNum=2;break;
                case 3:statNum=3;break;
            }
        }
        return statNum;
    }
}
