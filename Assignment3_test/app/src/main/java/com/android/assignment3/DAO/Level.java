package com.android.assignment3.DAO;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.android.assignment3.bangball.Game;

/**
 * Created by Administrator on 2016/6/7.
 */
public class Level {
    int level;
    int stars;
    Bitmap levelB;
    Bitmap levelTensB;
    Bitmap levelUnitsB;
    Rect rect;
    Rect rectT;
    Rect rectU;

    public Level(int level,int stars,Rect rect){
        this.level = level;
        this.stars = stars;
        switch (stars){
            case -1:levelB= Game.GlobalVar.asserts.noStar;break;
            case 0:levelB= Game.GlobalVar.asserts.noStar;break;
            case 1:levelB= Game.GlobalVar.asserts.oneStars;break;
            case 2:levelB= Game.GlobalVar.asserts.twoStars;break;
            case 3:levelB= Game.GlobalVar.asserts.threeStars;break;
        }
        this.rect = rect;
        int tens = level/10;
        int units = level%10;
        switch (tens){
            case 0:levelTensB= Game.GlobalVar.asserts.levelNZero;break;
            case 1:levelTensB= Game.GlobalVar.asserts.levelNOne;break;
            case 2:levelTensB= Game.GlobalVar.asserts.levelNTwo;break;
            case 3:levelTensB= Game.GlobalVar.asserts.levelNThree;break;
            case 4:levelTensB= Game.GlobalVar.asserts.levelNFour;break;
            case 5:levelTensB= Game.GlobalVar.asserts.levelNFive;break;
            case 6:levelTensB= Game.GlobalVar.asserts.levelNSix;break;
            case 7:levelTensB= Game.GlobalVar.asserts.levelNSeven;break;
            case 8:levelTensB= Game.GlobalVar.asserts.levelNEight;break;
            case 9:levelTensB= Game.GlobalVar.asserts.levelNNine;break;
        }
        switch (units){
            case 0:levelUnitsB= Game.GlobalVar.asserts.levelNZero;break;
            case 1:levelUnitsB= Game.GlobalVar.asserts.levelNOne;break;
            case 2:levelUnitsB= Game.GlobalVar.asserts.levelNTwo;break;
            case 3:levelUnitsB= Game.GlobalVar.asserts.levelNThree;break;
            case 4:levelUnitsB= Game.GlobalVar.asserts.levelNFour;break;
            case 5:levelUnitsB= Game.GlobalVar.asserts.levelNFive;break;
            case 6:levelUnitsB= Game.GlobalVar.asserts.levelNSix;break;
            case 7:levelUnitsB= Game.GlobalVar.asserts.levelNSeven;break;
            case 8:levelUnitsB= Game.GlobalVar.asserts.levelNEight;break;
            case 9:levelUnitsB= Game.GlobalVar.asserts.levelNNine;break;
        }
        int midW = (rect.left+rect.right)/2;
        int midH = (rect.top+rect.bottom)/2;
        int middleGap = 5;
        int midHPlus = 10;
        midH+=midHPlus;
        rectT = new Rect(
                midW-levelTensB.getWidth()-middleGap,
                midH-levelTensB.getHeight()/2,
                midW-middleGap,
                midH+levelTensB.getHeight()/2);
        rectU = new Rect(
                midW+middleGap,
                midH-levelUnitsB.getHeight()/2,
                midW+levelUnitsB.getWidth()+middleGap,
                midH+levelUnitsB.getHeight()/2);
    }

    public int getLevel() {
        return level;
    }

    public Bitmap getLevelB() {
        return levelB;
    }

    public Rect getRect() {
        return rect;
    }

    public Rect getRectU() {
        return rectU;
    }

    public Rect getRectT() {
        return rectT;
    }

    public Bitmap getLevelUnitsB() {
        return levelUnitsB;
    }

    public Bitmap getLevelTensB() {
        return levelTensB;
    }
}
