package com.earthblood.tictactoe.helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.earthblood.tictactoe.application.ToeApp;
import com.earthblood.tictactoe.util.Skill;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class PreferenceHelperImpl implements PreferenceHelper {

    public static final String SHARED_PREF_GAME_SETTINGS = "SHARED_PREF_GAME_SETTINGS";

    @Inject
    private Application application;


    public int getPreference(String key, int defaultValue){
        SharedPreferences prefs = application.getApplicationContext().getSharedPreferences(SHARED_PREF_GAME_SETTINGS, Context.MODE_PRIVATE);
        return prefs.getInt(key, defaultValue);
    }

    public void putPreference(int value, String preferenceName, int mode) {
        SharedPreferences prefs = application.getApplicationContext().getSharedPreferences(SHARED_PREF_GAME_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(preferenceName,value);
        editor.commit();
    }
}
