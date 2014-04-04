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

import com.earthblood.tictactoe.guice.TestToeRoboModule;
import com.earthblood.tictactoe.guice.ToeRoboModule;

import org.robolectric.Robolectric;
import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

import roboguice.RoboGuice;

@SuppressWarnings("UnusedDeclaration")
public class TestToe extends Toe implements TestLifecycleApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE, RoboGuice.newDefaultRoboModule(this), new ToeRoboModule());
    }

    @Override
    public void beforeTest(Method method) {
    }

    @Override
    public void prepareTest(Object test) {
        TestToe application = (TestToe) Robolectric.application;

        RoboGuice.setBaseApplicationInjector(application, RoboGuice.DEFAULT_STAGE, RoboGuice.newDefaultRoboModule(application), new TestToeRoboModule());

        RoboGuice.getInjector(application).injectMembers(test);
    }

    @Override
    public void afterTest(Method method) {
    }
}
