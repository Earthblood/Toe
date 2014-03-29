package com.earthblood.tictactoe.engine;

import android.content.Context;
import android.content.SharedPreferences;

import com.earthblood.tictactoe.application.EarthbloodApp;
import com.earthblood.tictactoe.util.Skill;

/**
 * @author John Piser developer@earthblood.com
 *         Copyright 2014.
 */
public class ToeGame {

    public static final String SHARED_PREF_GAME_SETTINGS = "SHARED_PREF_GAME_SETTINGS";

    public static final String PREF_SKILL_ID = "PREF_SKILL_ID";
    public static final String PREF_NUMBER_OF_PLAYERS = "PREF_NUMBER_OF_PLAYERS";

    private Skill skill;
    private int numOfPlayers;


    public ToeGame(){
        initSettings();
    }

    public void initSettings() {
        SharedPreferences prefs = EarthbloodApp.getContext().getSharedPreferences(SHARED_PREF_GAME_SETTINGS, Context.MODE_PRIVATE);
        this.skill        = Skill.byId(prefs.getInt(PREF_SKILL_ID, Skill.EASY.getId()));
        this.numOfPlayers = prefs.getInt(PREF_NUMBER_OF_PLAYERS, 1);
    }


    public void setSkill(Skill skill){
        this.skill = skill;
        putPreference(skill.getId(), SHARED_PREF_GAME_SETTINGS, PREF_SKILL_ID, Context.MODE_PRIVATE);
    }
    public void setNumOfPlayers(int numOfPlayers){
        this.numOfPlayers = numOfPlayers;
        putPreference(numOfPlayers, SHARED_PREF_GAME_SETTINGS, PREF_NUMBER_OF_PLAYERS, Context.MODE_PRIVATE);
    }

    public Skill getSkill(){
        return skill;
    }
    public int getNumOfPlayers(){
        return numOfPlayers;
    }



    private void putPreference(int value, String preferenceFileName, String preferenceName, int mode) {
        SharedPreferences prefs = EarthbloodApp.getContext().getSharedPreferences(preferenceFileName, mode);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(preferenceName,value);
        editor.commit();
    }

}
