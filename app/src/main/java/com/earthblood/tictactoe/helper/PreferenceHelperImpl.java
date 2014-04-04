/**
 * @author John Piser developer@earthblood.com
 *
 * Copyright (C) 2014 EARTHBLOOD, LLC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.earthblood.tictactoe.helper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.inject.Inject;

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
