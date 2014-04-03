package com.earthblood.tictactoe.activity;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;

import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.Robolectric.RobolectricGradleTestRunner;
import com.earthblood.tictactoe.engine.ToeGame;
import com.earthblood.tictactoe.helper.HapticFeedbackHelper;
import com.earthblood.tictactoe.strategy.ToeStrategyExplicit;
import com.earthblood.tictactoe.strategy.ToeStrategy;
import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.GameWinPattern;
import com.google.inject.Inject;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static org.fest.assertions.api.Assertions.assertThat;
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
    @Inject HapticFeedbackHelper hapticFeedbackHelper;

    ShadowActivity shadowActivity;

    @Before
    public void setup() {
        when(toeGame.getTurn()).thenReturn(GameSymbol.X);
        activity = Robolectric.buildActivity(GameActivity.class).create().start().resume().get();
        shadowActivity = Robolectric.shadowOf(activity);
    }

    @Test
    public void shouldChooseBox(){
        when(toeGame.getTurn()).thenReturn(GameSymbol.O);
        Button button = Mockito.mock(Button.class);
        when(button.getId()).thenReturn(R.id.box_8);

        activity.chooseBox(button);

        ToeStrategy expectedStrategy = new ToeStrategyExplicit(8, GameSymbol.O);
        verify(toeGame).chooseBox(any(ContentResolver.class), eq(expectedStrategy));
    }

    @Test
    public void shouldVibrateOnWin(){
        simulateOWins();
        verify(hapticFeedbackHelper).vibrate(HapticFeedbackHelper.VIBE_PATTERN_WIN, HapticFeedbackHelper.VIBE_PATTERN_NO_REPEAT);
    }

    @Test
    public void shouldDisplayWinnerOnWin(){
        simulateOWins();
        Assert.assertEquals("O WINS!", activity.messageTurnIndicatorValue.getText());
    }

    @Test
    public void shouldDisplayDrawIfNoOneWins(){
        int[] XIds = new int[]{1,2,4,5,9};
        int[] OIds = new int[]{3,6,7,8};
        activity.refreshUI(true, null, GameSymbol.O, XIds, OIds);
        Assert.assertEquals("DRAW!", activity.messageTurnIndicatorValue.getText());
    }

    @Test
    public void shouldDisplayCurrentTurnWhileGameInProgress(){
        int[] XIds = new int[]{1,2};
        int[] OIds = new int[]{3,6};
        activity.refreshUI(false, null, GameSymbol.O, XIds, OIds);
        Assert.assertEquals(GameSymbol.X.getValue(), activity.messageTurnIndicatorValue.getText());

        when(toeGame.getTurn()).thenReturn(GameSymbol.O);
        activity = Robolectric.buildActivity(GameActivity.class).create().start().resume().get();
        activity.refreshUI(false, null, GameSymbol.O, XIds, OIds);
        Assert.assertEquals(GameSymbol.O.getValue(), activity.messageTurnIndicatorValue.getText());
    }

    @Test
    public void shouldStartNewGame(){
        activity.newGameButton.performClick();
        Intent intent = shadowActivity.peekNextStartedActivityForResult().intent;

        assertThat(intent.getComponent()).isEqualTo(new ComponentName(activity, MainActivity.class));
    }

    private void simulateOWins() {
        int[] XIds = new int[]{4,5,6};
        int[] OIds = new int[]{1,9};
        activity.refreshUI(false, GameWinPattern.FOUR_ACROSS, GameSymbol.O, XIds, OIds);
    }

}
