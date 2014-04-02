package com.earthblood.tictactoe.activity;

import android.content.ContentResolver;
import android.content.Context;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.earthblood.tictactoe.R;
import com.earthblood.tictactoe.Robolectric.RobolectricGradleTestRunner;
import com.earthblood.tictactoe.engine.ToeGame;
import com.earthblood.tictactoe.helper.CoinTossHelper;
import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.Skill;
import com.google.inject.Inject;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.annotation.Config;

import static org.fest.assertions.api.ANDROID.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */

@Config(emulateSdk = 18)
@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest {

    MainActivity activity;

    @Inject Context context;
    @Inject ToeGame toeGame;
    @Inject CoinTossHelper coinTossHelper;

    @Before
    public void setup() {
        when(toeGame.getTurn()).thenReturn(GameSymbol.X);
        activity = Robolectric.buildActivity(MainActivity.class).create().start().resume().get();
    }

    @Test
    public void testInjection() throws Exception {
        assertEquals(toeGame,        activity.toeGame);
        assertEquals(coinTossHelper, activity.coinTossHelper);
    }

    @Test
    public void shouldNotBeNull() {
        assertThat(activity).isNotNull();

        Spinner spinner = (Spinner) activity.findViewById(R.id.skill_spinner);
        assertThat(spinner).isNotNull();
    }
    @Test
    public void shouldResetGameOnResume(){
        verify(toeGame).reset(any(ContentResolver.class));
    }
    @Test
    public void shouldSelectCorrectSkillLevelFromSkillSpinner(){
        Spinner spinner = (Spinner) activity.skillSpinner;

        spinner.setSelection(0);
        Object itemAtPosition = spinner.getItemAtPosition(0);
        Assert.assertEquals(Skill.EASY, (Skill)itemAtPosition);

        spinner.setSelection(1);
        itemAtPosition = spinner.getItemAtPosition(1);
        Assert.assertEquals(Skill.NORMAL, (Skill)itemAtPosition);

        spinner.setSelection(2);
        itemAtPosition = spinner.getItemAtPosition(2);
        Assert.assertEquals(Skill.HARD, (Skill)itemAtPosition);
    }

    @Test
    public void shouldSetNumberOfPlayersToOne(){
        RadioButton mockButton = Mockito.mock(RadioButton.class);
        when(mockButton.getId()).thenReturn(R.id.gameplay_one_player);
        when(mockButton.isChecked()).thenReturn(true);

        activity.setNumberOfPlayers(mockButton);
        verify(toeGame, times(1)).setNumOfPlayers(1);
    }
    @Test
    public void shouldSetNumberOfPlayersToTwo(){
        RadioButton mockButton = Mockito.mock(RadioButton.class);
        when(mockButton.getId()).thenReturn(R.id.gameplay_two_player);
        when(mockButton.isChecked()).thenReturn(true);

        activity.setNumberOfPlayers(mockButton);
        verify(toeGame, times(2)).setNumOfPlayers(2);
    }
    @Test
    public void shouldSetTurnXByDefault(){
        verify(toeGame, times(1)).setTurn(eq(GameSymbol.X));
    }
    @Test
    public void shouldSetTurnOnCoinTossX(){
        when(coinTossHelper.coinToss()).thenReturn(GameSymbol.X);
        Button button = Mockito.mock(Button.class);
        activity.coinToss(button);
        verify(toeGame, times(2)).setTurn(eq(GameSymbol.X));
    }
    @Test
    public void shouldSetMessageOnCoinTossX(){
        when(coinTossHelper.coinToss()).thenReturn(GameSymbol.X);
        Button button = Mockito.mock(Button.class);
        activity.coinToss(button);
        assertEquals("X goes first", activity.turnDisplay.getText());
    }
    @Test
    public void shouldSetTurnOnCoinTossO(){
        when(coinTossHelper.coinToss()).thenReturn(GameSymbol.O);
        Button button = Mockito.mock(Button.class);
        activity.coinToss(button);
        verify(toeGame).setTurn(eq(GameSymbol.O));
    }
    @Test
    public void shouldSetMessageOnCoinTossO(){
        when(coinTossHelper.coinToss()).thenReturn(GameSymbol.O);
        Button button = Mockito.mock(Button.class);
        activity.coinToss(button);
        assertEquals("O goes first", activity.turnDisplay.getText());
    }

}
