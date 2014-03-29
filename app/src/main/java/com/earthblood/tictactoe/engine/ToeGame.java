package com.earthblood.tictactoe.engine;

import android.content.Context;
import android.content.SharedPreferences;

import com.earthblood.tictactoe.application.ToeApp;
import com.earthblood.tictactoe.helper.PreferenceHelper;
import com.earthblood.tictactoe.util.Skill;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */

@Singleton
public class ToeGame {

    public static final String PREF_SKILL_ID = "PREF_SKILL_ID";
    public static final String PREF_NUMBER_OF_PLAYERS = "PREF_NUMBER_OF_PLAYERS";

    @Inject
    private PreferenceHelper preferenceHelper;


    public void setSkill(Skill skill){
        preferenceHelper.putPreference(skill.getId(), PREF_SKILL_ID, Context.MODE_PRIVATE);
    }
    public void setNumOfPlayers(int numOfPlayers){
        preferenceHelper.putPreference(numOfPlayers, PREF_NUMBER_OF_PLAYERS, Context.MODE_PRIVATE);
    }

    public Skill getSkill(){
        return Skill.byId(preferenceHelper.getPreference(PREF_SKILL_ID, Skill.EASY.getId()));
    }
    public int getNumOfPlayers(){
        return preferenceHelper.getPreference(PREF_NUMBER_OF_PLAYERS, 1);
    }




}
