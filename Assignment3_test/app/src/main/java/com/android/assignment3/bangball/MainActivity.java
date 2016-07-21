package com.android.assignment3.bangball;

import com.android.assignment3.Screens.MenuScreen;
import com.android.assignment3.Screens.OnePlayerScreen;
import com.android.assignment3.test.TestScreen;

public class MainActivity extends Game {

    /**
     * Used to set the initial screen
     */
    @Override
    public void setInitialScreen() {
        // e.g. GlobalVar.screen = new MenuScreen();
//        GlobalVar.screen = new OnePlayerScreen();
        GlobalVar.screen = new MenuScreen();
    }


}
