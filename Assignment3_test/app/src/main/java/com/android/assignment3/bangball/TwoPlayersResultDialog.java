package com.android.assignment3.bangball;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.assignment3.R;
import com.android.assignment3.Screens.LevelSelector;
import com.android.assignment3.Screens.MenuScreen;
import com.android.assignment3.Screens.OnePlayerScreen;
import com.android.assignment3.Screens.TwoPlayersScreen;

import java.lang.reflect.Constructor;

/**
 * Created by Administrator on 2016/6/6.
 */
public class TwoPlayersResultDialog extends DialogFragment {
    int player;

    @Override
    public void onResume() {
        super.onResume();
        TwoPlayersResultDialog.this.getDialog().getWindow().setLayout(
                (int)(Game.GlobalVar.drawingBitmap.getWidth()*0.5),
                Game.GlobalVar.asserts.pauseGame.getHeight());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        player = bundle.getInt("player");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        TextView textView = (TextView)getActivity().findViewById(R.id.TwoPlayers);
//        textView.setText("The winner is player " + player + " !!!!");

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.twoplayers_result, null);
        RelativeLayout layout = (RelativeLayout)view.findViewById(R.id.two_players);
        ImageButton playAgain = (ImageButton)view.findViewById(R.id.playAgainB);
        ImageButton exit = (ImageButton)view.findViewById(R.id.exitB);
        if (player==-1){
            layout.setBackgroundResource(R.drawable.drawgame);
        }else if (player==1){
            layout.setBackgroundResource(R.drawable.player1win);
        }else if (player==2){
            layout.setBackgroundResource(R.drawable.player2win);
        }
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game.GlobalVar.screen = new TwoPlayersScreen();
                Game.GlobalVar.inDialog = false;
                TwoPlayersResultDialog.this.getDialog().cancel();
                Game.GlobalVar.uiThreadView.onResume();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game.GlobalVar.screen = new MenuScreen();
                Game.GlobalVar.inDialog = false;
                TwoPlayersResultDialog.this.getDialog().cancel();
                Game.GlobalVar.uiThreadView.onResume();
            }
        });
        this.setCancelable(false);
        return  builder.create();
    }
}
