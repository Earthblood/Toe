package com.earthblood.tictactoe.engine;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.earthblood.tictactoe.Robolectric.RobolectricGradleTestRunner;
import com.earthblood.tictactoe.contentprovider.GameContentProvider;
import com.earthblood.tictactoe.helper.GameDatabaseHelper;
import com.earthblood.tictactoe.helper.PreferenceHelper;
import com.earthblood.tictactoe.strategy.ExplicitToeStrategy;
import com.earthblood.tictactoe.strategy.ToeStrategy;
import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.Skill;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricGradleTestRunner.class)
public class ToeGameTest {

    ToeGame toeGame;

    @Mock
    PreferenceHelper preferenceHelper;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        toeGame = new ToeGame(preferenceHelper);
    }

    @Test
    public void shouldSetSkill(){
        toeGame.setSkill(Skill.HARD);
        verify(preferenceHelper).putPreference(eq(Skill.HARD.getId()), eq(ToeGame.PREF_SKILL_ID), eq(Context.MODE_PRIVATE));
    }
    @Test
    public void shouldSetNumberOfPlayers(){
        toeGame.setNumOfPlayers(2);
        verify(preferenceHelper).putPreference(eq(2), eq(ToeGame.PREF_NUMBER_OF_PLAYERS), eq(Context.MODE_PRIVATE));
    }
    @Test
    public void shouldDefaultToXGoesFirst(){
        when(preferenceHelper.getPreference(eq(ToeGame.PREF_TURN), eq(GameSymbol.X.getId()))).thenReturn(GameSymbol.X.getId());
        GameSymbol turn = toeGame.getTurn();
        assertEquals(GameSymbol.X, turn);
    }
    @Test
    public void shouldSetTurn(){
        toeGame.setTurn(GameSymbol.O);
        verify(preferenceHelper).putPreference(eq(GameSymbol.O.getId()), eq(ToeGame.PREF_TURN), eq(Context.MODE_PRIVATE));

        when(preferenceHelper.getPreference(eq(ToeGame.PREF_TURN), eq(GameSymbol.X.getId()))).thenReturn(GameSymbol.O.getId());
        GameSymbol turn = toeGame.getTurn();
        assertEquals(GameSymbol.O, turn);
    }
    @Test
    public void shouldAdvanceTurn(){
        toeGame.advanceTurn(GameSymbol.X);
        verify(preferenceHelper).putPreference(eq(GameSymbol.O.getId()), eq(ToeGame.PREF_TURN), eq(Context.MODE_PRIVATE));
    }
    @Test
    public void shouldChooseBoxAndAdvanceTurn(){
        ContentResolver contentResolver = Mockito.mock(ContentResolver.class);

        ToeStrategy strategy = new ExplicitToeStrategy(3, GameSymbol.O);
        toeGame.chooseBox(contentResolver, strategy);

        ContentValues expectedValues = new ContentValues();
        expectedValues.put(GameDatabaseHelper.COLUMN_GAME_BOX_ID, strategy.getBoxId());
        expectedValues.put(GameDatabaseHelper.COLUMN_GAME_SYMBOL_ID, strategy.getSymbol().getId());

        verify(contentResolver).insert(eq(GameContentProvider.CONTENT_URI), eq(expectedValues));
        verify(preferenceHelper).putPreference(eq(GameSymbol.X.getId()), eq(ToeGame.PREF_TURN), eq(Context.MODE_PRIVATE));
    }
    @Test
    public void shouldResetGame(){
        ContentResolver contentResolver = Mockito.mock(ContentResolver.class);
        toeGame.reset(contentResolver);
        verify(contentResolver).delete(GameContentProvider.CONTENT_URI, null, null);
    }



}
