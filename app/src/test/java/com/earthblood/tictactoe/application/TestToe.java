package com.earthblood.tictactoe.application;

import com.earthblood.tictactoe.guice.TestToeRoboModule;
import com.earthblood.tictactoe.guice.ToeRoboModule;

import org.robolectric.Robolectric;
import org.robolectric.TestLifecycleApplication;

import java.lang.reflect.Method;

import roboguice.RoboGuice;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
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
