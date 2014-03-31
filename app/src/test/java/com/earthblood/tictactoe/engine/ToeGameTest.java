package com.earthblood.tictactoe.engine;

import android.content.Context;

import com.earthblood.tictactoe.Robolectric.RobolectricGradleTestRunner;
import com.earthblood.tictactoe.helper.PreferenceHelper;
import com.earthblood.tictactoe.util.Skill;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

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



}
