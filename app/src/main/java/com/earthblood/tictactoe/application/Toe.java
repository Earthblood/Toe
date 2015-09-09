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
package com.earthblood.tictactoe.application;

import android.app.Application;
import android.content.Context;

import com.earthblood.tictactoe.guice.ToeRoboModule;

import roboguice.RoboGuice;

public class Toe extends Application {

    public static final String TAG = "EARTHBLOOD_TOE";
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        RoboGuice.overrideApplicationInjector(this, new ToeRoboModule());

        context = this;
    }

    public static String getResourceString(int resourceId){
        return context.getString(resourceId);
    }
}
