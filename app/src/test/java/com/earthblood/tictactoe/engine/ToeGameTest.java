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
package com.earthblood.tictactoe.engine;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.earthblood.tictactoe.BuildConfig;
import com.earthblood.tictactoe.contentprovider.GameContentProvider;
import com.earthblood.tictactoe.helper.GameDatabaseHelper;
import com.earthblood.tictactoe.helper.PreferenceHelper;
import com.earthblood.tictactoe.strategy.ToeStrategy;
import com.earthblood.tictactoe.strategy.ToeStrategyExplicit;
import com.earthblood.tictactoe.util.GamePreference;
import com.earthblood.tictactoe.util.GameSymbol;
import com.earthblood.tictactoe.util.Skill;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(constants = BuildConfig.class)
@RunWith(RobolectricGradleTestRunner.class)
public class ToeGameTest {

    ToeGame toeGame;

    @Mock
    PreferenceHelper preferenceHelper;

    @Mock
    ContentResolver contentResolver;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        toeGame = new ToeGame(preferenceHelper);
    }
    @Test
    public void shouldSetSkill(){
        toeGame.setSkill(Skill.HARD);
        verify(preferenceHelper).putPreference(eq(Skill.HARD.getId()), eq(GamePreference.PREF_SKILL_ID.getKey()), eq(Context.MODE_PRIVATE));
    }
    @Test
    public void shouldSetNumberOfPlayers(){
        toeGame.setNumOfPlayers(2);
        verify(preferenceHelper).putPreference(eq(2), eq(GamePreference.PREF_NUMBER_OF_PLAYERS.getKey()), eq(Context.MODE_PRIVATE));
    }
    @Test
    public void shouldDefaultToXGoesFirst(){
        when(preferenceHelper.getPreference(eq(GamePreference.PREF_TURN.getKey()), eq(GameSymbol.X.getId()))).thenReturn(GameSymbol.X.getId());
        GameSymbol turn = toeGame.getTurn();
        assertEquals(GameSymbol.X, turn);
    }
    @Test
    public void shouldSetTurn(){
        toeGame.setTurn(GameSymbol.O);
        verify(preferenceHelper).putPreference(eq(GameSymbol.O.getId()), eq(GamePreference.PREF_TURN.getKey()), eq(Context.MODE_PRIVATE));

        when(preferenceHelper.getPreference(eq(GamePreference.PREF_TURN.getKey()), eq(GameSymbol.X.getId()))).thenReturn(GameSymbol.O.getId());
        GameSymbol turn = toeGame.getTurn();
        assertEquals(GameSymbol.O, turn);
    }
    @Test
    public void shouldChooseBox(){
        ToeStrategy strategy = simulateChooseBoxForO();

        ContentValues expectedValues = new ContentValues();
        expectedValues.put(GameDatabaseHelper.COLUMN_GAME_BOX_ID, strategy.getBoxId());
        expectedValues.put(GameDatabaseHelper.COLUMN_GAME_SYMBOL_ID, strategy.getSymbol().getId());

        verify(contentResolver).insert(eq(GameContentProvider.CONTENT_URI), eq(expectedValues));
    }
    @Test
    public void shouldAdvanceTurn(){
        simulateChooseBoxForO();
        verify(preferenceHelper).putPreference(eq(GameSymbol.X.getId()), eq(GamePreference.PREF_TURN.getKey()), eq(Context.MODE_PRIVATE));
    }
    @Test
    public void shouldResetGame(){
        ContentResolver contentResolver = Mockito.mock(ContentResolver.class);
        toeGame.reset(contentResolver);
        verify(contentResolver).delete(GameContentProvider.CONTENT_URI, null, null);
    }


    private ToeStrategy simulateChooseBoxForO() {
        ToeStrategy strategy = new ToeStrategyExplicit(3, GameSymbol.O);
        toeGame.chooseBox(contentResolver, strategy);
        return strategy;
    }



}
