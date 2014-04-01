package com.earthblood.tictactoe.application;

import android.app.Application;
import android.content.Context;

import com.earthblood.tictactoe.guice.ToeRoboModule;

import roboguice.RoboGuice;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */

public class Toe extends Application {

    public static final String TAG = "EARTHBLOOD_TOE";

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE, RoboGuice.newDefaultRoboModule(this), new ToeRoboModule());

        context = this;

    }

    public static String getResourceString(int resourceId){
        return context.getString(resourceId);
    }

}
