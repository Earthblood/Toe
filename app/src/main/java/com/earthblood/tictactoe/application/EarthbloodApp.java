package com.earthblood.tictactoe.application;

import android.app.Application;
import android.content.Context;

import com.earthblood.tictactoe.engine.ToeGame;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */

public class EarthbloodApp extends Application {

    private static Context context;
    private static ToeGame toeGame;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        toeGame = new ToeGame();

    }

    public static Context getContext(){
        return context;
    }
    public static ToeGame getToeGame(){
        return toeGame;
    }
}
