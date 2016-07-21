package com.android.assignment3.bangball;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.assignment3.R;

/**
 * Created by Administrator on 2016/4/20.
 */
public class Asserts {
    public Bitmap background_1;
    public Bitmap background_2;
    public Bitmap background_3;

    public Bitmap ball_red;
    public Bitmap ball_blue;
    public Bitmap ball_green;
    public Bitmap ball_orange;
    public Bitmap ball_purple;

    public Bitmap block_1;
    public Bitmap block_2;
    public Bitmap block_3;
    public Bitmap block_4;

    public Bitmap target;

    public Bitmap menu;
    public Bitmap onePB;
    public Bitmap twoPB;
    public Bitmap helpB;

    public Bitmap noStar;
    public Bitmap oneStars;
    public Bitmap twoStars;
    public Bitmap threeStars;

    public Bitmap levelNZero;
    public Bitmap levelNOne;
    public Bitmap levelNTwo;
    public Bitmap levelNThree;
    public Bitmap levelNFour;
    public Bitmap levelNFive;
    public Bitmap levelNSix;
    public Bitmap levelNSeven;
    public Bitmap levelNEight;
    public Bitmap levelNNine;

    public Bitmap pause;
    public Bitmap playAgainB;
    public Bitmap resumeB;
    public Bitmap exitB;
    public Bitmap nextLevelB;
    public Bitmap nextLevelZeroB;
    public Bitmap player1Win;
    public Bitmap player2Win;
    public Bitmap drawGame;
    public Bitmap pauseGame;
    public Bitmap zeroStar;
    public Bitmap oneStar;
    public Bitmap twoStar;
    public Bitmap threeStar;

    public Bitmap help_1p_1;
    public Bitmap help_1p_2;
    public Bitmap help_1p_3;
    public Bitmap help_1p_4;
    public Bitmap help_1p_5;
    public Bitmap help_2p_1;
    public Bitmap help_2p_2;
    public Bitmap help_2p_3;

    public Bitmap help_backB;
    public Bitmap help_exitB;
    public Bitmap help_nextB;

    public Asserts(Resources resources){
        help_backB = BitmapFactory.decodeResource(resources, R.drawable.back);
        help_exitB = BitmapFactory.decodeResource(resources, R.drawable.help_exit);
        help_nextB = BitmapFactory.decodeResource(resources, R.drawable.next);

        help_1p_1 = BitmapFactory.decodeResource(resources, R.drawable.help_1p_1);
        help_1p_2 = BitmapFactory.decodeResource(resources, R.drawable.help_1p_2);
        help_1p_3 = BitmapFactory.decodeResource(resources, R.drawable.help_1p_3);
        help_1p_4 = BitmapFactory.decodeResource(resources, R.drawable.help_1p_4);
        help_2p_1 = BitmapFactory.decodeResource(resources, R.drawable.help_2p_1);
        help_2p_2 = BitmapFactory.decodeResource(resources, R.drawable.help_2p_2);
        help_2p_3 = BitmapFactory.decodeResource(resources, R.drawable.help_2p_3);
        help_1p_5 = BitmapFactory.decodeResource(resources, R.drawable.help_1p_5);

        zeroStar = BitmapFactory.decodeResource(resources, R.drawable.zerostar);
        oneStar = BitmapFactory.decodeResource(resources, R.drawable.onestar);
        twoStar = BitmapFactory.decodeResource(resources, R.drawable.twostar);
        threeStar = BitmapFactory.decodeResource(resources, R.drawable.threestar);
        pause = BitmapFactory.decodeResource(resources, R.drawable.stopgame);
        playAgainB = BitmapFactory.decodeResource(resources, R.drawable.play_again);
        resumeB = BitmapFactory.decodeResource(resources, R.drawable.resume);
        exitB = BitmapFactory.decodeResource(resources, R.drawable.exit);
        nextLevelB = BitmapFactory.decodeResource(resources, R.drawable.next_level);
        nextLevelZeroB = BitmapFactory.decodeResource(resources, R.drawable.next_level_zero);
        player1Win = BitmapFactory.decodeResource(resources, R.drawable.player1win);
        player2Win = BitmapFactory.decodeResource(resources, R.drawable.player2win);
        drawGame = BitmapFactory.decodeResource(resources, R.drawable.drawgame);
        pauseGame = BitmapFactory.decodeResource(resources, R.drawable.pausegamebackground);


        levelNZero = BitmapFactory.decodeResource(resources, R.drawable.level_zero);
        levelNOne = BitmapFactory.decodeResource(resources, R.drawable.level_one);
        levelNTwo = BitmapFactory.decodeResource(resources, R.drawable.level_two);
        levelNThree = BitmapFactory.decodeResource(resources, R.drawable.level_three);
        levelNFour = BitmapFactory.decodeResource(resources, R.drawable.level_four);
        levelNFive = BitmapFactory.decodeResource(resources, R.drawable.level_five);
        levelNSix = BitmapFactory.decodeResource(resources, R.drawable.level_six);
        levelNSeven = BitmapFactory.decodeResource(resources, R.drawable.level_seven);
        levelNEight = BitmapFactory.decodeResource(resources, R.drawable.level_eight);
        levelNNine = BitmapFactory.decodeResource(resources, R.drawable.level_nine);


        background_1 = BitmapFactory.decodeResource(resources, R.drawable.background_1);
        background_2 = BitmapFactory.decodeResource(resources, R.drawable.background_2);
        background_3 = BitmapFactory.decodeResource(resources, R.drawable.background_3);

        ball_red = BitmapFactory.decodeResource(resources, R.drawable.ball_red);
        ball_blue = BitmapFactory.decodeResource(resources, R.drawable.ball_blue);
        ball_green = BitmapFactory.decodeResource(resources, R.drawable.ball_green);
        ball_orange = BitmapFactory.decodeResource(resources, R.drawable.ball_orange);
        ball_purple = BitmapFactory.decodeResource(resources, R.drawable.ball_purple);

        block_1 = BitmapFactory.decodeResource(resources, R.drawable.block_50x50);
        block_2 = BitmapFactory.decodeResource(resources, R.drawable.block_90x50);
        block_3 = BitmapFactory.decodeResource(resources, R.drawable.block_130x30);
        block_4 = BitmapFactory.decodeResource(resources, R.drawable.block_170x20);

        target = BitmapFactory.decodeResource(resources, R.drawable.target_250x250);

        menu = BitmapFactory.decodeResource(resources, R.drawable.menu);
        onePB = BitmapFactory.decodeResource(resources, R.drawable.menu_1player);
        twoPB = BitmapFactory.decodeResource(resources, R.drawable.menu_2player);
        helpB = BitmapFactory.decodeResource(resources, R.drawable.menu_help);

        noStar = BitmapFactory.decodeResource(resources, R.drawable.level_nostar);
        oneStars = BitmapFactory.decodeResource(resources, R.drawable.level_onestar);
        twoStars = BitmapFactory.decodeResource(resources, R.drawable.level_twostar);
        threeStars = BitmapFactory.decodeResource(resources, R.drawable.level_threestar);


    }

}
