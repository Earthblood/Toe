package com.earthblood.tictactoe.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.widget.Button;

import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.Robolectric.RobolectricGradleTestRunner;
import com.earthblood.tictactoe.engine.ToeGame;
import com.earthblood.tictactoe.strategy.ExplicitToeStrategy;
import com.earthblood.tictactoe.strategy.ToeStrategy;
import com.earthblood.tictactoe.util.GameSymbol;
import com.google.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricGradleTestRunner.class)
public class GameActivityTest {

    GameActivity activity;

    @Inject Context context;
    @Inject ToeGame toeGame;


    @Before
    public void setup() {
        when(toeGame.getTurn()).thenReturn(GameSymbol.X);
        activity = Robolectric.buildActivity(GameActivity.class).create().start().resume().get();
    }

    @Test
    public void shouldChooseBox(){
        when(toeGame.getTurn()).thenReturn(GameSymbol.O);
        Button button = Mockito.mock(Button.class);
        when(button.getId()).thenReturn(R.id.box_8);

        activity.chooseBox(button);

        ToeStrategy expectedStrategy = new ExplicitToeStrategy(8, GameSymbol.O);
        verify(toeGame).chooseBox(any(ContentResolver.class), eq(expectedStrategy));
    }

}
