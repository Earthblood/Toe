package com.earthblood.tictactoe.guice;

import com.earthblood.tictactoe.engine.ToeGame;
import com.earthblood.tictactoe.helper.PreferenceHelper;
import com.google.inject.AbstractModule;

import org.mockito.Mockito;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class TestToeRoboModule extends AbstractModule{

    @Override
    protected void configure() {

        bind(ToeGame.class).toInstance(Mockito.mock(ToeGame.class));
        bind(PreferenceHelper.class).toInstance(Mockito.mock(PreferenceHelper.class));

    }
}
